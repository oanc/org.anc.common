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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Extends the java.io.PrintStream and provides constructors that ensure the
 * stream's character encoding is set to UTF-8
 * 
 * @author not attributable
 * @version 1.0
 */
public class UTF8PrintStream extends PrintStream
{
   public static final String ENCODING = "UTF-8";

   public UTF8PrintStream(OutputStream out) throws UnsupportedEncodingException
   {
      this(out, true);
   }

   public UTF8PrintStream(String fileName) throws FileNotFoundException,
         UnsupportedEncodingException
   {
      this(new FileOutputStream(fileName), true);
   }

   public UTF8PrintStream(File file) throws FileNotFoundException,
         UnsupportedEncodingException
   {
      this(new FileOutputStream(file), true);
   }

   public UTF8PrintStream(OutputStream out, boolean autoFlush)
         throws UnsupportedEncodingException
   {
      super(out, autoFlush, ENCODING);
   }

}
