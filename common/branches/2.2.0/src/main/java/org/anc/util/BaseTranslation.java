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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Map.Entry;

/**
 * The BaseTranslation class is used to provide alternate language translations
 * for the text used in an application.
 * <p>
 * Users should extend the BaseTranslation class and define public String
 * instance fields to be used in the application. The BaseTranslation class
 * provides functionality for initializing these fields from a properties file.
 * <p>
 * For example:
 * 
 * <pre>
 *  public class Translation extends BaseTranslation
 *  {
 *    public String HelloWorld = &quot;Hello World&quot;;
 *    public String SomeText = &quot;Some text&quot;;
 *    ...
 *    
 *    public Translation()
 *    {
 *    	super(Translation.class);
 *    }
 *  }
 * 
 *  public class Application
 *  {
 *    public static final Translation MESSAGES = new Translation();
 *  
 *    public static void main(String[] args)
 *    {
 *    	System.out.println(MESSAGES.HelloWorld);
 *    }  
 *  }
 * </pre>
 * <p>
 * Subclass of BaseTranslation can call one of the <code>init</code> methods to
 * load an arbitrary language file if needed.
 * <p>
 * The advantage of using the BaseTranslation class over a standard Java
 * {@link ResourceBundle} is that since the messages are fields of the class
 * IDEs such as Eclipse or NetBeans are able to provide code completion, type
 * checking and other features not possible when the messages are stored in a
 * properties class and retrieved via a String key. For example, with a resource
 * bundle a programmer might do:
 * 
 * <pre>
 *  String message = myResourceBundle.getString(&quot;Hello World&quot;)
 * </pre>
 * 
 * However, there is nothing the compiler, or an IDE, can do ensure there are no
 * typos in the key value is being used. The keys used could be specified as
 * constant (final static) values of a class:
 * 
 * <pre>
 * public final class Keys 
 * {
 * 	public static final String HelloWorld = &quot;HelloWorld&quot;;
 * }
 * ...
 * String message = myResourceBundle.getString(Keys.HelloWorld);
 * </pre>
 * 
 * But that add another class to maintain and another set of values that need to
 * be kept synchronized.
 * 
 * <p>
 * 
 * @author Keith Suderman
 * @since Version 2.0.0
 * 
 */
public class BaseTranslation
{
   public static final class SystemProperties
   {
      public static final String LANG = "org.anc.lang";
   }

   public BaseTranslation()
   {
   }

   public static String complete(String template, String arg1)
   {
      if (template.indexOf("$1") < 0)
      {
         Throwable t = new Exception();
         System.out.println("Invalid template replacement. $1 not found");
         t.printStackTrace();
         return template;
      }
      return template.replace("$1", arg1);
   }

   public static String complete(String template, String arg1, String arg2)
   {
      if (template.indexOf("$1") < 0)
      {
         Throwable t = new Exception();
         System.out.println("Invalid template replacement. $1 not found");
         t.printStackTrace();
         return template;
      }
      String result = template.replace("$1", arg1);
      if (template.indexOf("$2") < 0)
      {
         Throwable t = new Exception();
         System.out.println("Invalid template replacement. $2 not found");
         t.printStackTrace();
         return result;
      }
      return result.replace("$2", arg2);
   }

   public static void save(Class<? extends BaseTranslation> msgClass)
         throws InstantiationException, IllegalAccessException, IOException
   {
      BaseTranslation messages = msgClass.newInstance();
      String className = BaseTranslation.getClassName(msgClass);
      File lang = new File("lang/" + className + ".en");
      messages.write(lang);
   }

   public void write(File file) throws IOException
   {
      write(file, "Default translation.");
   }

   public void write(File file, String comment) throws IOException
   {
      Properties props = new Properties();
      FileOutputStream out = null;
      try
      {
         Class<? extends BaseTranslation> theClass = this.getClass();
         Field[] fields = theClass.getFields();
         for (Field field : fields)
         {
            String name = field.getName();
            String value = (String) field.get(this);
            props.setProperty(name, value);
         }
         out = new FileOutputStream(file);
         props.store(out, comment);

      }
      catch (FileNotFoundException e)
      {
         // This should never happen since we test if the files exists.
         e.printStackTrace();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      finally
      {
         if (out != null)
         {
            out.close();
         }
      }
   }

   protected void init(Class<? extends BaseTranslation> subclass)
   {
      init(getClassName(subclass));
   }

   protected void init(String className)
   {
      String lang = System.getProperty(SystemProperties.LANG);
      if (lang == null)
      {
         return;
      }
      File file = new File("lang/" + className + "." + lang);
      if (file.exists())
      {
         try
         {
//			System.out.println("Loading language file from " + file.getAbsolutePath());
            init(file);
         }
         catch (Exception ex)
         {
            System.out.println("Error loading the specified language file.");
            ex.printStackTrace();
         }
      }
      else
      {
         System.out
               .println("The specified language file could not be loaded from "
                     + file.getAbsolutePath());
      }
   }

   protected static String getClassName(Class<?> theClass)
   {
      String name = theClass.getName();
      int lastDot = name.lastIndexOf('.');
      if (lastDot > 0)
      {
         return name.substring(lastDot + 1);
      }
      return name;
   }

// public BaseTranslation(File file, BaseTranslation instance) throws
   // FileNotFoundException, IOException, SecurityException,
   // NoSuchFieldException, IllegalArgumentException, IllegalAccessException
// {
// Properties props = new Properties();
// props.load(new FileReader(file));
// init(props, instance);
// }
//	
// public BaseTranslation(Properties props, BaseTranslation instance) throws
   // SecurityException, IllegalArgumentException, NoSuchFieldException,
   // IllegalAccessException
// {
// init(props, instance);
// }

   protected void init(File propFile) throws IOException, SecurityException,
         IllegalArgumentException, NoSuchFieldException, IllegalAccessException
   {
      java.util.Properties props = new java.util.Properties();
      FileInputStream out = null;
      try
      {
         out = new FileInputStream(propFile);
         props.load(out);
         init(props);
      }
      finally
      {
         if (out != null)
         {
            out.close();
         }
      }
   }

//	protected void init(String propPath)
//	throws FileNotFoundException, IOException, SecurityException,
//	IllegalArgumentException, NoSuchFieldException, IllegalAccessException
//	{
//		Properties props = new Properties();
//		props.load(new FileInputStream(propPath));
//		init(props);
//	}

   protected void init(Properties props) throws SecurityException,
         NoSuchFieldException, IllegalArgumentException, IllegalAccessException
   {
      Class<? extends BaseTranslation> theClass = this.getClass();
      for (Entry<Object, Object> entry : props.entrySet())
      {
         String key = (String) entry.getKey();
         String value = (String) entry.getValue();
         Field field = theClass.getField(key);
         if (field == null)
         {
            System.out.println("Invalid key \"" + key
                  + "\" in the language file.");
         }
         else
         {
//				System.out.println("Setting " + key + "=" + value);
            int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers))
            {
               field.setAccessible(true);
            }
            field.set(this, value);
            
//				System.out.println("Set " + key + "=" + field.get(this).toString());				
         }
      }
   }

}
