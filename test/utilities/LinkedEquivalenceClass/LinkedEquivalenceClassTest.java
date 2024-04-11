package utilities.LinkedEquivalenceClass;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

/**
 * This Junit test case test each method within the LinkeEquivalnceClass.
 * 
 * @author Kyler, Brodee, and Eleanor
 * Date: February 5, 2024
 */
class LinkedEquivalenceClassTest {

	/**
	 * Test to see that the LinkedList, Comparator, and canonical are properly 
	 * constructed 
	 */
	@Test
	void testConstructor() { 
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};	
		
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp); 
		int _canonical = 2;

		//Test that the list is actually constructed
		assertTrue("List was not empty.",list.isEmpty());
		//Test that there is a canonical element
		assertEquals("Canonical element was not detected",_canonical,2);


	}

	/**
	 * Test to see whether or not the canonical actually exists
	 */
	@Test
	void testCanonical() {
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};	
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp); 

		int[] l = new int[] {6,7,8,2,10};
		for(int i : l) {
			list.add(i);
		}

		assertEquals(6, list.canonical());
		assertTrue(list.add(22));
		//ensures canonical does not change when new elm is added
		assertEquals(6, list.canonical());

		LinkedEquivalenceClass<Integer> list2 = new LinkedEquivalenceClass<Integer>(comp);
		list2.add(2);
		list2.add(3);
		list2.add(-22);
		assertEquals(2, list2.canonical());

	}

	/**
	 * Test to check the boolean isEmpty condition for the LinkedList operates 
	 * properly.
	 */
	@Test
	void testisEmpty() {
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};	
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);


		assertTrue("List was not empty.",list.isEmpty());

		assertTrue(list.add(4));
		assertTrue(list.add(6));
		assertTrue(list.add(8));

		assertFalse("List was empty.",list.isEmpty());
		assertEquals("List does not contain elements.",list.size(),3);

		list.clear();

		assertEquals("List was not empty.",list.isEmpty(),true);
		assertEquals("List still contains elements.",list.size(),0);

	}

	/**
	 * Test that the LinkedList is when clear is called all elements are
	 * removed
	 */
	@Test
	void testClear() {
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};	
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);
		
		assertTrue(list.add(6));
		assertTrue(list.add(8));
		assertTrue(list.add(10));

		//ensures list is not originally empty
		assertEquals(3, list.size());
		assertFalse(list.isEmpty());

		list.clear();
		assertTrue(list.isEmpty());

		assertTrue(list.add(4));
		assertTrue(list.add(10));
		assertFalse(list.add(7));

		assertEquals(2, list.size());
		assertFalse(list.isEmpty());

	}

	/**
	 * Test to see that only the NonCanonicals are cleared within the list.
	 */
	@Test
	void testclearNonCanonical() {
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};	
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);

		list.add(6);
		list.add(8);
		list.add(10);
		list.add(7);
		
		assertEquals(3, list.size());

		list.clearNonCanonical();
		assertEquals(1, list.size());

	}


	/**
	 * Test that elements are properly added to either the back or front
	 */
	@Test
	void testAdd() {

		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);

		assertTrue(list.add(6));
		assertTrue(list.add(8));
		assertTrue(list.add(24));
		assertTrue(list.add(88));
		assertTrue(list.add(100));
		assertTrue(list.add(-2));

		//ensures that an element that does not belong cannot be added
		assertFalse(list.add(7));


		//ensured Elements properly added
		assertEquals("6, 8, 24, 88, 100, -2", list.toString());

		assertTrue(list.add(4));
		assertEquals("6, 8, 24, 88, 100, -2, 4", list.toString());

	}

	/**
	 * Test to see that the contains method properly 
	 * finds a given target with returning true or false if found.
	 */
	@Test 
	void testContains() {
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);

		assertTrue(list.add(6));
		list.add(8);
		list.add(10);
		assertFalse(list.add(7));
		assertFalse(list.add(null));

		assertTrue(list.contains(6));
		assertTrue("List does not contain 10.",list.contains(10));
		assertFalse("list contains 7,", list.contains(7));
		assertFalse("List contains the value.",list.contains(12));
		assertFalse("List does not contain null value.",list.contains(null)); 

	}

	@Test 
	void testBelongs() {

		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);

		list.add(7);
		list.belongs(3);
		list.belongs(4);

		assertTrue("Element does not belong in list.",list.belongs(3));

	}

	/**
	 * Test to see that a given element can be removed from the LinkedList.
	 */
	@Test
	void testRemove() { 


		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);

		list.add(6);
		list.add(8);
		list.add(10);
		list.add(12);

		//Test to make sure all the elements are in the list
		assertEquals("The integers were not in the list.",list.toString(),"6, 8, 10, 12" );
		//Remove instance number 1, removed the end element
		list.remove(12);
		assertEquals("Integer 12 was not removed.", list.size(),3);
		assertEquals("6, 8, 10", list.toString());
		//Remove instance number 2, removed the middle element
		list.remove(8);
		assertEquals("Integer 8 was not removed.",list.size(),2);
		assertEquals("6, 10", list.toString());

		//Removing a null value 
		list.remove(null);
		assertEquals("List was altered.",list.size(),2);

	}

	@Test 
	void testremoveCanonical() {
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp);
 
		list.add(6);
		list.add(8);
		list.add(10);
		
		assertEquals("6, 8, 10", list.toString());
		
		assertTrue(list.removeCanonical());
		assertEquals("8, 10", list.toString());
		assertEquals(8,list.canonical());
		
	}

	/**
	 * Test that the canonical is demoted and set into the LinkedList
	 */
	@Test

	void testdemoteAndSetCanonical() { 
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};

		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(comp); 

		list.add(6);
		list.add(8);
		list.add(10);
		list.add(12);
		
		assertEquals(6, list.canonical());
		
		//ensures canonical cannot be set to an elm that does not belong in EquicalenceClass
		assertFalse(list.demoteAndSetCanonical(3));
		
		assertTrue(list.demoteAndSetCanonical(20));
		assertEquals(20, list.canonical());
		
	}

	/**
	 * Test that the given elements are properly placed into 
	 * a given string.
	 */
	@Test
	void testToString() {

		Comparator<Integer> c = new Comparator<Integer>()
		{
			//All even integers are 'equivalent'
			//All odd integers are 'equivalent'
			public int compare(Integer x, Integer y)
			{return x %2 == y %2 ? 0 : 1;}

		};
		
		LinkedEquivalenceClass<Integer> list = new LinkedEquivalenceClass<Integer>(c);
		
		int [] l = new int[] {0,1,2,3,4,5};
		for(int i : l) {
			list.add(i);
		}
		
		assertEquals("0, 2, 4", list.toString());
		
		assertTrue(list.add(12));
		assertEquals("0, 2, 4, 12", list.toString());
		
		assertTrue(list.remove(4));
		assertEquals("0, 2, 12", list.toString());

	}

}
