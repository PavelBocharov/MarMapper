package com.marcom.TestClasses;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;

public class TestMapClassA {
	public static final int TEST_SOURCE_A = 1;
	public static final int TEST_DESTINATION_A = 2;
	public static final String TEST_SOURCE_STRING = "TestMapClass_A1";
	public static final String TEST_DESTINATION_STRING = "TestMapClass_A2";

	private int a;
	private String string;

	public TestMapClassA(int a, String string) {
		this.a = a;
		this.string = string;
	}

	@TranslateSource({ "TestMapClass_A", "TestMapClass_B" })
	public int getA() {
		return a;
	}

	@TranslateDestination("TestMapClass_A")
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

	@Override
	public String toString() {
		return "TestMapClassA{" +
				"a=" + a +
				", string='" + string + '\'' +
				'}';
	}
}
