package com.github.liaojiacan.spring.support.i18n.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD,PARAMETER })
@Retention(RUNTIME)
public @interface I18n {

}
