package org.anc.util;

import static org.junit.Assert.*;

import org.anc.args.IParameters;
import org.anc.args.Parameter;
import org.anc.args.Parameters;
import org.junit.Test;

public class ParametersTest
{
   public static class Params implements IParameters
   {
      public static final Parameter IN = new Parameter("-in", "input");
      public static final Parameter OUT = new Parameter("-out", "output");
      public static final Parameter HELP = new Parameter("-help", "help");
   }
   
   public static final String[] ALL_ARGS = {
     "-in=/path/to/source",
     "-out=/path/to/destination",
     "-help"
   };
   
   public static final String[] IN_OUT = {
      "-in=/path/to/source",
      "-out=/path/to/destination"
   };
   
   public static final String[] MISSING_ARGS = {
      "-in=/path/to/source",
      "-help"
    };
    
   public static final String[] BAD_ARGS = {
      "-in=/path/to/source",
      "-out=/path/to/destination",
      "-foo", "bar"
   };
   
   public static final Parameter[] REQUIRED = {
      Params.IN, Params.OUT
   };
   
   @Test
   public void testUsage() throws IllegalArgumentException, IllegalAccessException
   {
      Parameters.usage(Params.class);      
   }

   @Test
   public void testRequired()
   {
      Parameters args = new Parameters(IN_OUT, REQUIRED, Params.class);
      assertTrue(args.valid());
   }
   
   @Test
   public void testMissingArgs()
   {
      Parameters args = new Parameters(MISSING_ARGS, REQUIRED, Params.class);
      assertFalse(args.valid());
      args.printErrors();
   }
   
   @Test
   public void testBadArgs()
   {
      Parameters args = new Parameters(BAD_ARGS, REQUIRED, Params.class);
      assertFalse(args.valid());
      args.printErrors();
   }
}
