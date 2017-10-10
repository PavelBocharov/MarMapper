package com.marcom;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.marcom.Exception.MapperException;
import com.marcom.TestClasses.TestMapClassA;
import com.marcom.TestClasses.TestMapClassB;
import com.marcom.TestClasses.TestMapClassC;

public class BiMappertTest {

	@Test
	public void bitranslateAnnotationClassAClassA()
			throws MapperException, InvocationTargetException, IllegalAccessException {
		TestMapClassA sourceA = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		TestMapClassA destinationA = new TestMapClassA(
				TestMapClassA.TEST_DESTINATION_A,
				TestMapClassA.TEST_DESTINATION_STRING
		);

		System.out.println( "[BiMapper] Test annotation bitranslate (classA -> classA)." );
		BiMapper<TestMapClassA, TestMapClassA> mapperAA = new BiMapper<>();
		mapperAA.translate( sourceA, destinationA, false );
		Assert.assertEquals( "Translate pojo is failed.", TestMapClassA.TEST_SOURCE_A, destinationA.getA() );
		Assert.assertEquals(
				"Translate object is failed.",
				TestMapClassA.TEST_SOURCE_STRING,
				destinationA.getString()
		);

		System.out.println( "[BiMapper] Test annotation bitranslate (classA <- classA)." );
		sourceA = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		destinationA = new TestMapClassA( TestMapClassA.TEST_DESTINATION_A, TestMapClassA.TEST_DESTINATION_STRING );
		mapperAA.translateBack( sourceA, destinationA, false );
		Assert.assertEquals( "Translate pojo is failed.", sourceA.getA(), TestMapClassA.TEST_DESTINATION_A );
		Assert.assertEquals(
				"Translate object is failed.",
				sourceA.getString(),
				TestMapClassA.TEST_DESTINATION_STRING
		);
	}

	@Test
	public void bitranslateAnnotationClassAClassB()
			throws MapperException, InvocationTargetException, IllegalAccessException {
		System.out.println( "[BiMapper] Test annotation bitranslate (classA -> classB)." );
		TestMapClassA source = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		TestMapClassB destination = new TestMapClassB(
				TestMapClassB.TEST_DESTINATION_A,
				TestMapClassB.TEST_DESTINATION_STRING
		);

		BiMapper<TestMapClassA, TestMapClassB> mapper = new BiMapper<>();
		mapper.translate( source, destination, false );
		Assert.assertEquals( "[BiMapper] Translate pojo is failed.", TestMapClassA.TEST_SOURCE_A, destination.getA() );
		Assert.assertEquals(
				"[BiMapper] Translate object is failed.",
				TestMapClassA.TEST_SOURCE_STRING,
				destination.getString()
		);

		System.out.println( "[BiMapper] Test annotation bitranslate (classA <- classB)." );
		source = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		destination = new TestMapClassB( TestMapClassB.TEST_DESTINATION_A, TestMapClassB.TEST_DESTINATION_STRING );
		try {
			mapper.translateBack( source, destination, false );
		}
		catch (MapperException me) {
			Set<String> set = new HashSet<String>();
			set.add( "TestMapClass_A" );
			set.add( "TestMapClass_B" );
			Assert.assertEquals(
					"Mapped by values = [TestMapClass_A, TestMapClass_B], don't pass in source.",
					true,
					AssertTest.assertSet( me.getValues(), set )
			);
		}
	}

	@Test
	public void bitranslateAnnotationForMiniClass()
			throws MapperException, InvocationTargetException, IllegalAccessException {
		System.out.println( "[BiMapper] Test annotation for mini class (classA -> classC)." );
		TestMapClassA source = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		TestMapClassC destination = new TestMapClassC( TestMapClassC.TEST_DESTINATION_STRING );

		BiMapper<TestMapClassA, TestMapClassC> mapper = new BiMapper<>();
		mapper.translate( source, destination, false );

		Assert.assertEquals( "Translate object is failed.", TestMapClassA.TEST_SOURCE_STRING, destination.getString() );

		System.out.println( "[BiMapper] Test annotation for mini class (classA <- classC)." );
		source = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		destination = new TestMapClassC( TestMapClassC.TEST_DESTINATION_STRING );
		try {
			mapper.translateBack( source, destination, false );
		}
		catch (MapperException me) {
			Assert.assertEquals( "Mapped by value = [TestMapClass_A], don't pass in source.", me.getMessage() );
		}
	}
}
