/**
 * Creates a SegmentNode from 2 PointNodes. 
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.segment;

import input.components.point.PointNode;

/**
 * A utility class only for representing ONE segment
 */
public class SegmentNode {
	protected PointNode _point1;
	protected PointNode _point2;

	public PointNode getPoint1() {
		return _point1;
	}

	public PointNode getPoint2() {
		return _point2;
	}

	public SegmentNode(PointNode pt1, PointNode pt2) {
		_point1 = pt1;
		_point2 = pt2;
	}

	/**
	 * Seeing whether or not this is an actual segment by seeing if there are two
	 * points
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SegmentNode))
			return false;

		SegmentNode that = (SegmentNode) obj;

		return ((that._point1.equals(this._point1) && (that._point2.equals(this._point2)))
				|| (that._point2.equals(this._point1) && (that._point2.equals(this._point1))));

	}

	public String toString() {
		// StringBuilder appends both PointNodes and returns.
		StringBuilder s = new StringBuilder();
		s.append("{" + _point1 + ",");
		s.append(_point2 + "}");
		return s.toString();
	}

}