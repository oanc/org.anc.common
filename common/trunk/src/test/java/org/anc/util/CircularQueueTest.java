package org.anc.util;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Keith Suderman
 */
public class CircularQueueTest
{
	private CircularQueue<Integer> queue;

	public CircularQueueTest()
	{

	}

	@Before
	public void setup()
	{
		queue = new CircularQueue<>();
	}

	@After
	public void cleanup()
	{
		queue = null;
	}

	@Test
	public void newQueueShouldBeEmpty()
	{
		System.out.println("CircularQueueTest.newQueueShouldBeEmpty");
		assertTrue(queue.empty());
		assertTrue(queue.size() == 0);
	}

	@Test
	public void defaultQueueSizeIs16()
	{
		System.out.println("CircularQueueTest.defaultQueueSizeIs16");
		assertTrue(queue.getCapacity() == 16);
	}

	@Test
	public void queueWithOneElementNotEmpty()
	{
		System.out.println("CircularQueueTest.queueWithOneElementNotEmpty");
		queue.add(1);
		assertTrue(queue.size() == 1);
	}

	@Test
	public void testGrow()
	{
		System.out.println("CircularQueueTest.testGrow");
		int expected = 17;
		for(int i = 0; i < expected; ++i)
		{
			queue.add(i);
		}
		int actual = queue.size();
		String message = String.format("Expected %d Actual %d", expected, actual);

		assertTrue(message, actual == expected);
		assertTrue(queue.getCapacity() == 32);
		for (int i = 0; i < expected; ++i)
		{
			assertTrue(i == queue.remove());
		}
	}

	@Test
	public void testRollOver()
	{
		System.out.println("CircularQueueTest.testRollOver");
		for (int i = 0; i < 15; ++i)
		{
			assertTrue(queue.size() == i);
			queue.add(i);
		}
		assertTrue(queue.size() == 15);

		for (int i = 0; i < 15; ++i)
		{
			assertTrue(i == queue.remove());
			assertTrue(queue.size() == 15 - i - 1);
		}
		assertTrue(queue.empty());
		assertTrue(queue.size() == 0);
		// At this point we should have start == end == 15.  The next add
		// should cause end to roll over to 0 (zero).
		for (int i = 0; i < 15; ++i)
		{
			queue.add(i);
		}
		for (int i = 0; i < 15; ++i)
		{
			assertTrue(i == queue.remove());
		}
	}

	@Test
	public void testIterator()
	{
		System.out.println("CircularQueueTest.testIterator");
		int n = 57;
		for (int i = 0; i < n; ++i)
		{
			queue.add(i);
		}
		assertTrue(n == queue.size());
		assertTrue(queue.getCapacity() == 64);
		Iterator<Integer> it = queue.iterator();
		assertTrue(it.hasNext());
		int expected = 0;
		while (it.hasNext())
		{
			assertTrue(expected == it.next());
			++expected;
		}
		assertTrue(queue.size() == n);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveFromEmptyQueue() //throws IndexOutOfBoundsException
	{
		System.out.println("CircularQueueTest.testRemoveFromEmptyQueue");
		queue.remove();
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testIterateOverEmptyQueue() //throws IndexOutOfBoundsException
	{
		System.out.println("CircularQueueTest.testIterateOverEmptyQueue");
		Iterator<Integer> it = queue.iterator();
		assertFalse(it.hasNext());
		it.next();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveNotAllowed() //throws UnsupportedOperationException
	{
		System.out.println("CircularQueueTest.testRemoveNotAllowed");
		queue.add(1);
		Iterator<Integer> it = queue.iterator();
		assertTrue(it.hasNext());
		it.remove();
	}
	@Test
	public void testAddAllArray()
	{
		System.out.println("CircularQueueTest.testAddAllArray");
		Integer[] items = new Integer[] { 1,2,3,4,5,6,7,8,9,10 };
		queue.addAll(items);
		assertTrue(queue.size() == 10);
		for (Integer i : items)
		{
			assertTrue(i == queue.remove());
		}
	}

	@Test
	public void testAddAllList()
	{
		System.out.println("CircularQueueTest.testAddAllList");
		Integer[] items = new Integer[] { 1,2,3,4,5,6,7,8,9,10 };
		List<Integer> list = new ArrayList<>(Arrays.asList(items));
		queue.addAll(list);
		assertTrue(queue.size() == 10);
		Iterator<Integer> listIterator = list.iterator();
		Iterator<Integer> queueIterator = queue.iterator();
		while (listIterator.hasNext() && queueIterator.hasNext())
		{
			assertTrue(listIterator.next() == queueIterator.next());
		}
		assertFalse(listIterator.hasNext());
		assertFalse(queueIterator.hasNext());
	}
}
