package org.anc.constants;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TestConstantsTest
{
   TestConstants K;
   File dir;
   
   @Before
   public void setup()
   {
      K = new TestConstants();
      dir = new File(K.OANC);
   }
   
   @Test
   public void testDir()
   {
      assertTrue(dir.getPath() + " Not found.", dir.exists());
   }
   
   @Test
   public void testHeader()
   {
      File header = new File(dir, K.HEADER);
      assertTrue(header.getPath() + " not found.", header.exists());
   }
}
