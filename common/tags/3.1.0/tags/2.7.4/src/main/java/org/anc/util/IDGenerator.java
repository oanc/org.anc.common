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
import java.util.Map;

/**
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class IDGenerator
{
   protected Map<String, IDCounter> counters = new Hashtable<String, IDCounter>();
   protected int width = 0;
   private static final String zeroes = "000000000";

   public IDGenerator()
   {
      super();
   }

   public IDGenerator(int width)
   {
      this.width = width;
   }

   public synchronized String generate(String type)
   {
      IDCounter counter = counters.get(type);
      if (counter == null)
      {
         counter = new IDCounter();
         counters.put(type, counter);
      }
      String number = Integer.toString(counter.getNext());
      String pad = "";
      if (number.length() < width)
      {
         int n = width - number.length();
         if (n >= zeroes.length())
         {
            n = zeroes.length();
         }
         pad = zeroes.substring(0, n);
      }
      return type + pad + number;
   }

   public synchronized void reset()
   {
      counters.clear();
   }

   public synchronized void reset(String type)
   {
      counters.remove(type);
   }
}

class IDCounter
{
   private int nextId = 0;

   public int value()
   {
      return nextId;
   }

   public int getNext()
   {
      return nextId++;
   }
}
