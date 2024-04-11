/**
 * Test class for SegmentNode. 
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import input.components.point.PointNode;

class SegmentNodeTest { 

	/**
	 * Test that a SegmentNode is properly constructed by taking to points
	 */
	@Test
	void testConstructSegmentNode() {

		PointNode point1 = new PointNode("kyler", 3, 3);
		PointNode point2 = new PointNode("Brodee", 2, 2);

		SegmentNode segment = new SegmentNode(point1, point2);

		assertEquals("Points not the same", segment.getPoint1(), point1);
		assertEquals("Points not the same", segment.getPoint2(), point2);

	}

	/**
	 * Test to see if the two segments are same and if the object is a valid segment
	 */
	@Test
	void testbooleanEquals() {

		PointNode point1 = new PointNode("one", 1, 1);
		PointNode point2 = new PointNode("two", 2, 2);

		SegmentNode segment1 = new SegmentNode(point1, point2);
		SegmentNode segment2 = new SegmentNode(point1, point2);

		assertTrue("Segments not the same", segment1.equals(segment2));

		segment2 = new SegmentNode(point2, point1);

		assertTrue("Segments not the same", segment1.equals(segment2));

	}
}
