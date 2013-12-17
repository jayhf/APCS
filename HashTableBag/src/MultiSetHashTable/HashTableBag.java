package MultiSetHashTable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashTableBag<T> implements Bag<T> {
	private class Counter {
		public int number;
		public T t;
		
		public Counter(T t, int number) {
			this.t = t;
			this.number = number;
		}
		
		@Override
		public String toString() {
			return number > 1 ? t.toString() + " x " + number : t.toString();
		}
	}
	
	private Counter[] counters;
	private double loadFactor;
	
	private int size = 0, counterSize = 0;
	
	public HashTableBag() {
		this(10, .75);
	}
	
	public HashTableBag(int initialSize) {
		this(initialSize, .75);
	}
	
	@SuppressWarnings("unchecked")
	public HashTableBag(int initialSize, double loadFactor) {
		if (initialSize <= 0)
			throw new IllegalArgumentException();
		this.loadFactor = loadFactor;
		counters = new HashTableBag.Counter[initialSize];
	}
	
	@Override
	public int add(T elem) {
		return add(elem, 1);
	}
	
	@Override
	public int add(T t, int occurrences) {
		if (occurrences < 0)
			throw new IllegalArgumentException();
		return modify(t, occurrences);
	}
	
	@Override
	public boolean contains(T t) {
		return count(t) == 0;
	}
	
	@Override
	public int count(T t) {
		if (t == null)
			return 0;
		/*
		 * int index = t.hashCode() % counters.length; while (counters[index] != null) if (counters[index].t.equals(t))
		 * return counters[index].number; else if (++index == counters.length) index = 0; return 0;
		 */
		return modify(t, 0);
	}
	
	@Override
	public Set<T> elementSet() {
		HashSet<T> set = new HashSet<T>(counterSize);
		for (T t : this)
			set.add(t);
		return set;
	}
	
	@SuppressWarnings("unchecked")
	private void ensureCapacity(int size) {
		if (counterSize > counters.length * loadFactor) {
			Counter[] oldArray = counters;
			counters = new HashTableBag.Counter[2 * this.counters.length];
			this.size = 0;
			counterSize = 0;
			for (int i = 0; i < oldArray.length; i++)
				if (oldArray[i] != null)
					add(oldArray[i].t, oldArray[i].number);
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int currentIndex, numberLeft;
			{
				if (counters.length > 0 && counters[0] == null)
					nextIndex();
			}
			
			@Override
			public boolean hasNext() {
				return currentIndex < counters.length;
			}
			
			@Override
			public T next() {
				T result = counters[currentIndex].t;
				if (--numberLeft <= 0)
					nextIndex();
				return result;
			}
			
			private void nextIndex() {
				do
					currentIndex++;
				while (currentIndex < counters.length && counters[currentIndex] == null);
				if (currentIndex < counters.length)
					numberLeft = counters[currentIndex].number;
			}
			
			@Override
			public void remove() {
				HashTableBag.this.remove(counters[currentIndex].t);
				nextIndex();
			}
		};
	}
	
	private int modify(T t, int occurences) {
		if (t == null)
			throw new NullPointerException();
		if (occurences > 0)
			ensureCapacity(counterSize + 1);
		int index = t.hashCode() % counters.length;
		while (counters[index] != null && !counters[index].t.equals(t))
			if (++index == counters.length)
				index = 0;
		if (counters[index] == null) {
			if (occurences > 0) {
				counters[index] = new Counter(t, occurences);
				counterSize++;
				size += occurences;
			}
			return 0;
		} else {
			int result = counters[index].number;
			if (-occurences >= result) {
				occurences = -result;
				counterSize--;
			}
			if (occurences > 0 && counters[index].number > Integer.MAX_VALUE - occurences)
				throw new IllegalArgumentException();
			counters[index].number += occurences;
			size += Math.max(-counters[index].number, occurences);
			if (counters[index].number <= 0) {
				int previousIndex = index;
				while (counters[index] != null) {
					if (counters[index].t.hashCode() % counters.length == t.hashCode() % counters.length) {
						counters[previousIndex] = counters[index];
						previousIndex = index;
					}
					if (++index == counters.length)
						index = 0;
				}
				counters[previousIndex] = null;
			}
			return result;
		}
	}
	
	@Override
	public boolean remove(T t) {
		return modify(t, -Integer.MIN_VALUE) > 0;
	}
	
	@Override
	public int remove(T t, int occurrences) {
		if (occurrences < 0)
			throw new IllegalArgumentException();
		return modify(t, -occurrences);
	}
	
	@Override
	public int setCount(T t, int count) {
		if (count <= 0)
			return modify(t, -Integer.MIN_VALUE);
		int index = t.hashCode() % counters.length;
		while (counters[index] != null && !counters[index].t.equals(t))
			if (++index == counters.length)
				index = 0;
		if (counters[index] == null) {
			counters[index] = new Counter(t, count);
			counterSize++;
			size += count;
			return 0;
		} else {
			int result = counters[index].number;
			counters[index].number = count;
			size += count - result;
			return result;
		}
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[size];
		int i = 0;
		for (T t : this)
			array[i++] = t;
		return array;
	}
	
	@Override
	public String toString() {
		String result = "[";
		for (int i = 0; i < counters.length; i++)
			if (counters[i] != null)
				result += counters[i].toString() + ", ";
		return (result.length() > 1 ? result.substring(0, result.length() - 2) : result) + "]";// +
																								// Arrays.toString(counters);
	}
}
