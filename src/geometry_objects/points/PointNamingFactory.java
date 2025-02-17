package geometry_objects.points;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Given a pair of coordinates; generate a unique name for it;
 * return that point object.
 *
 * Names go from A..Z..AA..ZZ..AAA...ZZZ  (a name such as ABA does not occur)
 * 
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 03/26/24
 */
public class PointNamingFactory
{
	// Prefix associated with each generated name so those names are easily distinguishable
	private static final String _PREFIX = "*_";

    // Constants reflecting our naming characters for generated names.
	private static final char START_LETTER = 'A';
	private static final char END_LETTER = 'Z';

    //
    // the number of characters in the generated names:
	// "A" and 1 -> "A"
	// "B" and 3 -> "BBB"
	//
	private String _currentName = "A";
	private int _numLetters = 1;

	//
	// A hashed container for the database of points; this requires the Point
	// class implement equals based solely on the individual coordinates and
	// not a name. We need a get() method; HashSet doesn't offer one.
	// Each entry is a <Key, Value> pair where Key == Value
	//
	protected LinkedHashMap<Point, Point> _database;

	public PointNamingFactory()
	{
		_database = new LinkedHashMap<Point, Point>();
	}

	/**
	 * Initialize the database with points; must call put() to ensure all points are named
	 *
	 * @param points -- a list of points, named or not named
	 */
	public PointNamingFactory(List<Point> points)
	{
		_database = new LinkedHashMap<Point, Point>();

		for (Point value: points) {
			put(value);
		}
	
	}

	/**
	 * Overloaded add / lookup mechanism for this database.
	 *
	 * @param pt -- a Point object (may or may not be named)
	 
	 * @return THE point object in the database corresponding to its coordinate pair
                    * the object in the database if it already exists or
					* a completely new point that has been added to the database
	 */
	public Point put(Point pt)
	{
		return put(pt.getName(), pt.getX(), pt.getY()); 
	}

	/**
	 * Overloaded add / lookup mechanism for this database for an unnamed coordinate pair.
	 *
	 * @param x -- single coordinate
	 * @param y -- single coordinate

	 * @return THE point object in the database corresponding to its coordinate pair
                    * the object in the database if it already exists or
					* a completely new point that has been added to the database (with generated name)
	 */
	public Point put(double x, double y)
	{
		Point pt = new Point(x,y);
		return put(pt.getName() , x, y);
	}

	/**
	 * The 'main' overloaded add / lookup mechanism for this database.
	 * 
	 * @param name -- the name of the point 
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * 
	 * @return a point (if it already exists in the database) or a completely new point that
	 *         has been added to the database.
	 *         
	 *         If the point is in the database and the name differs from what
	 *         is given, nothing in the database will be changed; essentially
	 *         this means we use the first name given for a point.
	 *            e.g., a valid name cannot overwrite an existing valid name ;
	 *                  a generated name cannot be overwritten by another generated name
	 *         
	 *         The exception is that a valid name can overwrite an unnamed point.
	 */
	public Point put(String name, double x, double y)
	{
		Point pt = new Point(name, x,y);

		Point dbPoint = get(pt);

		//If in database
		if(dbPoint != null && dbPoint.equals(pt)) return dbPoint;

		//New point
		if (pt.isUnnamed()) {								
			pt.changeName(getCurrentName());
		}
		
		_database.put(pt, pt);
		return pt;
	}

	/**
	 * Strict access (read-only of the database)
	 * 
	 * @param x
	 * @param y
	 * @return stored database Object corresponding to (x, y) 
	 */
	public Point get(double x, double y)
	{
		return _database.get(new Point(x,y));
	}
	
	/**
	 * Gets and returns a point if in database, null if not
	 * 
	 * @param pt
	 * @return
	 */
	public Point get(Point pt)
	{
		return _database.get(pt);		
	}

	/**
	 * @param x -- single coordinate
	 * @param y -- single coordinate
	 * @return simple containment; no updating
	 */
	public boolean contains(double x, double y) { 		
		return _database.containsKey(new Point(x,y));  
	
	} 
	
	/**
	 * true if point is in the database
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(Point p) { 
		
		return _database.containsKey(p); 
	
	}

	/**
	 * Constructs the next (complete with prefix) generated name.
	 * Names should be of the form PREFIX + current name
	 *
	 * This method should also invoke updating of the current name
	 * to reflect the 'next' name in the sequence.
     *	 
	 * @return the next complete name in the sequence including prefix.
	 */
	private String getCurrentName()
	{
		String name = _PREFIX + _currentName;
		updateName();
		return name;
	}

	/**
	 * Advances the current generated name to the next letter in the alphabet:
	 * 'A' -> 'B' -> 'C' -> 'Z' --> 'AA' -> 'BB'i
	 * Increment the _num letters everytime you wrap back around
	 * Setup if conditions to be once hit Z increment num letters by one.
	 */
	private void updateName()
	{
		char currChar = _currentName.charAt(_currentName.length()-1);
		int newChar = (int) currChar + 1;
		
		//Wrap around
		if (currChar == END_LETTER) increaseCharCount();

		//A -> B -> C etc.
		else _currentName = _currentName.replace(currChar, (char) newChar);
	}

	/**
	 * Increases the number of letters and creates a new name. If name was "Z" -> "AA"
	 * 
	 * @param charCount
	 * @param newChar
	 * @return
	 */
	private void increaseCharCount(){
		_numLetters++;

		_currentName = "A";
		for (int i = 1; i < _numLetters; i ++){
			_currentName += START_LETTER;
		}
	}

	/**
	 * @return The entire database of points.
	 */
	public Set<Point> getAllPoints()
	{
		Set<Point> set = new HashSet<Point>();

		for(Entry<Point, Point> value: _database.entrySet()){
			set.add(value.getKey());
		}
		return set;
	}

	/*
	 * Clears the  database.
	 */
	public void clear() { 
		_database.clear(); 
	}
	
	/*
	 * Returns the size of the database.
	 */
	public int size() { 
		return _database.size(); 
	}

	/**
	 * toString method returns a string representation of the database
	 */
	@Override
	public String toString()
	{
        StringBuilder sb = new StringBuilder();
		for (Entry<Point, Point> entry : _database.entrySet()){
			sb.append(entry.getKey().getName());
			sb.append(" ");
		}
		return sb.toString();
	}
}