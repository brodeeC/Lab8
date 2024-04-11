/**
 * This class creates a database of PointNodes. It creates a Set of PointNodes to be manipulated
 * by many methods. This class can get a point with either it's name or it's points. It can get
 * the Name of the point in the same manor. 
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 01/25/24
 */

package input.components.point;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import components.ComponentNode;
import input.visitor.ComponentNodeVisitor;

public class PointNodeDatabase implements ComponentNode{
	protected Set<PointNode> _points;

	/**
	 * Basic constructor, creates a LinkedHashSet.
	 */
	public PointNodeDatabase() {
		_points = new LinkedHashSet<PointNode>();
	}

	/**
	 * Overloaded constructor that creates a LinkedHashSet.
	 * 
	 * @param list
	 */
	public PointNodeDatabase(List<PointNode> list) {
		_points = new LinkedHashSet<PointNode>();
		_points.addAll(list);
	}

	/**
	 * Puts the point into the database.
	 * 
	 * @param point
	 */
	public void put(PointNode point) {
		_points.add(point);
	}

	/**
	 * Contains method checks if a point is there.
	 * 
	 * @param point
	 * @return
	 */
	public boolean contains(PointNode point) {
		return _points.contains(point);
	}

	/**
	 * Checks if a point is present using its coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(double x, double y) {

		return _points.contains(new PointNode(x, y));
	}

	/**
	 * Gets the name of a point.
	 * 
	 * @param point
	 * @return
	 * @throws IllegalArgumentException
	 */
	public String getName(PointNode point) {
		return getPoint(point)._name;
	}

	/**
	 * Gets the name of a point with only coordinates.
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws IllegalArgumentException
	 */
	public String getName(double x, double y) {
		return getPoint(x, y)._name;
	}

	/**
	 * Gets the point and returns it, throws an exception if it's not there.
	 * 
	 * @param point
	 * @return
	 * @throws IllegalArgumentException
	 */
	public PointNode getPoint(PointNode point) {
		if (_points.contains(point)) {
			for (PointNode value : _points) {
				if (value.equals(point))
					return value;
			}
		}
		return null;
	}


	/**
	 * Added method for the JSONParser takes the name iterates through the 
	 * points if equals the parameter then return the point
	 * @param _name
	 * @return
	 */
	public PointNode getPoint( String _name) {
		
		for(PointNode point: _points) {
			if (getName(point).equals(_name));
			return point;
		}
		return null;
		

	}

	/**
	 * Gets the point and returns it using coordinates, throws an exception if it's
	 * not there.
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws IllegalArgumentException
	 */
	public PointNode getPoint(double x, double y) {
		PointNode point = new PointNode(x, y);
		return getPoint(point);
	}

	/**
	 * Acceptor method that reutrns the protected instance variable of points
	 * so that it can be utilized in other packages.
	 * 
	 * @return
	 */
	public Set<PointNode> getPoints(){
		return _points;
	}

	@Override
	public Object accept(ComponentNodeVisitor visitor, Object o) {
		return visitor.visitPointNodeDatabase(this, o);
	}
}
