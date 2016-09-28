package org.anc.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class StopWatchTest
{
   @Test
   public void testElapsedNotRunning()
   {
      StopWatch timer = new StopWatch();
      assertTrue("Invalid elapsed time for timer that hasn't been started.", timer.elapsed() == 0L);
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
         long min = expected - 100;
         long max = expected + 100;
         System.out.println("Elapsed interval time is " + elapsed);
         assertTrue("Timed interval is outside expected range", elapsed >= min && elapsed <= max);
      }
   }

   @Test
   public void testToString()
   {
      StopWatch timer = new StopWatch();
      System.out.println(timer.toString());
      assertTrue("00:00:000".equals(timer.toString()));
      assertTrue(timer.elapsed() == 0);
      assertTrue("00:00:00:00 UTC (+0000)".equals(timer.toString("HH:mm:ss:SS z (Z)")));
   }
}
