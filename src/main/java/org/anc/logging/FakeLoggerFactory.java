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

/** The FakeLoggerFactory class creates FakeLogger objects to be used for
 *  debugging purposes.  FakeLogger objects are primarily used during 
 *  initial development when the logging framework is not known.
 *  Using FakeLoggers allows users to add logging statements, run, and test
 *  their code while also making it easy to transition to a real logging
 *  framework later.
 *  <p>
 *  FakeLogger objects do not support <i>logging levels</i> directly, rather
 *  different types of FakeLogger objects are instantiated. The 
 *  {@link DebugLogger} provides methods that print a logged messages, the 
 *  {@link InfoLogger} replaces the <code>debug</code> methods with empty 
 *  method bodies. The  {@link WarnLogger} replaces the <code>info</code>
 *  methods with empty method bodies, etc. 
 * @author Keith Suderman
 *
 */
public class FakeLoggerFactory
{
   public static FakeLogger getLogger(FakeLogger.Level level)
   {
      FakeLogger logger = null;
      switch(level)
      {
      case DEBUG:
         logger = new DebugLogger();
         break;
      case INFO:
         logger = new InfoLogger();
         break;
      case WARN:
         logger = new WarnLogger();
         break;
      case ERROR:
         logger = new ErrorLogger();
         break;
      case NONE: // Fall through, NONE is also the default case.
      default:
         logger = new NullLogger();
      }
      return logger;
   }
   
   public static FakeLogger getDebugLogger()
   {
      return new DebugLogger();
   }
   
   public static FakeLogger getInfoLogger()
   {
      return new InfoLogger();
   }
   
   public static final FakeLogger getWarnLogger()
   {
      return new WarnLogger();
   }
   
   public static final FakeLogger getErrorLogger()
   {
      return new ErrorLogger();
   }
   
   public static final FakeLogger getNullLogger()
   {
      return new NullLogger();
   }
}

/** A DebugLogger prints all logging messages. */
class DebugLogger implements FakeLogger
{
   public void error(String message)
   {
      System.out.println(message);
   }
   public void error(String template, Object pattern)
   {
      System.out.println(template.replace("{}", pattern.toString()));
   }
   
   public void error(String template, Object arg1, Object arg2)
   {
      template = template.replaceFirst(template, arg1.toString());
      System.out.println(template.replace("{}", arg2.toString()));
   }
   
   public void warn(String message)
   {
      System.out.println(message);
   }
   public void warn(String template, Object pattern)
   {
      System.out.println(template.replace("{}", pattern.toString()));
   }
   
   public void warn(String template, Object arg1, Object arg2)
   {
      template = template.replaceFirst(template, arg1.toString());
      System.out.println(template.replace("{}", arg2.toString()));
   }
   
   public void info(String message)
   {
      System.out.println(message);
   }
   
   public void info(String template, Object pattern)
   {
      System.out.println(template.replace("{}", pattern.toString()));
   }

   public void info(String template, Object arg1, Object arg2)
   {
      template = template.replaceFirst(template, arg1.toString());
      System.out.println(template.replace("{}", arg2.toString()));
   }
   
   public void debug(String message)
   {
      System.out.println(message);
   }
   public void debug(String template, Object pattern)
   {
      System.out.println(template.replace("{}", pattern.toString()));
   }

   public void debug(String template, Object arg1, Object arg2)
   {
      template = template.replaceFirst(template, arg1.toString());
      System.out.println(template.replace("{}", arg2.toString()));
   }
   
}

/** The InfoLogger suppresses debug messages. */
class InfoLogger extends DebugLogger
{
   @Override
   public void debug(String message) { }
   @Override
   public void debug(String template, Object pattern) { }
   @Override
   public void debug(String template, Object arg1, Object arg2) { }
}

/** The WarnLogger suppresses debug and info messages. */
class WarnLogger extends DebugLogger
{
   @Override
   public void info(String message) { }
   @Override
   public void info(String template, Object pattern) { }
   @Override
   public void info(String template, Object arg1, Object arg2) { }
}

/** The ErrorLogger suppresses debug, info, and warning messages. */
class ErrorLogger extends WarnLogger
{
   @Override
   public void warn(String message) { }
   @Override
   public void warn(String template, Object pattern) { }
   @Override
   public void warn(String template, Object arg1, Object arg2) { }
}

/** The NullLogger suppresses all logging messages. */
class NullLogger extends ErrorLogger
{
   @Override
   public void error(String message) { }
   @Override
   public void error(String template, Object pattern) { }
   @Override
   public void error(String template, Object arg1, Object arg2) { }
}
