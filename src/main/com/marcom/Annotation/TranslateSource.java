package com.marcom.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * [ENG] The abstract indicates the data source for the translator.
 * [RU]  Аннотация обозначает источник данных для транслятора.
 * Created by Marolok on 02.12.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface TranslateSource {
    String[] value();
}
