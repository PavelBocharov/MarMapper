package com.marcom.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [ENG] This annotation marks objects for which you want to set properties marked with an annotation {@link TranslateSource}.
 * [RU]  Данной аннотацией размечаются объекты, для которых нужно установить свойства, размеченные аннотацией {@link TranslateSource}.
 * Created by Marolok on 02.12.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface TranslateDestination {
	String[] value();
}
