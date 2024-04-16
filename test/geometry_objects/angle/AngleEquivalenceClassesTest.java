package geometry_objects.angle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

public class AngleEquivalenceClassesTest {


    @Test
    public void testProperConstruction(){

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

        Segment seg1 = new Segment(point1, point2);
        Segment seg2 = new Segment(point2, point3);

        Segment seg3 = new Segment(point2, point4);

        Segment seg4 = new Segment(point0,point2);

        Segment seg5 = new Segment(point2, point5);

        Segment seg6 = new Segment(point2, point6);

        Segment seg7 = new Segment(point2, point7);
        Segment seg8 = new Segment(point2, point8);
        Segment seg9 = new Segment(point2, point9);

        try {
            Angle angle1 = new Angle(seg1, seg2); //Canonical Angle of 1 class
            Angle angle2 = new Angle(seg4, seg2);
            Angle angle3 = new Angle(seg1, seg5);
            Angle angle4 = new Angle(seg4, seg6);
            
            Angle angle5 = new Angle(seg1, seg3); //Canonical of second class
            Angle angle6 = new Angle(seg1, seg7);
            Angle angle7 = new Angle(seg1, seg8);
            Angle angle8 = new Angle(seg1, seg9);

            AngleEquivalenceClasses aec = new AngleEquivalenceClasses(new AngleStructureComparator());

            aec.add(angle1);
            aec.add(angle2);
            aec.add(angle3);
            aec.add(angle4);

            aec.add(angle5);
            aec.add(angle6);
            aec.add(angle7);
            aec.add(angle8);

            assertEquals(aec.numClasses(), 2);

            assertEquals(aec.size(), 8);

            assertEquals(aec.indexOfClass(angle1), 0);
            assertEquals(aec.indexOfClass(angle5), 1);

            assertTrue(aec._classes.get(0).canonical().equals(angle1));
            assertTrue(aec._classes.get(1).canonical().equals(angle5));



        } catch (FactException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
}
