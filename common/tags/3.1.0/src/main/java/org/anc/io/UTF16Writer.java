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
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Provides a simple bridge for writing the raw bytes of Unicode characters to
 * an output stream.
 * <p>
 * We write the "characters" as raw bytes to prevent Java from changing end of
 * line characters and fixing malformed byte sequences in the data. The data IS
 * THE DATA and it is not up to Java to decide when our data should be "fixed"
 * for us.
 * <p>
 * This class does nothing but provide some constructors that call the
 * OutputStreamWriter constructor with the appropriate values. No other methods
 * are overridden.
 * <p>
 * The first constructor takes an OutputStream and calls the superclass
 * constructor setting the encoding to UTF-16. The other constructors use the
 * parameter to construct a java.io.FileOutputStream object and then invoke the
 * first constructor with that.
 * 
 * @author Keith Suderman
 * @version 1.0
 */

public class UTF16Writer extends OutputStreamWriter
{
   public static final String ENCODING = "UTF-16";

   public UTF16Writer(OutputStream out) throws UnsupportedEncodingException
   {
      super(out, ENCODING);
   }

   public UTF16Writer(String filename) throws FileNotFoundException,
         UnsupportedEncodingException
   {
      this(new FileOutputStream(filename)); //, ENCODING);
   }

   public UTF16Writer(File file) throws FileNotFoundException,
         UnsupportedEncodingException
   {
      this(new FileOutputStream(file)); //, ENCODING);
   }
}
