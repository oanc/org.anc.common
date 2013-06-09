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
package org.anc.util.env;

import org.anc.util.Pair;

/**
 * Interface used by objects capable of parsing the environment variables of the
 * operating system shell being used. For example, in most shells typing
 * <code>set</code> produces a list of the environment variables to stdout.
 * <p>
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public interface IEnvironmentParser extends Iterable<Pair<String, String>>
{
   /*
    * Returns the name of the next environment variable.
    * 
    * @return String The name of the next environment variable or null if there
    * is no such variable.
    */
//    String getKey();

   /**
    * Returns the value of the next environment variable. If the previous call
    * to {@link #getKey} did not return null then getValue() should return a
    * non-null value. Once {@link #getKey} returns null then getValue should
    * always return null as well.
    * 
    * @return String The value of the next environment variable or null if there
    *         is no such value.
    */
//    String getValue();
}
