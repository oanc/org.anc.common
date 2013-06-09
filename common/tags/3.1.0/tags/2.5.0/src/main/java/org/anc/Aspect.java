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
package org.anc;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

/**
 * Encapsulates frequently needed functionality that is common to many classes.
 * There are two basic types of methods in the Aspect class:
 * <ol>
 * <li>Exception handling methods. These simplify consistent exception handling
 * within and across applications.</li>
 * <li>GUI-centric methods. These are common functions used my most GUI
 * components.</li>
 * 
 * @author Keith Suderman
 * @version 1.0
 */

public abstract class Aspect
{
   /** Sets the look and feel to the system look and feel. */
   public static void setLookAndFeel()
   {
      setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
   }

   /**
    * Attempts to set the look and feel to the named look and feel.
    * 
    * @param lookAndFeel
    *           String The name of the look and feel to set.
    * 
    * @return boolean <b>true</b> if the look and feel was set, <b>false</b>
    *         otherwise.
    */
   public static boolean setLookAndFeel(String lookAndFeel)
   {
      boolean result = true;
      try
      {
         UIManager.setLookAndFeel(lookAndFeel);
      }
      catch (Exception ex)
      {
         result = false;
      }
      return result;
   }

   /** Centers the component on the screen. */
   public static void center(java.awt.Component component)
   {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension compSize = component.getSize();
      int x = (int) ((screenSize.getWidth() - compSize.getWidth()) / 2);
      int y = (int) ((screenSize.getHeight() - compSize.getHeight()) / 2);
      component.setLocation(x, y);
   }

   /**
    * Centers the window on the parent.
    * 
    * @param parent
    *           Component The background window.
    * @param window
    *           Component The window to center.
    */
   public static void center(Component parent, Component window)
   {
//    Toolkit kit = Toolkit.getDefaultToolkit();
      if (parent == null)
      {
         center(window);
         return;
      }

      Dimension size = parent.getSize();
      int x = parent.getX() + ((int) size.getWidth() - window.getWidth()) / 2;
      int y = parent.getY() + ((int) size.getHeight() - window.getHeight()) / 2;
      window.setLocation(x, y);
   }

   public static String fixSlashes(String s)
   {
      return s.replaceAll("\\\\", "/");
   }

/** Replace the four characters that are entities in XML, that is, '&', '<',
	*  '>', and '"'.
	*/
   public static String encodeEntities(String s)
   {
      return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">",
            "&gt;").replaceAll("\"", "&quot;");
   }

   // Exception handling methods.

   public static void Catch(Exception e)
   {
      System.out.println(e);
      e.printStackTrace();
   }

   public static void Catch(Exception e, String message)
   {
      System.out.println(message);
      Catch(e);
   }

   public static void CatchQuit(Exception e)
   {
      CatchQuit(e, 1);
   }

   public static void CatchQuit(Exception e, String message)
   {
      System.out.println(message);
      CatchQuit(e, 1);
   }

   public static void CatchQuit(Exception e, int code)
   {
      Catch(e);
      System.exit(code);
   }
}
