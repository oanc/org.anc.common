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

import java.lang.reflect.Array;
import java.util.AbstractCollection;

/**
 * An array based heap data structure.
 * <p>
 * A heap data structure is (conceptually) a complete binary tree that obeys two
 * properties:
 * <ol>
 * <li>The smallest (or largest) item in the heap is contained in the root node
 * of the tree.
 * <li>All sub-trees in the heap are themselves heaps.
 * </ol>
 * <p>
 * What is meant by "smallest" and "largest" depends on the object and how it
 * implements the Comparable interface.
 * <p>
 * Heaps can be used to retrieve an object with the largest (or smallest) key
 * from a collection in <i>O(<b>log</b> n)</i> time, where <i>n</i> is the
 * number of objects in the heap. Another nice feature of heaps is that they can
 * be efficiently (and elegantly) implemented with arrays. If we use a one-based
 * array, that is the first element is heap[1], then given an element at
 * <code>heap[n]</code> we know that the element's parent is in
 * <code>heap[n/2]</code>, its left child is in <code>heap[2n]</code> and its
 * right child is in <code>heap[2n + 1]</code>.
 * <p>
 * Since the Heap uses a one-based array and Java uses zero-based arrays the
 * first element of the array is simply ignored.
 * <p>
 * The size of the heap is dynamic and doubles in size when more space is
 * needed. This means that the heap actualy performs in <i>ammortized
 * O(<b>log</b> n)</i> time, that is, every <i>n<supp>th</supp></i> insert will
 * require a <i>O(n)</i> resize operation.
 * 
 * @todo Have the heap shrink in size when it becomes "mostly" empty.
 * 
 * @author Keith Suderman
 * @version 1.0
 * 
 */
public class Heap<T extends Comparable<T>> extends AbstractCollection<T>
{
   /** Initial size of the heap */
   private static final int INITIAL_SIZE = 16;

   /** The number of objects currently on the heap. */
   protected int size = 0;

   /**
    * The number of objects the heap can hold before it needs to be resized. The
    * heap's capacity doubles every time it needs to resize.
    */
   protected int capacity;

   /** The heap's storage space. */
   protected Object[] heap = null;

   /** Default constructor. */
   public Heap()
   {
      this(INITIAL_SIZE);
   }

   /**
    * Constructor that permits the initial size of the heap to be specified.
    * 
    * @param initialSize
    *           The initial size of the heap.
    */
   public Heap(int initialSize)
   {
      capacity = initialSize;
      heap = new Object[capacity];
   }

   /**
    * Copy constructor. Note, this contructor only performs a shallow copy of
    * the array used to store the heap. The items contained in the heap are not
    * copied or cloned. Changing the elements in the copied heap will result in
    * changes to the element of the original heap.
    * 
    * @param other
    *           The heap that will be copied.
    */
   @SuppressWarnings("unchecked")
   public Heap(Heap<T> other)
   {
      size = other.size;
      capacity = other.capacity;
      heap = (T[]) Array.newInstance(other.peek().getClass(), capacity);
      System.arraycopy(other.heap, 0, heap, 0, size);
   }

   /**
    * Creates and returns an Iterator for traversing the underlying array. The
    * iterator does a linear traversal of the array, this returns the values in
    * the heap in a different order than is obtained by repeatedly calling
    * {@link #remove}.
    * 
    * @return An Iterator that iterates over the underlying array.
    */
   @Override
   public java.util.Iterator<T> iterator()
   {
      return new Iterator();
   }

   /**
    * Returns the number of objects in the heap. This is not the size of the
    * array used to represent the heap.
    * 
    * @return The number of objects in the heap.
    */
   @Override
   public int size()
   {
      return size;
   }

   /**
    * Adds the object to the heap and reorders the heap to obey the heap
    * properties. The object is added to the end of the array and then moved up
    * to it's proper location.
    * <p>
    * Every Nth insert (where N is the size of the array used to store the heap)
    * will cause the array to be resized and result in a O(N) cost for that
    * insert. However, this still gives us an ammortized O(log N) cost for all
    * inserts.
    * 
    * @param value
    *           The value to be added to the heap.
    */
   @Override
   public boolean add(T value)
   {

      if (size + 1 >= capacity)
      {
         grow();
      }
      ++size;
      heap[size] = value;
      this.bubbleUp();
      return true;
   }

   /**
    * Removes and returns the object at the top of the heap. To rebalance the
    * heap the last element is moved to the top of the heap and then moved back
    * down to its proper location.
    * <p>
    * Since the array is never resized when objects are removed all removals can
    * be performed in O(log N) time.
    * 
    * @return The object at the top of the heap.
    */
   @SuppressWarnings("unchecked")
   public T remove()
   {
      if (0 == size)
      {
         return null;
      }
      T result = (T) heap[1];
      heap[1] = heap[size];
      --size;
      this.bubbleDown(1);
      return result;
   }

   /**
    * Peeks at the object on top of the heap. The object is not removed from the
    * heap.
    * 
    * @return The object at the top of the heap, or null if the heap is empty.
    */
   @SuppressWarnings("unchecked")
   public T peek()
   {
      if (0 == size)
      {
         return null;
      }
      return (T) heap[1];
   }

   /**
    * Test if there are any objects on the heap.
    * 
    * @return true if the heap is empty, false otherwise.
    */
   @Override
   public boolean isEmpty()
   {
      return 0 == size;
   }

