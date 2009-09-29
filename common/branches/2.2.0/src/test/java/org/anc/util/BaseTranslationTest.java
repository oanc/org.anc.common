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

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class BaseTranslationTest
{

   @Test
   public final void testCompleteStringString()
   {
      String template = "This is a file: $1";
      String file = "C:/temp/round-4";
      String msg = BaseTranslation.complete(template, file);
      System.out.println(msg);

      template = "$1 and $1";
      msg = BaseTranslation.complete(template, file);
      System.out.println(msg);

   }

   @Test
   public final void testCompleteStringStringString()
   {
      String template = "this is one: $1 and this is two: $2";
      String file1 = "C:/temp/round-4";
      String file2 = "C:/temp/round-3";
      String msg = BaseTranslation.complete(template, file1, file2);
      System.out.println(msg);
   }

   public static void main(String[] args)
   {
      BaseTranslationTest test = new BaseTranslationTest();
      test.testCompleteStringString();
      test.testCompleteStringStringString();
   }
}
