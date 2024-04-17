package preprocessor;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.Angle;
import geometry_objects.angle.AngleEquivalenceClasses;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import components.FigureNode;
import input.InputFacade;

class AngleIdentifierTest
{
	protected PointDatabase _points;
	protected Preprocessor _pp;
	protected Map<Segment, Segment> _segments;
	Map.Entry<PointDatabase, Set<Segment>> pair;
	
	protected void init(String filename)
	{
		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		FigureNode fig = InputFacade.extractFigure(figureStr);

		pair = InputFacade.toGeometryRepresentation(fig);

		_points = pair.getKey();

		_pp = new Preprocessor(_points, pair.getValue());

		_pp.analyze();

		_segments = _pp.getAllSegments();
	}
	
	//      A                                 
	//     / \                                
	//    B___C                               
	//   / \ / \                              
	//  /   X   \  X is not a specified point (it is implied) 
	// D_________E
	//
	// This figure contains 44 angles
	//
	@Test
	void test_crossing_symmetric_triangle()
	{
		init("crossing_symmetric_triangle.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles = angleIdentifier.getAngles();

		// The number of classes should equate to the number of 'minimal' angles
		assertEquals("Number of Angle Equivalence classes", 25, computedAngles.numClasses());
		
		//
		// ALL original segments: 8 in this figure.
		//
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));

