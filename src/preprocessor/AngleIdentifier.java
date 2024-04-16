package preprocessor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
		// for(Segment seg1 : _segments.keySet()){

		// 	for (Segment seg2 : _segments.values()){
		// 		Angle angle;

		// 		try {
		// 			angle = new Angle(seg1, seg2);
		// 		} 

		// 		catch (FactException e) {
		// 			angle = null;
		// 		}

		// 		if (angle != null) _angles.add(angle);
		// 	}
		// }


		ArrayList<Segment> keys = new ArrayList<Segment>(_segments.keySet());
		ArrayList<Segment> vals = new ArrayList<Segment>(_segments.values());

		for (int seg1 = 0; seg1 < keys.size(); seg1++){
			Segment segment1 = keys.get(seg1);

			for (int seg2 = seg1+1; seg2 < vals.size(); seg2++){
				Segment segment2 = vals.get(seg2);

				Angle angle;
				try{
					angle = new Angle(segment1, segment2);
				}

				catch (FactException e){
					angle = null;
				}

				if (angle != null) _angles.add(angle);

			}
		}
	}
}
