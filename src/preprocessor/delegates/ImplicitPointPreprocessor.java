package preprocessor.delegates;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import geometry_objects.Segment;
import geometry_objects.delegates.intersections.SegmentIntersectionDelegate;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
/**
 * Implicit Point Preprocessor class iterates through a list of points seeing if there is an intersection if so, checks to make sure that
 * point has a name, if not a name is given to the point.
 * 
 * @author Kyler, Brodee, and Collin
 * @Date   March 26, 2024
 */

public class ImplicitPointPreprocessor
{
	/**
	 * It is possible that some of the defined segments intersect
	 * and points that are not named; we need to capture those
	 * points and name them.
	 * 
	 */

	 /*
	  * Iterate through a list of the given segments for each segment see if it intersects, then 
	  * if it does intersects take that point then check to see if that point has a name or not. If it does not
	  * take the ones without and name them. Maybe add the ones that are unamed to the set, then iterate over them and name them.
	  * 
	  */

	  /**
	   * For two segments to intersect they must cross at the sameif point
	   * Loop over the segements twice, use a tradition for loop.
	   * Given points are the ones like A,B,C, & D.
	   * @param givenPoints
	   * @param givenSegments
	   * @return
	   */
	public static Set<Point> compute(PointDatabase givenPoints, List<Segment> givenSegments)	 
	{
		Set<Point> implicitPoints = new LinkedHashSet<Point>();
		
		//Loops over the given list of segements twice to check the combinations to find segements.
		for(int seg1 = 0; seg1< givenSegments.size(); seg1++){
			Segment segment1 = givenSegments.get(seg1);
			
			for(int seg2 = 1; seg2 < givenSegments.size(); seg2++){
				Segment segment2 = givenSegments.get(seg2);
				
				//Takes the two segments and find the intersection point. 
				Point intersection_pt = SegmentIntersectionDelegate.findIntersection(segment1,segment2);

				//Checks to see that the intersection point is not null.
				if(intersection_pt != null){
		
					//Puts the point into the database to name it
					givenPoints.put(null, intersection_pt.getX(), intersection_pt.getY()); 
					
					//Retrieves the now named point from the database and adds it to the Set.
					implicitPoints.add(givenPoints.getPoint(intersection_pt));
				}	
			}
		}
		return implicitPoints;
	
	}


}
