package com.acme.example.test.graphene;

import static org.jboss.arquillian.ajocado.Graphene.waitGui;

import java.net.URL;

import org.jboss.arquillian.ajocado.framework.GrapheneSelenium;
import org.jboss.arquillian.ajocado.javascript.JavaScript;
import org.jboss.arquillian.ajocado.waiting.ajax.JavaScriptCondition;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.example.test.Firefox10;

@RunWith(Arquillian.class)
public class ViewportSample {

    @Drone
    @Firefox10
    GrapheneSelenium graphene;

    @Test
    @Ignore("This test requires a local resource")
    public void viewportScroll() throws Exception {
        URL path = new URL("file:///home/kpiwko/docs/prezentace/arquillian-drone-rhdc2012/index.html");

        graphene.open(path);
        graphene.windowFocus();
        graphene.windowMaximize();

        Assert.assertEquals("Title slide is in view", "1", graphene.getEval(JavaScript
                .js("var cw = selenium.browserbot.getCurrentWindow(); cw.$('#title:in-viewport').length")));

        // focus while debugging
        graphene.windowFocus();
        graphene.windowMaximize();
        // press right key
        graphene.keyPressNative(39);

        waitGui.until(new JavaScriptCondition() {
            @Override
            public JavaScript getJavaScriptCondition() {
                return JavaScript
                        .js("var cw = selenium.browserbot.getCurrentWindow(); cw.$('#bugs-off:in-viewport').length > 0");
            }
        });

        Assert.assertEquals("Bugs-off slide is in view", "1", graphene.getEval(JavaScript
                .js("var cw = selenium.browserbot.getCurrentWindow(); cw.$('#bugs-off:in-viewport').length")));

    }
}
