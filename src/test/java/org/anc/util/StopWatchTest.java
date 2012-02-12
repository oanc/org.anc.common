package org.anc.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class StopWatchTest
{
   @Test
   public void testElapsedNotRunning()
   {
      StopWatch timer = new StopWatch();
      assertTrue("Invalid elapsed time for timer that hasn't been started.", timer.elapsed() < 0.0001);
   }
   
   @Test
   public void testOneSecond()
   {
      StopWatch timer = new StopWatch();
      timer.start();
      try
      {
         Thread.sleep(1000);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }
      timer.stop();
      System.out.println("Elapsed time is " + timer.elapsed());
      
      assertTrue("Reported time not close to expected time.", timer.elapsed() > 990 && timer.elapsed() < 1010);
   }
   
   @Test
   public void testIntervals()
   {
      StopWatch timer = new StopWatch();
      timer.start();
      for (long expected = 1000; expected <= 5000; expected += 1000)
      {
         try
         {
            Thread.sleep(1000);
         }
         catch (InterruptedException e)
         {
            e.printStackTrace();
         }
         long elapsed = timer.elapsed();
         long min = expected - 10;
         long max = expected + 10;
         System.out.println("Elapsed interval time is " + elapsed);
         assertTrue("Timed interval is outside expected range", elapsed >= min && elapsed <= max);
      }
   }
}
