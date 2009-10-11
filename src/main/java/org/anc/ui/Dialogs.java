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
package org.anc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

/**
 * The Dialogs class provides several shortcuts to the JOptionPane dialogs.
 * <p>
 * All of the methods in the Dialogs class are declared as static so the class
 * never needs to be instantiated. However, the class also provides a
 * {@link Dialogs#getInstance getInstance} method should an instance of the 
 * class ever be required.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class Dialogs
{
   /** A singleton instance of the Dialogs class. */
   private static Dialogs instance = null;

   /** The same value as JOptionPane.OK_OPTION */
   public static int OK = JOptionPane.OK_OPTION;

   /** The same value as JOptionPane.CANCEL_OPTION */
   public static int CANCEL = JOptionPane.CANCEL_OPTION;

   /** The same value as JOptionPane.YES_OPTION */
   public static int YES = JOptionPane.YES_OPTION;

   /** The same value as JOptionPane.NO_OPTION */
   public static int NO = JOptionPane.NO_OPTION;

   private Dialogs()
   {
   }

   /**
    * Returns an instance of the Dialogs class.
    * <p>
    * Since the Dialogs class is implemented as a singleton the same instance
    * will always be returned.
    * 
    * @return an instance of the Dialogs class
    */
   public synchronized static Dialogs getInstance()
   {
      if (instance == null)
      {
         instance = new Dialogs();
      }

      return instance;
   }

   /**
    * Calls the three parameter version of the method with the title "Message"
    */
   public static void messageBox(Component parent, Object message)
   {
      messageBox(parent, message, "Message");
   }

   /**
    * Displays a JOptionPane dialog with the type
    * JOptionPane.INFORMATION_MESSAGE.
    * 
    * @param parent
    *           The parent component of the dialog.
    * @param message
    *           The message to display in the dialog.
    * @param title
    *           The title to display the dialog.
    */
   public static void messageBox(Component parent, Object message, String title)
   {
      JOptionPane.showMessageDialog(parent, message, title,
            JOptionPane.INFORMATION_MESSAGE);
   }

   /**
    * Calls the three parameter version of this method with the title "Error".
    */
   public static void errorBox(Component parent, Object message)
   {
      errorBox(parent, message, "Error");
   }

   /**
    * Displays a JOptionPane dialog of the type JOptionPane.ERROR_MESSAGE.
    * 
    * @param parent
    *           The parent component of the dialog.
    * @param message
    *           The message to display in the dialog.
    * @param title
    *           The title to display in the dialog.
    */
   public static void errorBox(Component parent, Object message, String title)
   {
      JOptionPane.showMessageDialog(parent, message, title,
            JOptionPane.ERROR_MESSAGE);
   }

   public static void errorBox(Component parent, Exception ex)
   {
      String title = ex.getClass().getName();
      StackTraceElement[] trace = ex.getStackTrace();
      JPanel panel = new JPanel();
      panel.setLayout(new GridBagLayout());
      GBC gbc = new GBC();
//      gbc.anchorLeft();
      gbc.anchorNorthwest();
      
      JTextPane pane = new JTextPane();
      pane.setLayout(new GridBagLayout());
      pane.setBackground(panel.getBackground());
      //setEditable used so users cannot delete the message accidentally
      pane.setEditable(false);

      //obtain the error string
      String s = new String();
      for (int i = 0; i < trace.length; ++i)
      {  
    	  s += ("     " + trace[i].toString()) + "\n";
      }
      
      pane.setText("CAUSE: " + ex.getMessage() + "\n\n" + s);
      panel.add(pane, gbc.down());
      
      JScrollPane scroll = new JScrollPane(panel);
      scroll.setPreferredSize(new Dimension(400, 250));
      errorBox(parent, scroll, title);
   }

   /**
    * Calls the three parameter version of the method with the title "Warning".
    */
   public static void warningBox(Component parent, Object message)
   {
      warningBox(parent, message, "Warning");
   }

   /**
    * Displays a JOptionPane dialog of the type JOptionPane.ERROR_MESSAGE.
    * 
    * @param parent
    *           The parent component of the dialog.
    * @param message
    *           The message to display in the dialog.
    * @param title
    *           The title to display in the dialog.
    */
   public static void warningBox(Component parent, Object message, String title)
   {
      JOptionPane.showMessageDialog(parent, message, title,
            JOptionPane.WARNING_MESSAGE);
   }

   /**
    * Calls the three parameter version of this method with the title "Confirm".
    */
   public static boolean confirmBox(Component parent, Object message)
   {
      return confirmBox(parent, message, "Confirm");
   }

   /**
    * Displays a JOptionPane confirm dialog with the OK and CANCEL options.
    * 
    * @param parent
    *           The parent component of the dialog.
    * @param message
    *           The message to display in the dialog.
    * @param title
    *           The title to display in the dialog.
    */
   public static boolean confirmBox(Component parent, Object message,
         String title)
   {
      return JOptionPane.showConfirmDialog(parent, message, title,
            JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
   }

   /**
    * Displays a JOptionPane confirm dialog with YES and NO buttons.
    * 
    */
   public static boolean yesNo(Component parent, Object message)
   {
      return JOptionPane.showConfirmDialog(parent, message, "Confirm",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
   }

   /**
    * Calls the three parameter version of this method with the title "Confirm".
    */
   public static int promptBox(Component parent, Object message)
   {
      return promptBox(parent, message, "Confirm");
   }

   /**
    * Displays a JOptionPane confirm dialog with the YES and NO options.
    * 
    * @param parent
    *           The parent component of the dialog.
    * @param message
    *           The message to display in the dialog.
    * @param title
    *           The title to display in the dialog.
    * @return {@link #YES} or {@link #NO} depending on what the user selected.
    */
   public static int promptBox(Component parent, Object message, String title)
   {
      return JOptionPane.showConfirmDialog(parent, message, title,
            JOptionPane.YES_NO_OPTION);
   }

   /**
    * Calls the three parameter version of this method with the title "Question"
    */
   public static String inputBox(Component parent, Object message)
   {
      return inputBox(parent, message, "Question");
   }

   /**
    * Displays a JOptionPane input dialog and returns the string entered.
    * 
    * @param parent
    *           The parent component of the dialog.
    * @param message
    *           The message to display in the dialog.
    * @param title
    *           The title to display in the dialog.
    * @return The string entered by the user.
    */
   public static String inputBox(Component parent, Object message, String title)
   {
      return JOptionPane.showInputDialog(parent, message, title);
   }

   /**
    * Displays a JOptionPane input dialog and returns the string entered.
    * 
    * @param parent
    *           The parent component of the dialog.
    * @param message
    *           The message to display in the dialog.
    * @param title
    *           The title to display in the dialog.
    * @param defaultValue
    *           The initial value displayed in the dialog.
    * @return The string entered by the user.
    */
   public static String inputBox(Component parent, Object message,
         String title, String defaultValue)
   {
//     JOptionPane.showInputDialog()
      return (String) JOptionPane.showInputDialog(parent, message, title,
            JOptionPane.QUESTION_MESSAGE, null, null, defaultValue);
   }

   /**
    * Creates a panel containing a label for each string in <code>lines</code>.
    * <p>
    * The labels are arranged in a single column and the text is centered in
    * each label.
    * 
    * @param lines
    *           The text to use for each label.
    * @return A JPanel object containing one JLabel object for each string in
    *         the array <code>lines</code>.
    */
   public static JPanel makePanel(String[] lines)
   {
      GridLayout layout = new GridLayout(0, 1);
      JPanel panel = new JPanel(layout);
      for (int i = 0; i < lines.length; ++i)
      {
         panel.add(new JLabel(lines[i], SwingConstants.CENTER));
      }
      return panel;
   }
}
