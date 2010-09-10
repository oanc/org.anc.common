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
import java.io.FileFilter;

/**
 * Filters files based on the start (prefix) of the file name.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class PrefixFilter implements FileFilter
{
   private String prefix;
   private boolean includeDirectories = false;

   public PrefixFilter(String prefix)
   {
      this.prefix = prefix;
   }

   public PrefixFilter(String prefix, boolean includeDirectories)
   {
      this.prefix = prefix;
      this.includeDirectories = includeDirectories;
   }

   /**
    * Tests whether or not the specified abstract pathname should be included in
    * a pathname list.
    * 
    * @param path
    *           The abstract pathname to be tested
    * @return <code>true</code> if and only if <code>pathname</code> should be
    *         included. False otherwise.
    */
   @Override
   public boolean accept(File path)
   {
      if (includeDirectories && path.isDirectory())
      {
         return true;
      }
      return path.getName().startsWith(prefix);
   }

}
