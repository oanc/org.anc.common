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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import org.anc.threads.Haltable;
import org.anc.threads.Notifiable;

/**
 * A dialog box that displays a text message, progress bar, and a cancel button.
 * The ProgressDialog is used by various thread objects to display their
 * progress and to allow the user to halt the thread.
 * <p>
 * If the dialog's owner implements the {@link Haltable} interface its
 * <code>halt</code> method will be called, and if the dialog's owner implements
 * the {@link Notifiable} interface the <code>signal</code> method will be
 * called.
 * 
 * @author Keith Suderman
 * @version 1.0
 */

public class ProgressDialog extends JDialog
{
   private static final long serialVersionUID = 1L;

// Panel used to hold the cancel button.
   private JPanel buttonPanel = new JPanel();
   // Panel used to hold the text message and the progress bar.
   private JPanel topPanel = new JPanel();
   // Layout manager for the top panel.
   private FlowLayout flowLayout1 = new FlowLayout();
   private JButton cancelButton = new JButton();
   private JProgressBar progressBar = new JProgressBar();
   // Text message to be displayed.
   private JLabel progressLabel = new JLabel();

   private Haltable haltableOwner = null;
   private Notifiable<ProgressDialog> notifiableOwner = null;

   public ProgressDialog(Frame frame, String title, boolean modal)
   {
      super(frame, title, modal);
      try
      {
         jbInit();
         pack();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   public ProgressDialog()
   {
      this(null, "Progress", false);
   }

   public ProgressDialog(Haltable owner, String message)
   {
      this(null, message, false);
      haltableOwner = owner;
      progressLabel.setText(message);
   }

   public ProgressDialog(Notifiable<ProgressDialog> owner, String message)
   {
      this(null, message, false);
      notifiableOwner = owner;
      progressLabel.setText(message);
   }

   public void setMessage(String message)
   {
      progressLabel.setText(message);
   }

   public void setValue(int value)
   {
      progressBar.setValue(value);
   }

   public void setMax(int max)
   {
      progressBar.setMaximum(max);
   }

   public int getMax()
   {
      return progressBar.getMaximum();
   }

   public int getValue()
   {
      return progressBar.getValue();
   }

   public void reset()
   {
      progressBar.setValue(0);
   }

   public void increment()
   {
      int n = progressBar.getValue();
      progressBar.setValue(++n);
//    this.paint(this.getGraphics());
   }

   private void jbInit() throws Exception
   {
      this.setResizable(false);
      this.setTitle("Progress");

      buttonPanel.setLayout(flowLayout1);
      cancelButton.setText("Cancel");
      cancelButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e)
         {
            cancelButton_actionPerformed(e);
         }
      });

      buttonPanel.add(cancelButton, null);

      progressBar.setStringPainted(true);
      progressBar.setPreferredSize(new Dimension(200, 20));
      progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
      progressLabel.setText("Progress");

      topPanel.setLayout(new GridBagLayout());
      GBC gbc = new GBC();
      topPanel.add(progressLabel, gbc);
      topPanel.add(progressBar, gbc.down());

      getContentPane().add(buttonPanel, BorderLayout.SOUTH);
      getContentPane().add(topPanel, BorderLayout.CENTER);
   }

   void cancelButton_actionPerformed(ActionEvent e)
   {
      if (haltableOwner != null)
      {
         haltableOwner.halt();
      }
      if (notifiableOwner != null)
      {
         notifiableOwner.signal(this);
      }
   }
}
