package org.anc.ui;

import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.*;

public class InputOutputSelectionPanel extends JPanel
{
   private static final long serialVersionUID = 1L;
   
   private JLabel inputLabel = new JLabel("Input");
   private JLabel outputLabel = new JLabel("Output");
   
   private JTextField inputField = new JTextField(32);
   private JTextField outputField = new JTextField(32);
   
   private FileChooserButton inputButton;
   private FileChooserButton outputButton;
   
   private JFileChooser fileChooser = new JFileChooser();
   
   public InputOutputSelectionPanel()
   {
      createUI();
   }

   public void setInputLabel(String label)
   {
      inputLabel.setText(label);
   }
   public void setOutputLabel(String label)
   {
      outputLabel.setText(label);
   }
   public void setInputField(String text)
   {
      inputField.setText(text);
   }
   public void setOutputField(String text)
   {
      outputField.setText(text);
   }
   public void setInputChooserButtonText(String text)
   {
      inputButton.setFileChooserButtonText(text);
   }
   public void setOutputChooserButtonText(String text)
   {
      outputButton.setFileChooserButtonText(text);
   }
   
   public String getInputField()
   {
      return inputField.getText();
   }
   public String getOutputField()
   {
      return outputField.getText();
   }

   private void createUI()
   {
      setBorder(BorderFactory.createEtchedBorder());
      setLayout(new GridBagLayout());
      
      inputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      outputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
   
      inputButton = new FileChooserButton(this, inputField, fileChooser, "Input");
      outputButton = new FileChooserButton(this, outputField, fileChooser, "Output");
      
      GBC gbc = new GBC();
      add(inputLabel, gbc.xy(0, 0));
      add(inputField, gbc.right());
      add(inputButton, gbc.right());
      
      add(outputLabel, gbc.xy(0, 1));
      add(outputField, gbc.right());
      add(outputButton, gbc.right());
   }
   
}
