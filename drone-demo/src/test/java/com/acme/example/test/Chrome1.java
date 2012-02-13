package com.acme.example.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.arquillian.drone.api.annotation.Qualifier;

@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Chrome1 {

}
