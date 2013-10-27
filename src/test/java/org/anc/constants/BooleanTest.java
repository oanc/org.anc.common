package org.anc.constants;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author Keith Suderman
 */
public class BooleanTest
{
   public BooleanTest()
   {

   }

   @Test
   public void booleanNoFileTest()
   {
      BooleanNoFile c = new BooleanNoFile();

      assertTrue(c.T);
      assertFalse(c.F);
   }

   @Test
   public void booleanFileTest()
   {
      BooleanFile c = new BooleanFile();
      assertTrue(c.T);
      assertFalse(c.F);
   }
}

class BooleanNoFile extends Constants
{
   @Default("false")
   public final Boolean F = null;

   @Default("true")
   public final Boolean T = null;

   public BooleanNoFile()
   {
      init();
   }
}

/* This class will be initialized from a properties file that provides
 * different values for the fields.
 */
class BooleanFile extends Constants
{
   @Default("true")
   public final Boolean F = null;

   @Default("false")
   public final Boolean T = null;

   public BooleanFile()
   {
      init();
   }
}