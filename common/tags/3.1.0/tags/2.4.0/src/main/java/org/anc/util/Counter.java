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
 * A simple counter class for the many things that need to be counted. This is
 * essentially a mutable Integer class.
 * 
 * @author Keith Suderman
 * @version 1.0
 */

public class Counter implements Comparable<Counter>
{
   protected int count;

   public Counter()
   {
      count = 0;
   }

   public Counter(int start)
   {
      count = start;
   }

   public void increment()
   {
      ++count;
   }

   public void add(int n)
   {
      count += n;
   }

   public void reset()
   {
      count = 0;
   }

   public int getCount()
   {
      return count;
   }

   public int compareTo(Counter c)
   {
      return count - c.count;
   }

   @Override
   public int hashCode()
   {
      return count;
   }
   
   @Override
   public boolean equals(Object other)
   {
      if (other instanceof Counter)
      {
         return count == ((Counter) other).count;
      }
//      else if (other instanceof Number)
//      {
//         return count == ((Number) other).intValue();
//      }
      return false;
   }
   
   @Override
   public String toString()
   {
      return Integer.toString(count);
   }
}
