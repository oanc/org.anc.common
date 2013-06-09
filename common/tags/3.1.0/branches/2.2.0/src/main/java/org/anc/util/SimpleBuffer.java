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

/**
 * A simple buffer class for building strings. This is similar to the
 * StringBuffer class except it is possible to reuse SimpleBuffer objects by
 * calling the <tt>reset()</tt> method.
 * 
 * @author Keith Suderman
 * @version 1.0
 */

public class SimpleBuffer
{
   private char[] buffer;
   private int size = 0;
   private int capacity = 64;
   private static final int GROW_SIZE = 32;

   public SimpleBuffer()
   {
      buffer = new char[capacity];
   }

   public SimpleBuffer(int initialCapacity)
   {
      capacity = initialCapacity;
      buffer = new char[capacity];
   }

   public void append(char[] ch, int start, int length)
   {
      if (size + length > capacity)
      {
         grow(size + length);
      }
      System.arraycopy(ch, start, buffer, size, length);
      size += length;
   }

   public void append(String s)
   {
      int len = s.length();
      int required = size + len;
      if (required > capacity)
      {
         grow(required);
      }
      System.arraycopy(s.toCharArray(), 0, buffer, size, len);
      size += len;
   }

   public void append(int ch)
   {
      add((char) ch);
   }

   public void add(char ch)
   {
//    char[] array = { ch };
      if (size + 1 > capacity)
      {
         grow(size + 1);
      }
      buffer[size] = ch;
      ++size;
   }

   protected void grow(int needed)
   {
      int newCap = capacity + GROW_SIZE;
      while (newCap < needed)
      {
         newCap += GROW_SIZE;
      }

      char[] newBuffer = new char[newCap];
      System.arraycopy(buffer, 0, newBuffer, 0, size);
      capacity = newCap;
      buffer = newBuffer;
   }

   @Override
   public String toString()
   {
      return new String(buffer, 0, size);
   }

   public void reset()
   {
      size = 0;
   }

   public int length()
   {
      return size;
   }

   public static void main(String[] args)
   {
      SimpleBuffer buffer = new SimpleBuffer();
      char[] array = "This is a test.".toCharArray();
      int n = array.length;
      buffer.append(array, 0, n);
      for (int i = 0; i < 100; ++i)
      {
         buffer.append(array, 0, n);
      }
      System.out.println(buffer.toString());
   }
}
