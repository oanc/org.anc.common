/*-
 * Copyright 2011 The American National Corpus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.anc.util;

import java.io.*;
import java.util.*;

import org.anc.io.UTF8Reader;
import org.anc.io.UTF8Writer;

/**
 * A synchronization free replacement for the {@link java.util.Properties} class
 * that uses a {@link java.util.HashMap HashMap} as the backing store.
 * <p>
 * Unlike the Java Properties class this class can only load/store to a line
 * based UTF-8 text file. Serialization to/from XML files is not supported.
 * 
 * @author Keith Suderman
 *
 */
public class Properties
{
   protected HashMap<String,String> map;
   
   public Properties()
   {
      map = new HashMap<String, String>();
   }
   
   /**
    * Adds the key/value pair to the properties table. Any previous key value
    * is overwritten. Adding a key with a NULL value removes that key from
    * the hash table.
    * 
    * @param key The property to be set.
    * @param value The value the property will be set to.
    */
   public void set(String key, String value)
   {
      if (value == null)
      {
         map.remove(key);
      }
      else
      {
         map.put(key, value);
      }
   }
   
   /**
    * Returns a property value from the properties table. Returns NULL if
    * the property has not been set, of the if the property has been
    * removed from the table.
    * 
    * @param key The property name.
    * @return The property value, or NULL if the property has not been set.
    */
   public String get(String key)
   {
      return map.get(key);
   }
   
   /** Removes all key/value pairs from the properties table. */
   public void clear()
   {
      map.clear();
   }
   
   /** Returns the number of key/value pairs contained in the properties table. */
   public int size() { return map.size(); }
   
//   public Iterator<String> keys()
//   {
//      return map.keySet().iterator();
//   }
   
   public Iterable<String> keys()
   {
      return Collections.unmodifiableSet(map.keySet());
   }
   
   public void save(String path) throws UnsupportedEncodingException, FileNotFoundException
   {
      save(new UTF8Writer(path));
   }
   
   public void save(File file) throws UnsupportedEncodingException, FileNotFoundException
   {
      save(new UTF8Writer(file));
   }
   
   public void save(OutputStream stream) throws UnsupportedEncodingException
   {
      save(new UTF8Writer(stream));
   }
   
   protected void save(UTF8Writer utf8)
   {
      PrintWriter writer = new PrintWriter(utf8);
      for (String key : map.keySet())
      {
         String value = map.get(key);
         if (value != null)
         {
            writer.println(key + "=" + value);
         }
      }
      writer.close();
   }
   
   public void load(String path) throws IOException, UnsupportedEncodingException
   {
      load(new UTF8Reader(path));
   }
   
   public void load(File file) throws IOException, UnsupportedEncodingException
   {
      load(new UTF8Reader(file));
   }
   
   public void load(InputStream stream) throws IOException, UnsupportedEncodingException
   {
      load(new UTF8Reader(stream));
   }
   
   protected void load(UTF8Reader utf8) throws IOException
   {
      map.clear();
      BufferedReader reader = new BufferedReader(utf8);
      try
      {
         String line = reader.readLine();
         while (line != null)
         {
            if (!line.startsWith("#") && line.length() > 0)
            {
               int equal = line.indexOf('=');
               if (equal < 0)
               {
                  throw new IOException("Malformed properties file. No '=' found in line: " + line);
               }
               if (equal == line.length() - 1)
               {
                  throw new IOException("Malformed properties file. No value specified in line: " + line);
               }
               String key = line.substring(0, equal).trim();
               String value = line.substring(equal + 1);
               map.put(key, value);
            }
            line = reader.readLine();
         }
      }
      finally
      {
         if (reader != null) try
         {
            reader.close();
         }
         catch (IOException e)
         {
            // Don't allow finally blocks to throw 
            // exceptions. If the finally block is being run because
            // of an exception in the try block we want the caller
            // to see the original exception.
         }
      }
   }
}
