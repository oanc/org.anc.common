package org.anc.util;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PropertiesTest
{
   private static final String FILENAME = "test.properties";
   
   public static class KV
   {
      public String key;
      public String value;
      
      public KV(String key, String value)
      {
         this.key = key;
         this.value = value;
      }
   };
   
   protected KV kv1 = new KV("key1", "value1");
   protected KV kv2 = new KV("key2", "value2");
   protected KV kv3 = new KV("key3", "value3");
   
   protected Properties p;
   
   @Before
   public void setUp()
   {
      p = new Properties();
      p.set(kv1.key, kv1.value);
      p.set(kv2.key, kv2.value);
      p.set(kv3.key, kv3.value);
   }
   
   @After
   public void tearDown()
   {
      p.clear();
      p = null;
      File file = new File(FILENAME);
      if (file.exists())
      {
         file.delete();
      }
   }
   
   @Test
   public void testSet()
   {
      assertTrue(kv1.value == p.get(kv1.key));
      assertTrue(kv2.value == p.get(kv2.key));
      assertTrue(kv3.value == p.get(kv3.key));
      assertTrue(p.get("foo") == null);
   }

   @Test
   public void testGet()
   {
      assertTrue(kv1.value == p.get(kv1.key));
      assertTrue(kv2.value == p.get(kv2.key));
      assertTrue(kv3.value == p.get(kv3.key));
   
      String replace = "replacement";
      p.set(kv1.key, replace);
      p.set(kv2.key, replace);
      p.set(kv3.key, replace);
      
      assertTrue(replace == p.get(kv1.key));
      assertTrue(replace == p.get(kv2.key));
      assertTrue(replace == p.get(kv3.key));
   }

   @Test
   public void testString() throws IOException
   {
      p.save(FILENAME);
      
      Properties p2 = new Properties();
      p2.load(FILENAME);
      
      for (String key : p.keys())
      {
         assertTrue(p.get(key).equals(p2.get(key)));
      }
   }

   @Test
   public void testFile() throws IOException
   {
      File file = new File(FILENAME);
      if (file.exists())
      {
         if (!file.delete())
         {
            fail("Unable to delete properties file.");
         }
      }
      p.save(file);
      
      Properties p2 = new Properties();
      p2.load(file);
      
      for (String key : p.keys())
      {
         assertTrue(p.get(key).equals(p2.get(key)));
      }
   }

   @Test
   public void testOutputStream() throws IOException
   {
      File file = new File(FILENAME);
      if (file.exists())
      {
         if (!file.delete())
         {
            fail("Unable to delete properties file.");
         }
      }
      OutputStream out = new FileOutputStream(file);
      p.save(out);
      out.close();
      
      InputStream in = new FileInputStream(file);
      Properties p2 = new Properties();
      p2.load(in);
      
      for (String key : p.keys())
      {
         assertTrue(p.get(key).equals(p2.get(key)));
      }
   }

}
