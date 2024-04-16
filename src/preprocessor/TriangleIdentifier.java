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
		try {

			List<Segment> list = new ArrayList<>();

			for(int seg1 = 0; seg1 < _segments.size(); seg1++) {
				list.add(_segments.get(seg1));
	
				for(int seg2 = 1; seg2 < _segments.size(); seg2++) {
					list.add(_segments.get(seg2));

					for (int seg3 = 2; seg3 < _segments.size(); seg3++) {
						list.add(_segments.get(seg3));
	

						Triangle triangle = new Triangle(list);
						_triangles.add(triangle);
					}
				}
			}
		}  catch (FactException e) {}
	}
}
