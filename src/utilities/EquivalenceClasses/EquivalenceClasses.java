/**
 * Creates an EquivalenceClass arrayList made up of LinkedEquivalenceClasses
 * @author Brodee Clontz, Kyler Bailey, Eleanor Badgett
 * @date 2/9/2024
 * @param <T>
 */

package utilities.EquivalenceClasses;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import utilities.LinkedEquivalenceClass.LinkedEquivalenceClass;


public class EquivalenceClasses<T>{
	
	protected Comparator<T> _comparator;
	protected List<LinkedEquivalenceClass<T>> _classes;
	

	public EquivalenceClasses(Comparator<T> comp){
		_classes = new ArrayList<LinkedEquivalenceClass<T>>();
		_comparator = comp;
	}
	

	
	/**
	 * adds element to appropriate LinkedEquivalenceClass in ArrayList, creates new if does not exist
	 * @param element
	 * @return
	 */
	public boolean add(T element) {
		if(indexOfClass(element)==-1) {
			LinkedEquivalenceClass<T> that = new LinkedEquivalenceClass<T>(_comparator); 
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
	public boolean contains(T target) {
		if(_classes.isEmpty()) return false;
		for(LinkedEquivalenceClass<T> elm : _classes) {
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
	public int size() {
		int sum = 0;
		for(LinkedEquivalenceClass<T> elm : _classes) {
			sum += elm.size();
		}
		return sum;
	}

	/**
	 * number of Classes in EquivalenceClasses arrayList
	 * @return total classes
	 */
	public int numClasses() {
		return _classes.size(); 
	}
	
	/**
	 * Returns index of EquivalanceClass where element belongs
	 * @param element
	 * @return int index of element
	 */
	protected int indexOfClass(T element) {
		for( LinkedEquivalenceClass<T> elm : _classes) {
			if(elm.belongs(element)) {
				return _classes.indexOf(elm);
			}
		}
		return -1;
	
	}
	
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
