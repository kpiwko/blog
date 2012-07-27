package com.acme.example.test.webdriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.acme.example.test.Deployments;
import com.google.common.io.Files;

@RunWith(Arquillian.class)
public class WebDriverDronesTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        WebArchive war = Deployments.createDeployment();

        return war;
    }

    @Test
    // FIXME temporary workaround for ARQ-1047
    public void simpleWebdriverTest(@Drone RemoteWebDriver webdriver) throws Exception {
        webdriver.get(contextPath.toString());

        takeScreenshot(webdriver, "opened-page");
        webdriver.findElement(By.id("name")).sendKeys("Samuel");
        takeScreenshot(webdriver, "after-name");
        webdriver.findElement(By.id("email")).sendKeys("samuel@vimes.dw");
        takeScreenshot(webdriver, "after-email");
        webdriver.findElement(By.id("phoneNumber")).sendKeys("1234567890");
        takeScreenshot(webdriver, "after-phone");
        webdriver.findElement(By.id("register")).submit();

        // FIXME with Graphene, you can wait for a request
        Assert.assertTrue(true);
        takeScreenshot(webdriver, "after-submit");
    }

    private void takeScreenshot(RemoteWebDriver driver, String testCaseName) {
        try {
            // RemoteWebDriver does not implement the TakesScreenshot class
            // if the driver does have the Capabilities to take a screenshot
            // then Augmenter will add the TakesScreenshot methods to the instance
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot, new File("target/" + testCaseName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
