package com.marcom;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import com.marcom.Annotation.TranslateDestination;
import com.marcom.Annotation.TranslateSource;
import com.marcom.Exception.MapperException;
import org.json.JSONObject;

public class JsonMapperTest {

	class TestMapClassA {
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
	}

	@Test
	public void toJson() throws InvocationTargetException, IllegalAccessException {
		System.out.println( "[JSON] Test toJson()." );
		TestMapClassA testMapClassA = new TestMapClassA(
				TestMapClassA.TEST_SOURCE_A,
				TestMapClassA.TEST_SOURCE_STRING
		);
		JSONObject testJson = new JSONObject(
				"{\"TestMapClass_String\":\"TestMapClass_A1\",\"TestMapClass_A\":1,\"TestMapClass_B\":1}" );

		JsonMapper<TestMapClassA> mapper = new JsonMapper<>();
		JSONObject jsonObject = mapper.translateToJson( testMapClassA );

		Assert.assertEquals( testJson.toString(), jsonObject.toString() );
	}

	@Test
	public void fromJson() throws IllegalAccessException, MapperException, InvocationTargetException {
		System.out.println( "[JSON] Test fromJson()." );
		JSONObject testJson = new JSONObject(
				"{\"TestMapClass_String\":\"TestMapClass_A1\",\"TestMapClass_A\":1}" );

		JsonMapper<TestMapClassA> mapper = new JsonMapper<>();
		TestMapClassA testMapClassA = new TestMapClassA(
				TestMapClassA.TEST_DESTINATION_A,
				TestMapClassA.TEST_DESTINATION_STRING
		);

		mapper.translateFromJson( testJson, testMapClassA, false );

		Assert.assertEquals( TestMapClassA.TEST_SOURCE_A, testMapClassA.getA() );
		Assert.assertEquals( TestMapClassA.TEST_SOURCE_STRING, testMapClassA.getString() );
	}

	@Test
	public void fromJsonWithException() throws IllegalAccessException, MapperException, InvocationTargetException {
		System.out.println( "[JSON] Test fromJsonWithException()." );
		JSONObject testJson = new JSONObject(
				"{\"TestMapClass_String\":\"TestMapClass_A1\",\"test\":1}" );

		JsonMapper<TestMapClassA> mapper = new JsonMapper<>();
		TestMapClassA testMapClassA = new TestMapClassA( -2, "test" );
		try {
			mapper.translateFromJson( testJson, testMapClassA, false );
		}
		catch (MapperException me) {
			// It's good
			System.out.println( "Good exception. - " + me.getMessage() );
		}
	}

	@Test
	public void fromJsonForce() throws IllegalAccessException, MapperException, InvocationTargetException {
		System.out.println( "[JSON] Test fromJsonForce()." );
		JSONObject testJson = new JSONObject(
				"{\"TestMapClass_String\":\"TestMapClass_A1\",\"TestMapClass_A\":1}" );

		JsonMapper<TestMapClassA> mapper = new JsonMapper<>();
		TestMapClassA testMapClassA = new TestMapClassA(
				TestMapClassA.TEST_DESTINATION_A,
				TestMapClassA.TEST_DESTINATION_STRING
		);

		mapper.translateFromJson( testJson, testMapClassA, true );

		Assert.assertEquals( TestMapClassA.TEST_SOURCE_A, testMapClassA.getA() );
		Assert.assertEquals( TestMapClassA.TEST_SOURCE_STRING, testMapClassA.getString() );
	}

	@Test
	public void fromJsonWithExceptionForce() throws IllegalAccessException, MapperException, InvocationTargetException {
		System.out.println( "[JSON] Test fromJsonWithExceptionForce()." );
		JSONObject testJson = new JSONObject(
				"{\"TestMapClass_String\":\"TestMapClass_A1\",\"test\":1}" );

		JsonMapper<TestMapClassA> mapper = new JsonMapper<>();
		TestMapClassA testMapClassA = new TestMapClassA( -2, "test" );
		mapper.translateFromJson( testJson, testMapClassA, true );

		Assert.assertEquals( TestMapClassA.TEST_SOURCE_STRING, testMapClassA.getString() );
	}
}
