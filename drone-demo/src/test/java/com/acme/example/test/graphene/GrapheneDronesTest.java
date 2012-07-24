package com.acme.example.test.graphene;

import java.net.URL;

import org.jboss.arquillian.ajocado.framework.GrapheneSelenium;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.example.test.Deployments;
import com.acme.example.test.Firefox9;

@RunWith(Arquillian.class)
@Ignore
public class GrapheneDronesTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return Deployments.createDeployment();
    }

    @Test
    public void simpleGrapheneTest(@Drone GrapheneSelenium graphene) {
        graphene.open(contextPath);
        graphene.waitForPageToLoad();
        Assert.assertTrue(true);
    }

    @Test
    public void simpleGrapheneFirefox9Test(@Drone @Firefox9 GrapheneSelenium graphene) {
        graphene.open(contextPath);
        graphene.waitForPageToLoad();
        Assert.assertTrue(true);
    }
}
