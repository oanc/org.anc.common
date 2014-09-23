package org.anc.constants;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author Keith Suderman
 */

abstract class Base extends Constants
{
   public Base()
   {
      super.init();
   }
}

class BaseVariables extends Base {
   @Default("Foo")
   public final String FOO = null;

   public BaseVariables()
   {
      super.init();
   }

}

public class BaseTest
{
   @Test
   public void testBase()
   {
      BaseVariables v = new BaseVariables();
      String expected = "Foo";
      String actual = v.FOO;
      assertTrue("Actual is null!", v.FOO != null);
      String message = String.format("Expected: %s Found: %s", expected, actual);
      assertTrue(message, actual.equals(expected));
   }

   public BaseTest()
   {

   }
}
