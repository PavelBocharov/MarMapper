package com.marcom;

import java.lang.annotation.Annotation;
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

public class MapperMethods<S, D> {

	protected MapperMethods(){

	}

	protected Set notIntersection(Set<String> sourceSet, Set<String> destinationSet) {
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

	protected Map<String, Method> getSourceMap(S source) {
		return getMapMethodByAnnotation( source.getClass().getMethods(), TranslateSource.class.getSimpleName() );
	}

	protected Map<String, Method> getDestinationMap(D destination) {
		return getMapMethodByAnnotation(
				destination.getClass().getMethods(),
				TranslateDestination.class.getSimpleName()
		);
	}

	protected Map<String, Method> getMapMethodByAnnotation(Method[] methods, String annotationName) {
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
