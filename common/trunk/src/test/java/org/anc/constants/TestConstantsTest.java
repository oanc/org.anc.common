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
      assertTrue(dir.exists());
      
      File header = new File(K.HEADER);
      assertTrue(header.exists());
   }
}
