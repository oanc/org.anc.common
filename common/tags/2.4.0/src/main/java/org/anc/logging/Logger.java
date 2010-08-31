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

/**
 * This is a simple, lightweight wrapper for the java.util.logging.Logger API.
 * Logger instances are created by the LoggerFactory class (hence the private
 * constructor).
 * 
 * @author Keith Suderman
 *
 */
public class Logger
{
   private java.util.logging.Logger logger = null;
   
   private Logger(java.util.logging.Logger logger)
   {
      this.logger = logger;
   }
   
   public static Logger getLogger(Class<?> aClass)
   {
      return getLogger(aClass.getName());
   }
   
   public static Logger getLogger(String name)
   {
      java.util.logging.Logger jLogger = java.util.logging.Logger.getLogger(name);
      return new Logger(jLogger);
      
   }
   
   public void trace(String message)
   {
      logger.finer(message);
   }
   
   public void debug(String message)
   {
      logger.fine(message);
   }
   
   public void log(String message)
   {
      logger.info(message);
   }
   
   public void info(String message)
   {
      logger.info(message);
   }
   
   public void warn(String message)
   {
      logger.warning(message);
   }
   
   public void error(String message)
   {
      logger.severe(message);
   }
}
