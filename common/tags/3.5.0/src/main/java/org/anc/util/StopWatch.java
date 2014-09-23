/*-
 * Copyright 2011 The American National Corpus
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A very simple class for collecting rudimentary timing information.
 * 
 * @author Keith Suderman
 *
 */
public class StopWatch
{
   /** When the timer was started. */
   private long started;
   /** When the timer was stopped. If the timer has not been stopped, that
    * is the timer is still running or hasn't been started yet, then stopped
    * will be set to -1;
    */
   private long stopped;

   private static DateFormat format;

   static {
      format = new SimpleDateFormat("mm:ss:SSS");
      format.setTimeZone(TimeZone.getTimeZone("UTC"));
   }

   public StopWatch()
   {
      started = -1;
      stopped = -1;
   }
   
   public void start()
   {
      started = now();
      stopped = -1;
   }
   
   public void stop()
   {
      if (started > 0)
      {
         stopped = now();
      }
   }
   
   public long elapsed()
   {
      if (stopped < started)
      {
         // The timer is still running.
         return now() - started;
      }
      return stopped - started;
   }

   public String toString()
   {
      return format.format(elapsed());
   }

   public String toString(String format)
   {
      DateFormat df = new SimpleDateFormat(format);
      df.setTimeZone(TimeZone.getTimeZone("UTC"));
      return df.format(elapsed());
   }

   protected long now()
   {
      return System.currentTimeMillis();
   }
}
