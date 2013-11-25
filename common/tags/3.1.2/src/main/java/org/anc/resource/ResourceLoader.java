package org.anc.resource;

import org.anc.io.UTF8Reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * A class that provides utility methods for loading resources from
 * the classpath.
 *
 * @author Keith Suderman
 */
public class ResourceLoader
{
   private ResourceLoader()
   {

   }

   /**
    * Returns an InputStream for reading the specified resource.
    *
    * @param name the name of the resource to be opened.
    * @return an opened InputStream that can be used to read the resource,
    * or null if no such resource exists.
    */
   public static InputStream open(String name)
   {
      return getClassLoader().getResourceAsStream(name);
   }

   /**
    * Reads a UTF-8 string from the specified resource.
    *
    * @param name the name of the resource to be read.
    * @return a UTF-8 string containing the contents of the resource.
    * @throws IOException if the resource can not be found
    */
   public static String loadString(String name) throws IOException
   {
      InputStream stream = open(name);
      if (name == null)
      {
         throw new IOException("Resource not found: " + name);
      }
      UTF8Reader reader = new UTF8Reader(stream);;
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

   private static ClassLoader getClassLoader()
   {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null)
      {
         loader = ResourceLoader.class.getClassLoader();
      }
      return loader;
   }
}
