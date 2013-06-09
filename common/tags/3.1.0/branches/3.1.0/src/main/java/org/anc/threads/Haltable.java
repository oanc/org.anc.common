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
package org.anc.threads;

/**
 * A counterpart to the Runnable interface. This interface indicates that an
 * object can be halted. Typically used by thread objects and any dialogs they
 * create.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public interface Haltable
{
   /** Signal an object that it is supposed to halt whatever it is doing. */
   void halt();
}
