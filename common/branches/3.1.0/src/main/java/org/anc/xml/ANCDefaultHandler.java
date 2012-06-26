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
package org.anc.xml;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Extends the org.xml.sax.helpers.DefaultHandler class with a few convenience
 * methods used by most handlers.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public abstract class ANCDefaultHandler extends DefaultHandler
{
   public ANCDefaultHandler()
   {
   }

   public String getName(String localName, String qName)
   {
      if (localName != null)
      {
         return localName;
      }
      return qName;
   }
}
