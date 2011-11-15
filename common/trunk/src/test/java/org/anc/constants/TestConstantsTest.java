package org.anc.constants;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TestConstantsTest
{
   @Test
   public void test()
   {
      TestConstants K = new TestConstants();
      File dir = new File(K.OANC);
      assertTrue(dir.getPath() + " Not found.", dir.exists());
      
      File header = new File(dir, K.HEADER);
      assertTrue(header.getPath() + " not found.", header.exists());
   }
}
