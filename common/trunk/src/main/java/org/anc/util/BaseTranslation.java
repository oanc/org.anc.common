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
import java.io.OutputStream;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * The BaseTranslation class is used to provide alternate language translations
 * for the static text used in an application.
 * <p>
 * Users should extend the BaseTranslation class and define <code>public final
 * String</code> instance fields to be used in the application. The 
 * BaseTranslation class provides functionality for initializing these fields 
 * from a properties file.
 * <p>
 * The <code>public final String</code> fields should be initialized to 
 * <code>null</code> and the default value to use specified with a
 * <code>@Default</code> annotation on the field. 
 * <p>
 * Derived classes <i>must</i> call the <code>BaseTranslation.init</code> 
 * method in the constructor to initialize the <code>public final String</code>
 * fields. The <code>BaseTranslation</code> class will look for language files
 * in a directory named <code>lang</code> in the application's directory. The
 * language file to use is determined by:
 * <ol>
 * <li>The value of the <code>org.anc.lang</code> Java system property.
 * <li>The current Java locale in use.
 * <li>The default values provided as annotations to the fields.
 * </ol>
 * <p>
 * To generate the default language file call the <code>BaseTranslation.save</code>
 * method with an instance of the translation class as the only parameter.
 * <pre>
 *     class Translation extends BaseTranslation
 *     {
 *         ...
 *         BaseTranslation.save(Translation.class);
 *     }
 * </pre>
 * this will generate the file <code>lang/Translation.en</code>. To provide
 * alternate languages translate the text in the generated translation file and
 * change the extension to the two letter ISO language code. Languages can also
 * be specified with the two letter ISO country code. For example, to provide
 * translations in German and Swiss German the translation files should be
 * <ul>
 * <li>Translation.de
 * <li>Translation.de-ch
 * </ul>
 * <b>Note:</b> The name of the translation file is the same as the name of 
 * the class that extends <code>BaseTranslation</code>.
 * <p>
 * <b>Example</b>
 * 
 * <pre>
 *  public class Translation extends BaseTranslation
 *  {
 *    @Default("Hello world.")
 *    public final String HelloWorld = null;
 *    @Default("Some text.");
 *    public final String SomeText = null;
 *    ...
 *    
 *    public Translation()
 *    {
 *       super();
 *       super.init(Translation.class);
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
 * However, there is nothing the compiler, or an IDE, can do ensure there are no
 * typos in the key value is being used. The keys used could be specified as
 * constant (final static) values of a class:
 * <pre>
 * public final class Keys 
 * {
 * 	public static final String HelloWorld = &quot;HelloWorld&quot;;
 * }
 * ...
 * String message = myResourceBundle.getString(Keys.HelloWorld);
 * </pre>
 * But that add another class to maintain and another set of values that need to
 * be kept synchronized.
 * <p>
 * @author Keith Suderman
 * @since Version 2.0.0
 * 
 */
public class BaseTranslation
{
   /**
    * Annotation type used by subclasses to declare default values for the
    * String constants.
    * 
    * @author Keith Suderman
    *
    */
   @Documented
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Default
   {
      String value();
   }
   
   public static final class SystemProperties
   {
      public static final String LANG = "org.anc.lang";
      public static final String DEFAULT_LANG_LOCATION = "lang";
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
      write(new FileOutputStream(file), "Default translation.");
   }

//   public void write(File file, String comment) throws IOException
   public void write(OutputStream out, String comment)
   {
      Properties props = new Properties();
//      FileOutputStream out = null;
      try
      {
         Class<? extends BaseTranslation> theClass = this.getClass();
         Field[] fields = theClass.getFields();
         for (Field field : fields)
         {
            String name = field.getName();
            String value = (String) field.get(this);
//            System.out.println("Field name : " + name);
//            System.out.println("Field value : " + value);
            
            props.setProperty(name, value);
         }
//         out = new FileOutputStream(file);
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
         if (out != null) try
         {
            out.close();
         }
         catch(IOException e)
         {
            // nothing we can do, so do nothing.
         }
      }
   }

   protected void init(Class<? extends BaseTranslation> subclass)
   {
//      System.out.println("BaseTranslation Initializing.");
      String className = getClassName(subclass);
      Properties translation = new Properties();
      // First check if the system property has been set manually.  This will
      // override all other methods of determining the language file to use.
      Locale locale = Locale.getDefault();
      String lang = System.getProperty(SystemProperties.LANG);
//      if (lang == null)
//      {
//         lang = locale.getLanguage();
//      }
      if (!loadLanguage(lang, className, translation))
      {
//         System.out.println("Attempting to load " + lang);
         // That didn't work, so check for a language file specified by country
         // and language code.
         String country = locale.getCountry();
         lang = locale.getLanguage();
         if (!loadLanguage(lang + "-" + country, className, translation))
         {
            // That didn't work either, try just the language code. If this
            // doesn't work the default values will be used.
            loadLanguage(lang, className, translation);
         }
      }
            
//      System.out.println("Setting fields.");
      Field[] fields = subclass.getDeclaredFields();
      for (Field field : fields)
      {
//         System.out.println("Setting " + field.getName());
         int modifiers = field.getModifiers();
//         String fieldClass = field.getType().getName();
//         String access = Modifier.isPublic(modifiers) ? "public " : "";
//         String sFinal = Modifier.isFinal(modifiers) ? "final " : "";
//         System.out.println(access + sFinal + field.getName() + " " + fieldClass);
         if (field.getType().equals(String.class) &&
//               Modifier.isFinal(modifiers) && 
               Modifier.isPublic(modifiers))
         {
            String value = translation.getProperty(field.getName());
            if (value == null)
            {
               Default defaultValue = field.getAnnotation(Default.class);
               if (defaultValue != null)
               {
                  value = defaultValue.value();
               }
            }
            if (Modifier.isFinal(modifiers))
            {
               field.setAccessible(true);
            }
            try
            {
//               System.out.println("Setting " + field.getName() + " to \"" + value + "\"");
               field.set(this, value);
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
   }

   protected static boolean loadLanguage(String lang, String className, Properties props)
   {
      if (lang == null)
      {
         return false;
      }
      
      File file = new File(SystemProperties.DEFAULT_LANG_LOCATION + "/" 
            + className + "." + lang);
      if (!file.exists())
      {
         return false;
      }
      try
      {
         loadProperties(file, props);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         return false;
      }
      return true;
   }
   
   protected static void loadProperties(File propFile, Properties props)
         throws IOException, SecurityException, IllegalArgumentException,
         NoSuchFieldException, IllegalAccessException
   {
      FileInputStream out = null;
      try
      {
         out = new FileInputStream(propFile);
         props.load(out);
      }
      finally
      {
         if (out != null)
         {
            out.close();
         }
      }
}

//   protected void init(Class<? extends BaseTranslation> subclass)
//   {
//      init(getClassName(subclass));
//   }
//
//   protected void init(String className)
//   {
//      String lang = System.getProperty(SystemProperties.LANG);
//      if (lang == null)
//      {
//         return;
//      }
//      File file = new File("lang/" + className + "." + lang);
//      if (file.exists())
//      {
//         try
//         {
////			System.out.println("Loading language file from " + file.getAbsolutePath());
//            init(file);
//         }
//         catch (Exception ex)
//         {
//            System.out.println("Error loading the specified language file.");
//            ex.printStackTrace();
//         }
//      }
//      else
//      {
//         System.out
//               .println("The specified language file could not be loaded from "
//                     + file.getAbsolutePath());
//      }
//   }

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

//   protected void init(File propFile) throws IOException, SecurityException,
//         IllegalArgumentException, NoSuchFieldException, IllegalAccessException
//   {
//      java.util.Properties props = new java.util.Properties();
//      FileInputStream out = null;
//      try
//      {
//         out = new FileInputStream(propFile);
//         props.load(out);
//         init(props);
//      }
//      finally
//      {
//         if (out != null)
//         {
//            out.close();
//         }
//      }
//   }

//	protected void init(String propPath)
//	throws FileNotFoundException, IOException, SecurityException,
//	IllegalArgumentException, NoSuchFieldException, IllegalAccessException
//	{
//		Properties props = new Properties();
//		props.load(new FileInputStream(propPath));
//		init(props);
//	}

//   protected void init(Properties props) throws SecurityException,
//         NoSuchFieldException, IllegalArgumentException, IllegalAccessException
//   {
//      Class<? extends BaseTranslation> theClass = this.getClass();
//      for (Entry<Object, Object> entry : props.entrySet())
//      {
//         String key = (String) entry.getKey();
//         String value = (String) entry.getValue();
//         Field field = theClass.getField(key);
//         if (field == null)
//         {
//            System.out.println("Invalid key \"" + key
//                  + "\" in the language file.");
//         }
//         else
//         {
////				System.out.println("Setting " + key + "=" + value);
//            int modifiers = field.getModifiers();
//            if (Modifier.isFinal(modifiers))
//            {
//               field.setAccessible(true);
//            }
//            field.set(this, value);
//            
////				System.out.println("Set " + key + "=" + field.get(this).toString());				
//         }
//      }
//   }

}