   /**
    * Remove all elements from the heap. All elements of the array are set to
    * null so the objects they referred to can be garbage collected.
    */
   @Override
   public void clear()
   {
      // Null all the references in the heap so they can be garbage collected.
      for (int i = 1; i <= size; ++i)
      {
         heap[i] = null;
      }
      size = 0;
   }

   /**
    * Doubles the size of the array used to store the heap elements.
    */
   protected void grow()
   {
      capacity += capacity;
      Object[] temp = new Object[capacity];
//        T[] temp = (T[]) Array.newInstance(heap[0].getClass(), capacity);
      System.arraycopy(heap, 0, temp, 0, heap.length);
      heap = temp;
   }

   /**
    * Swaps the two objects at <code>heap[e1]</code> and <code>heap[e2]</code>.
    * 
    * @param e1
    *           The array index of the first element.
    * @param e2
    *           The array index of the second element.
    */
   protected void swap(int e1, int e2)
   {
      Object temp = heap[e1];
      heap[e1] = heap[e2];
      heap[e2] = temp;
   }

   /**
    * Takes the value stored at the end of the array and moves it up to its
    * proper location in the array.
    * <p>
    * The element at the end of the array is compared to its parent and if it
    * compares "less than", that is
    * <code>comparator.compare(element, parent) < 0</code>, the two elements are
    * swapped. The element continues to bubble up the heap until either the root
    * of the heap is reached, or
    * <code>comparator.compare(element, parent) >= 0</code>.
    * 
    */
   protected void bubbleUp()
   {
      int current = size;
      int parent = current / 2;
      while (parent > 0)
      {
         if (compareElements(current, parent) < 0)
         {
            swap(current, parent);
            current = parent;
            parent = current / 2;
         }
         else
         {
            break;
         }
      }

   }

   /**
    * Takes the value from the subtree rooted at <code>heap[node]</code> and
    * moves it down to its proper location in the subtree. First the smallest
    * child is found and the value at <code>heap[node]</code> is compared to the
    * smaller of the two children. If the value at <code>heap[node]</code> is
    * smaller the two values are swapped and bubbleDown is called on that
    * subtree.
    * 
    * @param node
    *           The root of the subtree.
    */
   protected void bubbleDown(int node)
   {
      int lchild = node + node;
      int rchild = lchild + 1;

      if (lchild > size)
      {
         // There is no left child, which implies there is no right child,
         // which means we have reached the bottom of the tree and we can't
         // bubble down this value any farther.
         return;
      }

      if (rchild > size)
      {
         // There is no right child, so we only need to test the left child.
         if (compareElements(lchild, node) < 0)
         {
            // The left child contains a smaller value so swap them and rebalance
            // the subtree.
            swap(node, lchild);
            this.bubbleDown(lchild);
         }
      }
      else
      {
         // Find which subtree contains the smallest value, assume its the
         // right subtree to start
         int n = rchild;
         if (compareElements(lchild, rchild) < 0)
         {
            // Nope, left subtree contains the smallest value
            n = lchild;
         }

         // If the current node is smaller that the smallest child then swap
         // the nodes and continue to bubble down
         if (compareElements(n, node) < 0)
         {
            swap(n, node);
            this.bubbleDown(n);
         }
      }
   }

   /**
    * Prints the contens of the heap to System.out. Can be used for debugging
    * purposes.
    */
   protected void dump()
   {
      System.out.println("Heap dump");
      for (int i = 1; i <= size; ++i)
      {
         System.out.println("" + i + " : " + heap[i].toString());
      }

   }

   /**
    * Compares two objects on the heap. If a Comparator object was provided it
    * will be used to compare the objects on the heap, otherwise it is assumed
    * that the object implement the Comparable interface.
    * <p>
    * If no Comparator has been provided and the objects do not implement the
    * Comparable interface it is simply assumed the objects are "equal".
    * 
    * @param left
    *           The array index to the object on the left hand side of the
    *           comparison.
    * @param right
    *           The array index to the object on the right hand side of the
    *           comparison.
    * @return 0 if the objects are either not comparable with one another, or
    *         they compare as equal. -1 if the object at <code>heap[left]</code>
    *         compares smaller than <code>heap[right]</code> or 1 (one)
    *         otherwise.
    */
   @SuppressWarnings("unchecked")
   protected int compareElements(int left, int right)
   {
      T lhs = (T) heap[left];
      T rhs = (T) heap[right];

      return lhs.compareTo(rhs);
   }

   /**
    * Iterator class for the Heap. Note that the Heap Iterator simply performs a
    * linear traversal of the backing array. This results in the objects in the
    * heap being accessed in a different order than repeatedly calling
    * {@link #remove}. In particular, the elements in the heap will <b>not</b>
    * be accessed in the order specified by the comparator.
    * 
    * @author Keith Suderman
    * @version 1.0
    */
   class Iterator implements java.util.Iterator<T>
   {
      private int pointer = 1;

      public Iterator()
      {
      }

      public boolean hasNext()
      {
         return pointer <= size;
      }

      @SuppressWarnings("unchecked")
      public T next()
      {
         T result = (T) heap[pointer];
         ++pointer;
         return result;
      }

      public void remove()
      {
         throw new UnsupportedOperationException(
               "Objects in a heap can not be remove via an Iterator object.");
      }
   }
}

//class TestComparer implements java.util.Comparator
//{
//    public int compare(Object lhs, Object rhs)
//    {
//        int lvalue = ( (Integer) lhs).intValue();
//        int rvalue = ( (Integer) rhs).intValue();
//        return lvalue - rvalue;
//    }
//}

