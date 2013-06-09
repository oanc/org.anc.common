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

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class LinePrinter
{
   private ILinePrinter printer;

   public LinePrinter(PrintWriter writer)
   {
      super();
      printer = new WriterLinePrinter(writer);
   }

   public LinePrinter(PrintStream stream)
   {
      printer = new StreamLinePrinter(stream);
   }

   public void println()
   {
      printer.println();
   }

   public void println(Object object)
   {
      printer.println(object);
   }

   public void println(String s)
   {
      printer.println(s);
   }

   public void println(char[] ch)
   {
      printer.println(ch);
   }

   public void print(Object object)
   {
      printer.print(object);
   }

   public void print(String s)
   {
      printer.print(s);
   }

   public void print(char[] ch)
   {
      printer.print(ch);
   }

   public void print(char c)
   {
      printer.print(c);
   }

   public void close()
   {
      printer.close();
   }
}

interface ILinePrinter
{
   void println();

   void println(Object object);

   void println(String s);

   void println(char[] ch);

   void println(char c);

   void print(Object object);

   void print(String s);

   void print(char[] ch);

   void print(char c);

   void close();
}

class StreamLinePrinter implements ILinePrinter
{
   private PrintStream out;

   public StreamLinePrinter(PrintStream stream)
   {
      out = stream;
   }

   public void println()
   {
      out.println();
   }

   public void println(Object object)
   {
      out.println(object);
   }

   public void println(String s)
   {
      out.println(s);
   }

   public void println(char[] ch)
   {
      out.println(ch);
   }

   public void println(char c)
   {
      out.println(c);
   }

   public void print(Object object)
   {
      out.print(object);
   }

   public void print(String s)
   {
      out.print(s);
   }

   public void print(char[] ch)
   {
      out.print(ch);
   }

   public void print(char c)
   {
      out.print(c);
   }

   public void close()
   {
      out.close();
   }
}

class WriterLinePrinter implements ILinePrinter
{
   private PrintWriter out;

   public WriterLinePrinter(PrintWriter writer)
   {
      out = writer;
   }

   public void println()
   {
      out.println();
   }

   public void println(Object object)
   {
      out.println(object);
   }

   public void println(String s)
   {
      out.println(s);
   }

   public void println(char[] ch)
   {
      out.println(ch);
   }

   public void println(char c)
   {
      out.println(c);
   }

   public void print(Object object)
   {
      out.print(object);
   }

   public void print(String s)
   {
      out.print(s);
   }

   public void print(char[] ch)
   {
      out.print(ch);
   }

   public void print(char c)
   {
      out.print(c);
   }

   public void close()
   {
      out.close();
   }
}
