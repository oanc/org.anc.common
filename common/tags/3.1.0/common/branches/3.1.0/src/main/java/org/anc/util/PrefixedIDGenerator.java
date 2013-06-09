package org.anc.util;

public class PrefixedIDGenerator extends IDGenerator
{
   private String prefix;
   
   public PrefixedIDGenerator(String prefix)
   {
      super();
      this.prefix = prefix;
   }
   
   @Override
   public String generate(String type)
   {
      String id = super.generate(type);
      if (prefix != null)
      {
         return prefix + "-" + id;
      }
      return id;
   }
}
