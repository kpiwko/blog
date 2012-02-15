package com.acme.example.test;

import static org.jboss.arquillian.ajocado.Ajocado.waitGui;

import java.net.URL;

import org.jboss.arquillian.ajocado.framework.AjaxSelenium;
import org.jboss.arquillian.ajocado.javascript.JavaScript;
import org.jboss.arquillian.ajocado.waiting.ajax.JavaScriptCondition;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ViewportSample {

    @Drone
    @Firefox10
    AjaxSelenium ajocado;

    @Test
    public void viewportScroll() throws Exception {
        URL path = new URL("file:///home/kpiwko/docs/prezentace/arquillian-drone-rhdc2012/index.html");

        ajocado.open(path);
        ajocado.windowFocus();
        ajocado.windowMaximize();

        Assert.assertEquals("Title slide is in view", "1", ajocado.getEval(JavaScript
                .js("var cw = selenium.browserbot.getCurrentWindow(); cw.$('#title:in-viewport').length")));

        // focus while debugging
        ajocado.windowFocus();
        ajocado.windowMaximize();
        // press right key
        ajocado.keyPressNative(39);

        waitGui.until(new JavaScriptCondition() {
            @Override
            public JavaScript getJavaScriptCondition() {
                return JavaScript
                        .js("var cw = selenium.browserbot.getCurrentWindow(); cw.$('#bugs-off:in-viewport').length > 0");
            }
        });

        Assert.assertEquals("Bugs-off slide is in view", "1", ajocado.getEval(JavaScript
                .js("var cw = selenium.browserbot.getCurrentWindow(); cw.$('#bugs-off:in-viewport').length")));

    }
}
