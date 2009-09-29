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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ArgsTest
{
   class Params
   {
      public static final String ARG1 = "-arg1";
      public static final String ARG2 = "-arg2";
      public static final String ARG3 = "-arg3";
   }

   protected static final String[] all = { Params.ARG1, Params.ARG2,
         Params.ARG3 };
   protected static final String[] p12 = { Params.ARG1, Params.ARG2 };
   protected static final String[] bad = { "-foo" };
   protected static final String VALUE = "value";

   protected static final String[] REQUIRED = { Params.ARG1, Params.ARG3 };

   @Test
   public void testClassConstructor()
   {
      Args args = new Args(all, Params.class);
      assertTrue(args.valid());
   }

   @Test
   public void testBadClassConstructor()
   {
      Args args = new Args(bad, Params.class);
      assertFalse(args.valid());
   }

   @Test
   public void testDefined()
   {
      String[] params = { Params.ARG1 };
      Args args = new Args(params, Params.class);
      assertTrue(args.defined(Params.ARG1));
      assertFalse(args.defined(Params.ARG2));
   }

   @Test
   public void testArgValue()
   {
      String[] params = { Params.ARG1 + "=" + VALUE };
      Args args = new Args(params, Params.class);
      assertTrue(args.valid());
      assertTrue(args.defined(Params.ARG1));
      assertTrue(VALUE.equals(args.get(Params.ARG1)));
   }

   @Test
   public void testRequired()
   {
      String[] params = { Params.ARG1 + "=" + VALUE, Params.ARG3 + "=" + VALUE };
      Args args = new Args(params, REQUIRED, Params.class);
      assertTrue(args.valid());
   }

   @Test
   public void testRequiredNotPresent()
   {
      String[] params = { Params.ARG1 + "=" + VALUE, Params.ARG2 + "=" + VALUE };
      Args args = new Args(params, REQUIRED, Params.class);
      assertFalse(args.valid());
   }
}
