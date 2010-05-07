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
         qosLogger.addAppender(appender);
      }
      return logger;
   }
}
