package org.anc.io;

import java.io.File;
import java.io.IOException;

/**
 * Helper functions for reading/writing strings to/from files.
 *
 * @author Keith Suderman
 */
public class FileUtils
{
   public FileUtils()
   {

   }

   /** Writes the String to the file using UTF-8. */
   public static final void write(String path, String string) throws IOException
   {
      write(new File(path), string);
   }

   /** Writes the String to the file using UTF-8. */
   public static final void write(File file, String string) throws IOException
   {
      UTF8Writer writer = new UTF8Writer(file);
      try
      {
         writer.write(string);
      }
      finally
      {
         writer.close();
      }
   }

   public static final String read(String path) throws IOException
   {
      return read(new File(path));
   }

   public static final String read(File file) throws IOException
   {
      UTF8Reader reader = new UTF8Reader(file);
      String result = null;
      try
      {
         result = reader.readString();
      }
      finally
      {
         reader.close();
      }
      return result;
   }
}
