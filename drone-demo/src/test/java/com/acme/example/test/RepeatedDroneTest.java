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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class RepeatedDroneTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return ShrinkWrap.create(MavenImporter.class).loadEffectivePom("pom.xml").importBuildOutput().as(WebArchive.class);
    }

    @Test
    public void simpleWebdriverTest(@Drone WebDriver webdriver) {
        webdriver.get(contextPath.toString());

        webdriver.findElement(By.id("name")).sendKeys("Samuel");
        webdriver.findElement(By.id("email")).sendKeys("samuel@vimes.dw");
        webdriver.findElement(By.id("phoneNumber")).sendKeys("1234567890");
        webdriver.findElement(By.id("register")).submit();

        // FIXME with Ajocado, you can wait for a request
        Assert.assertTrue(true);
    }

}
