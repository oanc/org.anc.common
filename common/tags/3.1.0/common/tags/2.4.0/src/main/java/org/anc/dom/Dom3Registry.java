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

import org.apache.xerces.parsers.XMLGrammarPreparser;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XSGrammar;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xs.XSImplementation;
import org.apache.xerces.xs.XSLoader;
import org.apache.xerces.xs.XSModel;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

/**
 * Provides access to the Factory classes/methods used in the DOM Level 3
 * Load/Save API and the XML Schema API. In particular, there are methods to
 * obtain instances of the following objects:
 * <ul>
 * <li>XSLoader</li>
 * <li>XSModel</li>
 * <li>LSSerializer</li>
 * <li>LSOutput</li>
 * </ul>
 * 
 * @todo Many of the factory methods should throw excpetions rather than
 *       returning null.
 * @todo This class should be moved to a more appropriately named package.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class Dom3Registry
{
   /** Shortcut for the URI used to identify XML Schema. */
   public static final String SCHEMA = XMLGrammarDescription.XML_SCHEMA;

   /** The single instance of this class. */
   private static Dom3Registry fInstance = null;

   protected DOMImplementationRegistry fRegistry = null;
   protected XSImplementation fXSImpl = null;
   protected DOMImplementationLS fLSImpl = null;

   private Dom3Registry() throws Exception
   {
      System.setProperty(DOMImplementationRegistry.PROPERTY,
            "org.apache.xerces.dom.DOMXSImplementationSourceImpl");
      fRegistry = DOMImplementationRegistry.newInstance();

      fXSImpl = (XSImplementation) fRegistry.getDOMImplementation("XS-Loader");
      fLSImpl = (DOMImplementationLS) fRegistry.getDOMImplementation("LS");

   }

   synchronized public static Dom3Registry getInstance()
   {
      if (fInstance == null)
      {
         try
         {
            fInstance = new Dom3Registry();
         }
         catch (Exception ex)
         {
            org.anc.Aspect.Catch(ex);
            fInstance = null;
         }
      }
      return fInstance;
   }

   public XSLoader getXSLoader()
   {
      return fXSImpl.createXSLoader(null);
   }

   public LSSerializer getLSSerializer()
   {
      return fLSImpl.createLSSerializer();
   }

   public LSOutput getLSOutput()
   {
      return fLSImpl.createLSOutput();
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

   public XSModel getXSModel(String uri)
   {
      XMLGrammarPreparser preparser = new XMLGrammarPreparser();
      preparser.registerPreparser(SCHEMA, null);
      XMLInputSource source = new XMLInputSource(null, uri, null);
      XSGrammar grammar = null;
      try
      {
         grammar = (XSGrammar) preparser.preparseGrammar(SCHEMA, source);
      }
      catch (Exception ex)
      {
         org.anc.Aspect.Catch(ex);
         return null;
      }
      if (grammar == null)
      {
         System.out.println("Unable to parse the grammar");
         return null;
      }

      return grammar.toXSModel();
   }
}
