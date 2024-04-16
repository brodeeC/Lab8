package geometry_objects.angle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

public class AngleLinkedEquivalenceClassTest {

    @Test
    public void testProperConstruction(){
        Point point0 = new Point(0.0, 5.0);
        Point point1 = new Point(1.0, 5.0);
        Point point2 = new Point(5.0, 5.0);
        Point point3 = new Point(5.0, 10.0);
        Point point4 = new Point(8.0, 8.0);
        Point point5 = new Point(5.0, 11.0);
        Point point6 = new Point(5.0, 12.0);

        Segment seg1 = new Segment(point1, point2);
        Segment seg2 = new Segment(point2, point3);

        Segment seg3 = new Segment(point2, point4);

        Segment seg4 = new Segment(point0,point2);

        Segment seg5 = new Segment(point2, point5);

        Segment seg6 = new Segment(point2, point6);

        try {
            Angle angle1 = new Angle(seg1, seg2); //Canonical Angle
            Angle angle2 = new Angle(seg4, seg2);
            Angle angle3 = new Angle(seg1, seg5);
            Angle angle4 = new Angle(seg4, seg6);
            
            Angle angle5 = new Angle(seg1, seg3); //Shouldn't be added

            AngleLinkedEquivalenceClass lec = new AngleLinkedEquivalenceClass(new AngleStructureComparator());

            lec.add(angle1);
            lec.add(angle2);
            lec.add(angle3);
            lec.add(angle4);

            lec.add(angle5);

            assertFalse(lec.contains(angle5));
            
            assertTrue(lec.canonical().equals(angle1));

            assertEquals(lec.size(), 4);


        } catch (FactException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
