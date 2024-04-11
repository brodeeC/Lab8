package preprocessor;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import components.FigureNode;
import geometry_objects.Segment;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import input.InputFacade;
import preprocessor.delegates.ImplicitPointPreprocessor;
/**
 * Test class for ImplicitPointPreprocessor
 * 
 * @author Kyler, Brodee, and Collin
 * @Date   April 3, 2024
 *  
 */

public class ImplicitPointPreprocessorTest {

    
    

    /**
     * Builder method that constructs PointDatabase. 
     * @return 
     *   
     */
    public PointDatabase PointDatabasebuilder() {
        PointDatabase pointDatabase = new PointDatabase();
        return pointDatabase;
    }
    

    /**
     * Test the construction of segments list, pointdatabase, and set of points for the implicit points.
     */
    @Test
    public void testConstruction(){
        List<Segment> segments    =  new ArrayList<>();
        PointDatabase points      =  PointDatabasebuilder();
        Set<Point>    iPoints     =  new LinkedHashSet<>();

        iPoints = ImplicitPointPreprocessor.compute(points, segments);

		
        assertEquals("iPoints contains an implicit points.",0, iPoints.size());
        //assertEquals(iPoints.toString(),"");

    }

    /**
     * Basic test that sees if implicit points preprocessor can detect that there is an implicit point between the
     * two given segements.
     */
    @Test
    public void testTwoSegments(){
        List<Segment> segments    =  new ArrayList<>();
        PointDatabase points      =  PointDatabasebuilder();
        Set<Point>    iPoints     =  new LinkedHashSet<>();

        //Creation of sample points and segments.
        //Insertion at point (1,1).
        segments.add(new Segment(new Point(0.111,0.2435579),new Point(2.12121,2.342343)));   
        segments.add(new Segment(new Point(1.2132,1.434343),new Point(2.93232,3.11)));      


        //Creation of set points that utilzes the implicitPointPreprocessor to compute the number of points.
        iPoints = ImplicitPointPreprocessor.compute(points, segments);

        assertEquals("iPoints does not contain 1 implicit point.",1,iPoints.size());

    }

    /**
     * Working on segments to test that will give me an implicit point.
     */
    @Test
    public void testWithMultipleSegments(){
        List<Segment> segments    =  new ArrayList<>();
        PointDatabase points      =  PointDatabasebuilder();
        Set<Point>    iPoints     =  new LinkedHashSet<>();

        //Creation of sample points and segments.
        //Implicit Point located at (1,1).
        segments.add(new Segment(new Point(0.111,0.2435579),new Point(2.12121,2.342343)));
        segments.add(new Segment(new Point(1.2132,1.434343),new Point(2.93232,3.11)));      
        
        //Intersection at the point (1,1) and (3,1). Making sure that an implicit point is not counted twice.
        segments.add(new Segment(new Point(1.111, 1.232),new Point(4.9389, 1.3232)));
        segments.add(new Segment(new Point(3.0, 1.101001),new Point(3.33333, 2.23232322)));

        //Lone segment that does not intersect with any segment should not have an intersection.
        segments.add(new Segment(new Point(2.323, -2.4343), new Point(5.43434, -3.2323)));


        //Creation of set points that utilzes the implicitPointPreprocessor to compute the number of points.
        iPoints = ImplicitPointPreprocessor.compute(points, segments);

        assertEquals("iPoints does not contain that set number of implicit points.",2,iPoints.size());

    }


    /**
     * Test that if multiple segments intersect at a given point, only one point is returned. 
     */
    @Test
    public void testIntersectionsAtOnePoint(){
        List<Segment> segments    =  new ArrayList<>();
        PointDatabase points      =  PointDatabasebuilder();
        Set<Point>    iPoints     =  new LinkedHashSet<>();

        //Intersection at point (1,1).
        segments.add(new Segment(new Point(0.111,0.2435579),new Point(2.12121,2.342343)));
        segments.add(new Segment(new Point(1.2132,1.434343),new Point(2.93232,3.11)));
        segments.add(new Segment(new Point(1.0421, 1.0), new Point(4.0111, 1.0121)));

        
        //Creation of set  that utilzes the implicitPointPreprocessor to compute the one point.
        iPoints = ImplicitPointPreprocessor.compute(points, segments);

        assertEquals("Inaccurate number of points.",1, iPoints.size());

    }

    /**
     * Test to see that the implicit points are contained within the set.
     */
    @Test
    public void testImplicitPointSetContents(){
        String figureStr = utilities.io.FileUtilities.readFileFilterComments("fully_connected_irregular_polygon.json");
		                    
		FigureNode fig = InputFacade.extractFigure(figureStr);

		Map.Entry<PointDatabase, Set<Segment>> pair = InputFacade.toGeometryRepresentation(fig);

		PointDatabase points = pair.getKey();

		Set<Segment> segments = pair.getValue();
        
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
    }

}