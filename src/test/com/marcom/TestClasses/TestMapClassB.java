package com.marcom.TestClasses;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;

public class TestMapClassB {
	public static final int TEST_SOURCE_A = 3;
	public static final int TEST_DESTINATION_A = 4;
	public static final String TEST_SOURCE_STRING = "TestMapClass_B3";
	public static final String TEST_DESTINATION_STRING = "TestMapClass_B4";

	private int a;
	private String string;

	public TestMapClassB(int a, String string) {
		this.a = a;
		this.string = string;
	}

	@TranslateSource({ "TestMapClass_B" })
	public int getA() {
		return a;
	}

	@TranslateDestination("TestMapClass_B")
	public void setA(int a) {
		this.a = a;
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
