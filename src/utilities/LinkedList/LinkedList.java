/** This is a Doubly Linked List class with head and tail sentinel nodes. This class manipulates a 
 * linked list in different ways.
 * 
 * @author Brodee Clontz, Eleanor Badgett, Kyler Bailey
 * @date 02/06/24
 */

package utilities.LinkedList;

public class LinkedList<T> {

	protected int _size;
	protected Node _head;
	protected Node _tail;

	protected class Node {
		protected T _data;
		protected Node _next;
		protected Node _prev;

		/**
		 * Node constructor
		 */
		public Node() {
			this(null, null, null);
		}

		/**
		 * Overloaded Node constructor
		 * @param p
		 * @param i
		 * @param n
		 */
		public Node(Node p, T i, Node n) {
			_prev = p;
			_data = i;
			_next = n;
		}
	}

	/**
	 * LinkedList constructor
	 */
	public LinkedList() {
		_tail = new Node(null,null,null); 
		_head = new Node(null, null, _tail);
		_tail._prev = _head;
		_size = 0;
	}

	/**
	 * Determines if the List is empty
	 * @return if the list is empty
	 */
	public boolean isEmpty() {
		return _size == 0;
	}

	/**
	 * Empties the list by having head and tail point towards each other
	 */
	public void clear() {
		_head._next = _tail;
		_tail._prev = _head;
		_size = 0;
	}

	/**
	 * Gets the size of the list
	 * @return _size 
	 */
	public int size() {
		return _size;
	}

	/**
	 * Adds an element to the front of the list
	 * @param element
	 */
	public void addToFront(T element) {
		if (element != null) {
			Node tmpNode = _head._next;
			Node node = new Node(_head, element, _head._next);
			_head._next = node;
			tmpNode._prev = node;
			_size++;
		}
	}

	/**
	 * Recursive contains method
	 * @param target
	 * @return
	 */
	public boolean contains(T target) {
		if (isEmpty()) return false;
		
		if (target == null) return false;
		
		return contains(_head._next, _tail._prev, target);
	}
	
	/**
	 * Private recursive contains method
	 * @param head
	 * @param tail
	 * @param target
	 * @return true if the target is found, false otherwise
	 */
	private boolean contains(Node head, Node tail, T target) {
		if (head._data.equals(target) || (tail._data).equals(target)) return true;
		
		if (head._next == tail) return false;
		
		if (head == tail) return false;

		return contains(head._next, tail._prev, target);
	}

	/**
	 * Finds and returns the Node behind a specific target
	 * @param target
	 * @return
	 */
	private Node previous(T target) {
		if (!(contains(target))) return null;
		return previous(_head._next, _tail._prev, target);
	}
	
	/**
	 * Private recursive method 
	 * @param head
	 * @param tail
	 * @param target
	 * @return the previous node
	 */
	private Node previous(Node head, Node tail, T target) {
		if (head._data.equals(target)) return head._prev;
		
		if (tail._data.equals(target)) return tail._prev;
		
		if (head._next == tail) return null;
		
		if (head == tail) return null;

		return previous(head._next, tail._prev, target);
	}

	/**
	 * Removes a target element from the list
	 * @param target
	 * @return true if the element was removed
	 */
	public boolean remove(T target) {
		Node node = previous(target);
		
		if (node != null) {
			Node tmpNode = node._next;
			node._next = tmpNode._next;
			tmpNode._next._prev = node;
			_size--;
			return true;
		}
		return false;
	}

	/**
	 * @return the last valid element in the list.
	 */
	private Node last() {
		return _tail._prev;
	}

	/**
	 * Adds a valid element to the back of the list.
	 * @param element
	 */
	public void addToBack(T element) {
		if (element != null) {
			Node node = last();
			Node newNode = new Node(node, element, _tail);
			node._next = newNode;
			_tail._prev = newNode;
			_size++;
		}
	}

	/**
	 * Creates and returns the toString of the elements in the list.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Node node = _head._next; node != _tail._prev; node = node._next) {
			s.append(node._data);
			s.append(", ");
		}
		s.append(last()._data);
		return s.toString();
	}
	
	/**
	 * Gets the first valid element within the LinkedList
	 *
	 */
	
	public T getFirst() {
		
		return _head._next._data;
		
	}

	/**
	 * Recursive reverse method
	 */
	public void reverse() {
		if (!(isEmpty())) reverse(_head._next, _tail._prev);
	}
	
	/**
	 * Reverses the elements in the list by swapping the first and last element,
	 * moving the head and tail one node inwards and recursing.
	 * @param head
	 * @param tail
	 */
	private void reverse(Node head, Node tail) {
		if (head == tail) return;
		if (head._prev == tail) return;
		swap(head, tail);
		reverse(head._next, tail._prev);
	}
	
	/**
	 * Swap method that swaps to elements in a list.
	 * @param head
	 * @param tail
	 */
	private void swap(Node head, Node tail) {
		T tmpNode = head._data;
		head._data = tail._data;
		tail._data = tmpNode;
	}
}
