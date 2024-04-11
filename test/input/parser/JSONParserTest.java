/**
 * Tests parser with various figures of size and shape to ensure functionality.
 * 
 * @author Brodee Clontz, Collin Riddle, Kyler Bailey
 * @date 02/20/2024
 */

package input.parser;


import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import components.ComponentNode;
import components.FigureNode;
import input.builder.DefaultBuilder;
import input.builder.GeometryBuilder;
import input.exception.ParseException;
import input.visitor.ToJSONvisitor;

class JSONParserTest
{
	public static ComponentNode runFigureParseTest(String filename)
	{
		GeometryBuilder builder = new GeometryBuilder();
		JSONParser parser = new JSONParser(builder);

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		return parser.parse(figureStr);
	}

	@Test
	void empty_json_string_test()
	{
		GeometryBuilder builder = new GeometryBuilder();
		JSONParser parser = new JSONParser(builder);

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_test()
	{
		//
		// The input String ("single_triangle.json") assumes the file is
		// located at the top-level of the project. If you move your input
		// files into a folder, update this String with the path:
		//                                       e.g., "my_folder/single_triangle.json"
		//
		ComponentNode node = JSONParserTest.runFigureParseTest("single_triangle.json");

		assertTrue(node instanceof FigureNode);

		ToJSONvisitor visitor = new ToJSONvisitor();
		JSONObject object = visitor.visitFigureNode((FigureNode) node, null);
		System.out.println(object.toString(3));
	}

	@Test
	void crossing_symmetric_triangle_test() {

		ComponentNode node = JSONParserTest.runFigureParseTest("crossing_symmetric_triangle.json");

		assertTrue(node instanceof FigureNode);

		ToJSONvisitor visitor = new ToJSONvisitor();
		JSONObject object = visitor.visitFigureNode((FigureNode) node, null);
		System.out.println(object.toString(1));
	}

	@Test
	void collinear_line_segments_test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("collinear_line_segments.json");

		assertTrue(node instanceof FigureNode);

		ToJSONvisitor visitor = new ToJSONvisitor();
		JSONObject object = visitor.visitFigureNode((FigureNode) node, null);
		System.out.println(object.toString(3));
	}

	@Test
	void fully_connected_irregular_polygon_test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("fully_connected_irregular_polygon.json");

		assertTrue(node instanceof FigureNode);

		ToJSONvisitor visitor = new ToJSONvisitor();
		JSONObject object = visitor.visitFigureNode((FigureNode) node, null);
		System.out.println(object.toString(3));
	}

	
	@Test
	void many_triangles_test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("many_triangles.json");


		assertTrue(node instanceof FigureNode);

		ToJSONvisitor visitor = new ToJSONvisitor();
		JSONObject object = visitor.visitFigureNode((FigureNode) node, null);
		System.out.println(object.toString(3));

	}
	@Test
	void Irregular_polygon() {
		ComponentNode node = JSONParserTest.runFigureParseTest("Irregular_polygon.json");

		assertTrue(node instanceof FigureNode);

		ToJSONvisitor visitor = new ToJSONvisitor();
		JSONObject object = visitor.visitFigureNode((FigureNode) node, null);
		System.out.println(object.toString(3));

	}

	@Test
	void single_line_test() {
		ComponentNode node = JSONParserTest.runFigureParseTest("single_line.json");

		assertTrue(node instanceof FigureNode);

		ToJSONvisitor visitor = new ToJSONvisitor();
		JSONObject object = visitor.visitFigureNode((FigureNode) node, null);
		System.out.println(object.toString(3));
	}
	
	public static ComponentNode runFigureParseTestDef(String filename)
	{
		DefaultBuilder builder = new DefaultBuilder();
		JSONParser parser = new JSONParser(builder);

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);

		return parser.parse(figureStr);
	}

	@Test
	void empty_json_string_testDef()
	{
		DefaultBuilder builder = new DefaultBuilder();
		JSONParser parser = new JSONParser(builder);

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_testDef()
	{
		//
		// The input String ("single_triangle.json") assumes the file is
		// located at the top-level of the project. If you move your input
		// files into a folder, update this String with the path:
		//                                       e.g., "my_folder/single_triangle.json"
		//
		ComponentNode node = JSONParserTest.runFigureParseTestDef("single_triangle.json");

		assertTrue(node == null);
	}

	@Test
	void crossing_symmetric_triangle_testDef() {

		ComponentNode node = JSONParserTest.runFigureParseTestDef("crossing_symmetric_triangle.json");

		assertTrue(node == null);
	}

	@Test
	void collinear_line_segments_testDef() {
		ComponentNode node = JSONParserTest.runFigureParseTestDef("collinear_line_segments.json");

		assertTrue(node == null);
	}

	@Test
	void fully_connected_irregular_polygon_testDef() {
		ComponentNode node = JSONParserTest.runFigureParseTestDef("fully_connected_irregular_polygon.json");

		assertTrue(node == null);
	}

	
	@Test
	void many_triangles_testDef() {
		ComponentNode node = JSONParserTest.runFigureParseTestDef("many_triangles.json");


		assertTrue(node == null);
	}
	@Test
	void Irregular_polygonDef() {
		ComponentNode node = JSONParserTest.runFigureParseTestDef("Irregular_polygon.json");

		assertTrue(node == null);
	}

	@Test
	void single_line_testDef() {
		ComponentNode node = JSONParserTest.runFigureParseTestDef("single_line.json");

		assertTrue(node == null);
	}


}

