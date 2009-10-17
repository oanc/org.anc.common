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
package org.anc.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * A simple class to parse command line arguments and store them in a map as
 * name/value pairs. The command line arguments should be of the form
 * <tt>-name=value</tt>, or in the case of a switch type argument, simply
 * <tt>-name</tt>.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class Parameters
{
   protected Map<String, String> args = new Hashtable<String, String>();
   protected List<String> errors = new LinkedList<String>();
   protected Set<String> allArgs = null;
   protected List<String> missing = new LinkedList<String>();
   protected List<String> invalid = new LinkedList<String>();
   protected List<String> unknown = new LinkedList<String>();
   
   public Parameters(String[] args)
   {
      super();
      for (String arg : args)
      {
         parse(arg);
      }
   }

   public Parameters(String[] args, Parameter[] required)
   {
      this(args);
      checkRequired(required);
   }

//    public Args(String[] args, Set<String> required)
//    {
//     this(args);
//     checkRequired(required);
//    }

   public Parameters(String[] args, Parameter[] required,
         Class<? extends Object> parametersClass)
   {
      this(args, parametersClass);
      checkRequired(required);
   }

   public Parameters(String[] args, Class<? extends Object> parametersClass)
   {      
      super();
      if (parametersClass == Parameters.class)
      {
         throw new RuntimeException("You have passed the wrong parameter class to the Parameters constructor.  Check the class name.");
      }
      try
      {
         getParameterFields(parametersClass);
      }
      catch (Exception e)
      {
         errors.add(e.getMessage());
         return;
      }
      for (String arg : args)
      {
         parse(arg);
      }
   }

   public Set<Map.Entry<String, String>> get()
   {
      return args.entrySet();
   }

   public String get(Parameter p)
   {
      return get(p.name());
   }
   
   public String get(String name)
   {
      return args.get(name);
   }
   
   public String get(String name, String defaultValue)
   {
      String result = get(name);
      if (result == null)
      {
         return defaultValue;
      }
      return result;
   }

   public boolean defined(Parameter p)
   {
      return defined(p.name());
   }
   
   public boolean defined(String name)
   {
      return args.get(name) != null;
   }

   public boolean valid()
   {
      return (errors.size() == 0) 
         && (missing.size() == 0)
         && (invalid.size() == 0)
         && (unknown.size() == 0);
   }

   public List<String> getErrors()
   {
      return errors;
   }

   public List<String> getMissing() { return missing; }
   public List<String> getUnknown() { return unknown; }
   public List<String> getInvalid() { return invalid; }
   
   public void printErrors()
   {
      printErrors(System.out);
//      for (String error : errors)
//      {
//         System.out.println(error);
//      }
   }

   public void printErrors(OutputStream os) //throws IOException
   {
      PrintStream out;
      if (os instanceof PrintStream)
      {
         out = (PrintStream) os;
      }
      else
      {
         out = new PrintStream(os);
      }
      printErrors(out, "Errors", errors);
      printErrors(out, "Missing", missing);
      printErrors(out, "Invalid", invalid);
      printErrors(out, "Unknown", unknown);
   }

   public void printErrors(PrintStream out, String title, List<String> errors)
   {
      if (errors.size() == 0)
      {
         return;
      }
      out.println(title);
      for (String error : errors)
      {
         out.println("   " + error);
      }
   }
   
   public static void usage(Class<?> parameterClass) 
   {
      // First collect all the parameters and measure the length of the command
      // name.  We need to know the length of the longest command so we can 
      // pad the other commands to get the descriptions to line up nicely.
      int longest = 0;
      List<Parameter> parameters = new ArrayList<Parameter>();
      Field[] fields = parameterClass.getFields();
      for (Field field : fields)
      {
         int m = field.getModifiers();
         if (Modifier.isStatic(m) && Modifier.isFinal(m)
               && field.getType() == Parameter.class)
         {
//            String paramName = field.get(null).toString();
//            allArgs.add(paramName);
            Parameter p = null;
            try
            {
               p = (Parameter) field.get(null);
               parameters.add(p);
               String name = p.name();
               if (name.length() > longest)
               {
                  longest = name.length();
               }
//               System.out.println("\t" + p.usage());
            }
            catch (IllegalArgumentException e)
            {
               e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
               e.printStackTrace();
            }
         }
      }
      
      // Now we can print the usage message for each parameter using the
      // appropriate padding.
      for (Parameter p : parameters)
      {
         System.out.println("\t" + p.usage(longest));
      }
   }
   
   public static void usage(Class<?> applicationClass, Class<?> paramsClass, Parameter[] required)
   {
      Set<Parameter> req = new HashSet<Parameter>();
      for (Parameter p : required)
      {
         req.add(p);
      }
   
      // Determine if the user launched the class file or a jar file.
      String appName = applicationClass.getName();   
      String className = appName.replace('.', '/');
      String classJar = applicationClass.getResource(
         "/" + className + ".class").toString();

      if (classJar.startsWith("jar:")) 
      {
         appName = "-jar " + getJarName(classJar);
      }

      StringBuilder commandLine = new StringBuilder();
      commandLine.append("java ");
      commandLine.append(appName);

      // Now collect all the parameters and measure the length of the command
      // name.  We need to know the length of the longest command so we can 
      // pad the other commands to get the descriptions to line up nicely. We
      // also build up the command line at this time.
      int longest = 0;
      List<Parameter> parameters = new ArrayList<Parameter>();
      Field[] fields = paramsClass.getFields();
      for (Field field : fields)
      {
         int m = field.getModifiers();
         if (Modifier.isStatic(m) && Modifier.isFinal(m)
               && field.getType() == Parameter.class)
         {
//            String paramName = field.get(null).toString();
//            allArgs.add(paramName);
            Parameter p = null;
            try
            {
               commandLine.append(" ");
               p = (Parameter) field.get(null);
               if (req.contains(p))
               {
                  commandLine.append(p.commandLine());
               }
               else
               {
                  commandLine.append("[" + p.commandLine() + "]");
               }
               parameters.add(p);
               String name = p.name();
               if (name.length() > longest)
               {
                  longest = name.length();
               }
            }
            catch (IllegalArgumentException e)
            {
               e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
               e.printStackTrace();
            }
         }
         
      }
      System.out.println(commandLine.toString());
      System.out.println();
      // Now we can print the usage message for each parameter using the
      // appropriate padding.
      for (Parameter p : parameters)
      {
         System.out.println("\t" + p.usage(longest));
      }
   }
   
   private static String getJarName(String jarPath)
   {
      StringTokenizer tokenizer = new StringTokenizer(jarPath, "/");
      while (tokenizer.hasMoreTokens())
      {
         String token = tokenizer.nextToken();
         if (token.endsWith(".jar!"))
         {
            int n = token.length() - 1;
            return token.substring(0, n);
         }
      }
      return null;
   }
   
   protected void parse(String arg)
   {
      if (!arg.startsWith("-"))
      {
//         errors.add("Invalid argument " + arg
//               + ". Arguments must start with a '-' (dash).");
         invalid.add(arg);
         return;
      }

      String name = null;
      int equal = arg.indexOf('=');
      if (equal < 0)
      {
         args.put(arg, "true");
         name = arg;
      }
      else
      {
//       String[] parts = arg.split("=");
//       if (parts == null || parts.length != 2)
//       {
//          errors.add("Invalid argument form: " + arg + ". Arguments must be of the form -name=value.");
//          return;
//       }
//       args.put(parts[0], parts[1]);
//       name = parts[0];
         name = arg.substring(0, equal).trim();
         String value = arg.substring(equal + 1).trim();
         args.put(name, value);
      }
      if (allArgs != null)
      {
         if (!allArgs.contains(name))
         {
//            errors.add("Unknown parameter " + name);
            unknown.add(name);
            return;
         }
      }
   }

   private void checkRequired(Parameter[] required)
   {
      for (Parameter p : required)
      {
         if (!defined(p.name()))
         {
            missing.add(p.name());
//            errors.add("Required argument " + p.name() + " is missing.");
         }
      }
   }

   private void getParameterFields(Class<?> parameterClass)
         throws IllegalArgumentException, IllegalAccessException
   {
      allArgs = new HashSet<String>();
      Field[] fields = parameterClass.getFields();
      for (Field field : fields)
      {
         int m = field.getModifiers();
         if (Modifier.isStatic(m) && Modifier.isFinal(m)
               && field.getType() == Parameter.class)
         {
            String paramName = field.get(null).toString();
            allArgs.add(paramName);
         }
      }
   }
}