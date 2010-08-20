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
package org.anc.i18n;

import static org.junit.Assert.*;

import org.anc.i18n.BaseTranslation;
import org.junit.Ignore;
import org.junit.Test;

public class BaseTranslationTest
{

   @Test
   public final void testCompleteStringString()
   {
      String expected1 = "A B";
      String expected2 = "B and B";
         
      String template = "A $1";
      String B = "B";
      String msg = BaseTranslation.complete(template, B);
      assertTrue(msg.equals(expected1));

      template = "$1 and $1";
      msg = BaseTranslation.complete(template, B);
      assertTrue(msg.equals(expected2));

   }

   @Test
   public final void testCompleteStringStringString()
   {
      String template = "A $1 $2";
      String b = "B";
      String c = "C";
      String expected = "A B C";
      String msg = BaseTranslation.complete(template, b, c);
      assertTrue(msg.equals(expected));
   }

   @Test
   public final void testTranslation()
   {
      Translation t = new Translation();
      System.out.println(t.MSG1);
      System.out.println(t.MSG2);
   }

   public static void main(String[] args)
   {
//      BaseTranslationTest test = new BaseTranslationTest();
//      test.testCompleteStringString();
//      test.testCompleteStringStringString();
      Translation t = new Translation();
      t.write(System.out, "Test");
//      BaseTranslation.write(Translation.class, System.out);
//      BaseTranslation.write(Translation.class, System.out);
   }
}

final class Translation extends BaseTranslation
{
   @Default("This is the first message.")
   public final String MSG1 = null;
   @Default("This is the second message.")
   public final String MSG2 = null;
   
   public Translation()
   {
      super();
      init();
   }
}