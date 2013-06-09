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

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.anc.Sys;
import org.anc.util.Pair;

/**
 * Provides access to the operating system environmental variables. The
 * environmental variables are read-only since Java provides no mechanism for
 * manipulating environmental variables.
 * 
 * NOTE: 
 * 
 * @author Keith Suderman
 * @version 1.0
 * @deprecated This class is now obsolete with the introduction (finally) of
 * System.getenv.
 */
@Deprecated
public class Environment
{
   /**
    * A parser that is able to parse the operating system's environmental
    * variables and provide the name/value pairs.
    */
   private IEnvironmentParser parser = null;
   private Hashtable<String, String> environment = new Hashtable<String, String>();

   /**
    * The default constructor assumes we are operating in a Windows environment.
    */
   public Environment()
   {
      this(new WindowsEnvironmentParser());
   }

   public Environment(IEnvironmentParser parser)
   {
      this.parser = parser;
      load();
   }

   public void load()
   {
      for (Pair<String, String> pair : parser)
      {
         environment.put(pair.getKey().toLowerCase(), pair.getValue());
      }
   }

   public String get(String key)
   {
      if (environment.size() == 0)
      {
         return null;
      }

      return environment.get(key.toLowerCase());
   }

   @Override
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      Set<Map.Entry<String, String>> entrySet = environment.entrySet();
      Iterator<Map.Entry<String, String>> esIt = entrySet.iterator();
      while (esIt.hasNext())
      {
         Map.Entry<String, String> entry = esIt.next();
         buffer.append(entry.getKey() + " = " + entry.getValue() + Sys.EOL);
      }
      return buffer.toString();
   }

   public void print(PrintWriter out)
   {
      out.print(this.toString());
   }

   public void print(BufferedWriter out) throws IOException
   {
      out.write(this.toString());
   }

   public void print(PrintStream out)
   {
      out.print(this.toString());
   }

   /** For testing only. */
   public static void main(String[] args)
   {
      Environment environment = new Environment();
      environment.print(System.out);
   }

}
