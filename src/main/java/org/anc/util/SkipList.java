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

import java.util.Iterator;

/**
 * <b>Experimental</b>.
 * <p>
 * A skip list is a linked list with additonal links that skip over N elements
 * in the list. This permits linear searches of the list to skip N nodes when
 * looking for the desired node. Once the section of the list containing the
 * desired node is located a linear search of those N nodes is performed.
 * 
 * <p>
 * Note: only every Nth node contains the "skip link".
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class SkipList<T extends Comparable<T>> implements Iterable<T>
{
   /** Number of nodes each skip link skips over. */
   private static final int MAX_SKIP_SIZE = 64;

   /** Half of the above. Precalculated to save time. */
   private static final int HALF_MAX = 32;

   /** The first node in the list. */
   private SkipNode<T> fHead = null;

//  private SkipNode<T> fTail = null;
   /** The number of nodes in the list. */
   private long fSize = 0;

   public SkipList()
   {
   }

   public long size()
   {
      return fSize;
   }

   public void clear()
   {
      fSize = 0;
      fHead.delete();
   }

   public void add(T item)
   {
      // assume the insertion always suceeds.
      ++fSize;
      if (fHead == null)
      {
         fHead = new SkipNode<T>(item);
         return;
      }

      SkipNode<T> block = findBlock(item);
      addToBlock(block, item);
   }

   public void replace(T item)
   {
      if (fHead == null)
      {
         add(item);
         return;
      }

      SkipNode<T> node = find(item);
      if (node == null)
      {
         add(item);
      }
      else
      {
         node.setItem(item);
      }
   }

   public T get(T item)
   {
      SkipNode<T> node = find(item);
      if (node == null)
      {
         return null;
      }
      return node.getItem();
   }

   public SkipNode<T> find(T item)
   {
      SkipNode<T> block = findBlock(item);
      if (block == null)
      {
         return null;
      }
      SkipNode<T> end = block.getNextSkip();
      while (block != end)
      {
         if (item.compareTo(block.getItem()) == 0)
         {
            return block;
         }
         block = block.getNext();
      }
      return null;
   }

   public void remove(T item)
   {
      SkipNode<T> node = find(item);
      delete(node);
   }

   public void delete(SkipNode<T> node)
   {
      if (node == null)
      {
         return;
      }
//	  System.out.println("Deleting " + node.getItem());
      --fSize;
      assert (fSize >= 0);
      SkipNode<T> prev = node.getPrev();
      SkipNode<T> next = node.getNext();
      if (node == fHead)
      {
         // If next is null then we are deleting the last item in the list.
         if (next != null)
         {
            assert (fSize == 0);
            next.setPrev(null);
            next.setNextSkip(fHead.getNextSkip());
            next.setSkipSize(fHead.getSkipSize() - 1);
         }
         fHead = next;
         return;
      }

      if (prev != null)
      {
         prev.setNext(next);
      }
      if (next != null)
      {
         next.setPrev(prev);
      }

      // See if this node marks a skip point in the skip list.
      next = node.getNextSkip();
      prev = node.getPrevSkip();
      if (next != null)
      {
         next.setPrevSkip(prev);
      }
      if (prev != null)
      {
         prev.setNextSkip(next);
         prev.setSkipSize(prev.getSkipSize() + node.getSkipSize() - 1);
         if (prev.getSkipSize() > MAX_SKIP_SIZE)
         {
            split(prev);
         }
      }
      node = null;
   }

   @Override
   public Iterator<T> iterator()
   {
      return new SkipListIterator(fHead);
   }

   /**
    * Return the late node such that the item stored at that node is <= the item
    * we are looking for. Performs a linear search of the list using the skip
    * nodes.
    * 
    * @param item
    *           T The item being searched for.
    * @return SkipNode The last node in the list such that the item in that node
    *         is <= the item we are looking for.
    */
   protected SkipNode<T> findBlock(T item)
   {
      if (fHead == null)
      {
         return null;
      }
      SkipNode<T> block = fHead.getNextSkip();
      SkipNode<T> prev = fHead;
      while (block != null && item.compareTo(block.getItem()) >= 0)
      {
         prev = block;
         block = block.getNextSkip();
      }
      // If block == null then we reached the end of the list, otherwise
      // block points to the node after the block we are interested in.
      return prev;
   }

   protected void addToBlock(SkipNode<T> block, T item)
   {
      // addToBlock should not be called on an empty list, that case
      // should be handled by the add method directly.
      assert (block != null);

      // The node we will insert.
      SkipNode<T> node = new SkipNode<T>(item);

      SkipNode<T> current = block;
      SkipNode<T> prev = null;
      while (current != null && item.compareTo(current.getItem()) > 0)
      {
         prev = current;
         current = current.next;
      }

      // The new node should be inserted between the nodes referenced by
      // 'prev' and 'current'.  If prev == null the new node is the new head
      // of the list and if current == null the new node is the new tail of
      // the list.
      if (prev == null)
      {
         // But prev has not been set which implies this item is to be added as
         // the first item in the block. This also implies that the previous node
         // in the skip list needs its skip link updated also.
         if (current.getPrev() == null)
         {
            // this is the new head of the list
            node.setNext(fHead);
            node.setNextSkip(fHead.getNextSkip());
            node.setSkipSize(fHead.getSkipSize() + 1);

            fHead.setPrev(node);
            fHead.setSkipSize(0);
            fHead = node;
            if (fHead.getSkipSize() > MAX_SKIP_SIZE)
            {
               split(fHead);
            }
         }
         else
         {
            SkipNode<T> prevNode = current.getPrev();
            SkipNode<T> prevSkip = current.getPrevSkip();
            // This should be the first node in the block, and there should be
            // a previous block in the skip list.
            assert (prevSkip != null);
            prevNode.setNext(node);
            prevSkip.setNextSkip(node);
            node.setPrev(prevNode);
            node.setPrevSkip(prevSkip);
            node.setNext(current);
            node.setNextSkip(current.getNextSkip());
            node.setSkipSize(current.getSkipSize() + 1);
            current.setPrev(node);
            current.setPrevSkip(null);
            current.setSkipSize(0);

            if (block.getSkipSize() > MAX_SKIP_SIZE)
            {
               split(block);
            }
         }
      }
      else
      {
         // This node gets inserted somewhere in the middle of the block.
         block.incSkipSize();
         prev.setNext(node);
         node.setPrev(prev);
         node.setNext(current);
         if (current != null)
         {
            current.setPrev(node);
         }
         if (block.getSkipSize() > MAX_SKIP_SIZE)
         {
            split(block);
         }
      }
   }

   protected void split(SkipNode<T> block)
   {
      SkipNode<T> nextBlock = block.getNextSkip();
      SkipNode<T> midpoint = block;
      for (int i = 0; i < HALF_MAX; i++)
      {
         midpoint = midpoint.getNext();
      }

      midpoint.setSkipSize(block.getSkipSize() - HALF_MAX);
      midpoint.setPrevSkip(block);
      midpoint.setNextSkip(nextBlock);

      block.setSkipSize(HALF_MAX);
      block.setNextSkip(midpoint);

      if (nextBlock != null)
      {
         nextBlock.setPrevSkip(midpoint);
      }
   }

   public void print()
   {
      Iterator<T> it = iterator();
      while (it.hasNext())
      {
         T i = it.next();
         System.out.println(i);
      }
      System.out.println("Done");
   }

   public static void main(String[] args)
   {
      SkipList<Integer> skiplist = new SkipList<Integer>();
//    for (int i = 0; i < 200 ; ++i )
//    {
//      skiplist.add(new Integer(i));
//    }
//    skiplist.print();
//
//    for (int i = 0; i < 200; i += 2)
//    {
//      skiplist.remove(new Integer(i));
//    }
      skiplist.add(Integer.valueOf(1));
      skiplist.add(Integer.valueOf(5));
      skiplist.add(Integer.valueOf(2));
      skiplist.add(Integer.valueOf(10));
      skiplist.add(Integer.valueOf(9));
      skiplist.add(Integer.valueOf(5));
      skiplist.add(Integer.valueOf(3));
      skiplist.add(Integer.valueOf(4));
      skiplist.add(Integer.valueOf(6));
      skiplist.add(Integer.valueOf(8));
      skiplist.add(Integer.valueOf(7));

      skiplist.print();
   }

   public class SkipNode<T1>
   {
      protected SkipNode<T1> next = null;
      protected SkipNode<T1> prev = null;
      protected SkipNode<T1> nextSkip = null;
      protected SkipNode<T1> prevSkip = null;

      private T1 item;
      private int skipSize = 0;

      public SkipNode(T1 item)
      {
         this.item = item;
      }

//
//    public SkipNode(T item, SkipNode<T> next)
//    {
//      this(item, next, null, 0);
//    }
//
//    public SkipNode(T item, SkipNode<T> next, SkipNode<T> skip, int size)
//    {
//      this.item = item;
//      this.next = next;
//      this.skip = skip;
//      skipSize = size;
//    }

      public void delete()
      {
         if (next != null)
         {
            next.delete();
         }
         next = prev = nextSkip = prevSkip = null;
      }

      public SkipNode<T1> getNext()
      {
         return next;
      }

      public SkipNode<T1> getPrev()
      {
         return prev;
      }

      public SkipNode<T1> getNextSkip()
      {
         return nextSkip;
      }

      public SkipNode<T1> getPrevSkip()
      {
         return prevSkip;
      }

      public T1 getItem()
      {
         return item;
      }

      public int getSkipSize()
      {
         return skipSize;
      }

      public void incSkipSize()
      {
         ++skipSize;
      }

      public void setNext(SkipNode<T1> next)
      {
         this.next = next;
      }

      public void setPrev(SkipNode<T1> prev)
      {
         this.prev = prev;
      }

      public void setNextSkip(SkipNode<T1> next)
      {
         nextSkip = next;
      }

      public void setPrevSkip(SkipNode<T1> prev)
      {
         prevSkip = prev;
      }

      public void setItem(T1 item)
      {
         this.item = item;
      }

      public void setSkipSize(int size)
      {
         this.skipSize = size;
      }
   }

   class SkipListIterator implements Iterator<T>
   {
      private SkipNode<T> iterator;

      public SkipListIterator(SkipNode<T> start)
      {
         iterator = start;
      }

      @Override
      public boolean hasNext()
      {
         return iterator != null;
      }

      @Override
      public T next()
      {
         T result = iterator.getItem();
         iterator = iterator.getNext();
         return result;
      }

      @Override
      public void remove()
      {
         if (iterator == null)
         {
            return;
         }
         SkipNode<T> node = iterator;
         iterator = iterator.getNext();
         SkipList.this.delete(node);
      }
   }
}
