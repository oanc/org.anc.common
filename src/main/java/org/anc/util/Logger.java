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
package org.anc.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.anc.Sys;

/**
 * A proxy for the java.util.logging.Logger class that provides several helper
 * methods.
 * 
 * @author Keith Suderman
 * @deprecated Use SLF4J and Logback for logging.
 */
@Deprecated
public class Logger
{
   public static final Level TRACE = Level.FINER;
   public static final Level DEBUG = Level.FINE;
   public static final Level LOG = Level.INFO;
   public static final Level INFO = Level.INFO;
   public static final Level WARN = Level.WARNING;
   public static final Level ERROR = Level.SEVERE;
   
   private static final long serialVersionUID = 1L;

   /** Default size to use for and buffers created. */
//   public static final int BUFFER_SIZE = 4096;

   /** Default encoding used when reading/writing files. */
//   public static final String DEFAULT_ENCODING = "UTF-8";

   /** Java logger object to use when logging is enabled. */
   protected java.util.logging.Logger logger = null; //java.util.logging.Logger
//         .getLogger("org.anc.logger");

   /** Custom format for log messages. */
   protected Formatter formatter = new LogFormatter();

   /** True if logging is enabled false otherwise. */
//   protected boolean enabled;

   public Logger()
   {
      this("anc.util.logging");
   }

   public Logger(Class theClass) //throws IOException
   {
      this(theClass.getName());
   }

   public Logger(String name) 
   {
      logger = java.util.logging.Logger.getLogger(name);
      initLogger();
   }

   public Logger(Class theClass, String path) throws IOException
   {
      this(theClass);
      this.addLogFileHandler(path);
   }
   
   public Logger(Class theClass, File file) throws IOException
   {
      this(theClass);
      this.addLogHandler(file);
   }
   
   public Logger(String name, String path) throws IOException
   {
      this(name);
      this.addLogFileHandler(path);
   }

   public Logger(String name, File file) throws IOException
   {
      this(name);
      this.addLogHandler(file);
   }

   public void addLogHandler(File file) throws IOException
   {
      addLogFileHandler(file.getPath());
   }

   public void addLogHandler(Handler handler)
   {
      logger.addHandler(handler);
   }

   public Handler addLogFileHandler(String path) throws IOException
   {
      FileHandler handler = new FileHandler(path);
      handler.setFormatter(formatter);
      logger.addHandler(handler);
      return handler;
   }

   public void removeLogHandler(Handler handler)
   {
      logger.removeHandler(handler);
   }

   public void debug(String message)
   {
      publish(DEBUG, message);
   }
   
   public void trace(String message)   
   {
      publish(TRACE, message);
   }
   
   public void log(String message)
   {
      publish(INFO, message);
   }

   public void log(Exception e)
   {
      publish(INFO, e.getMessage());
   }
   
   public void warn(String message)
   {
      publish(WARN, message);
   }

   public void warn(Exception e)
   {
      publish(WARN, e.getMessage());
   }
   
   public void error(String message)
   {
      publish(ERROR, message);
   }

   public void error(Exception e)
   {
      publish(ERROR, e.getMessage());
   }
   
   public void start(String method)
   {
      publish(INFO, "Testing " + method);
   }

   public void passed()
   {
      publish(INFO, "Passed");
   }

   protected void publish(Level level, String message)
   {
      if (level.intValue() < logger.getLevel().intValue())
      {
         return;
      }
      
      StackTraceElement[] trace = Thread.currentThread().getStackTrace();      

      // trace[0] is the call the getStackTrace
      // trace[1] is this function.
      // trace[2] is the Logger method that called publish
      // trace[3] is the function that called a Logger method.
      StackTraceElement e = trace[3];
      LogRecord record = new LogRecord(level, message);
      String name = e.getClassName();
      int dot = name.lastIndexOf('.');
      if (dot > 0)
      {
         name = name.substring(dot + 1);
      }
      record.setSourceClassName(name);
      record.setSourceMethodName(e.getMethodName() + ":" + e.getLineNumber());
      System.err.println("Publishing " + level.getName() + " " + message);
      logger.log(record);
   }

//   public void disableLogging()
//   {
//      enabled = false;
//   }
//
//   public void enableLogging()
//   {
//      enabled = true;
//   }

   public void setLogLevel(Level level)
   {
      logger.setLevel(level);
      for (Handler h : logger.getHandlers())
      {
         h.setLevel(level);
      }
   }

   public Level getLogLevel()
   {
      return logger.getLevel();
   }
   
   public Formatter getLogFormatter()
   {
      return formatter;
   }

   /**
    * Initializes the logging system for first use and configures the root
    * logger to use our log formatter.
    */
   protected void initLogger()
   {
      java.util.logging.Logger rootLogger = java.util.logging.Logger
            .getLogger("");
      Handler[] handlers = rootLogger.getHandlers();
      handlers[0].setFormatter(formatter);
   }

   // Used in testing to generate a simple default properties file.
   public static void main(String[] args)
   {
      Logger logger = null;
      try
      {
         logger = new Logger(Logger.class);
         test(logger, Logger.TRACE);
         test(logger, Logger.DEBUG);
         test(logger, Logger.INFO);
         test(logger, Logger.WARN);
         test(logger, Logger.ERROR);
      }
      catch (RuntimeException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return;
      }
//         logger.enableLogging();
   }
   
   private static void test(Logger logger, Level level)
   {
      logger.setLogLevel(level);
      System.err.println("Log level: " + logger.getLogLevel().getName());
      logger.trace("This is a trace message.");
      logger.debug("This is a debug message.");
      logger.log("This is a standard message.");
      logger.warn("This is a warning.");
      logger.error("This is an error.");
   }
}

class LogFormatter extends Formatter
{
   /** Format used for the timestamp. */
   protected DateFormat dateFormat = DateFormat.getTimeInstance(
         DateFormat.LONG, Locale.US);

   @Override
   public String format(LogRecord record)
   {
      String message = record.getMessage();
      String theClass = record.getSourceClassName();
      String method = record.getSourceMethodName();
      String time = dateFormat.format(new Date(record.getMillis()));
      return (time + " [" + theClass + "." + method + "] " + record.getLevel().getName() + " : " + message + Sys.EOL);
   }
}
