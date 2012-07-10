package com.acme.example.test.webdriver;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.acme.example.test.Deployments;

@RunWith(Arquillian.class)
public class WebDriverDronesTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return Deployments.createDeployment();
    }

    @Test
    @Ignore("ARQ-1023")
    public void simpleWebdriverTest(@Drone FirefoxDriver webdriver) {
        webdriver.get(contextPath.toString());
        Assert.assertTrue(true);
    }

    @Test
    @Ignore("ARQ-1023")
    public void simpleWebdriverChromeTest(@Drone ChromeDriver webdriver) {
        webdriver.get(contextPath.toString());
        Assert.assertTrue(true);
    }
}
