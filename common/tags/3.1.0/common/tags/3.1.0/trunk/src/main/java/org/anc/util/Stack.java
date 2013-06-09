/*-
 * Copyright 2011 The American National Corpus
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

import java.util.ArrayList;

/**
 * A non-synchronized replacement for the java.util.Stack class. The 
 * stack is implemented with an {@link ArrayList} with the
 * "top" of the stack at the end of the list.
 * <p>
 * This implementation only includes the basic stack operations and does not
 * extend any Java collection class or implement any interface. None of 
 * the methods throw exceptions and <code>peek</code> and <code>pop</code>
 * return <code>null</code> if the stack is empty when they are called.
 * 
 * @author Keith Suderman
 *
 */
public class Stack<T>
{
   protected final ArrayList<T> stack;
   
   public Stack()
   {
      this(16);
   }
   
   public Stack(int initialSize)
   {
      stack = new ArrayList<T>(initialSize);
   }
   
   public boolean isEmpty()
   {
      return stack.size() == 0;
   }
   
   public T peek()
   {
      int top = stack.size() - 1;      
      if (top >= 0)
      {
         return stack.get(top);
      }
      return null;
   }
   
   public T pop()
   {
      int top = stack.size() - 1;      
      if (top >= 0)
      {
         return stack.remove(top);
      }
      return null;
   }
   
   public void push(T item)
   {
      stack.add(item);
   }
 
   public void clear()
   {
      stack.clear();
   }
   
   public int size()
   {
      return stack.size();
   }
}
