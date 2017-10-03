package com.marcom;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;
import com.marcom.Exception.MapperException;

/**
 * Самописный аналог ModelMapper`a. Использует аннотации {@link TranslateSource}
 * и {@link TranslateDestination} для размечения геттеров/сеттеров.
 * Created by Marolok on 02.12.16.
 */
public class Mapper<S, D> {

	/**
	 * Вызывает сеттеры у destination по аннотации TranslateDestination, записывая данные из геттеров source по аннотации TranslateSource.
	 *
	 * @param source
	 * @param destination
	 *
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void translate(S source, D destination, boolean force) throws MapperException {
		Map<String, Method> sourceMap = getSourceMap( source );
		Map<String, Method> destinationMap = getDestinationMap( destination );
		for ( String s : destinationMap.keySet() ) {
			Method destinationMethod = destinationMap.get( s );
			Method sourceMethod = sourceMap.get( s );
			if ( destinationMethod != null && sourceMethod != null ) {
				try {
					destinationMethod.invoke( destination, sourceMethod.invoke( source ) );
				}
				catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			else {
				// TODO Return all non detected annotation.
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

	private Set notIntersection(Set<String> sourceSet, Set<String> destinationSet) {
		Set<String> result = new HashSet<>();
		for ( String s : sourceSet ) {
			if ( !destinationSet.contains( s ) ) {
				result.add( s );
			}
		}
		for ( String s : destinationSet ) {
			if ( !sourceSet.contains( s ) && !result.contains( s ) ) {
				result.add( s );
			}
		}
		return result;
	}

	private Map<String, Method> getSourceMap(S source) {
		return getMapMethodByAnnotation( source.getClass().getMethods(), TranslateSource.class.getSimpleName() );
	}

	private Map<String, Method> getDestinationMap(D destination) {
		return getMapMethodByAnnotation(
				destination.getClass().getMethods(),
				TranslateDestination.class.getSimpleName()
		);
	}

	private Map<String, Method> getMapMethodByAnnotation(Method[] methods, String annotationName) {
		Map<String, Method> map = new HashMap<>();
		Pattern pattern = Pattern.compile( "^.+value=\\[(.+)\\][)]$" );
		Matcher matcher;
		for ( Method method : methods ) {
			for ( Annotation annotation : method.getAnnotations() ) {
				if ( annotation.toString().contains( annotationName ) ) {
					matcher = pattern.matcher( annotation.toString() );
					while ( matcher.find() ) {
						String s = matcher.group( 1 ).replace( ",", "" );
						Scanner scanner = new Scanner( s );
						while ( scanner.hasNext() ) {
							map.put( scanner.next(), method );
						}
					}
				}
			}
		}
		return map;
	}
}

