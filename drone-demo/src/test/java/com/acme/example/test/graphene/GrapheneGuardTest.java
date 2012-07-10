package com.acme.example.test.graphene;

import static org.jboss.arquillian.ajocado.Graphene.guardXhr;
import static org.jboss.arquillian.ajocado.Graphene.id;

import java.net.URL;

import org.jboss.arquillian.ajocado.framework.GrapheneSelenium;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.example.test.Deployments;

@RunWith(Arquillian.class)
public class GrapheneGuardTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return Deployments.createDeployment();
    }

    @Test
    public void xhrGrapheneTest(@Drone GrapheneSelenium graphene) {
        graphene.open(contextPath);

        graphene.type(id("name"), "Samuel Vimes");
        graphene.type(id("email"), "samuel@vimes.dw");
        graphene.type(id("phoneNumber"), "1234567890");

        // see the guard? we are waiting for an XHR request here
        guardXhr(graphene).click(id("register"));

        Assert.assertTrue("Vimes was registered", graphene.isTextPresent("Samuel Vimes"));
    }

}
