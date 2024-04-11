package utilities.LinkedEquivalenceClass;

import java.util.Comparator;

/**
 * This is a LinkedEquivalence Class that manages a linked list of elements 
 * that it groups equivalent elements together based on canonical element.
 * defined by a comparator.  
 * 
 * Authors: Kyler, Eleanor, and Brodee
 */

import utilities.LinkedList.LinkedList;

public class LinkedEquivalenceClass<T> {
	
	protected T _canonical;
	protected Comparator<T> _comparator;
	protected LinkedList<T> _rest;
	
	public  LinkedEquivalenceClass(Comparator<T> _comparator) {
		
		this._comparator = _comparator; 
		this._canonical = null;
		_rest = new LinkedList<T>();		
		
	}
	
	/**
	 * Returns the canonical element
	 * @return
	 */
	public T canonical() {
		return _canonical; 
		
	}
	
	/**
	 * Checks to see if the LinkedList is empty
	 * @return
	 */
	public boolean isEmpty() {
		
		return _rest.isEmpty();
		
	}
	
	/**
	 * Clears the LinkedList
	 */
	public void clear() {
		
		_rest.clear();
	}
	
	/**
	 * Clears the NonCanonical elements from the LinkedList
	 */
	public void clearNonCanonical() {
		
		_rest.clear();
		//replaces canonical information
		_rest.addToBack(_canonical);
		
		
	}
	
	/**
	 * Returns the size of the LinkedList 
	 * @return
	 */
	public int size() {
		
		return _rest.size();
		
	}
	
	/**
	 * Adds the specified element to the LinkedList based on it's canonical
	 * @param element
	 * @return
	 */
	public boolean add(T element) {

		if(_canonical == null) {
			_canonical = element;
			_rest.addToBack(element); 
			return true;
		}

		//Checks to see if the element actually belongs in the LinkedList will add the element

		if(belongs(element)) {
			_rest.addToBack(element);
			return true;
		} 
		
		return false;

	}
	
	/**
	 * Sees if the LinkedList contains the target T
	 * @param target
	 * @return
	 */
	public boolean contains(T target) { 
		
		if(belongs(target)) {
			if (_canonical.equals(target)) return true;
			return _rest.contains(target);
		}
		
		return false;
		
	}
	
	/**
	 * Test to see if the target belongs in the canonical
	 * @param target
	 * @return
	 */
	public boolean belongs (T target) {
		
		if(target != null && _comparator.compare(_canonical, target)==0) {
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * Removes the target element from the LinkedList
	 * @param target
	 * @return
	 */
	public boolean remove(T target) {

		return _rest.remove(target);
			
	}
	
	/**
	 * Removes the canonical from the LinkedList and replaces it with 
	 * a valid element
	 * @return
	 */
	public boolean removeCanonical() {
		
		if(_canonical != null && _rest.contains(_canonical)) {
			_rest.remove(_canonical);
			_canonical = _rest.getFirst();
			
			return true;
		}
	
		return false;
			
	}
	
	/**
	 * Demote the canonical and sets it into the list, then takes the 
	 * element and sets it as the canonical.
	 * @param element
	 * @return
	 */
	public boolean demoteAndSetCanonical(T element) {
		
		if(_canonical != null && element != null) {
			if(belongs(element)) {
				_rest.addToFront(_canonical);
				_canonical = element;
				
				return true;
			}
		}
		
		return false;
	}
	
//	/**
//	 * Constructs a string of the LinkedList
//	 */

	public String toString() {
		return _rest.toString();
	}

}
