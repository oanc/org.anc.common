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
 * Accepts files based on the file name's suffix. Note, the suffix need not be
 * just the file extension. The suffix filter simply calls
 * <code>java.lang.String.endsWith(<i>suffix</i>)</code> on the file name.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class SuffixFilter implements FileFilter
{
   private String suffix;
   private boolean includeDirectories = false;

   public SuffixFilter(String suffix)
   {
      this.suffix = suffix;
   }

   public SuffixFilter(String suffix, boolean includeDirectories)
   {
      this.suffix = suffix;
      this.includeDirectories = includeDirectories;
   }

   public SuffixFilter(String suffix, Boolean includeDirectories)
   {
      this.suffix = suffix;
      this.includeDirectories = includeDirectories.booleanValue();
   }

   /**
    * Tests whether or not the specified abstract pathname should be included in
    * a pathname list.
    * 
    * @param path
    *           The abstract pathname to be tested
    * @return <code>true</code> if and only if <code>path</code> should be
    *         included.
    */
   @Override
   public boolean accept(File path)
   {
      if (path.isDirectory())
      {
         return includeDirectories;
      }
      return path.getName().endsWith(suffix);
   }
}
