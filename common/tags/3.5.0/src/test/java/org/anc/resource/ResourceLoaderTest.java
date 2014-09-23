package org.anc.resource;

import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * @author Keith Suderman
 */
public class ResourceLoaderTest
{
   @Test
   public void testOpenStream()
   {
      InputStream stream = ResourceLoader.open("test.properties");
      assertTrue(stream != null);
   }

   @Test
   public void testLoadString() throws IOException
   {
      String string = ResourceLoader.loadString("test.properties");
      System.out.println("Loaded string: " + string);
      assertTrue(string.trim().equals("VALUE=PROPERTY"));
   }

   @Test
   public void testGetResource()
   {
      URL url = ResourceLoader.getResource("test.properties");
      assertNotNull(url);
   }
}
