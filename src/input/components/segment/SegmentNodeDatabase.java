/**
 * Creates a Database of Segment Nodes by creating a map and connecting a PointNode to a set of PointNodes. 
 * Counts the number of edges within the shape. And can provide a list of all segments and where they're 
 * connected.
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.segment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import components.ComponentNode;
import input.components.point.PointNode;
import input.visitor.ComponentNodeVisitor;

public class SegmentNodeDatabase implements ComponentNode{
	protected Map<PointNode, Set<PointNode>> _adjLists;

	/**
	 * Basic constructor
	 */
	public SegmentNodeDatabase() {

		this(new HashMap<PointNode, Set<PointNode>>());

	}

	/**
	 * Overloaded Constructor.
	 * 
	 * @param map
	 */
	public SegmentNodeDatabase(Map<PointNode, Set<PointNode>> map) {
		_adjLists = new HashMap<PointNode, Set<PointNode>>();
		for (java.util.Map.Entry<PointNode, Set<PointNode>> m : map.entrySet()) {
			_adjLists.put(m.getKey(), m.getValue());
		}
	}

	/**
	 * Counts the number of segments in the shape.
	 * 
	 * @return count
	 */
	public int numUndirectedEdges() {
		int count = 0;
		List<SegmentNode> list = new ArrayList<SegmentNode>();
		list = asSegmentList();
		count = list.size();
		return count;
	}

	/**
	 * Calls addAdjacencyList to add an edge to _adjLists.
	 * 
	 * @param point1
	 * @param point2
	 */
	private void addDirectedEdge(PointNode point1, PointNode point2) {
		addAdjacencyList(point1, Arrays.asList(point2));
	}

	/**
	 * Calls addDirectedEdge to add an edge to _adjLists.
	 * 
	 * @param point1
	 * @param point2
	 */
	public void addUndirectedEdge(PointNode point1, PointNode point2) {
		addDirectedEdge(point1, point2);
	}

	/**
	 * Connects a point to a list of points and adds it to _adjLists.
	 * 
	 * @param point
	 * @param pointList
	 */
	public void addAdjacencyList(PointNode point, List<PointNode> pointList) {
		Set<PointNode> points = new HashSet<PointNode>();
		for (PointNode Point : pointList) {
			points.add(Point);
		}
		if (_adjLists.get(point) != null) {
			for (PointNode Point : _adjLists.get(point)) {
				points.add(Point);
			}
		}
		_adjLists.put(point, points);
	}

	/**
	 * Returns a list of all SegmentNodes and where they're connected.
	 * 
	 * @return List<SegmentNode>
	 */
	public List<SegmentNode> asSegmentList() {
		ArrayList<SegmentNode> list = new ArrayList<SegmentNode>();
		for (PointNode point1 : _adjLists.keySet()) {
			Set<PointNode> set = new HashSet<PointNode>();
			set.addAll(_adjLists.get(point1));
			for (PointNode point2 : set) {
				list.add(new SegmentNode(point1, point2));
			}
		}
		return list;
	}

	/**
	 * Call list of segments from asSegmentList. Create HashSet, Sets eliminate
	 * repetitive elements. ArrayList will iterate over elements in HashSet and add
	 * them.
	 */
	public List<SegmentNode> asUniqueSegmentList() {
		return asSegmentList();
	}
	
	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitSegmentDatabaseNode(this, o);
	}

		/**
	 * Accessor method to retrieve the adjancy list within the SegmentNode database.
	 * @return
	 */
	public Map<PointNode, Set<PointNode>> getAdjLists(){
		
		return _adjLists;
	
	}
}
