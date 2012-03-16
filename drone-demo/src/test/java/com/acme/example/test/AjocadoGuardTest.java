package com.acme.example.test;

import static org.jboss.arquillian.ajocado.Ajocado.guardXhr;
import static org.jboss.arquillian.ajocado.Ajocado.id;

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
public class AjocadoGuardTest {

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> getApplicationDeployment() {
        return ShrinkWrap.create(MavenImporter.class).loadEffectivePom("pom.xml").importBuildOutput().as(WebArchive.class);
    }

    @Test
    public void xhrAjocadoTest(@Drone AjaxSelenium ajocado) {
        ajocado.open(contextPath);

        ajocado.type(id("name"), "Samuel Vimes");
        ajocado.type(id("email"), "samuel@vimes.dw");
        ajocado.type(id("phoneNumber"), "1234567890");

        // see the guard? we are waiting for an XHR request here
        guardXhr(ajocado).click(id("register"));
        
        Assert.assertTrue("Vimes was registered", ajocado.isTextPresent("Samuel Vimes"));
    }

}
