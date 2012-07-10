package com.acme.example.test.defaultselenium;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.example.test.Deployments;
import com.thoughtworks.selenium.DefaultSelenium;

@RunWith(Arquillian.class)
public class DefaultSeleniumDronesTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return Deployments.createDeployment();
    }

    @Test
    public void simpleDefaultSeleniumTest(@Drone DefaultSelenium selenium) {
        selenium.open(contextPath.toString());
        selenium.waitForPageToLoad("5000");
        Assert.assertTrue(true);
    }

}
