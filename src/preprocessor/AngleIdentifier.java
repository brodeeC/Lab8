/**
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 04/16/24
 * 
 * Creates an Equivalence Class of Angles. 
 */
package preprocessor;

import java.util.ArrayList;
import java.util.Map;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;

public class AngleIdentifier
{
	protected AngleEquivalenceClasses _angles;
	protected Map<Segment, Segment> _segments; // The set of ALL segments for this figure

	public AngleIdentifier(Map<Segment, Segment> segments)
	{
		_segments = segments;
	}

	/*
	 * Compute the figure triangles on the fly when requested; memoize results for subsequent calls.
	 */
	public AngleEquivalenceClasses getAngles()
	{
		if (_angles != null) return _angles;

		_angles = new AngleEquivalenceClasses();

		computeAngles();

		return _angles;
	}

	/**
	 * May need some sort of Math.Utilies to calculate the angle.
	 * Will definitely have to utilize the segment map. Maybe iterate through the list of segments calculating
	 * the angle and appending the calculated angle to the angle equivalnce class.
	 */
	private void computeAngles() 
	{
		//List to iterate through the segments
		ArrayList<Segment> keys = new ArrayList<Segment>(_segments.keySet());

		//Loop to get first segment
		for (int seg1 = 0; seg1 < keys.size(); seg1++){
			Segment segment1 = keys.get(seg1);

			//Loop to get second segment
			for (int seg2 = seg1+1; seg2 < keys.size(); seg2++){
				Segment segment2 = keys.get(seg2);

				Angle angle;
				try{
					//Create the angle
					angle = new Angle(segment1, segment2);
				}

				catch (FactException e){
					//If an exception is thrown set angle to null
					angle = null;
				}

				//Add angle too the Angle Equivalence Class if it was created
				if (angle != null) _angles.add(angle);

			}
		}
	}
}
