/**
 * Test class for InputFacade.
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 03/26/24
 */

package input;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.junit.Test;

import components.FigureNode;
import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import geometry_objects.Segment;
import input.visitor.ToJSONvisitor;

public class inputFacadeTest 
{

    /**
     * Extracts the figure node.
     * @param filename
     * @return
     */
    public static FigureNode runInputFacadeTest(String filename)
    {
        String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

        FigureNode node = InputFacade.extractFigure(figureStr);

        return node;
    }

    /**
     * Creates a JSON object to test that the figure node is there how it's supposed to be.
     * @param node
     * @return
     */
    public static String toJSONString(FigureNode node)
    {
        ToJSONvisitor visitor = new ToJSONvisitor();
        JSONObject obj = visitor.visitFigureNode(node, null);
        return obj.toString(3);
    }

    @Test
    public void extractFigureTest()
    {
        FigureNode node = runInputFacadeTest("collinear_line_segments.json");
        assertTrue(node instanceof FigureNode);
        System.out.println(toJSONString(node));
    
    }
    @Test
    public void toGeometryRepresentationTest()
    {
        FigureNode node = runInputFacadeTest("collinear_line_segments.json");
        Map.Entry<PointDatabase, Set<Segment>> map = InputFacade.toGeometryRepresentation(node);
        PointDatabase pdb = map.getKey();
        Set<Segment> segments = map.getValue();

        assertTrue(pdb.size() == 6);

        assertTrue(pdb.getPoint("A").equals(new Point(0,0)));
        assertTrue(pdb.getPoint("B").equals(new Point(4,0)));
        assertTrue(pdb.getPoint("C").equals(new Point(9,0)));
        assertTrue(pdb.getPoint("D").equals(new Point(11,0)));
        assertTrue(pdb.getPoint("E").equals(new Point(16,0)));
        assertTrue(pdb.getPoint("F").equals(new Point(26,0)));

        assertTrue(segments.size() == 5);
    }

}