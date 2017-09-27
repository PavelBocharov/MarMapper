package com.marcom.Annotation;

import java.lang.annotation.*;

/**
 * Аннотация обозначает источник данных для транслятора.
 * Created by Marolok on 02.12.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface TranslateSource {
    String[] value();
}
