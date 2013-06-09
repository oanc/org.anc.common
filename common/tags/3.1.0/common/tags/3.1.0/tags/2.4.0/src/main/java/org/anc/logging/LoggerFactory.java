/*-
 * Copyright 2010 The American National Corpus
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
package org.anc.logging;

import org.slf4j.*;
import org.slf4j.Logger;

import ch.qos.logback.classic.*;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.*;

public class LoggerFactory
{
   public static final String PATTERN = "%-5level [%class{0}.%method:%line] %msg%n";
   public static final String[] LEVELS = {
      "trace", "debug", "info", "warn", "error"
   };
   
   protected Level level = null;
//   protected FileAppender<ILoggingEvent> appender = null;
   protected String filename = null;
   protected boolean append = false;
   protected LoggerContext context = null;
   
   public LoggerFactory()
   {
      context = (LoggerContext) 
         org.slf4j.LoggerFactory.getILoggerFactory(); 
   }

   public LoggerFactory(Level level)
   {
      context = (LoggerContext) 
         org.slf4j.LoggerFactory.getILoggerFactory(); 
      this.level = level;
   }
   
   public LoggerFactory(Level level, String filename)
   {
      this(level, filename, false);
   }
   
   public LoggerFactory(Level level, String filename, boolean append)
   {
      context = (LoggerContext) 
         org.slf4j.LoggerFactory.getILoggerFactory(); 
      this.level = level;
      this.filename = filename;
      this.append = append;
   }
   
   public void setLevel(String level)
   {
      this.level = Level.toLevel(level);
   }
   
   public void setLevel(Level level)
   {
      this.level = level;
   }
   
   public void setFilename(String path)
   {
      setFilename(path, true);
   }
   
   public void setFilename(String path, boolean append)
   {
      this.filename = path;
      this.append = append;
   }
   
   public Logger getLogger(Class<?> theClass)
   {
      return getLogger(theClass.getName());
   }
   
   public Logger getLogger(String name)
   {
      Logger logger = context.getLogger(name);
      if (! (logger instanceof ch.qos.logback.classic.Logger))
      {
         return logger;
      }
      
      ch.qos.logback.classic.Logger qosLogger = (ch.qos.logback.classic.Logger) logger;
      if (level != null)
      {
         qosLogger.setLevel(level);
      }
      if (filename != null)
      {
         PatternLayoutEncoder encoder = new PatternLayoutEncoder();
         encoder.setPattern(PATTERN);
         encoder.setContext(context);
         encoder.start();

         FileAppender<ILoggingEvent> appender = new FileAppender<ILoggingEvent>();
         appender.setAppend(append);
         appender.setContext(context);
         appender.setEncoder(encoder);
         appender.setFile(filename);
         appender.setName("FILE");
         appender.start();
         qosLogger.addAppender(appender);
      }
      return logger;
   }
}
