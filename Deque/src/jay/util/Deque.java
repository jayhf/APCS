package jay.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * @author Jay Fleischer
 * @version 1.0 (1-6-14)
 */
public class Deque<E> implements Iterable<E>, java.util.Deque<E> {
	private class Node {
		E object;
		Node previous, next;
		
		public Node(E object) {
			this.object = object;
		}
	}
	
	private Node first = new Node(null);
	private int size = 0;
	{
		first.next = first;
		first.previous = first;
	}
	
	@Override
	public boolean add(E object) {
		size++;
		Node newNode = new Node(object);
		newNode.previous = first.previous;
		newNode.previous.next = newNode;
		first.previous = newNode;
		newNode.next = first;
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void addFirst(E object) {
		size++;
		Node newNode = new Node(object);
		newNode.next = first.next;
		newNode.next.previous = newNode;
		first.next = newNode;
		newNode.previous = first;
	}
	
	@Override
	public void addLast(E object) {
		add(object);
	}
	
	@Override
	public void clear() {
		first.previous = null;
		first.next = null;
		size = 0;
	}
	
	@Override
	public boolean contains(Object object) {
		for (E e : this)
			if (object.equals(e))
				return true;
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		for (E e : this)
			for (Object object : c)
				if (object.equals(e))
					return true;
		return false;
	}
	
	@Override
	public Iterator<E> descendingIterator() {
		return new Iterator<E>() {
			Node current = first.previous;
			
			@Override
			public boolean hasNext() {
				return current.previous != first;
			}
			
			@Override
			public E next() {
				return (current = current.previous).object;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	@Override
	public E element() {
		return first.next.object;
	}
	
	@Override
	public E getFirst() {
		return first.next.object;
	}
	
	@Override
	public E getLast() {
		return first.previous.object;
	}
	
	@Override
	public boolean isEmpty() {
		return first.next == first;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			Node current = first.next;
			
			@Override
			public boolean hasNext() {
				return current.next != first;
			}
			
			@Override
			public E next() {
				return (current = current.next).object;
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}
	
	@Override
	public boolean offer(E object) {
		return add(object);
	}
	
	@Override
	public boolean offerFirst(E object) {
		return add(object);
	}
	
	@Override
	public boolean offerLast(E object) {
		addLast(object);
		return true;
	}
	
	@Override
	public E peek() {
		return first.next.object;
	}
	
	@Override
	public E peekFirst() {
		return first.next.object;
	}
	
	@Override
	public E peekLast() {
		return first.previous.object;
	}
	
	@Override
	public E poll() {
		return pollFirst();
	}
	
	@Override
	public E pollFirst() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public E pollLast() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public E pop() {
		return remove();
	}
	
	@Override
	public void push(E object) {
		size++;
		Node newNode = new Node(object);
		newNode.next = first.next;
		newNode.next.previous = newNode;
		first.next = newNode;
		newNode.previous = first;
	}
	
	@Override
	public E remove() {
		Node node = first.next;
		first.next = node.next;
		node.next.previous = first;
		return node.object;
	}
	
	@Override
	public boolean remove(Object object) {
		return removeFirstOccurrence(object);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean changed = false;
		Node node = first.next;
		while (node != first)
			if (c.contains(node.object)) {
				changed = true;
				Node next = node.next;
				while (next != first && c.contains(next))
					next = next.next;
				node.previous.next = next;
				next.previous = node.previous;
				node = next.next;
			}
		return changed;
	}
	
	@Override
	public E removeFirst() {
		return remove();
	}
	
	@Override
	public boolean removeFirstOccurrence(Object object) {
		Node node = first.next;
		while (node != first) {
			if (object == null ? node.object == null : node.object.equals(object)) {
				node.previous.next = node.next;
				node.next.previous = node.previous;
				return true;
			}
			node = node.next;
		}
		return false;
	}
	
	@Override
	public E removeLast() {
		Node node = first.previous;
		first.previous = node.previous;
		node.previous.next = first;
		return node.object;
	}
	
	@Override
	public boolean removeLastOccurrence(Object object) {
		Node node = first.previous;
		while (node != first) {
			if (object == null ? node.object == null : node.object.equals(object)) {
				node.previous.next = node.next;
				node.next.previous = node.previous;
				return true;
			}
			node = node.previous;
		}
		return false;
	}
	
	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public Object[] toArray() {
		@SuppressWarnings("unchecked")
		E[] arr = (E[]) new Object[size];
		int i = 0;
		for (E t : this)
			arr[i++] = t;
		return arr;
	}
	
	@Override
	public <T> T[] toArray(T[] arr) {
		// TODO Auto-generated method stub
		return null;
	}
}