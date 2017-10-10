package com.marcom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;
import com.marcom.Exception.MapperException;

/**
 * [ENG] The analogue analog of ModelMapper. Uses annotations {@link TranslateSource} and {@link TranslateDestination} to tag getters / setters.
 * [RU]  Самописный аналог ModelMapper`a. Использует аннотации {@link TranslateSource} и {@link TranslateDestination} для размечения геттеров/сеттеров.
 * Created by Marolok on 02.12.16.
 */
public class Mapper<S, D> extends MapperMethods<S, D> {

	/**
	 * [ENG] Call setters at destination by the TranslateDestination annotation, writing data from source getters according to the TranslateSource annotation.
	 * [RU]  Вызывает сеттеры у destination по аннотации TranslateDestination, записывая данные из геттеров source по аннотации TranslateSource.
	 *
	 * @param source Source, откуда копирует значения при транслировании.
	 * @param destination Destination, куда копирует значения при транслировании.
	 * @param force пропускать не найденные аннотации, skip annotations not found.
	 * @throws MapperException см {@link MapperException}
	 * @throws InvocationTargetException см {@link InvocationTargetException}
	 * @throws IllegalAccessException см {@link IllegalAccessException}
	 */
	public void translate(S source, D destination, boolean force)
			throws MapperException, InvocationTargetException, IllegalAccessException {
		Map<String, Method> sourceMap = getSourceMap( source );
		Map<String, Method> destinationMap = getDestinationMap( destination );
		for ( String s : destinationMap.keySet() ) {
			Method destinationMethod = destinationMap.get( s );
			Method sourceMethod = sourceMap.get( s );
			if ( destinationMethod != null && sourceMethod != null ) {
				destinationMethod.invoke( destination, sourceMethod.invoke( source ) );
			}
			else {
				if ( !force ) {
					Set<String> values = notIntersection( sourceMap.keySet(), destinationMap.keySet() );
					throw new MapperException(
							"Mapped by value = " + values.toString() + ", don't pass in source.",
							values
					);
				}
			}
		}
	}

}

