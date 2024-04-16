/**
 * Write a succinct, meaningful description of the class here. You should avoid wordiness    
 * and redundancy. If necessary, additional paragraphs should be preceded by <p>,
 * the html tag for a new paragraph.
 *
 * <p>Bugs: (a list of bugs and / or other problems)
 *
 * @author Kyler, Brodee, & Collin
 * @date   April 15, 2024
 */

package geometry_objects.angle.comparators;

import java.util.Comparator;

import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.points.Point;
import utilities.math.MathUtilities;
import utilities.math.analytic_geometry.GeometryUtilities;

public class AngleStructureComparator implements Comparator<Angle>
{
	public static final int STRUCTURALLY_INCOMPARABLE = Integer.MAX_VALUE;
	
	/**
	 * Given the figure below:
	 * 
	 *    A-------B----C-----------D
	 *     \
	 *      \
	 *       \
	 *        E
	 *         \
	 *          \
	 *           F
	 * 
	 * What we care about is the fact that angle BAE is the smallest angle (structurally)
	 * and DAF is the largest angle (structurally). 
	 * 
	 * If one angle X has both rays (segments) that are subsegments of an angle Y, then X < Y.
	 * 
	 * If only one segment of an angle is a subsegment, then no conclusion can be made.
	 * 
	 * So:
	 *     BAE < CAE
   	 *     BAE < DAF
   	 *     CAF < DAF

   	 *     CAE inconclusive BAF
	 * 
	 * @param left -- an angle
	 * @param right -- an angle
	 * @return -- according to the algorithm above:
 	 *              Integer.MAX_VALUE will refer to our error result
 	 *              0 indicates an inconclusive result
	 *              -1 for less than
	 *              1 for greater than
	 */
	/**
	 * Check to see first if the angles share the same vertices and have somewhat a similar ray 
	 */
	@Override
	public int compare(Angle left, Angle right)
	{
		Point left_vert = left.getVertex();
		Point right_vert = right.getVertex();

		if (!left.overlays(right) || !left_vert.equals(right_vert)) {
			return STRUCTURALLY_INCOMPARABLE;
		}

		if(left.compareTo(right) == 0){
			return 0;
		}
      
		if(left.compareTo(right) < 0){
			return -1;
		}

		if(left.compareTo(right) > 0){
			return 1;
		}

		return STRUCTURALLY_INCOMPARABLE; //Return this since we are handling the error.
	}
}
