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
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * An abstract base class for dialog boxes. The parameterized type T is the type
 * of object returned by the <code>getResult</code> method.
 * <p>
 * Users must override the <code>makeMainPanel()</code> which should return an
 * object derived from {@link JPanel} and is used as the main user interface for
 * the dialog box.
 * <p>
 * Users should also override the <code>getResult</code> method to return an
 * object of the desired result type. The <code>display</code> method displays
 * the dialog (calls <code>setVisible(true)</code>) and then waits for the user
 * to close the dialog. If the user clicks the "OK" button to close the dialog
 * the <code>display</code> returns the object returned by
 * <code>getResult</code>. If the user closes the dialog in any other way the
 * <code>display</code> method returns null.
 * <p>
 * The text for the two buttons can be specified in the BaseDialog class
 * constructor.
 * <p>
 * Example:
 * 
 * <pre>
 * class TestDialog extends BaseDialog&lt;Integer&gt;
 * {
 *    public Integer getResult() { return new Integer(5); }
 *    protected JPanel makeMainPanel() { return new JPanel(); }
 * } 
 * ...
 * TestDialog dialog = new TestDialog();
 * Integer n = dialog.display();
 * if (n != null)
 * {
 *    // User accepted the dialog
 *    ...
 * }
 * else
 * {
 *     // User cancelled the dialog.
 *     ...
 * }
 * ...
 * </pre>
 * 
 * @author Keith Suderman
 * 
 * @param <T>
 *           The type of object that will be returned by the
 *           <code>getResult</code> method.
 */
public abstract class BaseDialog<T> extends JDialog implements ActionListener
{
//   protected Component parent;

   protected JButton createButton;
   protected JButton cancelButton;

//   protected JTextField repositoryField = new JTextField(40);
//   protected JTextField projectField = new JTextField(40);
//   protected JTextField workingCopyField = new JTextField(40);

   protected boolean accepted = false;

   public BaseDialog(Frame parent, String title)
   {
      this(parent, title, "Ok", "Cancel");
   }

   public BaseDialog(Frame parent, String title, String okButtonText,
         String cancelButtonText)
   {
      super(parent, title, true);
      createButton = new JButton(okButtonText);
      cancelButton = new JButton(cancelButtonText);
      initUI();
      org.anc.Aspect.center(parent, this);

   }

   public T display()
   {
      setVisible(true);
      if (accepted)
      {
         return getResult();
      }
      return null;
   }

   protected abstract T getResult();

   protected abstract JPanel makeMainPanel();

   @Override
   public void actionPerformed(ActionEvent event)
   {
      accepted = (event.getSource() == createButton);
      setVisible(false);
   }

   private void initUI()
   {
      Container contentPane = this.getContentPane();
      contentPane.setLayout(new BorderLayout());
      contentPane.add(makeMainPanel(), BorderLayout.CENTER);
      contentPane.add(makeButtonPanel(), BorderLayout.SOUTH);
      pack();
   }

   private JPanel makeButtonPanel()
   {
      createButton.addActionListener(this);
      cancelButton.addActionListener(this);

      JPanel panel = new JPanel();
      panel.setBorder(BorderFactory.createEtchedBorder());
      panel.add(createButton);
      panel.add(cancelButton);
      return panel;
   }

   protected JLabel makeLabel(String label)
   {
      return new JLabel(label, SwingConstants.RIGHT);
   }

}
