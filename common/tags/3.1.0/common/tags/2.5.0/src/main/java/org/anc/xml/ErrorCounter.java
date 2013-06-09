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

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A Sax ErrorHandler that keeps a count of the errors encountered and stores
 * the error messages in a linked list.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class ErrorCounter implements ErrorHandler
{
   /** The error messages that have been collected during a parse. */
   List<String> fErrorMessages = new LinkedList<String>();

   public ErrorCounter()
   {
   }

   /** Clears the error messages so the object can be re-used. */
   public void reset()
   {
      fErrorMessages.clear();
   }

   /**
    * The number of errors collected since the ErrorCounter was created or
    * <code>reset()</code> was last called.
    */
   public int numErrors()
   {
      return fErrorMessages.size();
   }

   /**
    * Returns a list of error messages collected since the ErrorCounter was
    * created or <code>reset()</code> was last called.
    */
   public List<String> getErrorMessages()
   {
      return fErrorMessages;
   }

   /**
    * Receive notification of a recoverable error.
    * 
    * @param ex
    *           The error information encapsulated in a SAX parse exception.
    * @throws SAXException
    *            Any SAX exception, possibly wrapping another exception.
    */
   public void error(SAXParseException ex) throws SAXException
   {
      fErrorMessages.add(ex.getMessage());
   }

   /**
    * Receive notification of a non-recoverable error.
    * 
    * @param ex
    *           The error information encapsulated in a SAX parse exception.
    * @throws SAXException
    *            Any SAX exception, possibly wrapping another exception.
    */
   public void fatalError(SAXParseException ex) throws SAXException
   {
      fErrorMessages.add(ex.getMessage());
   }

   /**
    * Receive notification of a warning.
    * 
    * @param ex
    *           The warning information encapsulated in a SAX parse exception.
    * @throws SAXException
    *            Any SAX exception, possibly wrapping another exception.
    */
   public void warning(SAXParseException ex) throws SAXException
   {
      fErrorMessages.add(ex.getMessage());
   }
}
