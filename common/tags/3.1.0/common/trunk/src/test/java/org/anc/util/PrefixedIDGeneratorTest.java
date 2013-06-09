package org.anc.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class PrefixedIDGeneratorTest
{
   @Test
   public void testGenerate()
   {
      PrefixedIDGenerator id = new PrefixedIDGenerator(null);
      String key = id.generate("n");
      assertTrue("n0".equals(key));
   }

   @Test
   public void testGeneratePrefix()
   {
      PrefixedIDGenerator id = new PrefixedIDGenerator("prefix");
      String key = id.generate("n");
      assertTrue("prefix-n0".equals(key));
   }

   @Test
   public void testReset()
   {
      PrefixedIDGenerator id = new PrefixedIDGenerator("prefix");
      id.generate("n");
      id.generate("n");
      assertTrue("prefix-n2".equals(id.generate("n")));
      id.reset();
      assertTrue("prefix-n0".equals(id.generate("n")));
   }

}
