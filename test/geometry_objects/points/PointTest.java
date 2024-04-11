/**
 * Test class for the Point class.
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 03/26/24
 */


package geometry_objects.points;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;


public class PointTest {


    @Test
    public void testConstructor() {

        Point point1 = new Point(0, 1);
        Point point2 = new Point(0, 2);

        assertTrue(point1.compareTo(point2) < 0);
        assertFalse(point1.compareTo(point2) > 0);
        assertFalse(point1.compareTo(point2) == 0);
    }

    @Test
    public void testNaming() {

        Point point1 = new Point(0, 1);
        assertEquals("__UNNAMED", point1.getName()); 
        
        point1 = new Point("Hey", 0, 1);
        assertEquals("Hey", point1.getName()); 

    }

    @Test
    public void testLexicographicOrdering() {

        Point point1 = new Point(1, 1);
        Point point2 = new Point(1, 1);
        assertTrue(point1.compareTo(point2) == 0);
        
        point1 = new Point(8.0, 9);
        assertTrue(point1.compareTo(point2) > 0);

        point2 = new Point(8, 10.5);
        assertTrue(point1.compareTo(point2) < 0);

        point1 = new Point(8, 11.3);
        assertTrue(point1.compareTo(point2) > 0);

        point2 = new Point(9.1, 11);
        assertTrue(point1.compareTo(point2) < 0);
    }

    @Test 
    public void testEquals() {

        Point point1 = new Point(0, 4);
        Point point2 = new Point(0, 4);
        Point point3 = new Point(2, 0);

        assertTrue(point1.equals(point2));
        assertFalse(point3.equals(point2));
    }
}
