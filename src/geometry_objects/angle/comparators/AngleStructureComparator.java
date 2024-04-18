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

		//Gets the overlaying segments from the left ray 1 and left ray 2.
		Segment OverlayR1 = right.overlayingRay(left.getRay1());
		Segment OverlayR2 = right.overlayingRay(left.getRay2());
		

		//If the angles does not share the same vertex then it is struturally incomparable.
		if (!left_vert.equals(right_vert)) {
			return STRUCTURALLY_INCOMPARABLE;
		}

		//If the left does not overlay with the right then it is structurally incomparable.
		if(!left.overlays(right)){
			return STRUCTURALLY_INCOMPARABLE;
		}


		//If either of the Overlays are null just return Structurally incomparable because they are incomparable. 
		if(OverlayR1 == null || OverlayR2 == null){
			return STRUCTURALLY_INCOMPARABLE;
		}

		Point ptR1 = OverlayR1.getPoint1();
		Point ptR2 = OverlayR2.getPoint1();

		Point ptR1_2 = OverlayR1.getPoint2();
		Point ptR2_2 = OverlayR2.getPoint2();

		/**
		 * At into this line of code, so you can properly comment this line.
		 */
		if(GeometryUtilities.between(ptR1,left_vert,ptR1_2) == GeometryUtilities.between(ptR2, right_vert, ptR2_2)){
			return 1;
		}  
		
		
		//When both of the left angles segments are greater or equal the right angles segments return 1.
		if(left.getRay1().length() >= right.getRay1().length() && 
		   left.getRay2().length() >= right.getRay2().length()){
			return 1;
		}

		//When both of the left angles segments are less than the right angles segments return -1.
		if(left.getRay1().length() < right.getRay1().length() && 
		   left.getRay2().length() < right.getRay2().length()){
			return -1;
		}

		//When the structure is comparable but one of the left angles is greater than one right segments
		//and the one of the right segments is greater than one of the left segments return 0 since it is inclusive.
		if(left.getRay1().length() > right.getRay1().length() && 
		   left.getRay2().length() < right.getRay2().length()){
			return 0;
		}

		//When the structure is comparable but one of the left angles is greater than one right segments
		//and the one of the right segments is greater than one of the left segments return 0 since it is inclusive.
		if(left.getRay1().length() < right.getRay1().length() && 
		   left.getRay2().length() > right.getRay2().length()){
			return 0;
		}

		return STRUCTURALLY_INCOMPARABLE; //Return STRUCTURALLY Incomparable as a fail safe.
	}
}
