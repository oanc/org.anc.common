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

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ThreadManagerTest
{

   @Test
   public void testThreadManager()
   {
      ThreadManager manager = new ThreadManager();
      for (int i = 0; i < 10; ++i)
      {
         Task task = new Task();
         manager.run(task);
      }
      pause(2000);
      manager.stopAll();
      while (manager.numberOfThreads() > 0)
      {
         System.out.println("Thread manager is waiting for "
               + manager.numberOfThreads() + " tasks to complete.");
         pause(500);
      }
      System.out.println("ThreadManager test complete.");
   }

   private void pause(long millis)
   {
      try
      {
         Thread.sleep(millis);
      }
      catch (InterruptedException e)
      {
      }
   }
}

class Task extends ANCThread
{
   private static int nextId = 0;
   private int count = 0;

//   private final int id;

   public Task()
   {
//      id = nextId;
      ++nextId;
   }

   @Override
   public void run()
   {
      System.out.println("Thread " + this.getId() + " is starting.");
      while (running())
      {
         System.out.println("Thread " + this.getId() + ": count = " + count);
         ++count;
      }
      System.out.println("Thread " + this.getId() + " has halted.");
   }

}
