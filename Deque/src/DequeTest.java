import static org.junit.Assert.assertEquals;
import jay.util.Deque;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * class DequeTest is a unit test for
 * the generic class Deque
 * 
 * Note: New Linked List structure lab for 2014,
 * idea from: http://www.cs.princeton.edu/courses/archive/spring13/cos226/assignments/queues.html
 * 
 * @author J. Smith
 * @version January 2014
 */
public class DequeTest
{
	/**
	 * Default constructor for test class DequeTest
	 */
	public DequeTest()
	{}

	@Test
	public void AddAddRemoveFirst()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addFirst("A");
		deque1.addFirst("B");
		assertEquals(2, deque1.size());
		assertEquals(false, deque1.isEmpty());
		String value = deque1.removeFirst();
		assertEquals(1, deque1.size());
		assertEquals(false, deque1.isEmpty());
		assertEquals(value, new String("B"));
	}

	@Test
	public void AddAddRemoveLast()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addLast("A");
		deque1.addLast("B");
		assertEquals(2, deque1.size());
		assertEquals(false, deque1.isEmpty());
		String value = deque1.removeLast();
		assertEquals(1, deque1.size());
		assertEquals(false, deque1.isEmpty());
		assertEquals(value, new String("B"));
	}

	// Stack behavior alone
	@Test
	public void AddFirstOne()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addFirst("A");
		assertEquals(1, deque1.size());
		assertEquals(false, deque1.isEmpty());
	}

	@Test
	public void AddFirstTwo()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addFirst("A");
		deque1.addFirst("B");
		assertEquals(2, deque1.size());
		assertEquals(false, deque1.isEmpty());
	}

	@Test
	public void AddLastOne()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addLast("A");
		assertEquals(1, deque1.size());
		assertEquals(false, deque1.isEmpty());
	}

	@Test
	public void AddLastTwo()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addLast("A");
		deque1.addLast("B");
		assertEquals(2, deque1.size());
		assertEquals(false, deque1.isEmpty());
	}

	@Test
	public void AddRemoveAddFirst()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addFirst("A");
		assertEquals(1, deque1.size());
		String value = deque1.removeFirst();
		assertEquals(0, deque1.size());
		assertEquals(value, new String("A"));
		deque1.addFirst("A");
		assertEquals(1, deque1.size());
	}

	@Test
	public void AddRemoveAddLast()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addLast("A");
		assertEquals(1, deque1.size());
		String value = deque1.removeLast();
		assertEquals(0, deque1.size());
		assertEquals(value, new String("A"));
		deque1.addLast("A");
		assertEquals(1, deque1.size());
	}

	@Test
	public void AddRemoveFirst()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addFirst("A");
		assertEquals(1, deque1.size());
		assertEquals(false, deque1.isEmpty());
		String value = deque1.removeFirst();
		assertEquals(0, deque1.size());
		assertEquals(true, deque1.isEmpty());
		assertEquals(value, new String("A"));
	}

	@Test
	public void AddRemoveLast()
	{
		Deque<String> deque1 = new Deque<String>();
		deque1.addLast("A");
		assertEquals(1, deque1.size());
		assertEquals(false, deque1.isEmpty());
		String value = deque1.removeLast();
		assertEquals(0, deque1.size());
		assertEquals(true, deque1.isEmpty());
		assertEquals(value, new String("A"));
	}

	@Test
	public void constructor()
	{
		Deque<String> deque1 = new Deque<String>();
		assertEquals(0, deque1.size());
		assertEquals(true, deque1.isEmpty());
	}

	@Test
	public void iterateIt()
	{
		Deque<Integer> deque1 = new Deque<Integer>();
		deque1.addLast(1);
		deque1.addLast(2);
		deque1.addLast(3);
		deque1.addLast(4); // list contains 1234
		deque1.addFirst(4);
		deque1.addFirst(3);
		deque1.addFirst(2);
		deque1.addFirst(1); // list contains 12341234
		assertEquals(8, deque1.size());
		int match = 1;
		for (int value : deque1) // Recall that for-each loops call on the iterator() method
		{
			System.out.println(value);
			assertEquals(match, value);
			match++;
			if (match == 5)
				match = 1; // back around
		}
		assertEquals(false, deque1.isEmpty());
		assertEquals(8, deque1.size());
	}

	@Test
	public void QueueBehavior()
	{
		Deque<Integer> deque1 = new Deque<Integer>();
		deque1.addLast(1);
		deque1.addLast(2);
		deque1.addLast(3);
		deque1.addLast(4);
		assertEquals(4, deque1.size());
		int match = 1;
		while (!deque1.isEmpty())
		{
			int value = deque1.removeFirst();
			assertEquals(match, value);
			match++;
		}
		assertEquals(true, deque1.isEmpty());
		assertEquals(0, deque1.size());
	}

	/**
	 * Sets up the test fixture.
	 * 
	 * Called before every test case method.
	 */
	@Before
	public void setUp()
	{}

	@Test
	public void StackBehavior()
	{
		Deque<Integer> deque1 = new Deque<Integer>();
		deque1.addFirst(1);
		deque1.addFirst(2);
		deque1.addFirst(3);
		deque1.addFirst(4);
		assertEquals(4, deque1.size());
		int match = 4;
		while (!deque1.isEmpty())
		{
			int value = deque1.removeFirst();
			assertEquals(match, value);
			match--;
		}
		assertEquals(true, deque1.isEmpty());
		assertEquals(0, deque1.size());
	}

	@Test
	public void StackBehavior2()
	{
		Deque<Integer> deque1 = new Deque<Integer>();
		deque1.addLast(1);
		deque1.addLast(2);
		deque1.addLast(3);
		deque1.addLast(4);
		assertEquals(4, deque1.size());
		int match = 4;
		while (!deque1.isEmpty())
		{
			int value = deque1.removeLast();
			assertEquals(match, value);
			match--;
		}
		assertEquals(true, deque1.isEmpty());
		assertEquals(0, deque1.size());
	}

	/**
	 * Tears down the test fixture.
	 * 
	 * Called after every test case method.
	 */
	@After
	public void tearDown()
	{}
}
