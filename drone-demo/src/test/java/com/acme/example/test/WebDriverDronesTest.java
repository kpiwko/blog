package com.acme.example.test;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.MavenImporter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Arquillian.class)
public class WebDriverDronesTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return ShrinkWrap.create(MavenImporter.class).loadEffectivePom("pom.xml").importBuildOutput().as(WebArchive.class);
    }

    @Test
    public void simpleWebdriverTest(@Drone FirefoxDriver webdriver) {
        webdriver.get(contextPath.toString());
        Assert.assertTrue(true);
    }

    @Test
    public void simpleWebdriverChromeTest(@Drone ChromeDriver webdriver) {
        webdriver.get(contextPath.toString());
        Assert.assertTrue(true);
    }
}
