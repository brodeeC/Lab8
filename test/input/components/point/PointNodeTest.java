/**
 * Test class for PointNode. 
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class PointNodeTest {

	@Test
	void testConstructPointNode() {
		PointNode point1 = new PointNode("kyler", 3, 3);
		PointNode point2 = new PointNode("brodee", 2, 1);
		PointNode point3 = new PointNode("collin", 4, 1);

		assertTrue("Points not the same", point1.equals(point1));
		assertTrue("Points not the same", point2.equals(point2));
		assertTrue("Points not the same", point3.equals(point3));

		point1 = new PointNode("one", 1, 2);
		assertEquals("Name not 'one'", point1.getName(), "one");
		assertTrue("X value is different", point1.getX() == 1);
		assertTrue("Y value is different", point1.getY() == 2);
	}

	@Test
	void testHashCode() {
		PointNode point1 = new PointNode("kyler", 3.5, 4.2);
		PointNode point2 = new PointNode("brodee", 2.3, 1.3);
		PointNode point3 = new PointNode("collin", 3.5, 4.2);

		// Calculate the hashCode value manually
		int expectedHashCode = Double.valueOf(3.5).hashCode() + Double.valueOf(4.2).hashCode();

		// Test to see whether the actual hashcode matches the expected hashcode
		assertEquals("Expected not the same", expectedHashCode, point1.hashCode());
		// Test to see if two points with the same coordinates will have the same
		// hashCode
		assertEquals("HashCode not the same", point1.hashCode(), (point3.hashCode()));
		// Test that two points with DIFFERENT coordinates will have different hashCode
		assertNotEquals("Hashcode is the same", point1.hashCode(), point2.hashCode());

	}

	@Test
	void testbooleanEquals() {

		PointNode point1 = new PointNode("kyler", 3, 3);
		PointNode point2 = new PointNode("brodee", 3, 3);
		PointNode point3 = new PointNode("collin", 4, 1);

		assertTrue("Points not the same", point1.equals(point2));
		assertTrue("Points not the same", point3.equals(point3));

		assertFalse("Points are the same", point2.equals(point3));
		assertFalse("Points are the same", point3.equals(point1));

		point1 = new PointNode("one", 1, 2);
		point2 = new PointNode("one", 1, 2);

		assertTrue("Points not the same", point1.equals(point2));

		point2 = new PointNode("one", 2, 2);

		assertFalse("Points are the same", point1.equals(point2));
	}
	/*
	@Test
	void testToString() {
		PointNode point1 = new PointNode("kyler", 3, 3);
		PointNode point2 = new PointNode("brodee", 3, 3);
		PointNode point3 = new PointNode(4, 1);

		assertEquals("toString is not 'kyler'", point1.toString(), "kyler");
		assertEquals("toString is not 'brodee'", point2.toString(), "brodee");
		assertEquals("toString is not '__UNNAMED'", point3.toString(), "__UNNAMED");

		point1 = new PointNode("one", 1, 2);
		assertEquals("toString is not 'one'", point1.toString(), "one");

	}
	*/

}
