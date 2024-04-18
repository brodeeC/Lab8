package geometry_objects.angle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import exceptions.FactException;
import geometry_objects.Segment;
import geometry_objects.angle.comparators.AngleStructureComparator;
import geometry_objects.points.Point;

public class AngleStructureComparatorTest {

    private AngleStructureComparator comparator;


    public void setUp() {
        comparator = new AngleStructureComparator();
    }

    @Test
    public void testCompare() {
        // Creating points for angles
        Point A = new Point(0, 0);
        Point B = new Point(2, 2);
        Point C = new Point (4, 4);
        Point D = new Point (6, 6);
        Point E = new Point (1, 1);
        Point F = new Point (3, 3);

        // Creating angles
        Angle angleBAE = new Angle(A, B, E);
        Angle angleCAE = new Angle(A, C, E);
        Angle angleDAF = new Angle(D, A, F);
        Angle angleCAF = new Angle(C, A, F);

        // Asserting comparisons
        assertEquals(-1, comparator.compare(angleBAE, angleCAE));
        assertEquals(-1, comparator.compare(angleBAE, angleDAF));
        assertEquals(1, comparator.compare(angleCAE, angleDAF));
        assertEquals(AngleStructureComparator.STRUCTURALLY_INCOMPARABLE, comparator.compare(angleCAE, angleCAF));
    }

}
