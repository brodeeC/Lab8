package geometry_objects.angle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

/**
 * Test the functionality of the AngleStructureComparator class. When given to angles to compare.
 * 
 * @author Kyler, Brodee, & Collin
 * @date April 20, 2024
 * 
 */

public class AngleStructureComparatorTest {

    private AngleStructureComparator comparator;


    public void setUp() {
        comparator = new AngleStructureComparator();
    }

    /**
     * Test the compare method in the class.
     */
    @Test
    public void testCompare() {
        setUp();

        //Creation of points.
        Point point0 = new Point(0.0, 5.0);
        Point point1 = new Point(1.0, 5.0);
        Point point2 = new Point(5.0, 5.0);
        Point point3 = new Point(5.0, 10.0);
        Point point5 = new Point(5.0, 11.0);
        Point point6 = new Point(5.0, 12.0);

        Point point4 = new Point(8.0, 8.0);
        Point point7 = new Point(7.0, 7.0);
        Point point8 = new Point(9.0, 9.0);
        Point point9 = new Point(10.0, 10.0);

        //Creation of the segments.
        Segment seg1 = new Segment(point1, point2);
        Segment seg2 = new Segment(point2, point3);
        Segment seg3 = new Segment(point2, point4);
        Segment seg4 = new Segment(point0,point2);
        Segment seg5 = new Segment(point2, point5);
        Segment seg6 = new Segment(point2, point6);
        Segment seg7 = new Segment(point2, point7);
        Segment seg8 = new Segment(point2, point8);
        Segment seg9 = new Segment(point2, point9);

        
        // Creating angles, use of segments instead.
        try{
            Angle angleBAE = new Angle(seg1,seg5);
            Angle angleCAE = new Angle(seg1,seg6);
            Angle angleDAF = new Angle(seg1,seg7);
            Angle angleCAF = new Angle(seg1,seg8);

            // Asserting comparisons
            assertEquals(-1, comparator.compare(angleBAE, angleCAE));
            assertEquals(-1, comparator.compare(angleBAE, angleDAF));
            assertEquals(1, comparator.compare(angleCAE, angleDAF));
            assertEquals(AngleStructureComparator.STRUCTURALLY_INCOMPARABLE, comparator.compare(angleCAE, angleCAF));

        } catch(FactException e){
            e.printStackTrace();
        }
        
    }

}
