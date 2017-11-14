package com.marcom;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;
import com.marcom.Exception.MapperException;
import org.json.JSONObject;

/**
 * [EN] Translater JSON<->Object. Use keys in JSON and annotation {@link TranslateSource}, {@link TranslateDestination} for link.
 * [RU] Трансятор JSON<->Object. Использует ключи из JSON и аннотации {@link TranslateSource}, {@link TranslateDestination} для связи.
 * Created by Marolok on 02.11.17.
 */
public class JsonMapper<C> extends MapperMethods {

	/**
	 * [EN] Translate JSON to Object.
	 * [RU] Переводит JSON в объект.
	 * @param source JSON.
	 * @param destination object.
	 * @param force skip don't pass keys. (Пропускает ошибки ненайденых ключей).
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws MapperException
	 */
	public void translateFromJson(JSONObject source, C destination, boolean force)
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
	 * [EN] Translate Object to JSON.
	 * [RU] Переводит объект в JSON.
	 * @param source Object.
	 * @return JSON.
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
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
