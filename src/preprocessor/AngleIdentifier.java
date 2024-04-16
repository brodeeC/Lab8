package preprocessor;

import java.util.List;
import java.util.Map;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import utilities.math.MathUtilities;

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
(
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
		// for(int seg1 = 0; seg1 < _segments.size(); seg1++){
		// 	Segment segment1 = _segments.get(seg1);

		// 	for(int seg2 = 1; seg2 < _segments.size(); seg2++){
		// 		Segment segment2 = _segments.get(seg2);

		// 		Angle angle = new Angle(segment1,segment2);

		// 		if (angle != null) _angles.add(angle);
		// 	}
		// }

		for(Segment seg1 : _segments.keySet()){

			for (Segment seg2 : _segments.values()){
				Angle angle;

				try {
					angle = new Angle(seg1, seg2);
				} 

				catch (FactException e) {
					angle = null;
				}

				if (angle != null) _angles.add(angle);
			}
		}

		
	}
}
