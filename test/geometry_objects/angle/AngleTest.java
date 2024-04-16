package geometry_objects.angle;

import geometry_objects.Segment;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import exceptions.FactException;
import geometry_objects.points.Point;
import geometry_objects.angle.Angle;


public class AngleTest {

    @Test
    void testEquals() throws FactException {

        Point p1 = new Point(0, 0);
        Point p3 = new Point(2, 2);
        Point p5 = new Point(4, 0);

        Segment s1 = new Segment(p1, p3);
        Segment s2 = new Segment(p3, p5);
        Segment s3 = new Segment(p1, p3);
        Segment s4 = new Segment(p3, p5);
        
        Angle a1 = new Angle(s1, s2);
        Angle a2 = new Angle(s3, s4);

        assertTrue(a1.equals(a2));



        p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        p3 = new Point(2, 2);
        Point p4 = new Point(3, 1);
        p5 = new Point(4, 0);

        s1 = new Segment(p1, p3);
        s2 = new Segment(p3, p5);
        s3 = new Segment(p2, p3);
        s4 = new Segment(p3, p4);

        a1 = new Angle(s1, s2);
        a2 = new Angle(s3, s4);

        assertTrue(a1.equals(a2));
    }
}
