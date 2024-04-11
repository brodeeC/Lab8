/**
 * Test class for PointDatabase.
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 03/26/24
 */


package geometry_objects.points;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class PointDatabaseTest {

    /**
     * A method to populate a PointDatabase
     * 
     * @return
     */
    public static ArrayList<Point> pointList(){
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(1 , 1));
        points.add(new Point(2 , 2));
        points.add(new Point(3 , 3));
        points.add(new Point(4 , 4));
        return points;
    }

    @Test
    void defConstructorTest(){
        PointDatabase pdb = new PointDatabase();
        assertTrue("Empty PointDatabase not present",pdb.size()==0);
    }

    @Test
    void overloadedConstructorTest(){
        PointDatabase pdb = new PointDatabase(pointList());
        assertTrue("Size not 4",pdb.size()==4);
    }

    @Test
    void putNameXYTest(){
        PointDatabase pdb = new PointDatabase();
        pdb.put("A", 1, 1);
        assertTrue("Size not 1",pdb.size()==1);
    }

    @Test
    void getNameXYTest(){
        PointDatabase pdb = new PointDatabase(pointList());
        assertTrue("Name not 'A'",pdb.getName(1,1).equals("*_A"));
        assertTrue("Name not 'B'",pdb.getName(2,2).equals("*_B"));
        assertTrue("Name not 'C'",pdb.getName(3,3).equals("*_C"));
        assertTrue("Name not 'D'",pdb.getName(4,4).equals("*_D"));
        assertFalse("Name is A",pdb.getName(2,2).equals("*_A"));
    }

    @Test
    void getNamePointTest(){
        PointDatabase pdb = new PointDatabase(pointList());
        assertTrue("Name not 'A'",pdb.getName(new Point(1,1)).equals("*_A"));
        assertTrue("Name not 'B'",pdb.getName(new Point(2,2)).equals("*_B"));
        assertTrue("Name not 'C'",pdb.getName(new Point(3,3)).equals("*_C"));
        assertTrue("Name not 'D'",pdb.getName(new Point(4,4)).equals("*_D"));
    }

    @Test
    void getPointName(){
        PointDatabase pdb = new PointDatabase(pointList());
        assertTrue("Point not found", pdb.getPoint("*_A").equals(pdb.getPoint(1,1)));
        assertTrue("Point not found", pdb.getPoint("*_B").equals(pdb.getPoint(2,2)));
        assertTrue("Point not found", pdb.getPoint("*_C").equals(pdb.getPoint(3,3)));
        assertTrue("Point not found", pdb.getPoint("*_D").equals(pdb.getPoint(4,4)));
    }

    @Test
    void getPointptTest(){
        PointDatabase pdb = new PointDatabase(pointList());
        assertTrue("Point not found",pdb.getPoint(new Point(1,1)).equals(new Point(1,1)));
        assertTrue("Point not found",pdb.getPoint(new Point(2,2)).equals(new Point(2,2)));
        assertTrue("Point not found",pdb.getPoint(new Point(3,3)).equals(new Point(3,3)));
        assertTrue("Point not found",pdb.getPoint(new Point(4,4)).equals(new Point(4,4)));
    }

    @Test
    void getPointXYTest(){
        PointDatabase pdb = new PointDatabase(pointList());
        assertTrue("Point not found",pdb.getPoint(1,1).equals(new Point(1,1)));
        assertTrue("Point not found",pdb.getPoint(2,2).equals(new Point(2,2)));
        assertTrue("Point not found",pdb.getPoint(3,3).equals(new Point(3,3)));
        assertTrue("Point not found",pdb.getPoint(4,4).equals(new Point(4,4)));
    }
    
    @Test
    void highVolumeTest(){
        PointDatabase pdb = new PointDatabase();

        for (int i = 1; i <= 79; i++){
            pdb.put(null, i, i);
        }

        assertTrue(pdb.getName(1,1).equals("*_A"));
        assertTrue(pdb.getName(26,26).equals("*_Z"));
        assertTrue(pdb.getName(27,27).equals("*_AA"));
        assertTrue(pdb.getName(52,52).equals("*_ZZ"));
        assertTrue(pdb.getName(53,53).equals("*_AAA"));
        assertTrue(pdb.getName(78,78).equals("*_ZZZ"));
        assertTrue(pdb.getName(79,79).equals("*_AAAA"));
    }
}