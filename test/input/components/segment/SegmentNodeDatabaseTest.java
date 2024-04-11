/**
 * Test class for SegmentNodeDatabase. 
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.segment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import input.components.point.PointNode;

class SegmentNodeDatabaseTest {
	public SegmentNodeDatabase build() {
		// A
		// / \
		// B___C
		// / \ / \
		// / X \
		// D_________E
		//
		//
		PointNode a = new PointNode("A", 3, 6);
		PointNode b = new PointNode("B", 2, 4);
		PointNode c = new PointNode("C", 4, 4);

		PointNode d = new PointNode("D", 0, 0);
		PointNode e = new PointNode("E", 6, 0);
		PointNode x = new PointNode("X", 3, 3);

		SegmentNodeDatabase db = new SegmentNodeDatabase();

		db.addUndirectedEdge(a, b);
		db.addUndirectedEdge(a, c);
		db.addUndirectedEdge(b, c);
		db.addUndirectedEdge(b, x);
		db.addUndirectedEdge(b, d);
		db.addUndirectedEdge(c, x);
		db.addUndirectedEdge(c, e);
		db.addUndirectedEdge(x, d);
		db.addUndirectedEdge(x, e);
		db.addUndirectedEdge(d, e);

		return db;
	}

	/**
	 * Test to count the number of undirected edges in the set
	 */
	@Test
	void testNumUndirectedEdges() {
		SegmentNodeDatabase db = build();

		assertEquals(10, db.numUndirectedEdges());
	}

	/**
	 * Test that the SegmentNode database is properly constructed with a map and
	 * set.
	 */
	@Test
	void testConstructor() {
		SegmentNodeDatabase db = new SegmentNodeDatabase();
		assertTrue(db._adjLists.isEmpty());

		Map<PointNode, Set<PointNode>> test = new HashMap<>();
		SegmentNodeDatabase db2 = new SegmentNodeDatabase(test);

		assertEquals(test, db2._adjLists);
	}

	/**
	 * Test that the adjacencylist method when called adds the point to a list of
	 * points.
	 */
	@Test
	void testAdjacencyList() {
		SegmentNodeDatabase db = new SegmentNodeDatabase();

		PointNode a = new PointNode("A", 0, 0);
		PointNode b = new PointNode("B", 1, 0);
		PointNode c = new PointNode("C", 0, 1);
		PointNode d = new PointNode("D", 1, 1);

		//List<PointNode> point = Arrays.asList(b, c);

		db.addUndirectedEdge(a, b);
		db.addUndirectedEdge(b, c);
		db.addUndirectedEdge(c, b);

		Set<PointNode> set = db._adjLists.get(a);

		assertTrue("Couldn't find point", set.contains(b));
		assertFalse("Couldn't find point", set.contains(d));
		assertEquals("Incorrect Value", 3, db.numUndirectedEdges());
	}

	/**
	 * Creates 2 databases that are identical and compares the segment lists of both
	 * Then changes one SegmentNode in the second database and confirms they are
	 * different.
	 */
	@Test
	void testAsSegmentList() {

		SegmentNodeDatabase db = new SegmentNodeDatabase();
		SegmentNodeDatabase db2 = new SegmentNodeDatabase();

		PointNode a = new PointNode("A", 0, 0);
		PointNode b = new PointNode("B", 1, 0);
		PointNode c = new PointNode("C", 0, 1);
		PointNode d = new PointNode("D", 1, 1);

		db.addUndirectedEdge(a, b);
		db.addUndirectedEdge(a, c);
		db.addUndirectedEdge(b, c);
		db.addUndirectedEdge(c, d);

		db2.addUndirectedEdge(a, b);
		db2.addUndirectedEdge(a, c);
		db2.addUndirectedEdge(b, c);
		db2.addUndirectedEdge(c, d);

		assertEquals("Lists not the same", db.asSegmentList(), db2.asSegmentList());

		db2 = new SegmentNodeDatabase();
		db2.addUndirectedEdge(a, b);
		db2.addUndirectedEdge(a, c);
		db2.addUndirectedEdge(b, c);
		db2.addUndirectedEdge(c, a);

		assertFalse("Lists are the same", db.asSegmentList().equals(db2.asSegmentList()));
	}

	/**
	 * Same as the test above this one but it tests a different method.
	 */
	@Test
	void testAsUniqueSegmentList() {
		SegmentNodeDatabase db = new SegmentNodeDatabase();
		SegmentNodeDatabase db2 = new SegmentNodeDatabase();

		PointNode a = new PointNode("A", 0, 0);
		PointNode b = new PointNode("B", 1, 0);
		PointNode c = new PointNode("C", 0, 1);
		PointNode d = new PointNode("D", 1, 1);

		db.addUndirectedEdge(a, b);
		db.addUndirectedEdge(a, c);
		db.addUndirectedEdge(b, c);
		db.addUndirectedEdge(c, d);

		db2.addUndirectedEdge(a, b);
		db2.addUndirectedEdge(a, c);
		db2.addUndirectedEdge(b, c);
		db2.addUndirectedEdge(c, d);

		assertEquals("Lists not the same", db.asUniqueSegmentList(), db2.asUniqueSegmentList());

		db2 = new SegmentNodeDatabase();
		db2.addUndirectedEdge(a, b);
		db2.addUndirectedEdge(a, c);
		db2.addUndirectedEdge(b, c);
		db2.addUndirectedEdge(c, a);

		assertFalse("Lists are the same", db.asUniqueSegmentList().equals(db2.asUniqueSegmentList()));
	}

}
