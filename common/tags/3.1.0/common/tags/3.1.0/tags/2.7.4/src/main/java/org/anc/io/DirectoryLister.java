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
import java.io.IOException;
import java.util.Iterator;

/**
 * The DirectoryLister class provides a way to iterate over the files in a
 * directory.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class DirectoryLister implements Iterable<File>
{
   private FileFilter filter = null;
   protected File directory;
   protected File[] listing = null;

   public DirectoryLister()
   {
   }

   public DirectoryLister(String directory) throws IOException
   {
      this(new File(directory));
   }

   public DirectoryLister(String directory, FileFilter filter)
         throws IOException
   {
      this(new File(directory), filter);
   }

   public DirectoryLister(File directory) throws IOException
   {
      if (!directory.isDirectory())
      {
         throw new IOException(directory.getPath() + " is not a directory.");
      }
      this.directory = directory;
   }

   public DirectoryLister(File directory, FileFilter filter) throws IOException
   {
      if (!directory.isDirectory())
      {
         throw new IOException(directory.getPath() + " is not a directory.");
      }
      this.directory = directory;
      this.filter = filter;
   }

   public int count()
   {
      getListing();
      return listing.length;
   }

   public boolean changeDirectory(String dir)
   {
      return changeDirectory(new File(dir));
   }

   public boolean changeDirectory(File dir)
   {
      if (!dir.isDirectory())
      {
         return false;
      }
      listing = null;
      this.directory = dir;
      return true;
   }

   public void setFilter(FileFilter filter)
   {
      this.filter = filter;
   }

   @Override
   public Iterator<File> iterator()
   {
      getListing();
      return new DirectoryListerIterator();
   }

   protected void getListing()
   {
      if (listing == null)
      {
         if (filter == null)
         {
            listing = directory.listFiles();
         }
         else
         {
            listing = directory.listFiles(filter);
         }
      }
   }

   class DirectoryListerIterator implements Iterator<File>
   {
      private int index = 0;

      public DirectoryListerIterator()
      {

      }

      @Override
      public boolean hasNext()
      {
         return listing != null && index < listing.length;
      }

      @Override
      public File next()
      {
         if (!hasNext())
         {
            return null;
         }
         File result = listing[index];
         ++index;
         return result;
      }

      @Override
      public void remove()
      {
         throw new UnsupportedOperationException(
               "Can not call remove() on a DiretoryLister iterator.");
      }
   }
}
