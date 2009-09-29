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

import java.util.Stack;

/**
 * @author Keith Suderman
 * @version 1.0
 */
public class IndentManager
{
   public String increment = "    ";
   protected String amount = "";
   protected Stack<String> stack = new Stack<String>();

//   public String size() { return amount; }

   public IndentManager()
   {
      super();
   }

   public IndentManager(String defaultIndent)
   {
//	   amount = defaultIndent;
      increment = defaultIndent;
   }

   public void more()
   {
      stack.push(amount);
      amount += increment;
   }

   public void more(String size)
   {
      stack.push(amount);
      amount += size;
   }

   public void less()
   {
      if (!stack.empty())
      {
         amount = stack.pop();
      }
      else
      {
         amount = "";
      }
   }

   public void reset()
   {
      stack.clear();
      amount = "";
   }

   @Override
   public String toString()
   {
      return amount;
   }
}
