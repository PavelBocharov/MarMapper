package com.marcom;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;
import com.marcom.Exception.MapperException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * [EN] Translater Map<->Object. Use keys in Map and annotation {@link TranslateSource}, {@link TranslateDestination} for link.
 * [RU] Трансятор Map<->Object. Использует ключи из Map и аннотации {@link TranslateSource}, {@link TranslateDestination} для связи.
 */
public class MapMapper<C> extends MapperMethods {

    /**
     * [EN] Translate Map to Object.
     * [RU] Переводит Map в объект.
     * @param source Map.
     * @param destination object.
     * @param force skip don't pass keys. (Пропускает ошибки ненайденых ключей).
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws MapperException
     */
    public void translateFromMap(Map<String, Object> source, C destination, boolean force)
            throws InvocationTargetException, IllegalAccessException, MapperException {
        Map<String, Method> methodMap = this.getDestinationMap( destination );
        Set<String> annotationKey = methodMap.keySet();
        Set<String> intersectionKey;
        if ( force ) {
            intersectionKey = intersectionKeys(source.keySet(), annotationKey);
        }
        else {
            intersectionKey = source.keySet();
        }

        for ( String s : intersectionKey ) {
            Object o = source.get( s );
            Method destinationMethod = methodMap.get( s );
            if ( destinationMethod != null && o != null ) {
                destinationMethod.invoke( destination, o );
            }
            else {
                if ( !force ) {
                    Set<String> values = notIntersection( source.keySet(), annotationKey );
                    throw new MapperException(
                            "Mapped by value = " + values.toString() + ", don't pass in source.",
                            values
                    );
                }
            }
        }
    }

    /**
     * [EN] Translate Object to Map.
     * [RU] Переводит объект в Map.
     * @param source Object.
     * @return Map.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Map<String, Object> translateToMap(C source)
            throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> destination = new HashMap<>();
        Map<String, Method> sourceMapMethods = getSourceMap( source );
        Set<String> sourceKeys = sourceMapMethods.keySet();

        for ( String sourceKey : sourceKeys ) {
            Method sourceMethod = sourceMapMethods.get( sourceKey );
            destination.put( sourceKey, sourceMethod.invoke( source ) );
        }

        return destination;
    }

}
