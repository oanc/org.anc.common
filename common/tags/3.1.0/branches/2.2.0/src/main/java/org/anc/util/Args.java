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
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple class to parse command line arguments and store them in a map as
 * name/value pairs. The command line arguments should be of the form
 * <tt>-name=value</tt>, or in the case of a switch type argument, simply
 * <tt>-name</tt>.
 * 
 * @author Keith Suderman
 * @version 1.0
 */
public class Args
{
   protected Map<String, String> args = new Hashtable<String, String>();
   protected List<String> errors = new LinkedList<String>();
   protected Set<String> allArgs = null;

   public Args(String[] args)
   {
      super();
      for (String arg : args)
      {
         parse(arg);
      }
   }

   public Args(String[] args, String[] required)
   {
      this(args);
      checkRequired(required);
   }

//    public Args(String[] args, Set<String> required)
//    {
//   	 this(args);
//   	 checkRequired(required);
//    }

   public Args(String[] args, String[] required,
         Class<? extends Object> parametersClass)
   {
      super();
      try
      {
         getParameterFields(parametersClass);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         errors.add(e.getMessage());
         return;
      }
      for (String arg : args)
      {
         parse(arg);
      }
      checkRequired(required);
   }

   public Args(String[] args, Class<? extends Object> parametersClass)
   {
      super();
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

   public boolean defined(String name)
   {
      return args.get(name) != null;
   }

   public boolean valid()
   {
      return errors.size() == 0;
   }

   public List<String> getErrors()
   {
      return errors;
   }

   public void printErrors()
   {
      for (String error : errors)
      {
         System.out.println(error);
      }
   }

   public void printErrors(OutputStream out) throws IOException
   {
      byte[] EOL = System.getProperty("line.separator").getBytes();
      for (String error : errors)
      {
         out.write(error.getBytes());
         out.write(EOL);
      }
   }

   protected void parse(String arg)
   {
      if (!arg.startsWith("-"))
      {
         errors.add("Invalid argument " + arg
               + ". Arguments must start with a '-' (dash).");
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
//			String[] parts = arg.split("=");
//			if (parts == null || parts.length != 2)
//			{
//				errors.add("Invalid argument form: " + arg + ". Arguments must be of the form -name=value.");
//				return;
//			}
//			args.put(parts[0], parts[1]);
//			name = parts[0];
         name = arg.substring(0, equal).trim();
         String value = arg.substring(equal + 1).trim();
         args.put(name, value);
      }
      if (allArgs != null)
      {
         if (!allArgs.contains(name))
         {
            errors.add("Unknown parameter " + name);
            return;
         }
      }
   }

   private void checkRequired(String[] required)
   {
      for (String arg : required)
      {
         if (!defined(arg))
         {
            errors.add("Required argument " + arg + " is missing.");
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
               && field.getType() == String.class)
         {
            String paramName = field.get(null).toString();
            allArgs.add(paramName);
         }
      }
   }
}
