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
package org.anc.i18n;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Properties;

import org.anc.io.UTF8Reader;
import org.anc.io.UTF8Writer;
import org.anc.logging.FakeLogger;
import org.anc.logging.FakeLoggerFactory;

/**
 * The BaseTranslation class is used to provide alternate language translations
 * for the static text used in an application.
 * <p>
 * Users should extend the BaseTranslation class and define <code>public final
 * String</code> instance fields for each translatable string in the 
 * application. The BaseTranslation class provides functionality for 
 * initializing these fields from a properties file.
 * <p>
 * The fields should be initialized to null and the default values specified 
 * with a <code>@Default</code> annotation on the field. 
 * <p>
 * Derived classes <i>must</i> call the <code>init</code> method in their 
 * constructor to initialize the <code>public final String</code> fields.
 * The <code>BaseTranslation</code> class will look for language files in a
 * directory named <code>i18n</code> in the application's rootdirectory. The
 * language file to use is determined by:
 * <ol>
 * <li>The value of the <code>org.anc.lang</code> Java system property.
 * <li>The current Java locale in use.
 * <li>The default values provided as annotations to the fields.
 * </ol>
 * <p>
 * The <code>BaseTranslation</code> class also provides the ability to save
 * the fields to a Java properties file and can call the Google translate
 * web service to provide simple alternate translations.
 * <b>Example</b>
 * <p>In Messages.java
 * <pre>
 *     package org.anc.example.i18n
 *     class Messages extends BaseTranslation
 *     {
 *         @Default("Hello world.")
 *         public final String HELLO_WORLD = null;
 *         
 *         public Messages()
 *         {
 *             init();
 *         }
 *     }
 * </pre>
 * <p>In MyApp.java
 * <pre>
 *     package org.anc.example
 *     import org.anc.example.i18n.Messages;
 *     public class MyApp
 *     {
 *         public static final MESSAGES = new Messages();
 *         
 *         public static void main(String[] args)
 *         {
 *             System.out.println(MESSAGES.HELLO_WORLD);
 *             MESSAGES.save();
 *             for (String lang : args)
 *             {
 *                // lang should be a two language letter code recognized by
 *                // Google translate.
 *                MESSAGES.translate(lang);
 *             }  
 *         } 
 *     }
 *     
 * </pre>
 * this will generate the file <code>i18n/Messages.en</code> and a Google 
 * translation in each of the specified languages. Languages can also
 * be specified with the two letter ISO country code. For example, to provide
 * translations in German and Swiss German the translation files should be
 * <ul>
 * <li>org.anc.example.i18n.Messages.de
 * <li>org.anc.example.i18n.Messages.de-ch
 * </ul>
 * <b>Note:</b> The name of the translation file is the same as the name of 
 * the class that extends <code>BaseTranslation</code>.
 * 
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
   
   public static final String LANG_PROPERTY = "org.anc.lang";
   public static final String DEFAULT_LANG_LOCATION = "i18n";

   protected static final FakeLogger logger = 
      FakeLoggerFactory.getLogger(FakeLogger.Level.NONE);
   
   /** Locale used by the text strings.  Used as the source language when
    *  translating into other languages. This field will be set by the
    *  {@link #init} method.
    */
   protected Locale locale;
   
   public BaseTranslation()
   {
   }

   public static String complete(String template, String arg1)
   {
//      if (template.indexOf("$1") < 0)
//      {
//         Throwable t = new Exception();
//         logger.error("Invalid template replacement. $1 not found");
//         t.printStackTrace();
//         return template;
//      }
//      return template.replace("$1", arg1);
      return replace(template, "$1", arg1);
   }

   public static String complete(String template, String arg1, String arg2)
   {
//      if (template.indexOf("$1") < 0)
//      {
//         Throwable t = new Exception();
//         logger.error("Invalid template replacement. $1 not found");
//         t.printStackTrace();
//         return template;
//      }
//      String result = template.replace("$1", arg1);
//      if (template.indexOf("$2") < 0)
//      {
//         Throwable t = new Exception();
//         logger.error("Invalid template replacement. $2 not found");
//         t.printStackTrace();
//         return result;
//      }
//      return result.replace("$2", arg2);
      String result = replace(template, "$1", arg1);
      return replace(result, "$2", arg2);
   }

   public static String complete(String template, String[] args)
   {
      String result = template;
      int i = 0;
      for (String arg : args)
      {
         ++i;
         String pattern = "$" + i;
         result = replace(result, pattern, arg);
      }
      return result;
   }
   
   public static String replace(String template, String pattern, String replacement)
   {
      if (template.indexOf(pattern) < 0)
      {
         Throwable t = new Exception();
         logger.error("Invalid template replacement. " + pattern + " not found");
         t.printStackTrace();
         return template;
      }
      return template.replace(pattern, replacement);
   }
   
   public static void translate(BaseTranslation[] classes, String lang) throws InstantiationException, IllegalAccessException, FileNotFoundException, UnsupportedEncodingException
   {
      for (BaseTranslation msgClass : classes)
      {
         msgClass.translate(lang);
      }
   }
   
   public void translate(String lang) throws InstantiationException, IllegalAccessException, FileNotFoundException, UnsupportedEncodingException
   {
      File directory = new File(DEFAULT_LANG_LOCATION);
      if (!directory.exists())
      {
         if (!directory.mkdirs())
         {
            logger.error("Unable to create the directory {}",  directory.getPath());
            return;
         }
      }
//      Class<? extends BaseTranslation> msgClass = this.getClass();
//      String className = BaseTranslation.getClassName(msgClass);
      String className = this.getClass().getName();
      File file = new File(directory, className + "." + lang);
      if (file.exists())
      {
         logger.info("Skipping {}. Translation already exists.", lang);
         return;
      }
//      BaseTranslation messages = msgClass.newInstance();
      Writer writer = new UTF8Writer(file);
      translate(writer, lang);
   }
   
