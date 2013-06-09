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
package org.anc.threads;

import java.util.LinkedList;
import java.util.List;

import org.anc.threads.ANCThread.Event;

/**
 * A class that can be used to manage several thread objects.
 * <p>
 * The class is still experimental and untested in highly concurrent situations.
 */
public class ThreadManager implements IANCThreadListener
{
   protected List<ANCThread> threads = new LinkedList<ANCThread>();

//   protected int runningThreads = 0;

   public void threadStateChanged(ANCThread thread, Event event)
   {
      if (event == Event.HALTED)
      {
         System.out.println("Receive a halted event from thread "
               + thread.getId());
         synchronized (threads)
         {
            threads.remove(thread);
         }
      }
   }

   public void run(ANCThread thread)
   {
      synchronized (threads)
      {
         threads.add(thread);
         thread.addListener(this);
         thread.start();
      }
   }

   public void stop(ANCThread thread)
   {
      thread.halt();
   }

   public void stopAll()
   {
      synchronized (threads)
      {
         List<ANCThread> threadsToHalt = new LinkedList<ANCThread>(threads);
         for (ANCThread thread : threadsToHalt)
         {
            thread.halt();
         }
      }
   }

   public int numberOfThreads()
   {
      return threads.size();
   }
}
