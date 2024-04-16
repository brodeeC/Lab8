/**
 * @author Brodee Clontz, Kyler Bailey, Collin Riddle
 * @date 04/16/24
 * 
 * Creates an equivalence class of angles.
 */

package geometry_objects.angle;

import geometry_objects.angle.comparators.AngleStructureComparator;
import utilities.EquivalenceClasses.*;

/**
 * Given the figure below:
 * 
 *    A-------B----C-----------D
 *     \
 *      \
 *       \
 *        E
 *         \
 *          \
 *           F
 * 
 * Equivalence classes structure we want:
 * 
 *   canonical = BAE
 *   rest = BAF, CAE, DAE, CAF, DAF
 */
public class AngleEquivalenceClasses extends EquivalenceClasses<Angle>
{
	//Default constructor
	public AngleEquivalenceClasses(){
		super(new AngleStructureComparator());
	}
	
	/**
	 * adds element to appropriate AngleLinkedEquivalenceClass in ArrayList, creates new if does not exist
	 * @param element
	 * @return true/false
	 */
	@Override
	public boolean add(Angle element) {
		if(indexOfClass(element)==-1) {
			AngleLinkedEquivalenceClass that = new AngleLinkedEquivalenceClass(new AngleStructureComparator()); 
			that.add(element);
			_classes.add(that);
			return true;
		}
		
		return _classes.get(indexOfClass(element)).add(element);
		
	}
}