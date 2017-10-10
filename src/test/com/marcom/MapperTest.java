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

public class MapperTest {

	@Test
	public void translateAnnotationClassAClassA()
			throws MapperException, InvocationTargetException, IllegalAccessException {
		TestMapClassA sourceA = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		TestMapClassA destinationA = new TestMapClassA(
				TestMapClassA.TEST_DESTINATION_A,
				TestMapClassA.TEST_DESTINATION_STRING
		);

		System.out.println( "[Mapper] Test annotation translate (classA -> classA)." );
		Mapper<TestMapClassA, TestMapClassA> mapperAA = new Mapper<>();
		mapperAA.translate( sourceA, destinationA, false );
		Assert.assertEquals( "Translate pojo is failed.", sourceA.getA(), destinationA.getA() );
		Assert.assertEquals( "Translate object is failed.", sourceA.getString(), destinationA.getString() );
	}

	@Test
	public void translateAnnotationClassAClassB()
			throws MapperException, InvocationTargetException, IllegalAccessException {
		System.out.println( "[Mapper] Test annotation translate (classA -> classB)." );
		TestMapClassA sourceA = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		TestMapClassB destinationB = new TestMapClassB(
				TestMapClassB.TEST_DESTINATION_A,
				TestMapClassB.TEST_DESTINATION_STRING
		);

		Mapper<TestMapClassA, TestMapClassB> mapperAB = new Mapper<>();
		mapperAB.translate( sourceA, destinationB, false );
		Assert.assertEquals( "Translate pojo is failed.", sourceA.getA(), destinationB.getA() );
		Assert.assertEquals( "Translate object is failed.", sourceA.getString(), destinationB.getString() );

		System.out.println( "[Mapper] Test annotation translate (classB -> classA)." );
		TestMapClassB sourceB = new TestMapClassB( TestMapClassB.TEST_SOURCE_A, TestMapClassB.TEST_SOURCE_STRING );
		TestMapClassA destinationA = new TestMapClassA(
				TestMapClassA.TEST_DESTINATION_A,
				TestMapClassA.TEST_DESTINATION_STRING
		);

		Mapper<TestMapClassB, TestMapClassA> mapperBA = new Mapper<>();
		try {
			mapperBA.translate( sourceB, destinationA, false );
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
	public void translateAnnotationForMiniClass()
			throws MapperException, InvocationTargetException, IllegalAccessException {
		System.out.println( "[Mapper] Test annotation for mini class (classA -> classC)." );
		TestMapClassA source = new TestMapClassA( TestMapClassA.TEST_SOURCE_A, TestMapClassA.TEST_SOURCE_STRING );
		TestMapClassC destination = new TestMapClassC( TestMapClassC.TEST_DESTINATION_STRING );

		Mapper<TestMapClassA, TestMapClassC> mapper = new Mapper<>();
		mapper.translate( source, destination, false );

		Assert.assertEquals( "Translate object is failed.", source.getString(), destination.getString() );
	}

	@Test
	public void forceTranslateAnnotationOutMiniClass()
			throws MapperException, InvocationTargetException, IllegalAccessException {
		System.out.println( "[Mapper] Test annotation out mini class (classC -> classA)." );
		TestMapClassC source = new TestMapClassC( TestMapClassC.TEST_SOURCE_STRING );
		TestMapClassA destination = new TestMapClassA(
				TestMapClassA.TEST_DESTINATION_A,
				TestMapClassA.TEST_DESTINATION_STRING
		);

		Mapper<TestMapClassC, TestMapClassA> mapper = new Mapper<>();
		mapper.translate( source, destination, true );

		Assert.assertEquals( "Force translate object is failed.", source.getString(), destination.getString() );
	}

}
