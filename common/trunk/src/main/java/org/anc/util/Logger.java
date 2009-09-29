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
 */
public class Logger
{
   private static final long serialVersionUID = 1L;

   /** Default size to use for and buffers created. */
//   public static final int BUFFER_SIZE = 4096;

   /** Default encoding used when reading/writing files. */
//   public static final String DEFAULT_ENCODING = "UTF-8";

   /** Java logger object to use when logging is enabled. */
   protected java.util.logging.Logger logger = java.util.logging.Logger
         .getLogger("org.anc.logger");

   /** Custom format for log messages. */
   protected Formatter formatter = new LogFormatter();

   /** True if logging is enabled false otherwise. */
   protected boolean enabled;

   public Logger()
   {
      this(false);
   }

   public Logger(boolean enabled) //throws IOException
   {
      this.enabled = enabled;
      initLogger();
   }

   public Logger(String path) throws IOException
   {
      this(new File(path), false);
   }

   public Logger(String path, boolean enabled) throws IOException
   {
      this(new File(path), enabled);
   }

   public Logger(File file) throws IOException
   {
      this(file, false);
   }

   public Logger(File file, boolean enabled) throws IOException
   {
      super();
      this.enabled = enabled;
      initLogger();
      addLogHandler(file);
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

   public void log(String message)
   {
      publish(Level.INFO, message);
   }

   public void log(Exception e)
   {
      publish(Level.INFO, e.getMessage());
   }
   
   public void warn(String message)
   {
      publish(Level.WARNING, message);
   }

   public void warn(Exception e)
   {
      publish(Level.WARNING, e.getMessage());
   }
   
   public void error(String message)
   {
      publish(Level.SEVERE, message);
   }

   public void error(Exception e)
   {
      publish(Level.SEVERE, e.getMessage());
   }
   
   public void start(String method)
   {
      publish(Level.INFO, "Testing " + method);
   }

   public void passed()
   {
      publish(Level.INFO, "Passed");
   }

   protected void publish(Level level, String message)
   {
      if (enabled)
      {
         logger.log(level, message);
      }
   }

   public void disableLogging()
   {
      enabled = false;
   }

   public void enableLogging()
   {
      enabled = true;
   }

   public void setLogLevel(Level level)
   {
      logger.setLevel(level);
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
      Logger logger;
      try
      {
         logger = new Logger("c:/temp/test.log");
         logger.enableLogging();
         logger.log("This is a message.");
         logger.warn("This is a warning.");
         logger.error("This is an error.");
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
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
      String time = dateFormat.format(new Date(record.getMillis()));
      return (time + " [" + record.getLevel().getName() + "] : " + message + Sys.EOL);
   }
}
