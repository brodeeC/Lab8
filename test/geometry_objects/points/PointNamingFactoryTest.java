/**
 * Test class for PointNamingFactory.
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 03/26/24
 */


package geometry_objects.points;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import geometry_objects.points.Point;
import geometry_objects.points.PointNamingFactory;


public class PointNamingFactoryTest {


    /**
     * Builds a PointNamingFactory
     * @return
     */
    public PointNamingFactory builder(){
        PointNamingFactory factory = new PointNamingFactory();
        
        return factory;
   }
    

    @Test
    public void defaultConstructorTest(){

        PointNamingFactory factory = builder();
        assertEquals(factory.size(), 0);
    }

     
    @Test
    public void overloadedConstructorTest(){


        List<Point> points = new ArrayList<>();
        points.add(new Point(0.0, 0.0));
        points.add(new Point(5.0, 7.0));
        points.add(new Point(3.4560, 2.9990));
    
        PointNamingFactory factory = new PointNamingFactory(points);

        assertEquals(factory.size(), 3);
    }

    @Test 
    public void putPoint(){
        PointNamingFactory factory = builder();
        Point pt1 = factory.put(new Point(2.9999, 3.5467));
        Point pt2 = factory.put(new Point(3.0, 5.0));


        assertNotNull(pt1);
        assertNotNull(pt2);

        assertEquals(2.9999, pt1.getX());
        assertEquals(3.0, pt2.getX());

        assertEquals(3.5467, pt1.getY());
        assertEquals(5.0, pt2.getY());
    }
    

    @Test 
    public void putDouble(){
        PointNamingFactory factory = builder();
        factory.put(5.056, 7.089);
        factory.put(0.01, 2.22);
        factory.put(4.9999, 3.47380);

        assertEquals(3, factory.size());

        factory.put(5.9898, 6.84848);

        assertEquals(4, factory.size());
    }

    @Test
    public void putNameXYTest(){
        PointNamingFactory factory = builder();
        Point pt1 = new Point("A",3.999,2.22232);
        Point pt2 = new Point("Point2",3.0, 5.0);
        Point pt3 = new Point("C",4.5,6.0);
        
        factory.put(pt1);
        factory.put(pt2);
        factory.put(pt3);

        assertNotNull(pt1);
        assertNotNull(pt2);
        assertNotNull(pt3);

        assertEquals("A", pt1.getName());
        assertEquals("Point2",pt2.getName());
        assertEquals("C",pt3.getName());

    }

    @Test
    public void putwithGeneratedTest(){
        PointNamingFactory factory = builder();
        Point pt1 = new Point(3.999,2.22232);
        Point pt2 = new Point(3.0, 5.0);
        Point pt3 = new Point(4.5,6.0);

        factory.put(new Point(3.999,2.22232));
        factory.put(new Point(3.0, 5.0));
        factory.put(new Point(4.5,6.0));

        assertEquals("*_A", factory.get(pt1).getName());
        assertEquals("*_B", factory.get(pt2).getName());
        assertEquals("*_C", factory.get(pt3).getName());


    }

    @Test
    public void getTest(){
        PointNamingFactory factory = builder();
        Point pt1 = new Point(0.0, 7.0);
        factory.put(pt1);    
    
        factory.put(4.5, 6.777);
        factory.put(3.232, 7.111);

        assertEquals(pt1, factory.get(pt1));
        assertEquals(new Point(4.5, 6.777), factory.get(4.5, 6.777)); 
    }

    @Test
    public void containsTest(){
        PointNamingFactory factory = builder();
        factory.put(1.0,2.0);
        factory.put(1.5676, 3.758);
        factory.put(.999999, 0.0);

        assertTrue(factory.contains(1.0,2.0));
        assertTrue(factory.contains(.999999,0.0)); 
    }

    @Test
    public void getAllPointsTest(){
        PointNamingFactory factory = builder();
		Set<Point> set = new HashSet<Point>();

        set.add(new Point(1.0, 2.0));
        set.add(new Point(1.5676, 3.758));
        set.add(new Point(.999999, 0.0));

        factory.put(new Point(1.0, 2.0));
        factory.put(new Point(1.5676, 3.758));
        factory.put(new Point(.999999, 0.0));

        assertEquals(set, factory.getAllPoints());

        assertEquals(3, factory.getAllPoints().size());
    }

    @Test
    public void clearTest(){
        PointNamingFactory factory = builder();
        factory.put(1.0, 2.0);
        factory.put(3.0, 4.0);
        factory.put(.999999, 0.0);

        assertEquals(3, factory.size());

        factory.clear();

        assertEquals(0, factory.size());

    }


    @Test
    public void sizeTest(){
        PointNamingFactory factory = builder();
        factory.put(1.0, 2.0);
        factory.put(3.0, 4.0);

        assertEquals(2, factory.size());

        factory.clear();

        assertEquals(0, factory.size());
    
    }

    @Test
    public void toStringTest(){
        PointNamingFactory factory = builder();
        factory.put(1.0, 2.0);
        factory.put(3.0, 4.0);
        factory.put(.999999, 0.0);

        assertEquals(factory.toString(), "*_A *_B *_C ");

    }
    
}
