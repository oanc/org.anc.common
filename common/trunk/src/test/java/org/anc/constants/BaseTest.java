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
}

public class BaseTest
{
   @Test
   public void testBase()
   {
      BaseVariables v = new BaseVariables();
      assertTrue("Foo".equals(v.FOO));
   }

   public BaseTest()
   {

   }
}
