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

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Provides functionality for setting the fields of
 * {@link java.awt.GridBagConstraints} objects.
 * <p>
 * One of the biggest drags of working with GridBag layouts is the number of
 * times you need to type the string <tt>GridBagConstraints</tt> into the text
 * editor. The GBC class extends the java.awt.GridBagConstraints class and adds
 * helper methods to set fields and to allow GBC objects to be used as a
 * "cursor" into the layout. If nothing else, a programmer can access the class
 * variables via the GBC class rather than having to type
 * <tt>GridBagConstraints</tt> all the time.
 * <p>
 * The GridBagLayout manager makes its own copy of the GridBagConstraints object
 * so it is possible to use the same GBC object for all controls that you add to
 * a container. For example: <code>
 * JPanel aPanel = new JPanel(new GridBagLayout());
 * GBC gbc = new GBC();
 * 
 * // Add controls in the first column
 * // Right align the controls
 * gbc.right();
 * // Don't resize
 * gbc.nofill();
 * aPanel.add(control1, gbc);
 * aPanel.add(control2, gbc.down());
 * aPanel.add(control3, gbc.down());
 * 
 * // Center the controls in the second column
 * gbc.center();
 * // Grow horizontally, and take up all new room along x-axis
 * gbc.horizontal();
 * gbc.weight(1, 0);
 * aPanel.add(control4, gbc.xy(1,0);
 * aPanel.add(control5, gbc.down());
 * aPanel.add(control6, gbc.down());
 * 
 * // Add controls to the third column
 * // Left aligned and won't resize
 * gbc.nofill();
 * gbc.weight(0,0);
 * aPanel.add(control7, gbc.xy(2,0));
 * aPanel.add(control8, gbc.down());
 * aPanel.add(control9, gbc.down());
 * ...
 * </code>
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class GBC extends GridBagConstraints
{
   private static final long serialVersionUID = 1L;

   public GBC()
   {
      this(CENTER, BOTH, 1, 1);
   }

   public GBC(int fill, int anchor)
   {
      this(fill, anchor, 1, 1);
   }

   public GBC(int fill, int anchor, int width, int height)
   {
      // Don't you just love constructors that take 11 parameters...
      super(0, 0, width, height, 0, 0, fill, anchor, new Insets(2, 4, 2, 4), 1,
            1);
   }

   /**
    * A static factory method that initializes a constraint object with the most
    * commonly used values.
    */

   public static GBC getDefaultGbc()
   {
      GBC gbc = new GBC();
      gbc.anchorLeft();
      gbc.fillBoth();
      gbc.weight(1, 0);
// gbc.pad(2, 2);
// gbc.insets(2, 4, 2, 4);
      return gbc;
   }

   // alignment
   /** Set anchor = WEST. */
   public GBC anchorLeft()
   {
      anchor = WEST;
      return this;
   }

   public GBC anchorCenter()
   {
      anchor = CENTER;
      return this;
   }

   public GBC anchorRight()
   {
      anchor = EAST;
      return this;
   }

   public GBC anchorWest()
   {
      anchor = WEST;
      return this;
   }

   public GBC anchorEast()
   {
      anchor = EAST;
      return this;
   }

   public GBC anchorNorth()
   {
      anchor = NORTH;
      return this;
   }

   public GBC anchorSouth()
   {
      anchor = SOUTH;
      return this;
   }

   public GBC anchorNortheast()
   {
      anchor = NORTHEAST;
      return this;
   }

   public GBC anchorNorthwest()
   {
      anchor = NORTHWEST;
      return this;
   }

   public GBC anchorSoutheast()
   {
      anchor = SOUTHEAST;
      return this;
   }

   public GBC anchorSouthwest()
   {
      anchor = SOUTHWEST;
      return this;
   }

   // fill
   public GBC fillNone()
   {
      fill = NONE;
      return this;
   }

   public GBC fillBoth()
   {
      fill = BOTH;
      return this;
   }

   public GBC fillVertical()
   {
      fill = VERTICAL;
      return this;
   }

   public GBC fillHorizontal()
   {
      fill = HORIZONTAL;
      return this;
   }

   // positioning
   public GBC x(int x)
   {
      gridx = x;
      return this;
   }

   public GBC y(int y)
   {
      gridy = y;
      return this;
   }

   public GBC xy(int x, int y)
   {
      gridx = x;
      gridy = y;
      return this;
   }

   public GBC down()
   {
      ++gridy;
      return this;
   }

   public GBC up()
   {
      --gridy;
      return this;
   }

   public GBC right()
   {
      ++gridx;
      return this;
   }

   public GBC left()
   {
      --gridx;
      return this;
   }

   public GBC insets(int top, int left, int bottom, int right)
   {
      insets = new Insets(top, left, bottom, right);
      return this;
   }

   public GBC weight(int wx, int wy)
   {
      this.weightx = wx;
      this.weighty = wy;
      return this;
   }

   public GBC size(int width, int height)
   {
      gridwidth = width;
      gridheight = height;
      return this;
   }

   public GBC pad(int x, int y)
   {
      ipadx = x;
      ipady = y;
      return this;
   }

}
