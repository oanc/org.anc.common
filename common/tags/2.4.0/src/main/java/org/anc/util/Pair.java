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

import java.util.Map;

/**
 * A simple pair class for all the pairs of things that we need to keep track
 * of.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class Pair<E1, E2> implements Map.Entry<E1, E2>
{
   protected E1 first = null;
   protected E2 second = null;

   public Pair()
   {
   }

   public Pair(E1 first, E2 second)
   {
      this.first = first;
      this.second = second;
   }

   public E1 getFirst()
   {
      return first;
   }

   public E2 getSecond()
   {
      return second;
   }

   public void setFirst(E1 first)
   {
      this.first = first;
   }

   public void setSecond(E2 second)
   {
      this.second = second;
   }

   public E1 getKey()
   {
      return first;
   }

   public E2 getValue()
   {
      return second;
   }

   public void setKey(E1 key)
   {
      first = key;
   }

   public E2 setValue(E2 value)
   {
      second = value;
      return second;
   }
}
