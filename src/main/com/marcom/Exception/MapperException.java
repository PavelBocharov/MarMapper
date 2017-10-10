package com.marcom.Exception;

import java.util.Set;

public class MapperException extends Exception {
	private Set<String> values;

	public MapperException(String message, Set<String> values) {
		super( message );
		this.values = values;
	}

	public Set<String> getValues() {
		return values;
	}
}
