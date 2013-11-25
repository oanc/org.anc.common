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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class HeapTest
{

   @Test
   public final void testSize0()
   {
      Heap<String> heap = new Heap<String>();
      assertTrue(heap.size() == 0);
   }

   public final void testSize1()
   {
      Heap<String> heap = new Heap<String>();
      heap.add("a");
      assertTrue(heap.size() == 1);
   }

   public final void testSize2()
   {
      Heap<String> heap = new Heap<String>();
      heap.add("a");
      heap.add("b");
   }

   public final void testSize1000()
   {
      Heap<String> heap = new Heap<String>();
      for (int i = 0; i < 1000; ++i)
      {
         heap.add("a");
         assertTrue(heap.size() == i - 1);
      }
   }

   @Test
   public final void testIsEmpty()
   {
      Heap<String> heap = new Heap<String>();
      assertTrue(heap.isEmpty());
   }

   @Test
   public final void testClear1()
   {
      Heap<Integer> heap = new Heap<Integer>();
      for (int i = 0; i < 100; ++i)
      {
         heap.add(i);
      }
      heap.clear();
      if (!heap.isEmpty())
      {
         fail("Heap still has " + heap.size() + " elements");
      }
   }

   @Test
   public final void testClear2()
   {
      Heap<Integer> heap = new Heap<Integer>();
      for (int i = 0; i < 100; ++i)
      {
         heap.add(i);
      }
      heap.clear();
      if (heap.size() != 0)
      {
         fail("Heap still has " + heap.size() + " elements");
      }
   }

   @Test
   public final void testPeek()
   {
      Heap<String> heap = new Heap<String>();
      heap.add("foo");
      assertTrue("foo".equals(heap.peek()));
   }

}
