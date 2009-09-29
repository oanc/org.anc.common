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
 * Filter for ANC header files.
 * 
 * @author Keith Suderman
 * @version 1.0
 */

public class ANCFileFilter implements FileFilter
{
   protected boolean acceptDirectories = true;

   public ANCFileFilter()
   {
   }

   public ANCFileFilter(boolean acceptDirectories)
   {
      this.acceptDirectories = acceptDirectories;
   }

   public boolean accept(File pathname)
   {
      if (pathname.isDirectory())
      {
         return acceptDirectories;
      }
      return pathname.getName().endsWith(".anc");
   }
}
