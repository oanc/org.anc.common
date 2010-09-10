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

/**
 * Extends the Java Thread call and add a <code>halt()</code> method.
 * <p>
 * Classes that extend ANCThread <b>must</b>:
 * <ul>
 * <li>Terminate cleanly when the <code>running</code> flag is set to false, or</li>
 * <li>Override the <code>halt()</code> method and terminate cleanly when it is
 * called.</li>
 * </ul>
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public abstract class ANCThread extends Thread implements Haltable
{
   /** State values. */
   public enum State
   {
      STOPPED, RUNNING, PAUSED, HALTED, TERMINATED
   }

   /** Events sent to listeners. */
   public enum Event
   {
      STOPPED, STARTED, PAUSED, RESUMED, HALTED, TERMINATED
   }

   /** Current state of the thread. */
   private State state = State.STOPPED;

   /** Objects that want to be notified of state changes. */
   private LinkedList<IANCThreadListener> listeners = new LinkedList<IANCThreadListener>();

   public void addListener(IANCThreadListener listener)
   {
      synchronized (listeners)
      {
         listeners.add(listener);
      }
   }

   public void removeListener(IANCThreadListener listener)
   {
      synchronized (listeners)
      {
         listeners.remove(listener);
      }
   }

   public State state()
   {
      return state;
   }

   public synchronized boolean running()
   {
      while (state == State.PAUSED)
      {
         try
         {
            wait();
         }
         catch (InterruptedException ex)
         {
         }
      }
      return state == State.RUNNING; // || state == State.PAUSED;
   }

   /**
    * Halts the thread by setting running to <code>false</code>. Also calls
    * <code>notifyAll()</code> to wake anything that might be waiting on this
    * thread.
    */
   @Override
   public synchronized void halt()
   {
      state = State.HALTED;
      fire(Event.HALTED);
      // In case anything is waiting on us.
      notifyAll();
   }

   /**
    * Helper method that wraps a call to <code>sleep</code> in a
    * <code>try/catch</code> block.
    * 
    * @param millis
    *           long The number of milliseconds the thread should sleep.
    */
   public synchronized void pause(long millis)
   {
      state = State.PAUSED;
      fire(Event.PAUSED);
      try
      {
         sleep(millis);
      }
      catch (InterruptedException ex)
      {
      }
      restart();
   }

   public synchronized void pause()
   {
      state = State.PAUSED;
      fire(Event.PAUSED);
   }

   public synchronized void restart()
   {
      state = State.RUNNING;
      fire(Event.RESUMED);
      notifyAll();
   }

   @Override
   public synchronized void start()
   {
      state = State.RUNNING;
      fire(Event.STARTED);
      super.start();
   }

   public synchronized void terminate()
   {
      state = State.TERMINATED;
      fire(Event.TERMINATED);
      notifyAll();
   }

   protected void fire(Event event)
   {
      for (IANCThreadListener listener : listeners)
      {
         listener.threadStateChanged(this, event);
      }
   }
}
