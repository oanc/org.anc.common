package org.anc.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Keith Suderman
 */
public class CircularQueue<T> implements Iterable<T>
{
	private T[] storage;
	private int start;
	private int end;
	private int capacity;

	public CircularQueue()
	{
		this(16);
	}

	public CircularQueue(int initialCapacity)
	{
		if (initialCapacity <= 0)
		{
			initialCapacity = 16;
		}

		this.capacity = initialCapacity;
		this.storage = (T[]) new Object[this.capacity];
		this.start = 0;
		this.end = 0;
	}

	public int size()
	{
		if (end < start)
		{
			return end + capacity - start;
		}
		return end - start;
	}

	public int getCapacity()
	{
		return this.capacity;
	}

	public boolean empty()
	{
		return start == end;
	}

	public void addAll(T[] items)
	{
		for (T item : items)
		{
			add(item);
		}
	}

	public void addAll(Collection<T> items)
	{
		for (T item : items)
		{
			add(item);
		}
	}

	public void add(T item)
	{
		if (size() == capacity - 1)
		{
			grow();
		}
		storage[end] = item;
		end = next(end);
	}

	public T remove()
	{
		if (empty())
		{
			throw new IndexOutOfBoundsException();
		}

		T item = storage[start];
		start = next(start);
		return item;
	}

	public Iterator<T> iterator()
	{
		return new CircularQueueIterator();
	}

	private int next(int index)
	{
		return (index + 1) % capacity;
	}

	private void grow()
	{
		int newCapacity = capacity + capacity;
		T[] newStorage = (T[]) new Object[newCapacity];
		int i = 0;
		while (start != end)
		{
			newStorage[i++] = storage[start];
			start = next(start);
		}
		storage = newStorage;
		capacity = newCapacity;
		start = 0;
		end = i;

	}

	public class CircularQueueIterator implements Iterator<T>
	{
		private int start;
		private int end;

		public CircularQueueIterator()
		{
			this.start = CircularQueue.this.start;
			this.end = CircularQueue.this.end;
		}

		public T next()
		{
			if (start == end)
			{
				throw new IndexOutOfBoundsException();
			}
			T item = CircularQueue.this.storage[start];
			start = CircularQueue.this.next(start);
			return item;
		}

		public boolean hasNext()
		{
			return start != end;
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}
