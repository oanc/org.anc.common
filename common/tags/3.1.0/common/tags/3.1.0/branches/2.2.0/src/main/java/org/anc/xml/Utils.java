package org.anc.xml;

/**
 * Helper methods for dealing with XML.
 * 
 * @author Keith Suderman
 * 
 */
public class Utils
{
   /**
    * Replaces ampersands, less-than, greater-than, and double quotes in the
    * string with the corresponding XML entity.
    * 
    */
   public static final String encode(String s)
   {
      String result = s.replaceAll("&", "&amp;");
      result = result.replaceAll("<", "&lt;");
      result = result.replaceAll(">", "&gt;");
      result = result.replaceAll("\"", "&quot;");
      return result;
   }

}
