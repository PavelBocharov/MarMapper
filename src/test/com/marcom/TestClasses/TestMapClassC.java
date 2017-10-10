package com.marcom.TestClasses;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;

public class TestMapClassC {
	public static final String TEST_SOURCE_STRING = "TestMapClass_C5";
	public static final String TEST_DESTINATION_STRING = "TestMapClass_C6";

	private String string;

	public TestMapClassC(String string) {
		this.string = string;
	}

	@TranslateSource({ "TestMapClass_String" })
	public String getString() {
		return string;
	}

	@TranslateDestination("TestMapClass_String")
	public void setString(String string) {
		this.string = string;
	}
}
