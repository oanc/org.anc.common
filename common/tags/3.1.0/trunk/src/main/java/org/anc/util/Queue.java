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

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A simple queue class that implements a first-in first-out collection. The
 * Queue class is an adapter class for a java.util.LinkedList and adds items to
 * the end of the list and removes items from the front of the list.
 * <p>
 * Now (mostly) obsolete in the 1.5 JDK.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class Queue<T> extends AbstractCollection<T>
{
   public LinkedList<T> queue = new LinkedList<T>();

   public Queue()
   {
   }

   @Override
   public boolean add(T object)
   {
      queue.addLast(object);
      return true;
   }

   public T remove()
   {
      return queue.removeFirst();
   }

   @Override
   public int size()
   {
      return queue.size();
   }

   @Override
   public boolean isEmpty()
   {
      return queue.isEmpty();
   }

   @Override
   public void clear()
   {
      queue.clear();
   }

   public void addAll(T[] list)
   {
      for (int i = 0; i < list.length; ++i)
      {
         queue.add(list[i]);
      }
   }

   @Override
   public Iterator<T> iterator()
   {
      return queue.iterator();
   }
}
