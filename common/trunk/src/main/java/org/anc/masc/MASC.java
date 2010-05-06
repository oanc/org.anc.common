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
   }
}
