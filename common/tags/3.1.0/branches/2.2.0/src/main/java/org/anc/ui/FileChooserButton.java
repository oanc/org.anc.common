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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 * A button that displays a JFileChooser object when clicked. The JFileChooser
 * to use can be specified in the constructor, or if none is specified a
 * JFileChooser object will be created the first time the button is clicked and
 * cached for further usages.
 * <p>
 * The FileChooserButton can also have a JTextField associated with it. If there
 * is an associated JTextField the contents of the field will be used to set the
 * current directory of the JFileChooser before displaying it (if the text field
 * contains a valid path) and the text field will be updated with the path to
 * the selected file if the JFileChooser is accepted.
 * 
 * @author Keith Suderman
 * 
 */
public class FileChooserButton extends JButton implements ActionListener
{
   protected Component parent;
   protected JTextField field;
   protected JFileChooser chooser;

   public FileChooserButton(Component parent, JTextField field)
   {
      this(parent, field, null);
   }

   public FileChooserButton(Component parent, JTextField field,
         JFileChooser chooser)
   {
      super("...");
      this.parent = parent;
      this.field = field;
      this.chooser = chooser;
      this.addActionListener(this);
   }

   public void actionPerformed(ActionEvent e)
   {
      if (chooser == null)
      {
         chooser = new JFileChooser();
         chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      }

      File file = new File(field.getText());
      if (file.exists())
      {
         chooser.setSelectedFile(file);
      }

      if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
      {
         field.setText(chooser.getSelectedFile().getAbsolutePath());
      }
   }
}
