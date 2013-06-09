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
package org.anc.util;

import java.io.File;

/**
 * FileMap objects are used to associate the symbolic path of a file in the
 * corpus to the file's location on the local filesystem. This can typically be
 * done with a regex string replacement in the symbolic path.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class FileMap
{
   protected String root = null;

   public FileMap()
   {
   }

   public FileMap(String root)
   {
      super();
      this.root = root;
   }

   public void setRoot(String root)
   {
      this.root = root;
   }

   public File mapFile(String path)
   {
      if (root == null)
      {
         return new File(path);
      }

      if (path.startsWith("/"))
      {
         path = root + path;
      }
      else
      {
         path = root + "/" + path;
      }
      return new File(path);
   }
}
