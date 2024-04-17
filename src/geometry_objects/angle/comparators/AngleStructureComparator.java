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
	@Override
	public int compare(Angle left, Angle right)
	{
		Point left_vert = left.getVertex();
		Point right_vert = right.getVertex();

		//If the angles does not share the same vertex then it is struturally incomparable.
		if (!left_vert.equals(right_vert)) {
			return STRUCTURALLY_INCOMPARABLE;
		}

		//If the left does not overlay with the right then it is structurally incomparable.
		if(!left.overlays(right)){
			return STRUCTURALLY_INCOMPARABLE;
		}

		//Gets the overlaying segments from the left ray 1 and left ray 2.
		Segment OverlayR1 = right.overlayingRay(left.getRay1());
		Segment OverlayR2 = right.overlayingRay(left.getRay2());
		
		Point ptR1 = OverlayR1.getPoint1();
		Point ptR2 = OverlayR2.getPoint1();

		Point ptR1_2 = OverlayR1.getPoint2();
		Point ptR2_2 = OverlayR2.getPoint2();

		if(OverlayR1 == null || OverlayR2 == null){
			return STRUCTURALLY_INCOMPARABLE;
		}

		
		//If I can grab the length of each segement from the angle and compare them to eachother then might be able to figure something out.
		if(!GeometryUtilities.between(ptR1,left_vert,ptR1_2) || 
	       !GeometryUtilities.between(ptR2, right_vert, ptR2_2)){
			
			return STRUCTURALLY_INCOMPARABLE;
		}  
		
		if(OverlayR1.length() > OverlayR2.length()){ //I believe the solution lies somewhere with the Overlay segment variables I created.
			return 1;
		}
      
		if(GeometryUtilities.distance(ptR1,left_vert) < GeometryUtilities.distance(ptR1_2, left_vert)){
			return -1;
		}

		return 0; //Return 0 to handle an inclusive case.
	}
}
