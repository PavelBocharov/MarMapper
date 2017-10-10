package com.marcom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AssertTest {

	public static boolean assertArray(String[] arr1, String[] arr2) {
		return assertList( Arrays.asList( arr1 ), Arrays.asList( arr2 ) );
	}

	public static boolean assertList(List<String> list1, List<String> list2) {
		if ( list1 == null & list2 == null ) {
			return true;
		}
		if ( list1 == null || list2 == null ) {
			return false;
		}
		if ( list1.size() != list2.size() ) {
			return false;
		}

		Collections.sort( list1 );
		Collections.sort( list2 );

		for ( int i = 0; i < list1.size(); i++ ) {
			if ( !list1.get( i ).equals( list2.get( i ) ) ) {
				return false;
			}
		}
		return true;
	}

	public static boolean assertSet(Set<String> set1, Set<String> set2) {
		if ( set1 == null & set2 == null ) {
			return true;
		}
		if ( set1 == null || set2 == null ) {
			return false;
		}
		if ( set1.size() != set2.size() ) {
			return false;
		}
		if ( !set1.containsAll( set2 ) ) {
			return false;
		}
		return true;
	}
}
