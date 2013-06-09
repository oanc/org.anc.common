/*-
 * Copyright 2009 The American National Corpus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.anc.util;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * <b>Experimental</b>.
 * <p>
 * An array/vector like data structure with a large capacity, that is a large
 * maximum index, but that is expected to be mostly empty. For example, an array
 * with space for one million elements but that will only ever contain a few
 * hundred items.
 * <p>
 * This implementation uses two a data structures to provide the backing store
 * for the sparse array. A {@link org.anc.util.SkipList} to provide efficient
 * linear access to the elements of the array, and a {@link java.util.Hashtable}
 * to provide efficient random access to the elements of the array.
 * <p>
 * <b>NOTE:</b> The iterator returned by a call to method <tt>iterator()</tt>
 * iterates over the items present in the array and not the individual array
 * elements. For example, the following only prints two items even though the
 * array is 1,000 elements "long". 
 * <pre>
 *     SparseArray<String> array = new SparseArray<String>();
 *     array.setElementAt(0, "Hello");
 *     arrar.setElementAt(999, "World");
 *     for (String s : array)
 *     {
 *         System.out.println(s);
 *     }
 * </pre>
 * 
 * 
 * @author Keith Suderman
 * @version 1.0
 */
// TODO Implement the Collection interface and/or extend AbstractCollection.
public class SparseArray<T> implements Iterable<T>
{
   /** A list of the elements in the array to provide efficient iterator access. */
   private SkipList<Element<T>> fList = new SkipList<Element<T>>();

   /** A hash table of the elements to provide efficient random access. */
   private Hashtable<Index, Element<T>> fTable = new Hashtable<Index, Element<T>>();

   /** The largest index of any element stored in the array. */
   private long fMaxIndex = 0;

   public SparseArray()
   {
   }

   public void setElementAt(long index, T value)
   {
      if (index > fMaxIndex)
      {
         fMaxIndex = index;
      }
      Element<T> e = new Element<T>(index, value);
      fList.replace(e);
      fTable.put(new Index(index), e);
   }

   public T getElementAt(long index)
   {
      Element<T> e = fTable.get(new Index(index));
      if (e == null)
      {
         return null;
      }
      return e.getElement();
   }

   public long size()
   {
      return fList.size();
   }

   public long getMaxIndex()
   {
      return fMaxIndex;
   }

   public void clear()
   {
      fMaxIndex = 0;
      fList.clear();
      fTable.clear();
   }

   @Override
   public Iterator<T> iterator()
   {
      return new SparseArrayIterator<T>(fList.iterator());
   }

   public static void main(String[] args)
   {
      SparseArray<Integer> sparsearray = new SparseArray<Integer>();
      for (int i = 0; i < 10; ++i)
      {
         System.out.println("Setting element at " + (i + i));
         sparsearray.setElementAt(i + i, Integer.valueOf(i));
      }

      for (int i = 0; i < 10; ++i)
      {
         Integer j = sparsearray.getElementAt(i + i);
         System.out.println("index " + (i + i) + ": " + j);
      }

      Iterator<Integer> it = sparsearray.iterator();
      while (it.hasNext())
      {
         System.out.println(it.next());
      }
   }
}

class SparseArrayIterator<T> implements Iterator<T>
{
   private Iterator<Element<T>> iterator;

   public SparseArrayIterator(Iterator<Element<T>> it)
   {
      iterator = it;
   }

   @Override
   public boolean hasNext()
   {
      return iterator.hasNext();
   }

   @Override
   public T next()
   {
      return iterator.next().getElement();
   }

   @Override
   public void remove()
   {
      //      Iterator<Element> next = iterator.next();
      //      iterator.remove();
      //      iterator = next;
      throw new UnsupportedOperationException(
            "Deletion via a SparseArrayIterator is not allowed.");
   }

}

/**
 * An element of the sparse array. The parameter type T is the type of objects
 * stored in the array.
 * 
 */
class Element<T> implements Comparable<Element<T>>
{
   /** The index of the this element in the array. */
   private long index;
   /** The item to be stored in this array element. */
   private T element;

   public Element(long index, T element)
   {
      this.index = index;
      this.element = element;
   }

   /**
    * Two elements are ordered by their index and are equal iff their indices
    * are the same.
    */
   @Override
   public int compareTo(Element<T> other)
   {
      return (int) (index - other.index);
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((element == null) ? 0 : element.hashCode());
      result = prime * result + (int) (index ^ (index >>> 32));
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Element<?> other = (Element<?>) obj;
      if (element == null)
      {
         if (other.element != null)
            return false;
      }
      else if (!element.equals(other.element))
         return false;
      if (index != other.index)
         return false;
      return true;
   }
   
   /** Return the item stored in this element. */
   public T getElement()
   {
      return element;
   }

   @Override
   public String toString()
   {
      return index + ": " + element.toString();
   }
}

/**
 * An index into the sparse array. Index objects are used as the keys into the
 * hash table backing store.
 */
class Index implements Comparable<Index>
{
   protected long fIndex;

   public Index(long index)
   {
      fIndex = index;
   }

   @Override
   public int compareTo(Index i)
   {
      return (int) (fIndex - i.fIndex);
   }

   @Override
   public boolean equals(Object object)
   {
      if (!(object instanceof Index))
      {
         return false;
      }
      Index i = (Index) object;
      return fIndex == i.fIndex;
   }

   @Override
   public int hashCode()
   {
      return (int) fIndex;
   }
}
