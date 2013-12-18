package test;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import MultiSetHashTable.Bag;
import MultiSetHashTable.HashTableBag;

/**
 * The test class UnitTestHashSet.
 * 
 * January 22 - Idea: setCount test setting count to lower than current amount - Idea: setCount decrease but not to 0,
 * ex. current count 8 -> setCount to 3 January 18, Second release - Correction and tighter testing on add8Resize test -
 * Updated occurrences of String compares to avoid referencing errors - Checked integer % 10 for bucket placement =
 * one's place assumption, seems OK to me - updated lab to include third constructor with cap parameter. - update to
 * setCountZero, I need to double check <===== UPDATE my implementation
 * 
 * January 18, -- First RELEASE for this year. Still more things to do though!
 * 
 * January 2013 - MAJOR update, merging the best of newer TreeTableBag unit test with tests for hash tables
 * 
 * Useful data information for Strings and hashCode%10
 * 
 * "bat" -- 1 "cat" -- 2 "mat" -- 2 "at" -- 3 "pat" -- 5 "fat" -- 5 "mad" -- 6 "gnat" -- 6 "hat" -- 7 "rat" -- 7 "sat"
 * -- 8 "tat" -- 9 "blat" -- 9
 * 
 * Uses Integer class for many tests due to ease of bucket location
 * 
 * Will need to update to add tests for additional methods
 * 
 * April 2010 Changed again, kept old file which tests a different underlying table structure Used for 2010 lab with a
 * different set of rules for the hash table (we had less time, so I changed collision resolution strategies - this is
 * also intended as a review exercise in using arrays for the revised AP exam) Rules: use an array with initial capacity
 * 10 -- The % 11 stuff made me nuts also it is much easier to figure out loadFactor -- since bucket 8 > .75*10 resolve
 * collisions by linear probing resize/rehash when loadfactor is reached
 * 
 * And for those interested in the evolution of the lab - here are pre-2010 notes on the unit test
 * 
 * Updated January 31, 200? -- modified resize test to more accurately catch errors in resizing the table.
 * 
 * This unit test evaluates the Set implementation from the AP Lab LinkedListHashSet. This test is a bit unusual since
 * it outputs a print of the hash table at the end of the ADD tests. This is intended to assist students in
 * understanding how the table is built and to help them debug any problems. Note that their tables should match the
 * output table. Most add tests evaluate this using the toString method provided here:
 * 
 * public String toString() { String s = ""; for (int i=0; i < capacity; i++) { Iterator itr = table[i].iterator(); if
 * (table[i].size() != 0) s += "bucket " + i + ": ["; while (itr.hasNext()) { s += itr.next(); s += "  "; } if
 * (table[i].size() != 0) s += "]\n"; } return s; }
 * 
 * Note: March 2009 -- this toString method is useful for displaying an organized view of the structure, but NOT
 * required -- see January 2008 notes.
 * 
 * Note: some tests take TIME to complete - be patient.
 * 
 * November 2006 - added new tests, edited testExercise to remove output UNLESS students wish to see it
 * 
 * December 2006 - added additional remove tests, thank you Will Kalb, Neal Poole January 2008 - updated to support
 * generics, general cleanup, - change to use toArray for evaluation (using java.util.Arrays.equals) *** This means one
 * of the first tests to pass is the toArray test to be *** sure you traverse the structure in the specified order which
 * should be *** down the table from 0..n, across the lists in full buckets front --> back - fixed/added exception
 * testing
 * 
 * Notes to Self: Unit tests still need work. Ex. 1 -- Resize test doesn't accurately make sure the table is resized
 * when 75% of BUCKETS not SIZE is used. Adam passed the basic resize test when he didn't keep track of bucketsUsed.
 * 
 * 
 * 
 * @author J. Smith
 * @version January 2006, November 2006, January 2008, January 2013
 */
public class UnitTestHashTableMultiSet extends junit.framework.TestCase {
	private String compare;
	private Bag<Integer> testSet;
	
	/**
	 * Default constructor for test class UnitTestLLHashSet
	 */
	public UnitTestHashTableMultiSet() {
	}
	
	/**
	 * Sets up the test fixture.
	 * 
	 * Called before every test case method.
	 */
	@Override
	@Before
	protected void setUp() {
		testSet = new HashTableBag<Integer>();
		compare = "[]";
	}
	