//   public static void save(Class<? extends BaseTranslation>[] classes) throws InstantiationException, IllegalAccessException, IOException
//   {
//      for (Class<? extends BaseTranslation> theClass : classes)
//      {
//         save(theClass);
//      }   
//   }
   
   public void save() throws InstantiationException, IllegalAccessException, IOException
   {
      save("en");
   }
   
   public void save(String lang)
         throws InstantiationException, IllegalAccessException, IOException
   {
      File directory = new File(DEFAULT_LANG_LOCATION);
      if (!directory.exists())
      {
         if (!directory.mkdirs())
         {
            logger.error("Unable to create the directory {}", directory.getPath());
            return;
         }
      }
      String className = BaseTranslation.getClassName(this.getClass());
      File file = new File(directory, className + "." + lang); // $NON-NLS-1$
      if (file.exists())
      {
         logger.info("Default language file already exists.");
         return;
      }
      logger.info("Saving messages for {}", className);
      logger.info("Saving to {}", file.getPath());
//      BaseTranslation messages = msgClass.newInstance();
      write(file);
   }

   public void saveAndTranslate(BaseTranslation[] instances, String[] langs) throws InstantiationException, IllegalAccessException, IOException
   {
      for (BaseTranslation instance : instances)
      {
         instance.save();
         for (String lang : langs)
         {
            instance.translate(lang);
         }
      }
   }
   
   public void write(File file) throws IOException
   {
      write(new FileOutputStream(file), "Default translation."); // $NON-NLS-1$ 
   }

   public void write(OutputStream out)
   {
      write(out, "Default translation"); // $NON-NLS-1$      
   }
   
   public void write(OutputStream out, String comment)
   {
      Properties props = new Properties();
      try
      {
         Class<? extends BaseTranslation> theClass = this.getClass();
         Field[] fields = theClass.getFields();
         for (Field field : fields)
         {
            String name = field.getName();
            String value = (String) field.get(this);   
            System.out.println(name + " = " + value);
            props.setProperty(name, value);
         }
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

   public void translate(Writer out, String lang)
   {
      String language = LocaleUtils.getLanguage(lang);
      // TODO We should use Google translate to translate the following
      // string!
      translate(out, lang, "Automatic translation for " + language);
   }
   
   public void translate(Writer out, String lang, String comment)
   {
      Class<? extends BaseTranslation> msgClass = this.getClass();
      logger.debug("Translating {} ", msgClass.getName());
      logger.debug(comment);
      Properties props = new Properties();
      try
      {
//         Class<? extends BaseTranslation> theClass = this.getClass();
         Field[] fields = msgClass.getFields();
         for (Field field : fields)
         {
            String name = field.getName();
            String value = (String) field.get(this);   
            String translation = value;
            try
            {
               String sourceLang = locale.getLanguage();
               translation = Translator.translate(value, sourceLang, lang);
            }
            catch (Exception ex)
            {
               // This is not allowed to fail; we have to recover gracefully.
               // The translation variable will still be set to the default
               // value and that is graceful enough so simply log the error.
               logger.error("Error translating {}", value);
            }
            props.setProperty(name, translation);
         }
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

//   protected void init(Class<? extends BaseTranslation> subclass)
   protected void init()
   {
      init(Locale.US);
   }
   
   protected void init(Locale locale)
   {
      init(locale, false);
   }
   
   protected void init(Locale locale, boolean translate)
   {
      this.locale = locale;
      
      Class<? extends BaseTranslation> subclass = this.getClass();
      String className = getClassName(subclass);
      logger.debug("Initializing " + className);
      Properties translation = new Properties();

      // This flag will be set if a language file can't be found for the
      // local language. When set the @Default string values will be 
      // translated to the local language using the Google translate web 
      // service and saved to disk for future use.
//      boolean translate = false;
      
      // If the translation (if any) was successful this flag will be set
      // and the translated file will be saved.
      boolean save = false;
      
      // First check if the system property has been set manually.  This will
      // override all other methods of determining the language file to use.
      String langProperty = System.getProperty(LANG_PROPERTY);
      
      // If we need to translate the messages then the LANG_PROPERTY setting
      // will take precedence.
      String lang = langProperty;
      
      if (lang == null)
      {
         logger.debug("org.anc.lang property not set.");
      }
      else
      {
         logger.debug("org.anc.lang property is {}", lang);
      }
      if (!loadLanguage(langProperty, className, translation))
      {
         // That didn't work, so check for a language file specified by country
         // and language code.
         logger.debug("Could not load language from org.anc.lang property.");
         Locale defaultLocale = Locale.getDefault();
         String country = defaultLocale.getCountry();
         lang = defaultLocale.getLanguage();
         if (country == null || "".equals(country)|| !loadLanguage(lang + "-" + country, className, translation))
         {
            // That didn't work either, try just the language code. If this
            // doesn't work the default values will be used.
            if (country == null || "".equals(country))
            {
               logger.debug("Country is null for default Locale");
            }
            else
            {
               logger.debug("Could not load language file country {}", country);
            }
            if (!loadLanguage(lang, className, translation))
            {
               logger.debug("Could not load language file for {}", lang);
               // translate = !defaultLocale.equals(locale);
               if (langProperty != null)
               {
                  // If the LANG_PROPERTY was set then that should
                  // be the target language.
                  lang = langProperty;
               }
               
               // We only need to translate the messages if the locale of the
               // messages is not the same as the desired locale.
               translate = translate && !lang.equals(locale.getLanguage());
               if (translate)
               {
                  logger.debug("Messages need translation to {}", lang);
               }
               else
               {
                  logger.debug("No translation requried.");
               }
            }
         }
      }
            
      // Now that we have figured out the language we can initialize the
      // fields.
      Field[] fields = subclass.getDeclaredFields();
      for (Field field : fields)
      {
         int modifiers = field.getModifiers();
         // We only set non-static public final String fields.
         if (field.getType().equals(String.class) && 
               Modifier.isPublic(modifiers) &&
               Modifier.isFinal(modifiers) && 
               !Modifier.isStatic(modifiers))
         {
            // Not sure if this is needed any more, but it is a final field.
            field.setAccessible(true);
            
            // Try to get the field value from the property file. If that
            // does not work then use the value of the @Default annotation.
            String value = translation.getProperty(field.getName());
            if (value == null)
            {
               Default defaultValue = field.getAnnotation(Default.class);
               if (defaultValue != null)
               {
                  value = defaultValue.value();
               }
               else
               {
                  // If the defaultValue is null then there is a programming
                  // error and someone forgot the @Default annotation on a 
                  // field. Programming errors like this should be caught early 
                  // and thrown as hard as possible.
                  throw new RuntimeException(
                        "Missing a @Default annotation on " + field.getName());
               }
            }

            // Check if we need to translate the string into the local language.
            if (translate)
            {
               logger.debug("Translating to " + lang);
               try
               {
                  value = Translator.translate(value, locale.getLanguage(), lang);
                  save = true;
               }
               catch (Exception e)
               {
                  // This translation attempt failed so don't bother
                  // trying any others.
                  logger.error(e.getLocalizedMessage());
                  translate = false;
                  
                  // Don't save potentially buggy translations either.
                  save = false;
               }
            }

            // Now try setting the field. Log any errors but we need to 
            // continue.
            try
            {
               logger.debug("Setting {} ", field.getName());
               logger.debug("To value {}", value);
               field.set(this, value);
            }
            catch (IllegalArgumentException e)
            {
               logger.error(e.getLocalizedMessage());
            }
            catch (IllegalAccessException e)
            {
               logger.error(e.getLocalizedMessage());
            }
         }
      }
      if (save)
      {
         // A translation was needed and completed successfully. Save the
         // language file so it does not need to be retranslated.
         try
         {
            save(lang);
         }
         catch (Exception e)
         {
            // Quietly ignore everything. At least the translation worked.
         }
      }
   }

   protected static boolean loadLanguage(String lang, String className, 
         Properties props)
   {
      if (lang == null)
      {
         return false;
      }
      
      File file = new File(DEFAULT_LANG_LOCATION + "/" 
            + className + "." + lang);  // $NON-NLS-1$ // $NON-NLS-2$
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
//      FileInputStream out = null;
      Reader reader = new UTF8Reader(propFile);
      try
      {
//         out = new FileInputStream(propFile);
         props.load(reader);
      }
      finally
      {
         if (reader != null)
         {
            reader.close();
         }
      }
}

   protected static String getClassName(Class<?> theClass)
   {
//      String name = theClass.getName();
//      int lastDot = name.lastIndexOf('.');
//      if (lastDot > 0)
//      {
//         return name.substring(lastDot + 1);
//      }
//      return name;
      return theClass.getName();
   }
   
   private static void debug(String message)
   {
      System.out.println(message);
   }
}

