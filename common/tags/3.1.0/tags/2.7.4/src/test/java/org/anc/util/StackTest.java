package org.anc.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest
{

   @Test
   public void testStack()
   {
      new Stack<String>();
      new Stack<Integer>(7);
   }

   @Test
   public void testIsEmpty()
   {
      Stack<String> s = new Stack<String>();
      assertTrue(s.isEmpty());
      s = new Stack<String>(4096);
      assertTrue(s.isEmpty());
   }

   @Test
   public void testPeek1()
   {
      Object item = new Object();
      Stack<Object> stack = new Stack<Object>();
      assertTrue(stack.peek() == null);
      stack.push(item);
      assertTrue(stack.peek() == item);
      assertTrue(stack.size() == 1);
   }

   @Test
   public void testPeek2()
   {
      Object item = new Object();
      Stack<Object> stack = new Stack<Object>();
      assertTrue(stack.peek() == null);
      stack.push(new Object());
      stack.push(new Object());
      stack.push(item);
      assertTrue(stack.peek() == item);
      assertTrue(stack.size() == 3);
   }

   @Test
   public void testPop1()
   {
      Object item = new Object();
      Stack<Object> stack = new Stack<Object>();
      assertTrue(stack.pop() == null);
      stack.push(new Object());
      stack.push(new Object());
      stack.push(item);
      assertTrue(stack.size() == 3);
      assertTrue(stack.pop() == item);
      assertTrue(stack.size() == 2);
   }

   @Test
   public void testPop2()
   {
      Stack<Object> ancStack = new Stack<Object>();
      assertTrue(ancStack.pop() == null);
      
      java.util.Stack<Object> javaStack = new java.util.Stack<Object>();
      for (int i = 0; i < 1024; ++i)
      {
         Object item = new Object();
         javaStack.push(item);
         ancStack.push(item);
      }
      
      while (!javaStack.isEmpty())
      {
         assertTrue(!ancStack.isEmpty());
         assertTrue(javaStack.pop() == ancStack.pop());
      }
      assertTrue(ancStack.isEmpty());
   }

   public void testClear()
   {
      Stack<Integer> stack = new Stack<Integer>();
      for (int i = 0; i < 4096; ++i)
      {
         assertTrue(stack.size() == i);
         stack.push(Integer.valueOf(i));
      }
      stack.clear();
      assertTrue(stack.isEmpty());
   }
}
