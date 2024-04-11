package preprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import components.FigureNode;
import preprocessor.delegates.ImplicitPointPreprocessor;

class PreprocessorTest
{
	@Test
	void test_implicit_crossings()
	{
		String figureStr = utilities.io.FileUtilities.readFileFilterComments("fully_connected_irregular_polygon.json");
		                    
		FigureNode fig = InputFacade.extractFigure(figureStr);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		// 5 new implied points inside the pentagon
		Set<Point> iPoints = ImplicitPointPreprocessor.compute(points, new ArrayList<Segment>(segments));
		assertEquals(5, iPoints.size());

		//
		//
		//		               D(3, 7)
		//
		//
		//   E(-2,4)       D*      E*
		//		         C*          A*       C(6, 3)
		//                      B*
		//		       A(2,0)        B(4, 0)
		//
		//		    An irregular pentagon with 5 C 2 = 10 segments

		Point a_star = new Point(56.0 / 15, 28.0 / 15);
		Point b_star = new Point(16.0 / 7, 8.0 / 7);
		Point c_star = new Point(8.0 / 9, 56.0 / 27);
		Point d_star = new Point(90.0 / 59, 210.0 / 59);
		Point e_star = new Point(194.0 / 55, 182.0 / 55);

		assertTrue(iPoints.contains(a_star));
		assertTrue(iPoints.contains(b_star));
		assertTrue(iPoints.contains(c_star));
		assertTrue(iPoints.contains(d_star));
		assertTrue(iPoints.contains(e_star));

		//
		// There are 15 implied segments inside the pentagon; see figure above
		//
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(iPoints);
		assertEquals(15, iSegments.size());

		List<Segment> expectedISegments = new ArrayList<Segment>();

		expectedISegments.add(new Segment(points.getPoint("A"), c_star));
		expectedISegments.add(new Segment(points.getPoint("A"), b_star));

		expectedISegments.add(new Segment(points.getPoint("B"), b_star));
		expectedISegments.add(new Segment(points.getPoint("B"), a_star));

		expectedISegments.add(new Segment(points.getPoint("C"), a_star));
		expectedISegments.add(new Segment(points.getPoint("C"), e_star));

		expectedISegments.add(new Segment(points.getPoint("D"), d_star));
		expectedISegments.add(new Segment(points.getPoint("D"), e_star));

		expectedISegments.add(new Segment(points.getPoint("E"), c_star));
		expectedISegments.add(new Segment(points.getPoint("E"), d_star));

		expectedISegments.add(new Segment(c_star, b_star));
		expectedISegments.add(new Segment(b_star, a_star));
		expectedISegments.add(new Segment(a_star, e_star));
		expectedISegments.add(new Segment(e_star, d_star));
		expectedISegments.add(new Segment(d_star, c_star));

		for (Segment iSegment : iSegments)
		{
			assertTrue(expectedISegments.contains(iSegment));
		}

		//
		// Ensure we have ALL minimal segments: 20 in this figure.
		//
		List<Segment> expectedMinimalSegments = new ArrayList<Segment>(iSegments);
		expectedMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("B")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("C")));
		expectedMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("D")));
		expectedMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("E")));
		expectedMinimalSegments.add(new Segment(points.getPoint("E"), points.getPoint("A")));
		
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(iPoints, segments, iSegments);
		assertEquals(expectedMinimalSegments.size(), minimalSegments.size());

		for (Segment minimalSeg : minimalSegments)
		{
			assertTrue(expectedMinimalSegments.contains(minimalSeg));
		}
		
		//
		// Construct ALL figure segments from the base segments
		//
		Set<Segment> computedNonMinimalSegments = pp.constructAllNonMinimalSegments(minimalSegments);
		
		//
		// All Segments will consist of the new 15 non-minimal segments.
		//
		assertEquals(15, computedNonMinimalSegments.size()); 

		//
		// Ensure we have ALL minimal segments: 20 in this figure.
		//
		List<Segment> expectedNonMinimalSegments = new ArrayList<Segment>();
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), d_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), c_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("D")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), c_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), b_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("E")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), d_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), e_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("E")));		

		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), a_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), b_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("C")));
		
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), e_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), a_star));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("D")));
		
		//
		// Check size and content equality
		//
		assertEquals(expectedNonMinimalSegments.size(), computedNonMinimalSegments.size());
		for (Segment computedNonMinimalSegment : computedNonMinimalSegments)
		{
			assertTrue(expectedNonMinimalSegments.contains(computedNonMinimalSegment));
		}
		
	}


	/**
	 * Test that all the implicit base segments are calculated correctly.
	 */
	@Test
	void testComputeImplicitBaseSegments(){
		String figureStr = utilities.io.FileUtilities.readFileFilterComments("Irregular_polygon.json");
		                    
		FigureNode fig = InputFacade.extractFigure(figureStr);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		//Compute implicit points and ensure the one's I have are correct
		Set<Point> iPoints = ImplicitPointPreprocessor.compute(points, new ArrayList<Segment>(segments));
		
		Point aStar = new Point(27.0 / 8.0, 59.0 / 8.0);
		Point bStar = new Point(4.5, 5.0);
		Point cStar = new Point(23.0 / 5.0, 16.0 / 3.0);
		Point dStar = new Point(129.0 / 32.0, 55.0 / 16.0);
		Point eStar = new Point(3.0, 11.0 / 3.0);

		iPoints.contains(aStar);
		iPoints.contains(bStar);
		iPoints.contains(cStar);
		iPoints.contains(dStar);
		iPoints.contains(eStar);

		//Getting implied Segments
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(iPoints);
		assertEquals(iSegments.size(), 17);

		//Creating expected Segments
		List<Segment> expectedISegments = new ArrayList<Segment>();
		expectedISegments.add(new Segment(points.getPoint("A"), aStar));
		expectedISegments.add(new Segment(points.getPoint("D"), aStar));
		expectedISegments.add(new Segment(points.getPoint("D"), cStar));

		expectedISegments.add(new Segment(points.getPoint("C"), dStar));
		expectedISegments.add(new Segment(points.getPoint("E"), bStar));
		expectedISegments.add(new Segment(points.getPoint("A"), bStar));

		expectedISegments.add(new Segment(points.getPoint("E"), cStar));
		expectedISegments.add(new Segment(points.getPoint("A"), eStar));
		expectedISegments.add(new Segment(points.getPoint("F"), aStar));

		expectedISegments.add(new Segment(points.getPoint("C"), eStar));
		expectedISegments.add(new Segment(points.getPoint("F"), eStar));
		expectedISegments.add(new Segment(points.getPoint("E"), dStar));
		expectedISegments.add(new Segment(points.getPoint("B"), eStar));

		expectedISegments.add(new Segment(aStar, cStar));
		expectedISegments.add(new Segment(bStar, cStar));
		expectedISegments.add(new Segment(dStar, bStar));
		expectedISegments.add(new Segment(eStar, dStar));
		
		
		assertEquals(iSegments.size(), expectedISegments.size());

		//Making sure that all segments are present
		for (Segment iSegment : iSegments){
			assertTrue(expectedISegments.contains(iSegment));

		}
	}

	/**
	 * Test to make sure implicit Segments are created properly
	 */
	@Test
	void testMakeSegments(){

		//Making a sorted set of points
		SortedSet<Point> pointsSet = new TreeSet<>();
		Point p1 = new Point(1, 1);
		Point p2 = new Point(2, 2);
		Point p3 = new Point (3, 3);

		pointsSet.add(p1);
		pointsSet.add(p2);
		pointsSet.add(p3);

		Preprocessor preprocessor = new Preprocessor(new PointDatabase(), new HashSet<Segment>()); 

		Set<Segment> segment = preprocessor.makeSegments(pointsSet);

		//List of segments to confirm segments exist
		ArrayList<Segment> pts = new ArrayList<>();
		pts.add(new Segment(p1, p2));
		pts.add(new Segment(p2, p3));

		for (Segment givenSegment : segment) {
			assertTrue(pts.contains(givenSegment));
		}

		assertEquals(segment.size(), pts.size());

	}

	/**
	 * Test to ensure preprocessor can identify all minimal segments
	 */
	@Test
	void testIdentifyAllMinimalSegments(){
		String figureStr = utilities.io.FileUtilities.readFileFilterComments("collinear_line_segments.json");
		                    
		FigureNode fig = InputFacade.extractFigure(figureStr);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		//Getting implicit points and segments
		Set<Point> iPoints = ImplicitPointPreprocessor.compute(points, new ArrayList<Segment>(segments));
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(iPoints);

		//Getting minimal segments
		Set<Segment> minSegs = pp.identifyAllMinimalSegments(iPoints, segments, iSegments);

		assertEquals(minSegs.size(), 5);

		//Checking if minimal Segments are the correct ones
		List<Segment> expectedMinSegs = new ArrayList<Segment>();
		expectedMinSegs.add(new Segment(points.getPoint("A"), points.getPoint("B")));
		expectedMinSegs.add(new Segment(points.getPoint("B"), points.getPoint("C")));
		expectedMinSegs.add(new Segment(points.getPoint("D"), points.getPoint("E")));
		expectedMinSegs.add(new Segment(points.getPoint("C"), points.getPoint("D")));
		expectedMinSegs.add(new Segment(points.getPoint("E"), points.getPoint("F")));

		for (Segment minSeg : minSegs){
			assertTrue(expectedMinSegs.contains(minSeg));
		}

	}

	/**
	 * Test to ensure preprocessor properly constructs all non Minimal Segments
	 */
	@Test
	void testConstructAllNonMinimalSegments(){
		String figureStr = utilities.io.FileUtilities.readFileFilterComments("collinear_line_segments.json");
		                    
		FigureNode fig = InputFacade.extractFigure(figureStr);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		//Getting implicit points and segments and minimal segments
		Set<Point> iPoints = ImplicitPointPreprocessor.compute(points, new ArrayList<Segment>(segments));
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(iPoints);
		Set<Segment> minSegs = pp.identifyAllMinimalSegments(iPoints, segments, iSegments);

		//Getting nonMinimal Segments
		Set<Segment> nonMinSegs = pp.constructAllNonMinimalSegments(minSegs);

		assertEquals(nonMinSegs.size(), 10);

		//Checking to see if all nonMinimal Segments are present
		List<Segment> expectedNonMin = new ArrayList<Segment>();
		expectedNonMin.add(new Segment(points.getPoint("D"), points.getPoint("F")));
		expectedNonMin.add(new Segment(points.getPoint("B"), points.getPoint("E")));
		expectedNonMin.add(new Segment(points.getPoint("A"), points.getPoint("E")));
		expectedNonMin.add(new Segment(points.getPoint("A"), points.getPoint("C")));
		expectedNonMin.add(new Segment(points.getPoint("C"), points.getPoint("E")));
		expectedNonMin.add(new Segment(points.getPoint("B"), points.getPoint("D")));
		expectedNonMin.add(new Segment(points.getPoint("A"), points.getPoint("D")));
		expectedNonMin.add(new Segment(points.getPoint("B"), points.getPoint("F")));
		expectedNonMin.add(new Segment(points.getPoint("A"), points.getPoint("F")));
		expectedNonMin.add(new Segment(points.getPoint("C"), points.getPoint("F")));

		for (Segment nonMin : nonMinSegs){
			assertTrue(expectedNonMin.contains(nonMin));
		}

	}

	/**
	 * Test to make sure combineToNewSegment works as intended
	 */
	@Test
	void testCombineToNewSegment(){
		Preprocessor pp = new Preprocessor(new PointDatabase(), new HashSet<Segment>());

		Point pt1 = new Point(1.0, 1.0);
		Point pt2 = new Point(2.0, 1.0);
		Point pt3 = new Point(3.0, 1.0);
		Point pt4 = new Point(10.0,10.0);

		//Combining segments that will create a new one
		Segment left = new Segment(pt1, pt2);
		Segment right = new Segment(pt2, pt3);

		Segment comSeg = pp.combineToNewSegment(left, right);

		assertTrue(comSeg.equals(new Segment(new Point(1.0, 1.0), new Point(3.0, 1.0))));

		//Combining segments that won't make a new one
		left = new Segment(pt1, pt2);
		right = new Segment(pt3, pt4);

		comSeg = pp.combineToNewSegment(left,right);

		assertEquals(comSeg, null);

	}

	/**
	 * Test method that tests the preprocessor class using the file crossing_symmetric_triangle.json
	 */
	@Test
	void testAnalyze(){

		String figureStr = utilities.io.FileUtilities.readFileFilterComments("crossing_symmetric_triangle.json");
		                    
		FigureNode fig = InputFacade.extractFigure(figureStr);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		Set<Point> iPoints = ImplicitPointPreprocessor.compute(points, new ArrayList<Segment>(segments));

		//Only 1 iPoint in this figure
		assertEquals(iPoints.size(), 1);
		
		Point x = new Point(3.0, 3.0);

		assertTrue(iPoints.contains(x));

		//Getting implied Segments
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(iPoints);
		assertEquals(iSegments.size(), 4);


		List<Segment> expectedISegments = new ArrayList<Segment>();
		expectedISegments.add(new Segment(points.getPoint("D"), x));
		expectedISegments.add(new Segment(x, points.getPoint("C")));
		expectedISegments.add(new Segment(points.getPoint("B"), x));
		expectedISegments.add(new Segment(x, points.getPoint("E")));


		//Making sure that all segments are present
		for (Segment iSegment : iSegments){
			expectedISegments.contains(iSegment);
		}

		//Expected minimal segments
		List<Segment> expectedMinimalSegments = new ArrayList<Segment>(iSegments);
		expectedMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("B")));
		expectedMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("C")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("A")));
		expectedMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("E")));
		expectedMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("E")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("C")));
		
		
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(iPoints, segments, iSegments);

		//Checking content and size
		assertEquals(expectedMinimalSegments.size(), minimalSegments.size());
		for (Segment minimalSeg : minimalSegments)
		{
			assertTrue(expectedMinimalSegments.contains(minimalSeg));
		}


		Set<Segment> computedNonMinimalSegments = pp.constructAllNonMinimalSegments(minimalSegments);
		
		//Only 4 nonMinimal Segments
		assertEquals(computedNonMinimalSegments.size(), 4); 


		List<Segment> expectedNonMinimalSegments = new ArrayList<Segment>();
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("C")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), points.getPoint("A")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("E")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("E")));
				

		//Checking content and size
		assertEquals(expectedNonMinimalSegments.size(), computedNonMinimalSegments.size());
		for (Segment computedNonMinimalSegment : computedNonMinimalSegments)
		{
			assertTrue(expectedNonMinimalSegments.contains(computedNonMinimalSegment));
		}

	}

	/**
	 * Test method that tests the preprocessor class using the file Irregular_polygon.json
	 */
	@Test
	void testIrregularPolygon(){
		String figureStr = utilities.io.FileUtilities.readFileFilterComments("Irregular_polygon.json");
		                    
		FigureNode fig = InputFacade.extractFigure(figureStr);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();

		Preprocessor pp = new Preprocessor(points, segments);

		Set<Point> iPoints = ImplicitPointPreprocessor.compute(points, new ArrayList<Segment>(segments));

		//5 implicit points
		assertEquals(iPoints.size(), 5);
		
		Point aStar = new Point(27.0 / 8.0, 59.0 / 8.0);
		Point bStar = new Point(4.5, 5.0);
		Point cStar = new Point(23.0 / 5.0, 16.0 / 3.0);
		Point dStar = new Point(129.0 / 32.0, 55.0 / 16.0);
		Point eStar = new Point(3.0, 11.0 / 3.0);

		iPoints.contains(aStar);
		iPoints.contains(bStar);
		iPoints.contains(cStar);
		iPoints.contains(dStar);
		iPoints.contains(eStar);

		//Getting implied Segments
		Set<Segment> iSegments = pp.computeImplicitBaseSegments(iPoints);

		//17 implied segments
		assertEquals(iSegments.size(), 17);

		List<Segment> expectedISegments = new ArrayList<Segment>();
		expectedISegments.add(new Segment(points.getPoint("A"), aStar));
		expectedISegments.add(new Segment(points.getPoint("D"), aStar));
		expectedISegments.add(new Segment(points.getPoint("D"), cStar));

		expectedISegments.add(new Segment(points.getPoint("C"), dStar));
		expectedISegments.add(new Segment(points.getPoint("E"), bStar));
		expectedISegments.add(new Segment(points.getPoint("A"), bStar));

		expectedISegments.add(new Segment(points.getPoint("E"), cStar));
		expectedISegments.add(new Segment(points.getPoint("A"), eStar));
		expectedISegments.add(new Segment(points.getPoint("F"), aStar));

		expectedISegments.add(new Segment(points.getPoint("C"), eStar));
		expectedISegments.add(new Segment(points.getPoint("F"), eStar));
		expectedISegments.add(new Segment(points.getPoint("E"), dStar));
		expectedISegments.add(new Segment(points.getPoint("B"), eStar));

		expectedISegments.add(new Segment(aStar, cStar));
		expectedISegments.add(new Segment(bStar, cStar));
		expectedISegments.add(new Segment(dStar, bStar));
		expectedISegments.add(new Segment(eStar, dStar));
		
		
		assertEquals(iSegments.size(), expectedISegments.size());

		//Making sure that all segments are present
		for (Segment iSegment : iSegments){
			assertTrue(expectedISegments.contains(iSegment));
		}

		List<Segment> expectedMinimalSegments = new ArrayList<Segment>(iSegments);
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("A")));
		expectedMinimalSegments.add(new Segment(points.getPoint("F"), points.getPoint("D")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("C")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("F")));
		expectedMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("E")));
		expectedMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("F")));
		expectedMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("D")));
		expectedMinimalSegments.add(new Segment(points.getPoint("E"), points.getPoint("D")));
		expectedMinimalSegments.remove(new Segment(points.getPoint("F"), eStar));
		
		
		Set<Segment> minimalSegments = pp.identifyAllMinimalSegments(iPoints, segments, iSegments);

		//24 minimal Segments
		assertEquals(minimalSegments.size(), 24);

		//Checking content and size
		assertEquals(expectedMinimalSegments.size(), minimalSegments.size());
		for (Segment minimalSeg : minimalSegments)
		{
			assertTrue(expectedMinimalSegments.contains(minimalSeg));
		}

		Set<Segment> computedNonMinimalSegments = pp.constructAllNonMinimalSegments(minimalSegments);
		
		//17 nonMinimal Segments
		assertEquals(computedNonMinimalSegments.size(), 17); 


		List<Segment> expectedNonMinimalSegments = new ArrayList<Segment>();
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("D")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("A"), points.getPoint("E")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("D")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("F"), points.getPoint("E")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("A")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), points.getPoint("F")));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), points.getPoint("E")));

		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), bStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), aStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), bStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("F"), eStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("E"), eStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("B"), dStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("D"), dStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("F"), cStar));
		expectedNonMinimalSegments.add(new Segment(points.getPoint("C"), cStar));
		
		
		expectedNonMinimalSegments.add(new Segment(dStar, cStar));
		
		//Checking content and size
		assertEquals(expectedNonMinimalSegments.size(), computedNonMinimalSegments.size());
		for (Segment computedNonMinimalSegment : computedNonMinimalSegments)
		{
			assertTrue(expectedNonMinimalSegments.contains(computedNonMinimalSegment));
		}
	}


}