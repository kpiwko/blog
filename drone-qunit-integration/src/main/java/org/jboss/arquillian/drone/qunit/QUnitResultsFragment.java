package org.jboss.arquillian.drone.qunit;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.graphene.spi.annotations.Root;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class QUnitResultsFragment {

    @Root
    WebElement root;

    // ARQGRA-192
    // @FindBy(xpath = "li[@class='fail']/strong")
    // List<WebElement> failedTestCases;

    // ARQGRA-192
    // @FindBy(xpath = "li[@class='pass']/strong")
    // List<WebElement> passedTestCases;

    public List<String> getFailedCasesNames() {
        return getCasesNames(root.findElements(By.xpath("li[@class='fail']/strong")));
    }

    public List<String> getPassedCasesNames() {
        return getCasesNames(root.findElements(By.xpath("li[@class='passed']/strong")));
    }

    private List<String> getCasesNames(List<WebElement> list) {
        List<String> names = new ArrayList<String>();
        for (WebElement e : list) {
            names.add(e.getText());
        }
        return names;
    }

}
