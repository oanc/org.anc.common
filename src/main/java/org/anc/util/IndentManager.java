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
 * Instances of the IndentManager class are used to control the indentation
 * level when pretty printing text/xml output. By default the IndentManager
 * will use an indentation size of four spaces, but this can be changed with
 * the parameterized constructor <code>IndentManager(String)</code>/
 * <p>
 * Everytime the {@link #more()} method is called the current indentation 
 * level is pushed onto a stack and the indentation level is increased by the
 * default amount.  Everytime the {@link #less()} method is called the stack
 * is popped and the current indentation level is set to the String popped from
 * the stack.
 * <p>
 * <b>Example</b>
 * <pre>
 *     IndentManager indent = new IndentManager();
 *     System.out.println(indent + "&lt;start&gt;");
 *     prettyPrintSomething(indent);
 *     System.out.println(indent + "&lt;/start&gt;");
 *     ...
 *     void prettyPrintSomething(IndentManager indent)
 *     {
 *         indent.more();
 *         System.out.println(indent + "&lt;something/&gt;");
 *         indent.less();
 *     }
 * </pre>
 * 
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
