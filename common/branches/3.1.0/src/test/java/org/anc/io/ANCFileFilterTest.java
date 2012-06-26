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

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

public class ANCFileFilterTest
{
   protected static final String ROOT = "/";
   protected File directory = new File(ROOT);
   protected File header = new File("test.anc");
   protected File nonHeader = new File("test.txt");

   @Test
   public void testANCFileFilterDefault()
   {
      ANCFileFilter filter = new ANCFileFilter();
      if (!filter.accept(directory))
      {
         fail("Default ANCFileFilter should accept directories");
      }
   }

   @Test
   public void testANCFileFilterTrue()
   {
      ANCFileFilter filter = new ANCFileFilter(true);
      if (!filter.accept(directory))
      {
         fail("Directory not accepted by filter.");
      }
   }

   @Test
   public void testANCFileFilterFalse()
   {
      ANCFileFilter filter = new ANCFileFilter(false);
      if (filter.accept(directory))
      {
         fail("Directory not rejected by filter.");
      }
   }

   @Test
   public void testAccept1()
   {
      ANCFileFilter filter = new ANCFileFilter();
      if (!filter.accept(header))
      {
         fail("ANCFileFilter did not accept the header file");
      }
   }

   @Test
   public void testAccept2()
   {
      ANCFileFilter filter = new ANCFileFilter();
      if (filter.accept(nonHeader))
      {
         fail("Non header file accepted.");
      }
   }
}
