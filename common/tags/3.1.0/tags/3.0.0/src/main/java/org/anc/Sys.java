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
package org.anc;

/**
 * @author Keith Suderman
 * @version 1.0
 */

public class Sys
{
   public static final String EOL = System.getProperty("line.separator");
//   public static final String FILE_SEP = System.getProperty("file.separator");
   
   /** Operating system dependant file separator. */
   public static final String OS_FILE_SEP = System.getProperty("file.separator");
   /** The Java file separator.  This should be used 99% of the time (even
     * on Windows). The only time to use the OS_FILE_SEP is if an application
     * has to write an external file/script that will be processed natively
     * by the OS (rather than by another Java application).
     */ 
   public static final String FILE_SEP = "/";

   private Sys()
   {
   }
}
