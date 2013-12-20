package MultiSetHashTable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A collection that stores elements in a hashtable and keeps track of duplicates.
 * 
 * @author Jay Fleischer
 * 
 * @param <T>
 *            - the type of object stored
 */
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
	
	/**
	 * Creates a HashTableBag with the default size of 10 and a load factor of .75.
	 */
	public HashTableBag() {
		this(10, .75);
	}
	
	/**
	 * Creates a HashTableBag with the passed size and a load factor of .75.
	 */
	public HashTableBag(int initialSize) {
		this(initialSize, .75);
	}
	
	/**
	 * Creates a HashTableBag with the passed initial size and load factor.
	 */
	@SuppressWarnings("unchecked")
	public HashTableBag(int initialSize, double loadFactor) {
		if (initialSize <= 0)
			throw new IllegalArgumentException();
		this.loadFactor = loadFactor;
		counters = new HashTableBag.Counter[initialSize];
	}
	
	/**
	 * Adds 1 of the element to the bag.
	 */
	@Override
	public int add(T t) {
		return add(t, 1);
	}
	
	/**
	 * Adds the passed number of occurences of t.
	 */
	@Override
	public int add(T t, int occurrences) {
		if (occurrences < 0)
			throw new IllegalArgumentException();
		return modify(t, occurrences);
	}
	
	/**
	 * Returns true if there is at least one t in the bag.
	 */
	@Override
	public boolean contains(T t) {
		return count(t) > 0;
	}
	
	/**
	 * Returns the number of t in the bag.
	 */
	@Override
	public int count(T t) {
		if (t == null)
			return 0;
		return modify(t, 0);
	}
	
	/**
	 * returns a HashSet with all of the elements in this bag.
	 */
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
	
	private int getHashCode(T t) {
		return Math.abs(t.hashCode());
	}
	
	/**
	 * Iterates over all of the elements in this bag
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int currentIndex, numberLeft;
			boolean removed = false;
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
				if (!hasNext())
					throw new IllegalStateException();
				removed = false;
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
				if (removed)
					throw new IllegalStateException();
				removed = true;
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
		int tHashCode = getHashCode(t);
		int index = tHashCode % counters.length;
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
			if (occurences <= -counters[index].number) {
				occurences = -counters[index].number;
				counterSize--;
			}
			if (occurences > 0 && counters[index].number > Integer.MAX_VALUE - occurences)
				throw new IllegalArgumentException();
			counters[index].number += occurences;
			size += occurences;
			if (counters[index].number <= 0) {
				int previousIndex = index;
				boolean wrapped = false;
				while (counters[index] != null) {
					int hashIndex = getHashCode(counters[index].t) % counters.length;// /where it wants to go
					// previous index - where the opening is
					// index - where it is
					if (hashIndex == tHashCode % counters.length
							|| (wrapped ? hashIndex - counters.length : hashIndex) <= previousIndex) {
						counters[previousIndex] = counters[index];
						previousIndex = index;
					}
					if (++index == counters.length) {
						index = 0;
						wrapped = true;
					}
				}
				counters[previousIndex] = null;
			}
			return result;
		}
	}
	
	/**
	 * Removes 1 instance of t from the bag.
	 */
	@Override
	public boolean remove(T t) {
		return modify(t, -1) > 0;
	}
	
	/**
	 * Removes the passed number of occurrences of t from the bag.
	 */
	@Override
	public int remove(T t, int occurrences) {
		if (occurrences < 0)
			throw new IllegalArgumentException();
		return modify(t, -occurrences);
	}
	
	/**
	 * Sets the number of t in the bag
	 */
	@Override
	public int setCount(T t, int count) {
		if (count == 0)
			return modify(t, -Integer.MIN_VALUE);
		else if (count < 0)
			throw new IllegalArgumentException();
		int index = getHashCode(t) % counters.length;
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
	
	/**
	 * Returns the current size of the bag
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns all of the elements in an array
	 */
	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[size];
		int i = 0;
		for (T t : this)
			array[i++] = t;
		return array;
	}
	
	/**
	 * Returns a string representation of this bag.
	 */
	@Override
	public String toString() {
		String result = "[";
		for (int i = 0; i < counters.length; i++)
			if (counters[i] != null)
				result += counters[i].toString() + ", ";
		return (result.length() > 1 ? result.substring(0, result.length() - 2) : result) + "]";
		// + Arrays.toString(counters);
	}
}
