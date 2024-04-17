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

	// Take three segments, smash them together, use isTriangle()?
	private void computeTriangles()
	{

		List<Segment> list = new ArrayList<Segment>(_segments.keySet());
		List<Segment> list2 = new ArrayList<Segment>();

		for(int seg1 = 0; seg1 < list.size(); seg1++) {
			list2.add(list.get(seg1));
	
			for(int seg2 = seg1 + 1; seg2 < list.size(); seg2++) {
				list2.add(list.get(seg2));

				for (int seg3 = seg1 + 2; seg3 < list.size(); seg3++) {
					list2.add(list.get(seg3));
	
					try {

						Triangle triangle = new Triangle(list2);

						_triangles.add(triangle);

					}  catch (FactException e) { }		
				}
			}
		}
	}
}
