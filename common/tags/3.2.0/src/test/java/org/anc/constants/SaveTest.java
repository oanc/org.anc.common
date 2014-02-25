package org.anc.constants;

import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Keith Suderman
 */
@Ignore
public class SaveTest
{
   private TestClass testClass;

   public SaveTest()
   {

   }

   @Before
   public void setup()
   {
      testClass = new TestClass();
   }

   @After
   public void teardown()
   {
      testClass = null;
   }

   @Test
   public void testSetup()
   {
      assertTrue(testClass.ONE != null);
      assertTrue(testClass.ONE == 1);
      assertTrue(testClass.PI != null);
      assertTrue(testClass.PI == 3.14);
      assertTrue(testClass.T != null);
      assertTrue (testClass.T);
      assertTrue(testClass.F != null);
      assertFalse(testClass.F);
   }

   @Test
   public void testSave() throws IOException
   {
      testClass.save();
   }
}

class TestClass extends Constants
{
   @Default("1")
   public final Integer ONE = null;

   @Default("3.14")
   public final Double PI = null;

   @Default("true")
   public final Boolean T = null;

   @Default("false")
   public final Boolean F = null;

   public TestClass()
   {
      init();
   }
}