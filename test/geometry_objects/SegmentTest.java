/* 
 * Tests three main methods created, checks vertical, horizontal, & diagonal.
 * 
 * @author Brodee, Kyler, and Collin
 * @date 4/2/2024
 */

package geometry_objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import geometry_objects.points.Point;

public class SegmentTest {

    @Test
    void testHasSubSegment() {

        Segment segment = new Segment(new Point(1,1), new Point(5.789, 5.789));
        Segment segment2 = new Segment(new Point(2.22,2.22), new Point(3, 3));
        assertTrue(segment.HasSubSegment(segment2));

        segment = new Segment(new Point(1, 1), new Point(1, 5));
        segment2 = new Segment(new Point(1,2.22), new Point(1, 4.082));
        assertTrue(segment.HasSubSegment(segment2));

        segment = new Segment(new Point(1.00001, 5), new Point(1.00001, 5));
        segment2 = new Segment(new Point(1.00001,5), new Point(1.00001, 5));
        assertTrue(segment.HasSubSegment(segment2));

        segment = new Segment(new Point(.0934, 5), new Point(7, 5));
        segment2 = new Segment(new Point(3,5), new Point(9, 5));
        assertFalse(segment.HasSubSegment(segment2));
    }

    @Test
    void testCoincideWithoutOverlap() {  
        
        Segment segment = new Segment(new Point(1,5), new Point(4, 5));
        Segment segment2 = new Segment(new Point(4,5), new Point(8, 5));
        assertTrue(segment.coincideWithoutOverlap(segment2));

        segment = new Segment(new Point(2.093,.093), new Point(2.093, 4.67));
        segment2 = new Segment(new Point(2.093,9.80), new Point(2.093, 13.45));
        assertTrue(segment.coincideWithoutOverlap(segment2));

        segment = new Segment(new Point(9,9.2), new Point(11,9.2));
        segment2 = new Segment(new Point(4.001,9.2), new Point(8.9999,9.2));
        assertTrue(segment.coincideWithoutOverlap(segment2));

        segment = new Segment(new Point(9,9), new Point(12,9));
        segment2 = new Segment(new Point(9,4), new Point(12,4));
        assertFalse(segment.coincideWithoutOverlap(segment2));

    }

    @Test
    void testCollectOrderedPointsOnSegment() {

        Point p = new Point(2,9.2);
        Point p2 = new Point(14,9.2);

        Segment segment = new Segment(p, p2);  
        
        Point p3 = new Point(3, 9.2);
        Point p4 = new Point(11,9.2);
        Point p5 = new Point(14,8.1);

        Set<Point> pointsSet = new HashSet<Point>();
        pointsSet.add(p);
        pointsSet.add(p2);
        pointsSet.add(p3);
        pointsSet.add(p4);
        pointsSet.add(p5);

        SortedSet<Point> pointsOn = segment.collectOrderedPointsOnSegment(pointsSet);

        assertEquals(5, pointsSet.size());
        assertEquals(4, pointsOn.size());

        

        p = new Point(1.0001, 1.0001);
        p2 = new Point(7.0001,7.0001);

        segment = new Segment(p, p2);  

        Set<Point> pointsSet2 = new HashSet<Point>();
        pointsSet2.add(p);
        pointsSet2.add(p2);
        pointsSet2.add(p3);
        pointsSet2.add(p4);
        pointsSet2.add(p5);

        SortedSet<Point> pointsOn2 = segment.collectOrderedPointsOnSegment(pointsSet2);

        assertEquals(5, pointsSet2.size());
        assertEquals(2, pointsOn2.size());  
        
    

        p = new Point(0, 1.00001);
        p2 = new Point(0,5.00001);

        segment = new Segment(p, p2); 
        
        p3 = new Point(0, 3.0495);
        p4 = new Point(0,5.00002);
        p5 = new Point(0,1.9874);

        Set<Point> pointsSet3 = new HashSet<Point>();
        pointsSet3.add(p);
        pointsSet3.add(p2);
        pointsSet3.add(p3);
        pointsSet3.add(p4);
        pointsSet3.add(p5);

        SortedSet<Point> pointsOn3 = segment.collectOrderedPointsOnSegment(pointsSet3);

        assertEquals(5, pointsSet3.size());
        assertEquals(4, pointsOn3.size());  

    }
}
