/*-
 * Copyright 2010 The American National Corpus
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
package org.anc.i18n;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.anc.io.UTF8Writer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/** Test to ensure local languages are detected are detected correctly. */
//@Ignore
public class LocaleTest
{
   protected static void printAll()
   {
      Messages messages = new Messages();
      try
      {
//         OutputStreamWriter osw = new OutputStreamWriter(System.out, "UTF-8");
         UTF8Writer osw = new UTF8Writer("c:/temp/translation.txt");
         PrintWriter writer = new PrintWriter(osw);
         writer.println(messages.HELLO);
         writer.println(messages.GOODBYE);
         writer.close();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   @Before
   public void setup()
   {
      System.out.println("Setting up Locale test.");
      File dir = new File(BaseTranslation.DEFAULT_LANG_LOCATION);
      if (dir.exists())
      {
         System.out.println("Deleting " + dir.getPath());
         rm(dir);
      }
      else
      {
         System.out.println(dir.getPath() + " does not exists");
      }
   }
   
   private void rm(File file)
   {
      if (file.isDirectory())
      {
         for (File f : file.listFiles())
         {
            rm(f);
         }
         
      }
      if (file.delete())
      {
         System.out.println("Removed " + file.getPath());
      }
      else
      {
         System.out.println(file.getPath() + 
               " not deleted. Tests may not work as expected");
      }
   }
   
   @Test
   public void testEnglish()
   {
      Locale.setDefault(Locale.US);
      printAll();
   }
   
   @Test
   public void testFrench()
   {
      Locale.setDefault(Locale.FRENCH);
      printAll();
   }
   
   @Test
   public void testGerman()
   {
      Locale.setDefault(Locale.GERMAN);
      printAll();
   }
   
   @Test
   public void testJapanese()
   {
      // There is no Japanese translation, so this should print the English
      // language messages.
      Locale.setDefault(Locale.JAPANESE);
      printAll();
   }
   
//   public static void main(String[] args)
//   {
//      try
//      {
//         BaseTranslation.save(Messages.class);
//         BaseTranslation.translate(Messages.class, "fr");
//         BaseTranslation.translate(Messages.class, "de");
//      }
//      catch (Exception e)
//      {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }
//      
//   }
}

final class Messages extends BaseTranslation
{
   @Default("Hello world.")
   public final String HELLO = null;
   @Default("Goodbye cruel world. You may edit the file.")
   public final String GOODBYE = null;
   
//   public Messages() { }
   public Messages()
   {
      init();
   }
}