package org.anc.args;

import java.util.*;

/**
 * Parameter objects are used to encapsulate information about command line
 * parameters is CLI/shell programs. Each parameter consists of:
 * <ul>
 * <li>the command line argument used to specify the parameter, ie. -some-flag.
 * <li>a description of the parameter used when printing a usage message.
 * <li>a type, which can be one of string, path, list, flag, or choice.
 * <li>a method to display the proper usage of the parameter on the command line.
 * </ul>
 * Choice parameters also maintain a list of the valid values that can be 
 * specified. The <i>type</i> of the parameter only determines how the usage message will
 * be displayed. No real type checking is done.
 * <p>
 * Parameters can be constructed directly and optionally supply a value for the
 * type parameter, or use one of the factory methods to create a parameter of
 * the desired type. For example, the following two parameters are equivalent.
 * <pre>
 * Parameter p1 = new Parameter("-x", "a flag", Parameter.FLAG);
 * Parameter p2 = Parameter.flag("-x", "a flag");
 * </pre>
 * 
 * @see org.anc.args.Parameters Parameters for usage examples. 
 * @author Keith Suderman
 * @since 2.2.0
 */
public class Parameter
{
   public static final String STRING = "<string>";
   public static final String PATH = "<path>";
   public static final String LIST = "<list>";
   public static final String FLAG = null;
   
   protected String command;
   protected String description;
   protected String type;
   protected Set<String> validChoices = null;
   
   public Parameter(String command, String description)
   {
      this(command, description, null);
   }
   
   public Parameter(String command, String description, String type)
   {
      this.command = command;
      this.description = description;
      this.type = type;
   }
   
   public static Parameter string(String command, String description)
   {
      return new Parameter(command, description, STRING);
   }
   
   public static Parameter path(String command, String description)
   {
      return new Parameter(command, description, PATH);
   }
   
   public static Parameter list(String command, String description)
   {
      return new Parameter(command, description, LIST);
   }
   
   public static Parameter flag(String command, String description)
   {
      return new Parameter(command, description, null);
   }
   
   public static Parameter choice(String command, String description, String[] choices)
   {
      HashSet<String> valid = new HashSet<String>();
      StringBuilder format = new StringBuilder();
      format.append('[');
      String choice = choices[0];
      format.append(choice);
      valid.add(choice);
      for (int i = 1; i < choices.length; ++i)
      {
         choice = choices[i];
         format.append('|');         
         format.append(choice);
         valid.add(choice);
      }
      format.append(']');
      Parameter p = new Parameter(command, description, format.toString());
      p.validChoices = valid;
      return p;
   }
   
   public String type() { return type; }
   public String commandLine() 
   {
      if (type == null)
      {
         return command;
      }
      return command + "=" + type;
   }
   
   public String usage()
   {
      return command + ": " + description;
   }
   
   public String name() { return command; }
   
   @Override
   public String toString()
   {
      return command;
   }
   
   @Override
   public boolean equals(Object object)
   {
      if (object instanceof Parameter)
      {
         return command.equals(((Parameter) object).command);
      }
      return false;
   }
   
   @Override
   public int hashCode()
   {
      return command.hashCode();
   }

   public String usage(int longest)
   {
      StringBuilder builder = new StringBuilder();
      builder.append(command);
      int spaces = longest - command.length();
      while (spaces > 0)
      {
         builder.append(' ');
         --spaces;
      }
      builder.append(": ");
      builder.append(description);
      return builder.toString();
   }
   
   public boolean valid(String choice)
   {
      if (validChoices == null)
      {
         return true;
      }
      return validChoices.contains(choice);
   }
}
