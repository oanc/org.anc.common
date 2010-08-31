/*-
 * Copyright 2010 The American National Corpus
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
package org.anc.logging;

/** For the most part the FakeLogger presents an interface similar to a
 *  SLF4J Logger. When/if the class using a FakeLogger does use a real logging
 *  framework it should be easy enough easy enough to transition.
 *  <p>
 *  FakeLogger classes do not support <i>levels</i>, all messages are always
 *  printed. Instead, different FakeLogger implementations override some of
 *  the methods with empty method bodies. Hopefully a clever JIT will optimize
 *  these empty function calls away for us.
 *  
 */
public interface FakeLogger
{
   enum Level { DEBUG, INFO, WARN, ERROR, NONE }
   
   void error(String message);
   void error(String template, Object pattern);
   void error(String template, Object arg1, Object arg2);
   void warn(String message);
   void warn(String template, Object pattern);
   void warn(String template, Object arg1, Object arg2);
   void info(String message);
   void info(String template, Object pattern);
   void info(String template, Object arg1, Object arg2);
   void debug(String message);
   void debug(String template, Object pattern);
   void debug(String template, Object arg1, Object arg2);
}

