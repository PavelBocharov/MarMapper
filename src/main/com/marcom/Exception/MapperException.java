package com.marcom.Exception;

import java.util.Set;

/**
 * [ENG] Exception mapping with a list of annotations that were not found.
 * [RU]  Exception маппинга со списком аннотаций, которые не нашлись.
 * Created by Marolok.
 */
public class MapperException extends Exception {
	private Set<String> values;

	/**
	 * [ENG/RU]  Constructor
	 * @param message сообщение.
	 * @param values [RU] не найденные аннотации, [EN] list of annotations that were not found.
	 */
	public MapperException(String message, Set<String> values) {
		super( message );
		this.values = values;
	}

	public Set<String> getValues() {
		return values;
	}
}
