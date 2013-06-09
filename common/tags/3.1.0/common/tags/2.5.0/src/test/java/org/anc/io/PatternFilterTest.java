package org.anc.io;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class PatternFilterTest
{

   @Test
   public void testAccept()
   {
      PatternFilter filter = new PatternFilter("*.txt");
      File file = new File("text.txt");
      assertTrue(filter.accept(file));
   }

   @Test
   public void testNotAccept()
   {
      PatternFilter filter = new PatternFilter("*.xml");
      File file = new File("text.txt");
      assertFalse(filter.accept(file));
   }
   
   @Test
   public void testPattern()
   {
      PatternFilter filter = new PatternFilter("sw*-ptb.xml");
      File shouldPass = new File("sw2014-a-trans-ptb.xml");
      File shouldFail = new File("sw2014-a-trans-fn.xml");
      assertTrue(filter.accept(shouldPass));
      assertFalse(filter.accept(shouldFail));      
   }
}
