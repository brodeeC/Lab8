/**
 * Creates a PointNode with a name and a coordinate (x, y). 
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.point;

import components.ComponentNode;
import input.visitor.ComponentNodeVisitor;
import utilities.math.MathUtilities;

/**
 * A 2D Point (x, y).
 */
public class PointNode implements ComponentNode{
	protected static final String ANONYMOUS = "__UNNAMED";

	protected double _x;

	public double getX() {
		return this._x;
	}

	protected double _y;

	public double getY() {
		return this._y;
	}

	protected String _name;

	public String getName() {
		return _name;
	}

	/**
	 * Create a new Point with the specified coordinates.
	 * 
	 * @param x The X coordinate
	 * @param y The Y coordinate
	 */
	public PointNode(double x, double y) {
		this(ANONYMOUS, x, y);
	}

	/**
	 * Create a new Point with the specified coordinates.
	 * 
	 * @param name -- The name of the point. (Assigned by the UI)
	 * @param x    -- The X coordinate
	 * @param y    -- The Y coordinate
	 */
	public PointNode(String name, double x, double y) {
		_name = name;
		_x = x;
		_y = y;
	}

	/**
	 * Returns the hashcode of a given PointNode
	 */
	@Override
	public int hashCode() {
		return Double.valueOf(_x).hashCode() + Double.valueOf(_y).hashCode();
	}

	/**
	 * Checks to see if the PointNode is a valid object
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PointNode))
			return false;

		PointNode that = (PointNode) obj;
		
		return MathUtilities.doubleEquals(this._x, that._x) && MathUtilities.doubleEquals(this._y, that._y);
	}

	/**
	 * toString method, returns a string output of a PointNode
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(("Point(" + _name + ")(" + _x + "," + _y + ")"));
		return s.toString();

	}
		
	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitPointNode(this, o);
	}
}