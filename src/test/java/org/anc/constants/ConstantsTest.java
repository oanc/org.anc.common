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

public class ConstantsTest 
{

   @Test
   public void tesDefault()
   {
      A a = new A();
      assertTrue("default".equals(a.VALUE));
   }

   /** NOTE: For this test to work un-comment the main function below, 
    *  run the class, and move the generated conf directory (in the root
    *  of the project) into src/test/resources
    */
   @Test
   public void testFromConf()
   {
      C c = new C();
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
   
//   public static void main(String[] args)
//   {
//      try
//      {
//         C c = new C();
//         System.out.println(c.VALUE);
//         c.save();
//      }
//      catch (FileNotFoundException e)
//      {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//   }
}
