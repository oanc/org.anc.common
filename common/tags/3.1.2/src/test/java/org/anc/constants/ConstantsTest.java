package org.anc.constants;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

class A extends Constants
{
   @Default("default")
   public final String VALUE = null;
   
   public A()
   {
      super.init();
      System.out.println("A.A() " + VALUE);
   }
}

class C extends Constants
{
   @Default("default")
   public final String VALUE = null;
   
   public C()
   {
      super.init();
      System.out.println("C.C() " + VALUE);
   }
}

class D extends Constants
{
   @Default("default")
   public final String VALUE = null;
   
   public D()
   {
      super.init("org.anc.constants.test");
      System.out.println("D.D() " + VALUE);
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

class F extends Constants
{
   @Default("3.14")
   public final Float F_PI = null;
   @Default("3.14")
   public final Double D_PI = null;
   
   public F()
   {
      super.init();
   }
}

class Variables extends Constants
{
   @Default("root")
   public final String ROOT = null;
   
   @Default("$ROOT/foo")
   public final String FOO = null;
   
   @Default("$FOO/bar")
   public final String FOOBAR = null;
   
   @Default("$ROOT/$BAR")
   public final String ROOT_BAR = null;
   
   @Default("$BAR/$ROOT")
   public final String BAR_ROOT = null;
   
   @Default("$BAR/$ROOT/$BAR")
   public final String BAR_ROOT_BAR = null;
   
   @Default("$ROOT/$BAR/$ROOT")
   public final String ROOT_BAR_ROOT = null;
   
   public Variables()
   {
      super.init();
   }
}

class FileVariables extends Constants
{
   @Default("root")
   public final String ROOT = null;
   
   @Default("$ROOT/foo")
   public final String FOO = null;
   
   @Default("$FOO/bar")
   public final String FOOBAR = null;

   public FileVariables()
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
   public void testVariables()
   {
      Variables V = new Variables();
      check(V.ROOT, "root");
      check(V.FOO, "root/foo");
      check(V.FOOBAR, "root/foo/bar");
      check(V.ROOT_BAR, "root/$BAR");
      check(V.BAR_ROOT, "$BAR/root");
      check(V.BAR_ROOT_BAR, "$BAR/root/$BAR");
      check(V.ROOT_BAR_ROOT, "root/$BAR/root");
      
   }
   
   @Test
   public void testFileVariables()
   {
      FileVariables V = new FileVariables();
      check(V.ROOT, "root");
      check(V.FOO, "root/foo");
      check(V.FOOBAR, "root/foo/bar");
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
      assertTrue("XFILEX".equals(c.VALUE));
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
   
   @Test
   public void testFloats()
   {
      F f = new F();
      assertTrue(f.D_PI == 3.14);
      assertTrue(f.F_PI == 3.14f);
   }
   
   protected void check(String actual, String expected)
   {
//      System.out.println(actual + " : " + expected);
      assertTrue(actual.equals(expected));
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
         
         FileVariables f = new FileVariables();
         f.save();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
   }
}
