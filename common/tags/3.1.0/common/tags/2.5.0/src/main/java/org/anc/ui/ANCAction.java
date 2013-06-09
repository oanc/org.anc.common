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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

/**
 * An abstract class that simply provides a few constructors for the
 * AbstractAction class.
 * 
 * @author Keith Suderman
 * @version 1.0
 */

public abstract class ANCAction extends AbstractAction
{
   public ANCAction(String name, String desc)
   {
      super(name);
      putValue(Action.SHORT_DESCRIPTION, desc);
   }

   public ANCAction(String name, String desc, Icon icon)
   {
      super(name, icon);
      putValue(Action.SHORT_DESCRIPTION, desc);
   }

   public ANCAction(String name, String desc, KeyStroke hotKey)
   {
      super(name);
      putValue(Action.SHORT_DESCRIPTION, desc);
      putValue(Action.ACCELERATOR_KEY, hotKey);
   }
}
