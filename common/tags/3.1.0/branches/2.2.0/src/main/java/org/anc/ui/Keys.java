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

import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * Common key strokes used in the UI (CTRL-C, CTRL-V, etc.).
 * 
 * @author Keith Suderman
 *
 */
public class Keys
{
   public static final KeyStroke CTRL_N = 
      KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK); 
   public static final KeyStroke CTRL_O = 
      KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK); 
   public static final KeyStroke CTRL_S = 
      KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK); 
   public static final KeyStroke CTRL_Q = 
      KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK); 
   public static final KeyStroke CTRL_A = 
      KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK); 
   public static final KeyStroke CTRL_C = 
      KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK); 
   public static final KeyStroke CTRL_V = 
      KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK); 
   public static final KeyStroke CTRL_X = 
      KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK); 
}