	/**
	 * Tears down the test fixture.
	 * 
	 * Called after every test case method.
	 */
	@Override
	@After
	protected void tearDown() {
		String result = testSet.toString();
		if (compare.equals(result))
			;// System.out.print("Success ");
		else {
			System.out.print("Failed ");
			System.out.println("comparing " + compare + " to " + result);
		}
		assertEquals(true, compare.equals(result));
	}
	
	@Test
	public void testAdd() {
		assertEquals(0, testSet.add(new Integer(11)));
		assertEquals(1, testSet.size());
		compare = "[11]";
	}
	
	@Test
	public void testAdd7() {
		int bound = 7; // should result in no resize
		int start = 7;
		for (int i = 0; i < bound; i++) {
			testSet.add(new Integer(start)); // all go to same bucket, linear probe forward
			start = start + 10;
		}
		assertEquals(7, testSet.size());
		compare = "[37, 47, 57, 67, 7, 17, 27]";
	}
	
	@Test
	public void testAdd8Resize() {
		int bound = 7; // should result in no resize since 7 is maximum # elements
		int start = 7;
		for (int i = 0; i < bound; i++) {
			Integer value = new Integer(start);
			testSet.add(value); // all go to same bucket, linear probe forward
			System.out.println(new Integer(start).hashCode() + " and the bucket " + value.hashCode() % 10);
			start = start + 10;
		}
		String compareBefore = testSet.toString();
		assertEquals(true, compareBefore.equals(new String("[37, 47, 57, 67, 7, 17, 27]")));
		assertEquals(bound, testSet.size());
		// now add the 8th which forces resize
		testSet.add(start); // add 77,
		assertEquals(bound + 1, testSet.size());
		// after 7 [37, 47, 57, 67, 7, 17, 27]
		// resize to 20, add 77
		// 77 in 7
		// 37 in 17
		// 47 in 8
		// 57 in 18
		// 67 in 9
		// 7 in 10
		// 17 in 19
		// 27 in 11
		
		compare = "[77, 47, 67, 7, 27, 37, 57, 17]";
	}
	
	@Test
	public void testAddAlot() {
		// testSet = new HashTableBag<Integer>(16); // start with power of 2
		// int max = 1024;
		// java.util.Set<Integer> compareSet = new java.util.HashSet<Integer> (10);
		// for (int i=0; i < max; i++)
		// {
		// compareSet.add(i);
		// testSet.add(i);
		// }
		// // force the tearDown to succeed
		// correctArray = new Object[0];
		// generatedArray = new Object[0];
		// // here's the critical test
		// assertEquals (true, compareSet.containsAll(testSet));
		// // System.out.println("Add a lot");
		// // System.out.println(testSet);
		// assertEquals(max, testSet.size());
	}
	
	@Test
	public void testAddDuplicate() {
		assertEquals(0, testSet.add(new Integer(10)));
		assertEquals(1, testSet.add(new Integer(10))); // duplicate
		assertEquals(2, testSet.add(new Integer(10))); // third
		assertEquals(3, testSet.size());
		compare = "[10 x 3]";
	}
	
	@Test
	public void testAddOccurBound() {
		boolean success = false;
		try {
			assertEquals(0, testSet.add(new Integer(3), Integer.MAX_VALUE)); // add maximum number of items
			assertEquals(Integer.MAX_VALUE, testSet.count(new Integer(3)));
			testSet.add(new Integer(3), 1); // add one that causes error
		} catch (IllegalArgumentException e) {
			success = true;
		}
		assertEquals(true, success);
		compare = "[3 x " + Integer.MAX_VALUE + "]";
	}
	
	@Test
	public void testAddOccurNegative() {
		boolean success = false;
		try {
			testSet.add(Integer.MAX_VALUE, Integer.MIN_VALUE);
		} catch (IllegalArgumentException e) {
			success = true;
		}
		assertEquals(true, success);
		compare = "[]";
	}
	
	@Test
	public void testAddOccurNull() {
		boolean success = false;
		try {
			testSet.add(null, Integer.MAX_VALUE);
		} catch (NullPointerException e) {
			success = true;
		}
		assertEquals(true, success);
		compare = "[]";
	}
	
