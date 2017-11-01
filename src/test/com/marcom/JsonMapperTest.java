package com.marcom;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import com.marcom.Exception.MapperException;
import com.marcom.TestClasses.TestMapClassA;
import org.json.JSONObject;

public class JsonMapperTest {

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
		System.out.println("[JSON] Test fromJson(): from - " + testJson.toString());
		JsonMapper<TestMapClassA> mapper = new JsonMapper<>();
		TestMapClassA testMapClassA = new TestMapClassA(
				TestMapClassA.TEST_DESTINATION_A,
				TestMapClassA.TEST_DESTINATION_STRING
		);

		mapper.translateFromJson( testJson, testMapClassA, false );
		System.out.println("[JSON] Test fromJson(): to - " + testMapClassA.toString());
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
			System.out.println( "[JSON] Test fromJsonWithException(): Good exception. - " + me.getMessage() );
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