		Segment bd = new Segment(_points.getPoint("B"), _points.getPoint("D"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));

		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));
		Segment cd = new Segment(_points.getPoint("C"), _points.getPoint("D"));

		//
		// Implied minimal segments: 4 in this figure.
		//
		Point a_star = _points.getPoint(3,3);

		Segment a_star_b = new Segment(a_star, _points.getPoint("B"));
		Segment a_star_c = new Segment(a_star, _points.getPoint("C"));
		Segment a_star_d = new Segment(a_star, _points.getPoint("D"));
		Segment a_star_e = new Segment(a_star, _points.getPoint("E"));

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));
		Segment ae = new Segment(_points.getPoint("A"), _points.getPoint("E"));

		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		try {
			//
			//
			// Angles broken down by equivalence class
			//
			//

			// Straight angles
			//
			expectedAngles.add(new Angle(a_star_b, a_star_e));
			
			expectedAngles.add(new Angle(ac, ce));
			
			expectedAngles.add(new Angle(a_star_d, a_star_c));
			
			expectedAngles.add(new Angle(ab, bd));
			
			// right angles
			//
			expectedAngles.add(new Angle(a_star_b, a_star_d));
			
			expectedAngles.add(new Angle(a_star_b, a_star_c));
			
			expectedAngles.add(new Angle(a_star_d, a_star_e));

			expectedAngles.add(new Angle(a_star_c, a_star_e));
			
			// 
			//
			expectedAngles.add(new Angle(a_star_b, ab));
			expectedAngles.add(new Angle(be, ab));

			expectedAngles.add(new Angle(a_star_b, bc));
			expectedAngles.add(new Angle(be, bc));

			expectedAngles.add(new Angle(a_star_b, bd));
			expectedAngles.add(new Angle(be, bd));
			
			expectedAngles.add(new Angle(ac, a_star_c));
			expectedAngles.add(new Angle(ac, cd));

			expectedAngles.add(new Angle(bc, a_star_c));
			expectedAngles.add(new Angle(cd, bc));
			
			expectedAngles.add(new Angle(a_star_c, ce));
			expectedAngles.add(new Angle(cd, ce));
			
			expectedAngles.add(new Angle(a_star_d, de));
			expectedAngles.add(new Angle(cd, de));
			
			expectedAngles.add(new Angle(bd, de));
			expectedAngles.add(new Angle(ad, de));
			
			expectedAngles.add(new Angle(a_star_e, de));
			expectedAngles.add(new Angle(be, de));

			expectedAngles.add(new Angle(ce, de));
			expectedAngles.add(new Angle(ae, de));
			
			// Larger equivalence classes
			//
			expectedAngles.add(new Angle(ac, ab));
			expectedAngles.add(new Angle(ab, ae));
			expectedAngles.add(new Angle(ad, ae));
			expectedAngles.add(new Angle(ac, ad));

			expectedAngles.add(new Angle(a_star_d, bd));
			expectedAngles.add(new Angle(cd, bd));
			expectedAngles.add(new Angle(ad, a_star_d));
			expectedAngles.add(new Angle(cd, ad));

			expectedAngles.add(new Angle(a_star_e, ce));
			expectedAngles.add(new Angle(be, ae));
			expectedAngles.add(new Angle(be, ce));
			expectedAngles.add(new Angle(a_star_e, ae));

			
			// More singletons
			//
			expectedAngles.add(new Angle(ac, bc));

			expectedAngles.add(new Angle(ab, bc));
			
			expectedAngles.add(new Angle(bc, bd));

			expectedAngles.add(new Angle(bc, ce));			
		}
		catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }		
		
		assertEquals(expectedAngles.size(), computedAngles.size());
		
		//
		// Equality
		//
		for (Angle expected : expectedAngles)
		{
			assertTrue(computedAngles.contains(expected));
		}
	}

	@Test
	void test_many_triangles(){

		init("many_triangles.json");

		AngleIdentifier angleIdentifier = new AngleIdentifier(_segments);

		AngleEquivalenceClasses computedAngles = angleIdentifier.getAngles();

		assertEquals("Number of Angle Equivalence classes", 44, computedAngles.numClasses());

		//System.out.println(computedAngles.toString());

		// for (Segment seg : computedAngles){
			
		// 	System.out.print(seg.getPoint1().getName() + " : ");
		// 	System.out.println(seg.getPoint2().getName());
			
		// }

		System.out.println();



		//Original segments from figure - 11 here
		Segment ac = new Segment(_points.getPoint("A"), _points.getPoint("C"));
		Segment ad = new Segment(_points.getPoint("A"), _points.getPoint("D"));

		Segment bc = new Segment(_points.getPoint("B"), _points.getPoint("C"));
		Segment be = new Segment(_points.getPoint("B"), _points.getPoint("E"));

		Segment cf = new Segment(_points.getPoint("C"), _points.getPoint("F"));

		Segment df = new Segment(_points.getPoint("D"), _points.getPoint("F"));
		Segment dc = new Segment(_points.getPoint("D"), _points.getPoint("C"));

		Segment ef = new Segment(_points.getPoint("E"), _points.getPoint("F"));
		Segment ec = new Segment(_points.getPoint("E"), _points.getPoint("C"));

		Segment fb = new Segment(_points.getPoint("F"), _points.getPoint("B"));
		Segment fa = new Segment(_points.getPoint("F"), _points.getPoint("A"));
	

		//
		// Implied minimal segments: 8 in this figure.
		//
		Point aStar = _points.getPoint(1.5, 0.0);
		Point bStar = _points.getPoint(-1.5, 0.0);

		Segment caStar = new Segment(_points.getPoint("C"), aStar);
		Segment bStarc = new Segment(bStar, _points.getPoint("C"));
		Segment bStarf = new Segment(bStar, _points.getPoint("F"));

		Segment faStar = new Segment(_points.getPoint("F"), aStar);
		Segment aStarb = new Segment(aStar, _points.getPoint("B"));
		Segment abStar = new Segment(_points.getPoint("A"), bStar);

		Segment dbStar = new Segment(_points.getPoint("D"), bStar);
		Segment aStare = new Segment(aStar, _points.getPoint("E"));

		Segment fc = new Segment(_points.getPoint("F"), _points.getPoint("C"));
		Segment ab = new Segment(_points.getPoint("A"), _points.getPoint("B"));
		Segment ce = new Segment(_points.getPoint("C"), _points.getPoint("E"));
		Segment da = new Segment(_points.getPoint("D"), _points.getPoint("C"));

		//
		// Non-minimal, computed segments: 2 in this figure.
		//
		Segment de = new Segment(_points.getPoint("D"), _points.getPoint("E"));
		Segment fd = new Segment(_points.getPoint("F"), _points.getPoint("D"));
		Segment cb = new Segment(_points.getPoint("C"), _points.getPoint("B"));
		Segment ca = new Segment(_points.getPoint("C"), _points.getPoint("A"));
		Segment fe = new Segment(_points.getPoint("F"), _points.getPoint("E"));
		

		//
		// Angles we expect to find
		//
		List<Angle> expectedAngles = new ArrayList<Angle>();
		// try {

		// 	expectedAngles.add(new Angle)
			
						
		// }
		// catch (FactException te) { System.err.println("Invalid Angles in Angle test."); }		
		
		// assertEquals(expectedAngles.size(), computedAngles.size());
		
		
		// //Equality
		
		// for (Angle expected : expectedAngles)
		// {
		// 	assertTrue(computedAngles.contains(expected));
		// }

	}
}
