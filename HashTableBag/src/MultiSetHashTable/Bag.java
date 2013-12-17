package MultiSetHashTable;

/**
 * interface Bag represents a multiset -- a set
 * of methods to support a set type structure that
 * allows duplicates by keeping a count.
 * 
 * method ideas come from Google Multiset
 *  -- add (E elem)
 *  -- add(E elem, int occurrences)
 *  -- contains (E element)
 *  -- count (E element)
 *  --Set<E> elementSet() 
 *  --Iterator<E> iterator () 
 *  -- boolean remove (E elem)
 *  -- int remove( E elem, int occurrences)
 *  -- int setCount (E elem, int count) -- add or remove to make count correct throw IllegalArgument Exception if count < 0
 * 
 * January 2013
 *  -- Added int size () to the interface, after checking Google's MultiSet from which this is
 *      derived, I saw that size() was inherited through the Collection interface.  Such a 
 *      useful method ought to be here!
 *      
 *      
 * @author J. Smith 
 * @version January 2010, January 2013
 */

import java.util.Iterator;
import java.util.Set;

public interface Bag<E> extends Iterable<E> {
	/**
	 * Adds one more occurrence to the Bag, always increments the count by one
	 * 
	 * @param elem
	 *            the element to add
	 * @throws NullPointerException
	 *             if elem==null
	 * @throws IllegalArgumentException
	 *             if count == Integer.MAX_VALUE
	 * @returns the count before the add -- possibly 0
	 */
	int add(E elem);
	
	/**
	 * adds occurrences number of elements, if occurrences = 0 does nothing
	 * 
	 * @param elem
	 *            the element to add
	 * @param occurrences
	 *            the number of times to put elem in the bag
	 * @throws NullPointerException
	 *             if elem == null
	 * @throws IllegalArgumentException
	 *             if occurrences < 0 or adding will result in more than Integer.MAX_VALUE
	 * @returns the count before the add -- possibly 0
	 */
	int add(E elem, int occurrences);
	
	/**
	 * @return true if element occurs in the Bag or elem == null, false otherwise
	 */
	boolean contains(E element);
	
	/**
	 * @return the count for element, 0 if element == null or not found
	 */
	int count(E element);
	
	/**
	 * @return a Set of one occurrence of each element
	 */
	Set<E> elementSet();
	
	/**
	 * returns an Iterator over the elements The iterator will return multiple occurrences of items as indicated by
	 * count No particular ordering of the elements is guaranteed, unless the implementation does this.
	 * 
	 * @return an Iterator over the elements
	 */
	@Override
	Iterator<E> iterator();
	
	/**
	 * Remove a single occurrence of elem
	 * 
	 * @param elem
	 *            the element to remove a single item of
	 * @return true if elem is found and removed
	 */
	boolean remove(E elem);
	
	/**
	 * remove a number of occurrences, if occurrences > count then all are removed.
	 * 
	 * @param occurrences
	 *            the number of elem to remove, may be 0
	 * @param elem
	 *            the element to be removed
	 * @throws IllegalArgumentException
	 *             if occurrences < 0
	 * @return the count of the element before some are removed
	 */
	int remove(E elem, int occurrences);
	
	/**
	 * add or remove to make count correct
	 * 
	 * @throws IllegalArgumentException
	 *             if count < 0
	 * @throws NullPointerException
	 *             if elem == null
	 * @param elem
	 *            the item to add/remove up to count
	 * @param count
	 *            the desired count for element elem
	 * @return the count of the element before the operation
	 */
	int setCount(E elem, int count);
	
	/**
	 * @return the number of elements in the structure, include duplicates as unique new elements
	 */
	int size();
	
	/**
	 * @return an array of all the objects (multiple times if duplicated)
	 */
	Object[] toArray();
	
	/**
	 * String format [a x 3, c, d x 2, e, f]
	 * 
	 * @return a String-ized version, including counts
	 */
	@Override
	String toString();
}
