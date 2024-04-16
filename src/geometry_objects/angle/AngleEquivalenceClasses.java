package geometry_objects.angle;

import java.util.ArrayList;
import java.util.List;

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
	protected AngleStructureComparator _comparator;
	protected List<AngleLinkedEquivalenceClass> _classes;

	public AngleEquivalenceClasses(){
		this(new AngleStructureComparator());
	}

	public AngleEquivalenceClasses(AngleStructureComparator comp) {
		super(comp);
		_comparator = comp;
		_classes = new ArrayList<AngleLinkedEquivalenceClass>();
	}

	
	/**
	 * adds element to appropriate LinkedEquivalenceClass in ArrayList, creates new if does not exist
	 * @param element
	 * @return
	 */
	@Override
	public boolean add(Angle element) {
		if(indexOfClass(element)==-1) {
			AngleLinkedEquivalenceClass that = new AngleLinkedEquivalenceClass(_comparator); 
			that.add(element);
			_classes.add(that);
			return true;
		}
		
		return _classes.get(indexOfClass(element)).add(element);
		
	}
	
	/**
	 * sees if desired element target is contained in _classes
	 * @param target
	 * @return
	 */
	@Override
	public boolean contains(Angle target) {
		if(_classes.isEmpty()) return false;
		for(AngleLinkedEquivalenceClass elm : _classes) {
			if(elm.contains(target)) {
				return true;
			}
		}
		return false;		
	}
	
	
	/**
	 * sums total number of elements in all linkedEquivlalenceClasses 
	 * @return int total size
	 */
	@Override
	public int size() {
		int sum = 0;
		for(AngleLinkedEquivalenceClass elm : _classes) {
			sum += elm.size();
		}
		return sum;
	}

	/**
	 * number of Classes in EquivalenceClasses arrayList
	 * @return total classes
	 */
	@Override
	public int numClasses() {
		return _classes.size(); 
	}
	
	/**
	 * Returns index of EquivalanceClass where element belongs
	 * @param element
	 * @return int index of element
	 */
	@Override
	protected int indexOfClass(Angle element) {
		for(AngleLinkedEquivalenceClass elm : _classes) {
			if(elm.belongs(element)) {
				return _classes.indexOf(elm);
			}
		}
		return -1;
	
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		//str.append("comparator: " + _comparator);
		for(int i = 0; i<numClasses()-1; i++) {
			str.append("{"+_classes.get(i)+"},");
		}
		str.append("{"+_classes.get(_classes.size()-1)+"}");
		return str.toString();
	}


}