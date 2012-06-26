/*-
 * Copyright 2011 The American National Corpus
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
package org.anc.xml;

/**
 * Helper methods for dealing with XML entities.
 * 
 * @author Keith Suderman
 * 
 */
public class Utils
{
   /**
    * Replaces ampersands, less-than, greater-than, and double quotes in the
    * string with the corresponding XML entity.
    * 
    */
   public static final String encode(String s)
   {
      String result = s.replaceAll("&", "&amp;");
      result = result.replaceAll("<", "&lt;");
      result = result.replaceAll(">", "&gt;");
      result = result.replaceAll("\"", "&quot;");
      return result;
   }

   public static final String decode(String s)
   {
      String result = s.replaceAll("&lt;", "<");
      result = result.replaceAll("&gt;", ">");
      result = result.replaceAll("&quot;", "\"");
      result = result.replaceAll("&amp;", "&");
      return result;
   }
}
