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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Reads Unicode characters from a stream of raw bytes. Use a UTF8Reader object
 * to read files written with a UTF8Writer.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class UTF8Reader extends InputStreamReader
{
   public static final String ENCODING = "UTF-8";
   public static final int BUFFER_SIZE = 4096;

   public UTF8Reader(InputStream in) throws UnsupportedEncodingException
   {
      super(in, ENCODING);
   }

   public UTF8Reader(String filename) throws FileNotFoundException,
         UnsupportedEncodingException
   {
      super(new FileInputStream(filename), ENCODING);
   }

   public UTF8Reader(File file) throws FileNotFoundException,
         UnsupportedEncodingException
   {
      super(new FileInputStream(file), ENCODING);
   }

   public String readString() throws FileNotFoundException, IOException
   {
      char[] buffer = new char[BUFFER_SIZE];
      StringBuilder builder = new StringBuilder();
      int nChars = this.read(buffer);
      while (nChars > 0)
      {
         builder.append(buffer, 0, nChars);
         nChars = this.read(buffer);
      }
      return builder.toString();
   }

}
