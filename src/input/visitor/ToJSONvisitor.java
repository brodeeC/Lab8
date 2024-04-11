/** Creates a JSONObject of the unparsed Abstract Syntax Tree.
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 03/14/24
 */
package input.visitor;

import org.json.JSONArray;
import org.json.JSONObject;

import components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;

public class ToJSONvisitor implements ComponentNodeVisitor {


    /**
     * Constructor
     */
    public ToJSONvisitor(){


    }

    /** Puts the entire tree into a JSONObject
     * @param FigureNode node, Object o
     * @return JSONObject object
     */
    @Override
    public JSONObject visitFigureNode(FigureNode node, Object o) {
        JSONObject object = new JSONObject();
        JSONObject obj = new JSONObject();

        //Putting each branch into one object
        object.put("Description", node.getDescription());
        object.put("Points", visitPointNodeDatabase(node.getPointsDatabase(), null));
        object.put("Segments", visitSegmentDatabaseNode(node.getSegments(), null));
        
        //Putting that object into the "root" object
        obj.put("Figure", object);
        return obj;

    }

    /** Puts the SegmentNodeDatabase into a JSONObject by first creating arrays of connected points and putting them into the object
     * @param SegmentNodeDatabase node, Object o
     * @return JSONObject object
     */
    @Override
    public JSONObject visitSegmentDatabaseNode(SegmentNodeDatabase node, Object o) {
       JSONObject arrOfSegments = new JSONObject();

       //Getting the first point of a segment list
       for (PointNode mainPoint : node.getAdjLists().keySet()){
            
            //Putting the list of connected segments into a JSONArray
            JSONArray ptArr = new JSONArray();
            for(PointNode connectedPoint : node.getAdjLists().get(mainPoint)){
                ptArr.put(connectedPoint.getName());
            }
            arrOfSegments.put(mainPoint.getName(), ptArr);
       }

        
        return arrOfSegments;

    }

    /** Unused
     * @param SegmentNode node, Object o
     * @return JSONObject object
     */
    @Override
    public Object visitSegmentNode(SegmentNode node, Object o) {
        return null;
    }

    /** Puts one PointNode into a JSONObject and returns it
     * @param PointNode node, Object o
     * @return JSONObject object
     */
    @Override
    public JSONObject visitPointNode(PointNode node, Object o) {
        JSONObject obj = new JSONObject();

        //Putting the PointNode into the Object
        obj.put("name", node.getName());
        obj.put("x", node.getX());
        obj.put("y", node.getY());

        return obj;

    }

    /** Puts the PointNodeDatabase into a JSONArray and returns it
     * @param PointNodeDatabase node, Object o
     * @return JSONObject object
     */
    @Override
    public JSONArray visitPointNodeDatabase(PointNodeDatabase node, Object o) {
        JSONArray arr = new JSONArray();        

        //Loops through the database putting each PointNode into a JSONArray
        for(PointNode point : node.getPoints()){
            JSONObject obj = visitPointNode(point, null);
            arr.put(obj);
        }
        
        return arr;
    }
}
