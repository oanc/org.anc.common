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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileContentsIterator implements Iterable<String>
{
   protected File file;

   public FileContentsIterator(File file) throws FileNotFoundException
   {
      this.file = file;
   }

   public java.util.Iterator<String> iterator()
   {
      return new Iterator();
   }

   class Iterator implements java.util.Iterator<String>
   {
      protected BufferedReader in;
      protected String line;
      protected IOException error = null;

      public Iterator()
      {
         try
         {
            in = new BufferedReader(new FileReader(file));
            line = in.readLine();
         }
         catch (IOException e)
         {
            error = e;
            line = null;
         }
      }

      public boolean hasNext()
      {
         return line != null;
      }

      public String next()
      {
         String result = line;
         try
         {
            line = in.readLine();
         }
         catch (IOException e)
         {
            error = e;
            return null;
         }
         return result;
      }

      public void remove()
      {
         throw new UnsupportedOperationException(
               "Unable to remove content from a file with this method.");
      }

      public IOException getError()
      {
         return error;
      }
   }
}
