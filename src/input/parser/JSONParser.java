/**
 * Parser method scans JSON File geometry figure and creates an abstract syntax tree with the root as a 
 * FigureNode. The tree consists of a description, segmentnodedatabase, and pointnodedatabase which stores
 * the descriptions, segments, and points represented in the JSON file.
 *   
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 02/20/2024
 */

package input.parser; 

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import components.ComponentNode;
import input.builder.DefaultBuilder;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

public class JSONParser
{
	protected ComponentNode _astRoot;
	protected DefaultBuilder builder;

	public JSONParser(DefaultBuilder builder)
	{
		this.builder = builder;
		_astRoot = null;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	/**
	 * Parses a given string into a description, PointNodeDatabase, and a SegmentNodeDatabase
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public ComponentNode parse(String str) throws ParseException
	{	
		// Parsing is accomplished via the JSONTokenizer class.
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();

		if (JSONroot.isEmpty()) error("No root found");

		//Initializing variables
		String description = "";
		PointNodeDatabase pdb = builder.buildPointDatabaseNode(null);
		SegmentNodeDatabase sdb = builder.buildSegmentNodeDatabase();
		JSONObject JSONobj = JSONroot.getJSONObject("Figure");
		
		description = JSONobj.getString("Description");
		pdb = scanPointNode(JSONobj.getJSONArray("Points"));
		sdb = scanSegmentNode(pdb, JSONobj.getJSONArray("Segments"));


		_astRoot = builder.buildFigureNode(description, pdb, sdb);

		return _astRoot; //Will return the root node
	}
	
	/**
	 * Takes a jsonArray and iterates through it, adding PointNodes to the database each
	 * iteration
	 * @param jsonArray
	 * @return
	 */
	private PointNodeDatabase scanPointNode(JSONArray jsonArray) {

		ArrayList<PointNode> db = new ArrayList<PointNode>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			db.add(createPointNode(obj));
		}		
		return builder.buildPointDatabaseNode(db);
	} 

	/**
	 * Method that builds a single PointNode
	 * @param obj
	 * @return PointNode
	 */
	private PointNode createPointNode(JSONObject obj){
		String name = obj.getString("name");
			
		int x = obj.getInt("x");
		int y = obj.getInt("y");
		return builder.buildPointNode(name, x, y);
	}

	/**
	 * Iterates through the "Segments" section of the file adding segments to 
	 * the database as we go.
	 * @param pdb
	 * @param jsonArray
	 * @return
	 */
	private SegmentNodeDatabase scanSegmentNode(PointNodeDatabase pdb, JSONArray jsonArray) {
		SegmentNodeDatabase _sdb = builder.buildSegmentNodeDatabase();
		for (Object row: jsonArray) {
			
			//Getting the name of the segment
			JSONObject obj = (JSONObject) row;
			String name = obj.keys().next();
			
			//Creating a point to be added
			PointNode keyPoint = builder.buildPointNode(name, Math.random(), Math.random());
			
			//Creating a second JSONArray to iterate through Points connected to the
			//first point.
			JSONArray arr = obj.getJSONArray(name);
			addSegmentNode(arr, keyPoint, _sdb);
		}
		return _sdb;
	}

	private void addSegmentNode(JSONArray arr, PointNode keyPoint, SegmentNodeDatabase segments){	
		//Looping through second array to create the list that will be used
		for (Object segment: arr) {
			PointNode valPoint = builder.buildPointNode((String) segment, Math.random(), Math.random());
			builder.buildSegmentNode(keyPoint, valPoint);
			//ArrayList<PointNode> points = new ArrayList<PointNode>();
			//points.add(valPoint);
			builder.addSegmentToDatabase(segments, keyPoint, valPoint);
		}	
	}
} 