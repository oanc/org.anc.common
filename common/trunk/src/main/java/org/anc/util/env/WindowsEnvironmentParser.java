/*-
 * Copyright 2009 The American National Corpus
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
package org.anc.util.env;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;

import org.anc.util.Pair;

/**
 * Parses the output of the Windows <code>set</code> command to determine the
 * environment variables that have been set.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class WindowsEnvironmentParser implements IEnvironmentParser
{
   private static final String command = "cmd /C set";

   private LinkedList<Pair<String, String>> variables = new LinkedList<Pair<String, String>>();

   public WindowsEnvironmentParser()
   {
      reload();
   }

   @Override
   public Iterator<Pair<String, String>> iterator()
   {
      return variables.iterator();
   }

   /*
    * Returns the name of the next environment variable.
    * 
    * @return String The name of the next environment variable or null if there
    * is no such variable.
    * 
    * @todo Implement this ANC.util.env.IEnvironmentParser method
    */
//   public String getKey()
//   {
//      if (iterator.hasNext())
//      {
//         current = iterator.next();
//      }
//      else
//      {
//         current = END_OF_LIST;
//      }
//      return current.getFirst();
//   }

   /*
    * Returns the value of the next environment variable.
    * 
    * @return String The value of the next environment variable or null if there
    * is no such value.
    * 
    * @todo Implement this ANC.util.env.IEnvironmentParser method
    */
//   public String getValue()
//   {
//      return current.getSecond();
//   }

   public void reload()
   {
      Process process = null;
      try
      {
         process = Runtime.getRuntime().exec(command);
      }
      catch (IOException ex)
      {
         System.err.println("Could not execute command \"" + command
               + "\" to obtain environment information.");
         ex.printStackTrace();
         return;
      }

      InputStream stream = process.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
      try
      {
         String line = reader.readLine();
         while (line != null)
         {
            int equal = line.indexOf('=');
            if (equal > 0)
            {
               String key = line.substring(0, equal).toLowerCase();
               String value = line.substring(equal + 1);
               variables.add(new Pair<String, String>(key, value));
            }
            line = reader.readLine();
         }
         reader.close();
      }
      catch (IOException ex)
      {
         System.err.println("Error parsing environment information.");
         return;
      }
   }
}

//class KeyValue extends Pair<String, String>
//{
//   public KeyValue(String key, String value)
//   {
//	  super(key, value);
//   }
//}
