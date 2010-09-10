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
package org.anc.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Keith Suderman
 * @version 1.0
 */
public class Path implements Iterable<String>
{
   protected List<String> components = new ArrayList<String>();
   protected String asString = null;

   public Path()
   {
      super();
   }

   public Path(File root)
   {
      this(root.getPath());
   }

   public Path(String root)
   {
      asString = root;
      parse(root);
   }

   @Override
   public String toString()
   {
      if (asString == null)
      {
         makeString();
      }
      return asString;
   }

   public void add(Path path)
   {
      for (String component : path)
      {
         components.add(component);
      }
      asString = null;
   }

   public void add(String directory)
   {
//        components.addLast(directory);
      parse(directory);
      asString = null;
   }

   public String remove()
   {
      if (components.size() == 0)
      {
         return null;
      }
      asString = null;
      return components.remove(components.size() - 1);
   }

   public int length()
   {
      return components.size();
   }

   public String component(int i)
   {
      if (i >= 0 && i < components.size())
      {
         return null;
      }
      return components.get(i);
   }

   public String last()
   {
      if (components.size() == 0)
      {
         return null;
      }
      return components.get(components.size() - 1);
   }

   public File asFile()
   {
      if (asString == null)
      {
         makeString();
      }
      return new File(asString);
   }

   @Override
   public Iterator<String> iterator()
   {
      return components.iterator();
   }

   protected void parse(String root)
   {
      StringTokenizer tokens;
      if (root.startsWith("/"))
      {
         tokens = new StringTokenizer(root.substring(1), "/");
      }
      else
      {
         tokens = new StringTokenizer(root, "/");
      }
      while (tokens.hasMoreTokens())
      {
         components.add(tokens.nextToken());
      }
   }

   protected void makeString()
   {
      if (components.size() == 0)
      {
         asString = "/";
      }
      else
      {
         StringBuilder builder = new StringBuilder();
         Iterator<String> it = components.iterator();
         builder.append(it.next());
         while (it.hasNext())
         {
            builder.append("/" + it.next());
         }
         asString = builder.toString();
      }
   }
}
