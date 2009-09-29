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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A FileFilter that takes a perl like regular expression and accepts files that
 * matches the pattern.
 */
public class PatternFilter implements FileFilter
{
   private Pattern pattern = null;
   private boolean listDirectories = true;

   public PatternFilter()
   {
      super();
   }

   public PatternFilter(String regex)
   {
      this(regex, true);
   }

   public PatternFilter(String regex, boolean listDirectories)
   {
      regex = regex.replaceAll("\\.", "\\\\.");
      regex = regex.replaceAll("\\*", ".*");
      pattern = Pattern.compile(regex);
      this.listDirectories = listDirectories;
   }

   public boolean accept(File file)
   {
      if (pattern == null)
      {
         return file.isFile() || listDirectories;
      }
      if (file.isDirectory())
      {
         return listDirectories;
      }

      String filename = file.getName();
      CharSequence s = filename.subSequence(0, filename.length());
      Matcher matcher = pattern.matcher(s);
      return matcher.matches();
   }

   public static void main(String[] args)
   {
      PatternFilter filter = new PatternFilter("*.txt", false);
      filter.test("foo.txt", true);
      filter.test("foo.anc", false);
      filter.test("foo.xml", false);
      filter.test("foo.bar", false);
      filter.test("C:/", false);
      filter.test("/usr", false);
//      File file = new File("foo.txt");
//      if (filter.accept(file))
//         System.out.println("PASS");
//      else
//         System.out.println("FAIL");
   }

   protected void test(String name, boolean expected)
   {
      if (this.accept(new File(name)) == expected)
      {
         System.out.println("passed");
      }
      else
      {
         System.out.println("failed");
      }
   }
}