	@Test
	public void testAddProbeBetween() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4));
		testSet.add(new Integer(25));
		testSet.add(new Integer(35)); // skip 6 go to 7
		assertEquals(0, testSet.add(new Integer(45))); // skip to 8
		
		compare = "[4, 25, 6, 35, 45]";
		
	}
	
	@Test
	public void testAddRequiringProbe() {
		assertEquals(0, testSet.add(new Integer(0)));
		assertEquals(0, testSet.add(new Integer(1)));
		assertEquals(0, testSet.add(new Integer(2)));
		assertEquals(0, testSet.add(new Integer(3)));
		assertEquals(0, testSet.add(new Integer(4)));
		assertEquals(0, testSet.add(new Integer(10))); // probe to bucket 5
		assertEquals(6, testSet.size());
		compare = "[0, 1, 2, 3, 4, 10]";
		
	}
	
	@Test
	public void testAddRequiringWrapAround() {
		assertEquals(0, testSet.add(new Integer(5)));
		assertEquals(0, testSet.add(new Integer(6)));
		assertEquals(0, testSet.add(new Integer(7)));
		assertEquals(0, testSet.add(new Integer(8)));
		assertEquals(0, testSet.add(new Integer(9)));
		assertEquals(0, testSet.add(new Integer(19))); // collision at bucket 9, probe to 0
		assertEquals(6, testSet.size());
		compare = "[19, 5, 6, 7, 8, 9]";
		
	}
	
	@Test
	public void testConstruct() {
		assertEquals(0, testSet.size());
		
		compare = "[]";
	}
	
	@Test
	public void testConstructCapacity() {
		// size 0 is not allowed
		boolean passed = true;
		try {
			testSet = new HashTableBag<Integer>(0);
		} catch (IllegalArgumentException e) {
			passed = false;
		}
		assertEquals(false, passed);
		// size 1
		testSet = new HashTableBag<Integer>(1);
		assertEquals(0, testSet.size());
		
		// capacity is 1, must resize immediately to 2, then 4
		testSet.add(new Integer(3)); // bucket 3
		assertEquals(1, testSet.size());
		compare = "[3]";
	}
	
	@Test
	public void testContainsFound() {
		assertEquals(0, testSet.add(new Integer(10)));
		assertEquals(true, testSet.contains(new Integer(10)));
		assertEquals(1, testSet.size());
		compare = "[10]";
	}
	
	@Test
	public void testContainsnotfound() {
		assertEquals(false, testSet.contains(new Integer(11)));
		assertEquals(0, testSet.size());
		compare = "[]";
	}
	
	@Test
	public void testContainsNotfoundProbe() {
		assertEquals(0, testSet.add(new Integer(0)));
		assertEquals(0, testSet.add(new Integer(1)));
		assertEquals(0, testSet.add(new Integer(2)));
		assertEquals(false, testSet.contains(new Integer(10)));
		compare = "[0, 1, 2]";
		
	}
	
	@Test
	public void testContainsProbe() {
		assertEquals(0, testSet.add(new Integer(0)));
		assertEquals(0, testSet.add(new Integer(1)));
		assertEquals(0, testSet.add(new Integer(2)));
		assertEquals(0, testSet.add(new Integer(3)));
		assertEquals(0, testSet.add(new Integer(4)));
		assertEquals(0, testSet.add(new Integer(10))); // probe to bucket 5
		assertEquals(true, testSet.contains(new Integer(10)));
		assertEquals(6, testSet.size());
		compare = "[0, 1, 2, 3, 4, 10]";
	}
	
	@Test
	public void testContainsProbeWrap() {
		assertEquals(0, testSet.add(new Integer(8)));
		assertEquals(0, testSet.add(new Integer(9)));
		assertEquals(0, testSet.add(new Integer(19)));
		assertEquals(true, testSet.contains(new Integer(19)));
		assertEquals(3, testSet.size());
		compare = "[19, 8, 9]";
	}
	
	@Test
	public void testCountNull() {
		assertEquals(0, testSet.count(null));
		compare = "[]";
	}
	
	@Test
	public void testCountOne() {
		assertEquals(0, testSet.add(new Integer(10)));
		assertEquals(1, testSet.count(new Integer(10)));
		compare = "[10]";
	}
	
	@Test
	public void testCountProbe() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		testSet.add(new Integer(7));
		testSet.add(new Integer(7));
		assertEquals(1, testSet.count(new Integer(17)));
		assertEquals(3, testSet.count(new Integer(7)));
		assertEquals(1, testSet.count(new Integer(4)));
		assertEquals(1, testSet.count(new Integer(14)));
		assertEquals(1, testSet.count(new Integer(6)));
		assertEquals(1, testSet.count(new Integer(16)));
		compare = "[4, 14, 6, 7 x 3, 16, 17]";
	}
	
	@Test
	public void testDupsProbe() {
		assertEquals(0, testSet.add(new Integer(10)));
		assertEquals(0, testSet.add(new Integer(11)));
		assertEquals(0, testSet.add(new Integer(12)));
		assertEquals(0, testSet.add(new Integer(60)));
		assertEquals(1, testSet.add(new Integer(60)));
		
		assertEquals(5, testSet.size());
		compare = "[10, 11, 12, 60 x 2]";
		
	}
	
	/**
	 * If your data structure is failing this test, uncomment the display of the set and run the test with the terminal
	 * window in unbuffered mode.
	 */
	public void testExercise() {
		// System.out.println("Exercise");
		//
		// // note the two sets do not have the same underlying structure
		// java.util.Set<Integer> compareSet = new java.util.HashSet<Integer> (10);
		// Random r = new Random();
		// int size=0;
		// int target = 2048;
		// int modcount = 0;
		// // add size unique items
		// while (size < target )
		// {
		// int next = r.nextInt();
		// if (testSet.add(next))
		// {
		// size++;
		// compareSet.add(next);
		// }
		// assertEquals(compareSet.size(), testSet.size());
		// if (modcount % 5 == 0) // every 5th time
		// {
		// boolean result = testSet.add(next); // add dup
		// assertEquals(result, false);
		// boolean randRemove = testSet.remove(next+1); // maybe there, maybe not
		// boolean compRandRemove = compareSet.remove(next+1);
		// assertEquals(true, randRemove == compRandRemove);
		// boolean remResult = testSet.remove(next); // remove found
		// assertEquals(remResult, true);
		// compareSet.remove(next);
		// // after this, the two sets should still be the same size (and contain the same elements)
		// assertEquals(compareSet.size(), testSet.size());
		// }
		// modcount++;
		// }
		// // make these two the same so tearDown works
		// generatedArray = new Object[0];
		// correctArray = new Object [0];
		// // here's the critical assert
		// assertEquals(true, compareSet.containsAll(testSet));
		// // System.out.println(testSet);
		
	}
	
	/*
	 * test that the number of elements is separate from the number of buckets by adding huge numbers of single elements
	 * re: thanks to John Chen
	 */
	@Test
	public void testNumElementsResize() {
		HashTableBag<Integer> hashTabl1 = new HashTableBag<Integer>(); // 10, 1);
		assertEquals(0, hashTabl1.add(100));
		assertEquals(0, hashTabl1.add(3));
		assertEquals(0, hashTabl1.add(12));
		assertEquals(0, hashTabl1.add(21, 13231));
		System.out.println(hashTabl1);
		assertEquals(0, hashTabl1.add(222, 123111));
		System.out.println(hashTabl1);
		assertEquals("[100, 21 x 13231, 12, 3, 222 x 123111]", hashTabl1.toString());
		compare = "[]";
	}
	
	@Test
	public void testRemoveAll() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		assertEquals(6, testSet.size());
		// table is 4, 14, 6, 7, 16, 17
		System.out.println(testSet);
		// add dups
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		assertEquals(12, testSet.size());
		System.out.println(testSet);
		
		assertEquals(true, testSet.remove(16));
		assertEquals(11, testSet.size());
		assertEquals(2, testSet.remove(6, 2));
		assertEquals(9, testSet.size());
		assertEquals(2, testSet.remove(14, 2));
		assertEquals(7, testSet.size());
		assertEquals(2, testSet.remove(7, 2));
		assertEquals(5, testSet.size());
		assertEquals(2, testSet.remove(4, 2));
		assertEquals(3, testSet.size());
		// System.out.println(testSet.count(17));
		assertEquals(2, testSet.remove(17, 2));
		assertEquals(1, testSet.size());
		assertEquals(true, testSet.remove(16));
		assertEquals(0, testSet.size());
		
		compare = "[]";
	}
	
	// changed to test removing all items when capacity stays the same
	@Test
	public void testRemoveAllBucketsOf1Element() {
		int max = 7; // capacity without resize
		for (int i = 0; i < max; i++)
			testSet.add(new Integer(i));
		assertEquals(7, testSet.size());
		for (int i = 0; i < max; i++)
			assertEquals(true, testSet.remove(new Integer(i)));
		
		assertEquals(0, testSet.size());
		compare = "[]";
	}
	
	@Test
	public void testRemoveDuplicates() {
		assertEquals(0, testSet.add(1));
		assertEquals(1, testSet.add(1));
		assertEquals(2, testSet.count(1));
		assertEquals(2, testSet.remove(new Integer(1), 2));
		assertEquals(0, testSet.count(1));
		compare = "[]";
	}
	
	@Test
	public void testRemoveFound() {
		assertEquals(0, testSet.add(new Integer(10)));
		assertEquals(1, testSet.size());
		assertEquals(true, testSet.remove(new Integer(10)));
		assertEquals(0, testSet.size());
		compare = "[]";
	}
	
	@Test
	public void testRemoveGreaterCount() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(6)); // goes to bucket 4
		testSet.add(new Integer(6)); // goes to bucket 5
		
		assertEquals(3, testSet.size());
		// table is 6 x 3
		assertEquals(3, testSet.remove(6, Integer.MAX_VALUE));
		assertEquals(0, testSet.count(6));
		compare = "[]";
	}
	
	@Test
	public void testRemovenotfoundEmpty() {
		assertEquals(false, testSet.remove(new Integer(11)));
		assertEquals(0, testSet.size());
		compare = "[]";
	}
	
	@Test
	public void testRemoveProbe() {
		assertEquals(0, testSet.add(new Integer(0)));
		assertEquals(0, testSet.add(new Integer(1)));
		assertEquals(0, testSet.add(new Integer(2)));
		assertEquals(0, testSet.add(new Integer(3)));
		assertEquals(0, testSet.add(new Integer(4)));
		assertEquals(0, testSet.add(new Integer(10))); // probe to bucket 5
		assertEquals(true, testSet.remove(new Integer(10)));
		assertEquals(5, testSet.size());
		compare = "[0, 1, 2, 3, 4]";
	}
	
	@Test
	public void testRemoveProbeBetween() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4));
		testSet.add(new Integer(25));
		testSet.add(new Integer(35)); // skip 6 go to 7
		assertEquals(false, testSet.remove(new Integer(45)));
		assertEquals(true, testSet.remove(new Integer(35)));
		assertEquals(false, testSet.remove(new Integer(35)));
		
		compare = "[4, 25, 6]";
		
	}
	
	/**
	 * Test that remove is also replacing elements below the removed item into the correct buckets below the element
	 * removed
	 */
	@Test
	public void testRemoveProbeError() {
		testSet.add(10);
		testSet.add(20);
		testSet.add(30);
		testSet.add(40);
		testSet.add(9); // bucket 9 filled
		
		// table is 10, 20, 30, 40, 9
		assertEquals(true, testSet.remove(20));
		// add back 30 and 40
		// probably will have trouble finding 30 and 40 if you didn't rehash
		assertEquals(true, testSet.contains(30));
		assertEquals(true, testSet.contains(40));
		// just to make sure lookup the others
		assertEquals(true, testSet.contains(10));
		assertEquals(true, testSet.contains(9));
		assertEquals(4, testSet.size());
		compare = "[10, 30, 40, 9]";
	}
	
	@Test
	public void testRemoveProbeTwice() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		assertEquals(6, testSet.size());
		// table is 4, 14, 6, 7, 16, 17
		assertEquals(true, testSet.remove(16)); // continue ahead past 7
		
		assertEquals(5, testSet.size());
		// table must be FIXED or 17 gets lost,
		assertEquals(true, testSet.contains(17));
		compare = "[4, 14, 6, 7, 17]";
	}
	
	// January 2008 -- changed, adjusted comments,adding testing with toArray
	@Test
	public void testRemoveProbeWrapAround() {
		// fill table with one element per bucket for the last 4 buckets
		testSet.add(new Integer(6));
		testSet.add(new Integer(7));
		testSet.add(new Integer(8));
		testSet.add(new Integer(9));
		
		// cause probe in bucket 9 to bucket 0
		testSet.add(new Integer(19));
		// now remove it
		assertEquals(true, testSet.remove(new Integer(19)));
		assertEquals(4, testSet.size());
		compare = "[6, 7, 8, 9]";
		
	}
	
	@Test
	public void testRemoveSome() {
		assertEquals(0, testSet.add(1));
		assertEquals(1, testSet.add(1));
		assertEquals(2, testSet.add(1));
		assertEquals(3, testSet.count(1));
		assertEquals(3, testSet.remove(new Integer(1), 2));
		assertEquals(1, testSet.count(1));
		assertEquals(true, testSet.contains(1));
		compare = "[1]";
	}
	
	@Test
	public void testRemoveZero() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(6)); // goes to bucket 4
		testSet.add(new Integer(6)); // goes to bucket 5
		
		assertEquals(3, testSet.size());
		// table is 6 x 3
		assertEquals(3, testSet.remove(6, 0));
		compare = "[6 x 3]";
	}
	
	@Test
	public void testSetCountDecrease() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		testSet.add(new Integer(7));
		testSet.add(new Integer(7));
		testSet.add(6, 10);
		
		assertEquals(18, testSet.size());
		assertEquals(3, testSet.setCount(new Integer(7), 1));
		assertEquals(1, testSet.count(7));
		assertEquals(16, testSet.size());
		assertEquals(11, testSet.setCount(6, 5));
		assertEquals(5, testSet.count(6));
		assertEquals(10, testSet.size());
		
		compare = "[4, 14, 6 x 5, 7, 16, 17]";
	}
	
	@Test
	public void testSetCountIncrease() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		testSet.add(new Integer(7));
		testSet.add(new Integer(7));
		assertEquals(8, testSet.size());
		assertEquals(3, testSet.setCount(new Integer(7), 10));
		assertEquals(10, testSet.count(7));
		assertEquals(15, testSet.size());
		assertEquals(1, testSet.setCount(new Integer(17), 10));
		assertEquals(10, testSet.count(17));
		assertEquals(24, testSet.size());
		compare = "[4, 14, 6, 7 x 10, 16, 17 x 10]";
	}
	
	@Test
	public void testSetCountNegative() {
		boolean success = false;
		try {
			testSet.setCount(Integer.MAX_VALUE, Integer.MIN_VALUE);
		} catch (IllegalArgumentException e) {
			success = true;
		}
		assertEquals(true, success);
		compare = "[]";
	}
	
	@Test
	public void testSetCountNoChange() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		testSet.add(new Integer(7));
		testSet.add(new Integer(7));
		testSet.add(6, 10);
		
		assertEquals(18, testSet.size());
		assertEquals(11, testSet.setCount(6, 11));
		assertEquals(11, testSet.count(6));
		assertEquals(18, testSet.size());
		
		compare = "[4, 14, 6 x 11, 7 x 3, 16, 17]";
	}
	
	@Test
	public void testSetCountNull() {
		boolean success = false;
		try {
			testSet.setCount(null, Integer.MAX_VALUE);
		} catch (NullPointerException e) {
			success = true;
		}
		assertEquals(true, success);
		compare = "[]";
	}
	
	/**
	 * January 22 Andrew -- points out that it looked like I was testing for setCount to 0 NOT altering the list
	 * 
	 * Test below is updated to reflect correction While I think this is now accurate, it needs fact checking.
	 * 
	 * Fine -- update is correct
	 */
	@Test
	public void testSetCountToZero() {
		testSet.add(new Integer(6));
		testSet.add(new Integer(4)); // goes to bucket 4
		testSet.add(new Integer(14)); // goes to bucket 5
		testSet.add(new Integer(7)); // goes to bucket 7
		testSet.add(new Integer(16)); // goes to bucket 8
		testSet.add(new Integer(17)); // goes to 9
		testSet.add(new Integer(7));
		testSet.add(new Integer(7));
		assertEquals(3, testSet.setCount(new Integer(7), 0));
		assertEquals(0, testSet.count(7));
		assertEquals(false, testSet.contains(new Integer(7)));
		assertEquals(5, testSet.size());
		assertEquals(1, testSet.setCount(new Integer(17), 0));
		assertEquals(0, testSet.count(17));
		assertEquals(false, testSet.contains(17));
		assertEquals(4, testSet.size());
		compare = "[4, 14, 6, 16]";
	}
	
	/**
	 * Added November 2006 Stress test - repeatedly call test Exercise to look for consistency. In other year's student
	 * implementations failed testExercise about 1 in 10 times with an ArrayIndexOutOfBounds error
	 */
	@Test
	public void testStress() {
		// for (int i=0; i < 256; i++)
		// {
		// testExercise();
		// setUp();
		// }
	}
	
	@Test
	public void testToArray() {
		assertEquals(0, testSet.add(55));
		assertEquals(0, testSet.add(10));
		assertEquals(0, testSet.add(20));
		assertEquals(0, testSet.add(35));
		Object[] data = testSet.toArray();
		Object[] correctArray = new Integer[4];
		correctArray[0] = 10;
		correctArray[1] = 20;
		correctArray[2] = 55;
		correctArray[3] = 35;
		System.out.println(testSet);
		assertEquals(true, Arrays.equals(data, correctArray));
		assertEquals(4, testSet.size());
		compare = "[10, 20, 55, 35]";
	}
}
