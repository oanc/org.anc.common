package org.anc.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.anc.Aspect;

public class InputOutputDriver implements Runnable
{
   public void run()
   {
      Window window = new Window();
      window.setVisible(true);
   }

   /**
    * @param args
    */
   public static void main(String[] args)
   {
      SwingUtilities.invokeLater(new InputOutputDriver());
   }

}

class Window extends JFrame
{
   public Window()
   {
      super();
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Container contentPane = this.getContentPane();
      contentPane.setLayout(new BorderLayout());
      contentPane.add(new InputOutputSelectionPanel(), BorderLayout.CENTER);
      contentPane.add(createButtonPanel(), BorderLayout.SOUTH);
      this.pack();
      Aspect.center(this);
   }
   
   public void exit()
   {
      this.setVisible(false);
      System.exit(0);
   }
   
   protected JPanel createButtonPanel()
   {
      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createEtchedBorder());
      JButton button = new JButton("Close");
      button.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e)
         {
            exit();
         }
      });
      panel.add(button);
      return panel;
   }
}
