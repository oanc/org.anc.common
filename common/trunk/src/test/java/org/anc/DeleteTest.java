package org.anc;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.anc.Aspect;
import org.junit.Test;

public class DeleteTest
{

   @Test
   public void testDeleteFile() throws IOException
   {
      File target = new File("target");
      assertTrue(target.exists());
      File file = new File(target, "deleteMe");
      createFile(file);
      assertTrue(file.exists());
      Aspect.delete(file);
      assertTrue(!file.exists());
   }

   @Test
   public void testDeleteDirectory() throws IOException
   {
      File target = new File("target");
      assertTrue(target.exists());
      File directory = new File(target, "deleteMe");
      assertTrue(directory.mkdirs());
      File file = new File(directory, "deleteMe");
      createFile(file);
      assertTrue(file.exists());
      Aspect.delete(file);
      assertTrue(!file.exists());
   }
   
   private void createFile(File file) throws IOException
   {
      FileWriter writer = new FileWriter(file);
      writer.write("delete me");
      writer.close();
   }
}
