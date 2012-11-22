package org.jboss.arquillian.drone.qunit.test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.drone.qunit.QUnitPage;
import org.jboss.arquillian.drone.qunit.QUnitResults;
import org.jboss.arquillian.graphene.spi.annotations.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class QUnitWrapTest {

    private static final String QUNIT_SRC = "src/test/qunit";

    @Page
    QUnitPage page;

    @Drone
    WebDriver driver;

    @Test
    public void testPage1() throws Exception {

        String location = new File(QUNIT_SRC + "/qunit-tests1.html").toURI().toURL().toString();
        QUnitResults results = page.startTestExecution(location).timeout(10, TimeUnit.SECONDS).waitForExecutionToFinish();

        if (results.containsFailures()) {
            throw new AssertionError(results);
        }
    }

    @Test
    public void testPage2() throws Exception {
        String location = new File(QUNIT_SRC + "/qunit-tests2.html").toURI().toURL().toString();
        QUnitResults results = page.startTestExecution(location).timeout(10, TimeUnit.SECONDS).waitForExecutionToFinish();

        Assert.assertEquals("There is one failure expected", 1, results.getFailedTests());

    }

}
