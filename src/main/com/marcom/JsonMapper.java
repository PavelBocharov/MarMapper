package com.marcom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.marcom.Exception.MapperException;
import org.json.JSONObject;

public class JsonMapper<C> extends MapperMethods {

	public void translateFromJson(JSONObject source, C destination, boolean force)
			throws InvocationTargetException, IllegalAccessException, MapperException {
		Map<String, Method> methodMap = this.getDestinationMap( destination );
		Set<String> annotationKey = methodMap.keySet();
		Set<String> intersectionKey = new HashSet<>();
		if ( force ) {
			for ( String s : source.keySet() ) {
				if ( annotationKey.contains( s ) ) {
					intersectionKey.add( s );
				}
			}
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

	public JSONObject translateToJson(C source)
			throws InvocationTargetException, IllegalAccessException {
		JSONObject destination = new JSONObject();
		Map<String, Method> sourceMapMethods = getSourceMap( source );
		Set<String> sourceKeys = sourceMapMethods.keySet();

		for ( String sourceKey : sourceKeys ) {
			Method sourceMethod = sourceMapMethods.get( sourceKey );
			destination.put( sourceKey, sourceMethod.invoke( source ) );
		}

		return destination;
	}

}
