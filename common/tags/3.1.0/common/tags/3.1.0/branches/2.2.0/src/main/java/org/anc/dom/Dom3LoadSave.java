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
package org.anc.dom;

import java.io.OutputStream;
import java.io.Writer;

import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * Provides access to the Factory classes/methods used in the DOM Level 3
 * Load/Save API.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class Dom3LoadSave
{

   /** Shortcut for the URI used to identify XML Schema. */
   public static final String SCHEMA = XMLGrammarDescription.XML_SCHEMA;

   /** The single instance of this class. */
   private static Dom3LoadSave instance = null;

   protected DOMImplementationLS impl = null;

   private Dom3LoadSave() throws Exception
   {
      System.setProperty(DOMImplementationRegistry.PROPERTY,
            "org.apache.xerces.dom.DOMXSImplementationSourceImpl");
      DOMImplementationRegistry registry = DOMImplementationRegistry
            .newInstance();

      impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
   }

   synchronized public static Dom3LoadSave getInstance()
   {
      if (instance == null)
      {
         try
         {
            instance = new Dom3LoadSave();
         }
         catch (Exception ex)
         {
            System.out.println("");
            instance = null;
         }
      }
      return instance;
   }

   public LSSerializer getLSSerializer()
   {
      return impl.createLSSerializer();
   }

   public LSSerializer getPrettyPrinter()
   {
      LSSerializer serializer = this.getLSSerializer();
      serializer.getDomConfig().setParameter("format-pretty-print",
            Boolean.TRUE);
      return serializer;
   }

   public LSOutput getLSOutput()
   {
      return impl.createLSOutput();
   }

   public LSOutput getLSOutput(Writer writer)
   {
      LSOutput lso = this.getLSOutput();
      lso.setCharacterStream(writer);
      return lso;
   }

   public LSOutput getLSOutput(OutputStream stream)
   {
      LSOutput lso = this.getLSOutput();
      lso.setByteStream(stream);
      return lso;
   }
}
