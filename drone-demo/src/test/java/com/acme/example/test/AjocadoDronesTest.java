package com.acme.example.test;

import java.net.URL;

import org.jboss.arquillian.ajocado.framework.AjaxSelenium;
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

@RunWith(Arquillian.class)
public class AjocadoDronesTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return ShrinkWrap.create(MavenImporter.class).loadEffectivePom("pom.xml").importBuildOutput().as(WebArchive.class);
    }

    @Test
    public void simpleAjocadoTest(@Drone AjaxSelenium ajocado) {
        ajocado.open(contextPath);
        ajocado.waitForPageToLoad();
        Assert.assertTrue(true);
    }

    @Test
    public void simpleAjocadoFirefox9Test(@Drone @Firefox9 AjaxSelenium ajocado) {
        ajocado.open(contextPath);
        ajocado.waitForPageToLoad();
        Assert.assertTrue(true);
    }
}
