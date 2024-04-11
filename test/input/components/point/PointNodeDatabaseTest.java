/**
 * Test class for PointNodeDatabase. 
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class PointNodeDatabaseTest {

	/**
	 * Test to properly see that the constructor properly constructs a point node.
	 */
	@Test
	void testConstructor() {
		PointNode point1 = new PointNode("cartoon", 1, 1);
		PointNode point2 = new PointNode("hello", 2, 2);
		PointNode point3 = new PointNode(3, 3);

		PointNodeDatabase points = new PointNodeDatabase(Arrays.asList(point1, point2, point3));

		assertTrue("Point not found", points.contains(point1));
		assertTrue("Point not found", points.contains(point2));
		assertTrue("Point not found", points.contains(point3));
	}

	/**
	 * Test to properly see that the point node is placed within the
	 * PointNodeDatabase.
	 */
	@Test
	void testPut() {
		PointNode point1 = new PointNode("cartoon", 1, 1);
		PointNode point2 = new PointNode("hello", 2, 2);
		PointNode point3 = new PointNode(3, 3);
		PointNodeDatabase points = new PointNodeDatabase();

		points.put(point1);
		points.put(point2);
		points.put(point3);

		assertTrue("Point not found", points.contains(point1));
		assertTrue("Point not found", points.contains(point2));
		assertTrue("Point not found", points.contains(point3));

		assertFalse("Point found", points.contains(new PointNode(4, 4)));
	}

	/**
	 * Test to see that if the point node is contained within the the
	 * PointNodeDatabase
	 */
	@Test
	void testContainsDouble() {
		PointNode point1 = new PointNode("cartoon", 1, 1);
		PointNode point2 = new PointNode("hello", 2, 2);
		PointNode point3 = new PointNode(3, 3);

		PointNodeDatabase points = new PointNodeDatabase(Arrays.asList(point1, point2, point3));

		assertTrue("Point not found", points.contains(1, 1));
		assertTrue("Point not found", points.contains(2, 2));
		assertTrue("Point not found", points.contains(3, 3));

		assertFalse("Point found", points.contains(4, 4));
	}

	/**
	 * Test to see that if the point has a name it is returned along with if the
	 * point does not have a name the point is given the name _UNAMED
	 */
	@Test
	void testGetNamePoint() {
		PointNode point1 = new PointNode("cartoon", 1, 1);
		PointNode point2 = new PointNode("hello", 2, 2);
		PointNode point3 = new PointNode(3, 3);

		PointNodeDatabase points = new PointNodeDatabase(Arrays.asList(point1, point2, point3));

		assertEquals("Name not found", points.getName(point1), "cartoon");
		assertEquals("Name not found", points.getName(point2), "hello");
		assertEquals("Name not found", points.getName(point3), "__UNNAMED");
	}

	/**
	 * Test to see that if the point's name can be retrieved if given a double
	 */
	@Test
	void testGetNameDouble() {
		PointNode point1 = new PointNode("cartoon", 1, 1);
		PointNode point2 = new PointNode("hello", 2, 2);
		PointNode point3 = new PointNode(3, 3);

		PointNodeDatabase points = new PointNodeDatabase(Arrays.asList(point1, point2, point3));

		assertEquals("Name not found", points.getName(1, 1), "cartoon");
		assertEquals("Name not found", points.getName(2, 2), "hello");
		assertEquals("Name not found", points.getName(3, 3), "__UNNAMED");
	}

	/**
	 * Test to ensure that when getPoint is called the point is properly retrieved
	 */
	@Test
	void testGetPointPoint() {
		PointNode point1 = new PointNode("cartoon", 1, 1);
		PointNode point2 = new PointNode("hello", 2, 2);
		PointNode point3 = new PointNode(3, 3);

		PointNodeDatabase points = new PointNodeDatabase(Arrays.asList(point1, point2, point3));

		assertEquals("Point not found", points.getPoint(point1), point1);
		assertEquals("Point not found", points.getPoint(point2), point2);
		assertEquals("Point not found", points.getPoint(point3), point3);
	}

	/**
	 * Test to ensure that when getPoint is called the point's double is properly
	 * retrieved
	 */
	@Test
	void testGetPointDouble() {
		PointNode point1 = new PointNode("cartoon", 1, 1);
		PointNode point2 = new PointNode("hello", 2, 2);
		PointNode point3 = new PointNode(3, 3);

		PointNodeDatabase points = new PointNodeDatabase(Arrays.asList(point1, point2, point3));

		assertEquals("Point not found", points.getPoint(1, 1), point1);
		assertEquals("Point not found", points.getPoint(2, 2), point2);
		assertEquals("Point not found", points.getPoint(3, 3), point3);
	}

}
