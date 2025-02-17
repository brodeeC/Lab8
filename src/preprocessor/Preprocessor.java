package preprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentLinkedDeque;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import preprocessor.delegates.ImplicitPointPreprocessor;
import geometry_objects.Segment;

public class Preprocessor
{
	// The explicit points provided to us by the user.
	// This database will also be modified to include the implicit
	// points (i.e., all points in the figure).
	protected PointDatabase _pointDatabase;

	// Minimal ('Base') segments provided by the user
	protected Set<Segment> _givenSegments;

	// The set of implicitly defined points caused by segments
	// at implicit points.
	protected Set<Point> _implicitPoints;

	// The set of implicitly defined segments resulting from implicit points.
	protected Set<Segment> _implicitSegments;

	// Given all explicit and implicit points, we have a set of
	// segments that contain no other subsegments; these are minimal ('base') segments
	// That is, minimal segments uniquely define the figure.
	protected Set<Segment> _allMinimalSegments;

	// A collection of non-basic segments
	protected Set<Segment> _nonMinimalSegments;

	// A collection of all possible segments: maximal, minimal, and everything in between
	// For lookup capability, we use a map; each <key, value> has the same segment object
	// That is, key == value. 
	protected Map<Segment, Segment> _segmentDatabase;
	public Map<Segment, Segment> getAllSegments() { return _segmentDatabase; }

	public Preprocessor(PointDatabase points, Set<Segment> segments)
	{
		_pointDatabase  = points;
		_givenSegments = segments;
		
		_segmentDatabase = new HashMap<Segment, Segment>();
		
		analyze();
	}

	/**
	 * Invoke the precomputation procedure.
	 */
	public void analyze()
	{
		//
		// Implicit Points
		//
		_implicitPoints = ImplicitPointPreprocessor.compute(_pointDatabase, _givenSegments.stream().toList());

		//
		// Implicit Segments attributed to implicit points
		//
		_implicitSegments = computeImplicitBaseSegments(_implicitPoints);

		//
		// Combine the given minimal segments and implicit segments into a true set of minimal segments
		//     *givenSegments may not be minimal
		//     * implicitSegmen
		//
		_allMinimalSegments = identifyAllMinimalSegments(_implicitPoints, _givenSegments, _implicitSegments);

		//
		// Construct all segments inductively from the base segments
		//
		_nonMinimalSegments = constructAllNonMinimalSegments(_allMinimalSegments);

		//
		// Combine minimal and non-minimal into one package: our database
		//
		_allMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
		_nonMinimalSegments.forEach((segment) -> _segmentDatabase.put(segment, segment));
	}
	
	/**
	 * If two segments cross at an unnamed point, the result is an implicit point.
	 * 
	 * This new implicit point may be found on any of the existing segments (possibly
	 * with others implicit points on the same segment).
	 * This results in new, minimal sub-segments.
	 *
	 * For example,   A----*-------*----*---B will result in 4 new base segments.
	 *
	 * @param impPoints -- implicit points computed from segment intersections
	 * @return a set of implicitly defined segments
	 */
	protected Set<Segment> computeImplicitBaseSegments(Set<Point> impPoints)
	{
		Set<Segment> segments = new LinkedHashSet<Segment>();

		for (Segment segment: _givenSegments){

			//Adding all implicit segments from the ordered points
			SortedSet<Point> segmentSet = segment.collectOrderedPointsOnSegment(impPoints);
			if (segmentSet.size() != 2) segments.addAll(makeSegments(segmentSet));

		}
		return segments;
	}
	
	/**
	 * A set of ordered points:
	 * 
	 *  1          2      3          4        5 
	 *
	 * in which we will create the corresponding base segments:
	 * 
	 *  1----------2------3----------4--------5
	 *   
	 * @param points -- an ordred list of points
	 * @return a set of n-1 segments between all points provided
	 */
	protected Set<Segment> makeSegments(SortedSet<Point> points)
	{
		Set<Segment> segments = new HashSet<Segment>();

		//Want the ability to index
		ArrayList<Point> pts = new ArrayList<Point>(points);

		for (int i = 0; i < points.size()-1; i++){	
			
			//Add implicit segments
			segments.add(new Segment(pts.get(i), pts.get(i+1)));

		}
		 return segments;		
	}
	
	/**
	 * From the 'given' segments we remove any non-minimal segment.
	 * 
	 * @param impPoints -- the implicit points for the figure
	 * @param givenSegments -- segments provided by the user
	 * @param minimalImpSegments -- minimal implicit segments computed from the implicit points
	 * @return -- a 
	 */
	protected Set<Segment> identifyAllMinimalSegments(Set<Point> impPoints,
			Set<Segment> givenSegments,
			Set<Segment> minimalImpSegments)
	{
		Set<Segment> minimal = new HashSet<Segment>();

		//Adding points of all segments into a list
		Set<Point> pointList = new HashSet<Point>();
		for (Segment givenSegment : givenSegments){
			pointList.add(givenSegment.getPoint1());
			pointList.add(givenSegment.getPoint2());
		}
		pointList.addAll(impPoints);
		
		//Making minimal segments out of the ordered Points
		for (Segment givenSegment : givenSegments){
			SortedSet<Point> orderedPoints = givenSegment.collectOrderedPointsOnSegment(pointList);
			minimal.addAll(makeSegments(orderedPoints));
		}

		return minimal;
	}
	
	/**
	 * Given a set of minimal segments, build non-minimal segments by appending
	 * minimal segments (one at a time).
	 * 
	 * (Recursive construction of segments.)
	 */
	public Set<Segment> constructAllNonMinimalSegments(Set<Segment> minimalSegs)
	{
		Set<Segment> nonMinimalSegs = new HashSet<Segment>();

		constructAllNonMinimalSegments(minimalSegs, minimalSegs.stream().toList(), nonMinimalSegs);

		return nonMinimalSegs;
	}

	private void constructAllNonMinimalSegments(Set<Segment> lastLevelSegs, List<Segment> minimalSegs, Set<Segment> nonMinimalSegs)
	{
		//Queue to hold all segments that need to be checked for nonMinimalSegments
		Queue<Segment> q = new ConcurrentLinkedDeque<Segment>(minimalSegs);

		while (!q.isEmpty()){

			//Get the top segment
			Segment segment = q.remove();

			for (Segment segment2 : lastLevelSegs){

				//Combine the segments
				Segment newSeg = combineToNewSegment(segment, segment2);

				//If a segment was returned
				if (newSeg != null) {

					//Add it to the queue if it isn't there, then add it to the Set
					if (!q.contains(newSeg)) q.add(newSeg);
					nonMinimalSegs.add(newSeg);
				}
			}
		}
	}
	
	//
	// Our goal is to stitch together segments that are on the same line:
	//                       A---------B----------C
	// resulting in the segment AC.
	//
	// To do so we ask:
	//    * Are each segment on the same (infinite) line?
	//    * If so, do they share an endpoint?
	// If both criteria are satisfied we have a new segment.
	protected Segment combineToNewSegment(Segment left, Segment right)
	{
		//Get shared vertex
		Point sharedVertex = left.sharedVertex(right);

		//If sharedVertex exists and the two segments are on the same line
		if(!left.coincideWithoutOverlap(right) || sharedVertex == null) return null;
		
		//Getting points to create segment
		Point leftPoint = left.other(sharedVertex);
		Point rightPoint = right.other(sharedVertex);

		//Creating segment
		return new Segment(leftPoint, rightPoint);
	}
}
