/**
 * This class extracts a FigureNode from a file. It also coverts a PointNode to a Point and a SegmentNode into a Segment.
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 03/26/24
 */

package input;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import geometry_objects.points.Point;
import geometry_objects.points.PointDatabase;
import geometry_objects.Segment;
import input.builder.GeometryBuilder;
import components.ComponentNode;
import components.FigureNode;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.parser.JSONParser;

public class InputFacade
{
	/**
	 * A utility method to acquire a figure from the given JSON file:
	 *     Constructs a parser
	 *     Acquries an input file string.
	 *     Parses the file.
	 *
	 * @param filepath -- the path/name defining the input file
	 * @return a FigureNode object corresponding to the input file.
	 */
	public static FigureNode extractFigure(String filepath)
	{
		JSONParser parser = new JSONParser(new GeometryBuilder());
		ComponentNode node = parser.parse(filepath);
		return (FigureNode) node;
	}
	
	/**
	 * 1) Convert the PointNode and SegmentNode objects to a Point and Segment objects 
	 *    (those classes have more meaningful, geometric functionality).
	 * 2) Return the points and segments as a pair.
	 *
	 * @param fig -- a populated FigureNode object corresponding to a geometry figure
	 * @return a point database and a set of segments
	 */
	public static Map.Entry<PointDatabase, Set<Segment>> toGeometryRepresentation(FigureNode fig)
	{
		PointNodeDatabase pdb = fig.getPointsDatabase();
		SegmentNodeDatabase segments = fig.getSegments();

		PointDatabase points = nodesToPoints(pdb);
		Set<Segment> segmentSet = nodesToSegments(segments, points);

		return new AbstractMap.SimpleEntry<PointDatabase, Set<Segment>>(points, segmentSet);
	}

	private static PointDatabase nodesToPoints(PointNodeDatabase pdb){
		PointDatabase points = new PointDatabase();
		for (PointNode pt : pdb.getPoints()){
			String name = pt.getName();
			double x = pt.getX();
			double y = pt.getY();

			if (name == "__UNNAMED") points.put(new Point(x,y).getName(), x, y);
			else points.put(name, x, y);
		}
		return points;
	}

	private static Set<Segment> nodesToSegments(SegmentNodeDatabase segments, PointDatabase points){

		Set<Segment> set = new HashSet<Segment>();
		Map<PointNode, Set<PointNode>> map = segments.getAdjLists();
		for (PointNode point : map.keySet()){
			Segment segment;

			Point mainPoint = points.getPoint(point.getName());

			for(PointNode connectedPoint : map.get(point)){
				Point conPoint = points.getPoint(connectedPoint.getName());
				segment = new Segment(mainPoint, conPoint);
				set.add(segment);
			}
		}
		return set;
	}
}