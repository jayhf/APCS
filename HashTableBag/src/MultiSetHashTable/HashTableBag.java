package MultiSetHashTable;

import java.util.Iterator;
import java.util.Set;

public class HashTableBag<T> implements Bag<T> {
	private class Counter<T> {
		int number;
		T t;
	}
	
	private Counter[] counters;
	private int size = 0;
	
	public HashTableBag() {
		// TODO Auto-generated constructor stub
	}
	
	public HashTableBag(int i) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int add(T elem) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int add(T elem, int occurrences) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean contains(T element) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int count(T element) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Set<T> elementSet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int currentIndex;
			{
				nextIndex();
			}
			
			@Override
			public boolean hasNext() {
				return currentIndex < size;
			}
			
			@Override
			public T next() {
				nextIndex();
				return counters[currentIndex].t;
			}
			
			private void nextIndex() {
				while (currentIndex < size && counters[currentIndex] == null)
					currentIndex++;
			}
			
			@Override
			public void remove() {
				remove(counters[currentIndex].t);
				nextIndex();
			}
		};
	}
	
	@Override
	public boolean remove(Object elem) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int remove(Object elem, int occurrences) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int setCount(Object elem, int count) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public T[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
}
