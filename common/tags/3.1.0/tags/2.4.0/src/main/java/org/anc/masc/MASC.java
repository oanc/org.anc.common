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
package org.anc.masc;

public final class MASC
{
   public static final String NAMESPACE = "http://www.anc.org/ns/masc/1.0";
   
   public final class AnnotationSets
   {
      public final class ANC
      {
         public static final String NAME = "masc";
         public static final String TYPE = MASC.NAMESPACE;         
      }
      public final class XCES
      {
         public static final String NAME = "xces";
         public static final String TYPE = "http://www.xces.org/schema/2003";
      }
      public final class PTB
      {
         public static final String NAME = "PTB";
         public static final String TYPE = "http://www.cis.upenn.edu/~treebank/";
      }
      public final class FrameNet
      {
         public static final String NAME = "FrameNet";
         public static final String TYPE = "http://framenet.icsi.berkeley.edu/";
      }
      public final class WordNet
      {
         public static final String NAME = "WordNet";
         public static final String TYPE = "http://wordnet.princeton.edu/";
      }
      public final class MPQA
      {
         public static final String NAME = "mpqa";
         public static final String TYPE = "http://www.cs.pitt.edu/mpqa/";
      }
   }
}
