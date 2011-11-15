package org.anc.constants;

import java.io.FileNotFoundException;


public class TestConstants extends Constants
{
   @Default("/home/www/anc/oanc-graf")
   public final String OANC = null;
   @Default("OANC-corpus-header.xml")
   public final String HEADER = null; 
   @Default("/tmp")
   public final String TEMP = null;

   public TestConstants()
   {
      super.init();
   }
   
   public static void main(String[] args)
   {
      try
      {
         new TestConstants().save();
         System.out.println("Constants saved.");
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }
}
