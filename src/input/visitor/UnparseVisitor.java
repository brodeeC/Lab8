package input.visitor;

import java.util.AbstractMap;
import components.FigureNode;
import input.components.point.*;
import input.components.segment.SegmentNode;
import input.components.segment.SegmentNodeDatabase;
import utilities.io.StringUtilities;
// @Authors Kyler, Collin, and Brodee
//Date: March 12, 2024
//
// This file implements a Visitor (design pattern) with 
// the intent of building an unparsed, String representation
// of a geometry figure.
//
public class UnparseVisitor implements ComponentNodeVisitor
{
	/*
	 * Unparses the FigureNode in the AST to match the output and the 
     * contents of the tree as a string so we can verify that our input matches the object-based representation
	 */
	@Override
	public Object visitFigureNode(FigureNode node, Object o)
	{
		
		// Unpack the input object containing a Stringbuilder and an indentation level
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
		StringBuilder sb = pair.getKey();
		int level = pair.getValue();

		//Begins appending the basic structure of the AST.
		sb.append("Figure :\n{\n");
		
		//Identation of the JSON contents provides clarity.
		sb.append(StringUtilities.indent(level));
		sb.append("Description : ");
		
		//Appending the description of the AST. 
		sb.append(node.getDescription());
		sb.append('\n');
		level++;
		
		//Calls the visitPointNodeDatabase and visitSegementDatabaseNode methods to unparse.
		this.visitPointNodeDatabase(node.getPointsDatabase(),o);
		this.visitSegmentDatabaseNode(node.getSegments(), o);
		sb.append("}\n");
		
        return null;
	}

	/*
	 * Unparses SegmentNodedatabase in the AST to match the output and the 
     * contents of the tree as a string so we can verify that our input matches the object-based representation
	 */
	@Override
	public Object visitSegmentDatabaseNode (SegmentNodeDatabase node, Object o)
	{

		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
        StringBuilder sb = pair.getKey();
		int level = pair.getValue();


		sb.append(StringUtilities.indent(level));
		sb.append("Segments : \n    {\n");
		

		//Iterate through the keys, appending them one at time
		for (PointNode point1 : node.getAdjLists().keySet()) {		
			sb.append(StringUtilities.indent(level+1));
			sb.append(point1.getName() + " : ");
			
			//Iterating through the values at point1 and appending them
			for (PointNode point2 : node.getAdjLists().get(point1)) {		   
				sb.append(point2.getName() + "  ");
			}
			sb.append("\n");
		}
		sb.append("    }\n");
		
        return null;
	}

	/**
	 * This method should NOT be called since the segment database
	 * uses the Adjacency list representation
	 */
	@Override
	public Object visitSegmentNode(SegmentNode node, Object o)
	{

		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
        StringBuilder sb = pair.getKey();
		

		// StringBuilder appends both PointNodes and returns.
		
		sb.append("{" + node.getPoint1() + ",");
		sb.append(node.getPoint2()+ "}");
		
		return null;
	}


	/*
	 * Same concept as PointNode, unpacks to obtain SB and level.
	 * Appends an indent, then iterates through the database, visiting each PointNode in this instance.
	 */

	@Override
	public Object visitPointNodeDatabase(PointNodeDatabase node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
        StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		
		
		sb.append(StringUtilities.indent(level));
		sb.append("Points : \n    {\n");

		//Iterates through every given pointnode in the database
		//Calls the visitPointNode to gather information and appending them.
		for (PointNode point: node.getPoints()) {	  
			this.visitPointNode(point,o);				 
		}
		sb.append("    }\n\n");

		return null;
	}
		

	/*
	 * Unpacks a PointNode, using key & value pair to get the level and SB to append to.
	 * Updates level, appends an indent, and then appends the PointNode information.
	 */
	@Override
	public Object visitPointNode(PointNode node, Object o)
	{
		@SuppressWarnings("unchecked")
		AbstractMap.SimpleEntry<StringBuilder, Integer> pair = (AbstractMap.SimpleEntry<StringBuilder, Integer>)(o);
        StringBuilder sb = pair.getKey();
		int level = pair.getValue();
		level++;


		sb.append(StringUtilities.indent(level));

		//Appends the PointNode's name and coordinates
		sb.append("Point(" + node.getName() + ")(" + node.getY() + "," + node.getX() + ")");
		sb.append("\n");

		return null;
	}
}