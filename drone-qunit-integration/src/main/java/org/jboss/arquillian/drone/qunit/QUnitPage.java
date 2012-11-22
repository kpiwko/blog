package org.jboss.arquillian.drone.qunit;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class QUnitPage {

    @FindBy(className = "failed")
    WebElement failedCasesCount;

    @FindBy(className = "passed")
    WebElement passedCasesCount;

    @FindBy(className = "total")
    WebElement totalCasesCount;

    @FindBy(id = "qunit-tests")
    QUnitResultsFragment results;

    private URL pageUrl;

    private long timeout = 60;

    private TimeUnit unit = TimeUnit.SECONDS;

    public QUnitPage startTestExecution(String url) {

        try {
            this.pageUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid URL {0}", url));
        }
        // open page
        GrapheneContext.getProxy().get(pageUrl.toString());
        return this;
    }

    public QUnitPage timeout(long timeout, TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
        return this;
    }

    public QUnitResults waitForExecutionToFinish() {
        getExecutionResult(totalCasesCount);

        return new QUnitResults(pageUrl, getTotalTestCount(), getFailedTestCount(), getPassedTestCount(),
                results.getPassedCasesNames(), results.getFailedCasesNames());
    }

    public int getPassedTestCount() {
        return getExecutionResult(passedCasesCount);
    }

    public int getFailedTestCount() {
        return getExecutionResult(failedCasesCount);
    }

    public int getTotalTestCount() {
        return getExecutionResult(totalCasesCount);
    }

    public List<String> getPassedTests() {
        return results.getPassedCasesNames();
    }

    public List<String> getFailedTests() {
        return results.getFailedCasesNames();
    }

    private int getExecutionResult(WebElement element) {
        if (Graphene.waitModel().withTimeout(timeout, unit).until(Graphene.element(element).isPresent())) {
            return Integer.valueOf(element.getText());
        }
        throw new QUnitExecutionException(MessageFormat.format(
                "Unable to execute QUnit tests at {0} within {1}{2}, timeouted.",
                pageUrl, timeout, unit));
    }
}
