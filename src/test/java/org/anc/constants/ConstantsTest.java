package org.anc.constants;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

class A extends Constants
{
   @Default("default")
   public final String VALUE = null;
   
   public A()
   {
      super.init();
   }
}

class C extends Constants
{
   @Default("default")
   public final String VALUE = null;
   
   public C()
   {
      super.init();
   }
}

class D extends Constants
{
   @Default("default")
   public final String VALUE = null;
   
   public D()
   {
      super.init("org.anc.constants.test");
   }
}

//class Int1 extends Constants
//{
//   @Default("999")
//   public final int VALUE;
//   
//   public Int1()
//   {
//      super.init();
////      VALUE = VALUE;
//   }
//}

class Int2 extends Constants
{
   @Default("999")
   public final Integer VALUE = null;
   
   public Int2()
   {
      super.init();
   }
}

public class ConstantsTest 
{
   // This isn't so much a unit test as it is a way to log on remote servers
   // what it thinks its name is.
   @Test
   public void testGetName()
   {
      A a = new A();
      System.out.println("getName returns " + a.getName());
      assertTrue(a.getName() != null);
   }

   @Test
   public void tesDefault()
   {
      A a = new A();
      assertTrue("default".equals(a.VALUE));
   }

   /** NOTE: For this test to work run this class as a Java application (i.e run
    *  the main function below) and move the generated conf directory (in the root
    *  of the project) into src/test/resources.
    */
   @Test
   public void testFromConf()
   {
      C c = new C();
      System.out.println("getName returns " + c.getName());
      System.out.println("c.VALUE = " + c.VALUE);
      assertTrue("FILE".equals(c.VALUE));
   }
   
   @Test
   public void testFromProperty()
   {
      System.setProperty("org.anc.constants.test", "src/test/resources/test.properties");
      D d = new D();
      System.out.println(d.VALUE);
      assertTrue("PROPERTY".equals(d.VALUE));
   }
   
//   @Test
//   public void testInt1()
//   {
//      Int1 i = new Int1();
//      System.out.println("Int1: " + i.VALUE);
//      assertTrue(i.VALUE == 999);
//   }
   
   @Test
   public void testInt2()
   {
      Int2 i = new Int2();
      System.out.println("Int2: " + i.VALUE);
      assertTrue(i.VALUE == 999);
   }
   
   public static void main(String[] args)
   {
      try
      {
         C c = new C();
         System.out.println(c.VALUE);
         c.save();
         
//         Int1 i = new Int1();
//         i.save();
         Int2 j = new Int2();
         j.save();
      }
      catch (FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
   }
}
