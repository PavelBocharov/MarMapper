package com.marcom.Annotation;

import java.lang.annotation.*;

/**
 * Данной аннотацией размечаются объекты, для которых нужно установить свойства, размеченные аннотацией {@link TranslateSource}.
 * Created by Marolok on 02.12.16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface TranslateDestination {
    String[] value();
}
