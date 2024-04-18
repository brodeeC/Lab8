/**
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 04/16/24
 * 
 * Creates an Equivalence Class of Triangles. Takes three segments, places them within a triangle (via list).
 */

package preprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.Triangle;

public class TriangleIdentifier
{
	protected Set<Triangle>         _triangles;
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure.

	public TriangleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested;
	 * memoize results for subsequent calls.
	 */
	public Set<Triangle> getTriangles()
	{
		if (_triangles != null) return _triangles;

		_triangles = new HashSet<Triangle>();

		computeTriangles();

		return _triangles;
	}

	
	private void computeTriangles()
	{

		ArrayList<Segment> list = new ArrayList<Segment>(_segments.keySet());

		//First iteration, grabs first segment
		for(int seg1 = 0; seg1 < list.size(); seg1++) {
			Segment segment1 = list.get(seg1);
	
			//Second iteration, grabs second segment
			for(int seg2 = seg1 + 1; seg2 < list.size(); seg2++) {
				Segment segment2 = list.get(seg2);

				//Third iteration, grabs third segment
				for (int seg3 = seg2 + 1; seg3 < list.size(); seg3++) {
					Segment segment3 = list.get(seg3);

					Triangle triangle;
					
					try {
						//Smash three segments together into triangle structure
						triangle = new Triangle(Arrays.asList(segment1, segment2, segment3));

					}

					catch (FactException e) {
						triangle = null;
					}
					//Add to _triangles set
					if (triangle != null) _triangles.add(triangle);	 
				}
			}
		}
	}
}
