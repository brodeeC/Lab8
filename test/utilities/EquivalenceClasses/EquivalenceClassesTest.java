/** Equivalence test class
 * 
 * @author Brodee Clontz, Eleanor Badgett, Kyler Bailey
 * @date 02/09/24
 */
package utilities.EquivalenceClasses;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

class EquivalenceClassesTest {

	

	@Test
	void testConstructor() {
		Comparator<Integer> comp = new Comparator<Integer>() 
		{
			public int compare(Integer x, Integer y)
			{return x % 3 == y % 3 ? 0:1; }
			
		};
		EquivalenceClasses<Integer> ec = new EquivalenceClasses<Integer>(comp);
		
		assertEquals(0, ec.numClasses());
		assertEquals(0, ec.size());
		
	}
	
	@Test
	void testAdd() {
		
		Comparator<Integer> comp = new Comparator<Integer>() 
		{
			public int compare(Integer x, Integer y)
			{return x % 2 == y % 2 ? 0 : 1; }
			
		};

		EquivalenceClasses<Integer> ec = new EquivalenceClasses<Integer>(comp);
		
		int [] list = new int[] {0,1,2,3,4,5,6,7,8,10};
		for( int i : list) {
			assertTrue(ec.add(i));
		}
		
		assertTrue(ec.add(-45));
	}
	
	@Test
	void testContains() {
		Comparator<Integer> comp = new Comparator<Integer>() 
		{
			public int compare(Integer x, Integer y)
			{return x % 3 == y % 3 ? 0:1;}
			
		};
		
		EquivalenceClasses<Integer> ec = new EquivalenceClasses<Integer>(comp);
		
		assertFalse(ec.contains(0));
		
		int[] list = new int [] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for(int i : list) {
			ec.add(i);
			assertTrue(ec.contains(i));
		}
		
		assertFalse(ec.contains(200));
		
		assertTrue(ec.add(-2));
		assertTrue(ec.contains(-2));
		
	}
	
	@Test
	void testSize() {
		
		Comparator<Integer> comp = new Comparator<Integer>() 
		{
			public int compare(Integer x, Integer y)
			{return x % 5 == y % 5 ? 0:1;}
			
		};
		
		EquivalenceClasses<Integer> ec = new EquivalenceClasses<Integer>(comp);
		
		assertEquals(0,ec.size());
		
		int [] list = new int[] {0,1,2,3,4,5,6,12,15,80};
		for(int i : list) {
			ec.add(i);
		}
		
		assertEquals(10,ec.size());
		
		ec.add(-30);
		assertEquals(11,ec.size());
		
	}

	@Test 
	void testNumClasses() {
		Comparator<Integer> comp = new Comparator<Integer>() 
		{
			public int compare(Integer x, Integer y)
			{return x % 5 == y % 5 ? 0:1;}
			
		};
		EquivalenceClasses<Integer> ec = new EquivalenceClasses<Integer>(comp);
		
		assertEquals(0,ec.numClasses());
		
		assertTrue(ec.add(0));
		assertEquals(1, ec.numClasses());
		
		int[] list = new int[] {1,3,5,6,23,-4};
		
		for(int i :list) {
			ec.add(i);
		}
		
		assertEquals(4, ec.numClasses());
		
		ec.add(2);
		assertEquals(5, ec.numClasses());
		
	}
	
	@Test
	void testIndexOfClass() {
		Comparator<Integer> comp = new Comparator<Integer>() 
		{
			public int compare(Integer x, Integer y)
			{return x % 3 == y % 3 ? 0:1;}
			
		};
		
		EquivalenceClasses<Integer> ec = new EquivalenceClasses<Integer>(comp);
		
		int [] list = new int[] {1,3,6,9};
		for(int i : list) {
			ec.add(i);
		}
		
		assertEquals(-1, ec.indexOfClass(2));
		assertEquals(0, ec.indexOfClass(1));
		assertEquals(1, ec.indexOfClass(3));
		assertEquals(1, ec.indexOfClass(6));
		
		assertTrue(ec.add(2));
		assertEquals(2, ec.indexOfClass(2));
			
	}


	@Test 
	void testToString() {
		
		Comparator<Integer> comp = new Comparator<Integer>() 
		{
			public int compare(Integer x, Integer y)
			{return x % 2 == y % 2 ? 0:1;}
			
		};
		EquivalenceClasses<Integer> ec = new EquivalenceClasses<Integer>(comp);
				
		int [] list = new int[] {3,7,23,48,-4,21};
		for(int i : list) {
			ec.add(i);
		}
		
		assertEquals("{3, 7, 23, 21},{48, -4}", ec.toString());
		
		assertTrue(ec.add(-32));
		assertEquals("{3, 7, 23, 21},{48, -4, -32}", ec.toString());

	}
}
