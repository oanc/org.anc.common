/*-
 * Copyright 2011 The American National Corpus
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
package org.anc.constants;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The base class used for declaring project wide constant values.
 * 
 * <p>Each sub-class defines {@code public final} fields that will be
 * initialized with values from a properties file. The default values to use
 * if the properties file does not exist are specified with &#064;Default annotations on each 
 * field.
 * <pre>
 * package org.anc.example;
 * public class MyConstants extends Constants
 * {
 *    &#064;Default("Hello world.")
 *    public final String HELLO_WORLD = null;
 *    
 *    public MyConstants()
 *    {
 *       super.init();
 *    }
 * }
 * </pre>
 * <p>Each public field in the subclass must be initialized to null and the subclass
 * constructor(s) must call {@code super.init()}. An instance of the subclass can then 
 * be created (usually as a static final field of the application object) to access the 
 * defined constants.
 * <pre>
 * public class Application
 * {
 *    public static final MyConstants CONST = new MyConstants();
 *    
 *    public void run()
 *    {
 *       System.out.println(CONST.HELLO_WORLD);
 *    }
 * }
 * </pre>
 * <p>
 * If no properties file is specified the {@code Constants} class will look for the 
 * resource {@code /conf/machineName/className.properties} on the classpath; where
 * {@code machineName} is the value of the environment variable COMPUTERNAME (Windows) or
 * HOSTNAME (OS X/Linux) and {@code className} is the fully qualified name of the Java
 * class that extends {@code Constants}, ie {@code org.anc.example.MyConstants.properties}.
 * <p>
 * A sub-class can also specify the properties file to use with the {@code init(String)} 
 * method. The String parameter passed to the {@code init} method should be the name of a 
 * Java system property or an OS environmental variable. The {@code Constants} class 
 * will first try {@code System.getProperty} and then {@code System.getenv} to obtain 
 * a file name. If neither property has been set the above method is used to locate
 * the properties file. For example,
 * <pre>
 * // In MyConstants.java
 * package org.anc.example;
 * public class MyConstants extends Constants
 *    {
 *       &#064;Default("Hello world")
 *       public final String HELLO_WORLD = null;
 *       
 *       public MyConstants()
 *       {
 *          super.init("org.anc.hello");
 *       }
 *       
 *       public static void main(String[] args)
 *       {
 *          MyConstants constants = new MyConstants();
 *          System.out.println(constants.HELLO_WORLD);
 *       }
 *    }
 * 
 * # In /home/anc/hello.properties
 * HELLO_WORLD=Bonjour le monde.
 * 
 * # From the command line:
 * > java -cp MyConstants.jar -Dorg.anc.hello=/home/anc/hello.properties org.anc.example.MyConstants
 * > Bonjour le monde
 * </pre>
 * 
 * 
 * @author Keith Suderman
 *
 */
public abstract class Constants
{
   private static final long serialVersionUID = 1L;

   /**
    * Symbol table used to resolve variable names during initialization.
    * After initialization is complete this should be set back to null to
    * release the memory.
    */
   private Map<String, String> variables = null;
   
   /**
    * Annotation used to provide a default value for constants.
    * 
    * @author Keith Suderman
    */
   @Documented
   @Target(ElementType.FIELD)
   @Retention(RetentionPolicy.RUNTIME)
   public @interface Default
   {
      String value();
   }

   public void save() throws FileNotFoundException
   {
      String name = getName();
      File file = new File(name);
      File parent = file.getParentFile();
      if (!parent.exists())
      {
         if (!parent.mkdirs())
         {
            throw new FileNotFoundException("Unable to create " + parent.getPath());
         }
      }
      save(file);
   }
   
   public void save(String path) throws FileNotFoundException
   {
      save(new File(path));
   }
   
   public void save(File file) throws FileNotFoundException
   {
      Properties props = new Properties();
      Class<? extends Constants> subclass = this.getClass();
      for (Field field : subclass.getDeclaredFields())
      {
         String name = field.getName();
         if (isPublicFinalInt(field))
         {
            try
            {
               int value = field.getInt(this);
               props.put(name, Integer.toString(value));
            }
            catch (Exception e)
            {
               // Ignore
            }
         }
         else if (isPublicFinalString(field) || 
               isPublicFinalInteger(field) || 
               isPublicFinalFloat(field) || 
               isPublicFinalDouble(field))
         {
            try
            {
               props.put(name, field.get(this).toString());
            }
            catch (Exception e)
            {
               // Ignore.
            }
         }
      }
      OutputStream os = new FileOutputStream(file);
      try
      {
         props.store(os, "Constants.");
      }
      catch (IOException e)
      {
         // TODO : this should be logged.
      }
   }
   
   protected Properties getProperties(String propName) throws FileNotFoundException, IOException
   {
      Properties props = new Properties();
      String propValue = null;
      if (propName != null)
      {
         propValue = System.getProperty(propName);
         if (propValue != null)
         {
            props.load(new FileReader(propValue));
            return props;
         }
         
         propValue = System.getenv(propName);
         if (propValue != null)
         {
            props.load(new FileReader(propValue));
            return props;
         }
      }      
      propValue = getName();
      InputStream in = null;
      // First try to find the properties file on the file system.
      File propFile = new File(propValue);
      if (propFile.exists())
      {
         in = new FileInputStream(propFile);
      }
      
      if (in == null)
      {
         in = ClassLoader.getSystemResourceAsStream(propValue);
         if (in == null)
         {
            throw new FileNotFoundException("Properties " + propValue + " not found.");
         }
      }
      // 'in' can not be null or an exception would have been thrown above.
      props.load(in);
      return props;
   }
   
   protected String getName()
   {      
      Class<? extends Constants> subclass = this.getClass();     
      String name = null;
      if (name == null)
      {
    	  name = System.getenv("COMPUTERNAME");
      }
      if (name == null)
      {
         name = System.getenv("HOSTNAME");
      }
      if (name == null)
      {
         name = System.getProperty("user.name");
      }
      if (name == null)
      {
         name = "constants";
      }
      return "conf/" + name.toLowerCase() + "/" + subclass.getName() + ".properties";
   }
   
   protected void init()
   {
      init(null);
   }
   
   protected void init(String propertyName)
   {
      variables = new HashMap<String, String>();
      Properties props;
      try
      {
         props = getProperties(propertyName);
      }
      catch (Exception e)
      {
         // Ignore exceptions and use the default values.
//         e.printStackTrace();
         props = new Properties();
      }

      Class<? extends Constants> subclass = this.getClass();
      Field[] fields = subclass.getDeclaredFields();
      for (Field field : fields)
      {
         String value = getInitValue(props, field);
         if (isPublicFinalString(field))
         {
            set(field, value);
         }
         else if (isPublicFinalInt(field))
         {
            set(field, Integer.valueOf(value));
         }
         else if (isPublicFinalInteger(field))
         {
            set(field, new Integer(value));
         }
         else if (isPublicFinalFloat(field))
         {
            set(field, new Float(value));
         }
         else if (isPublicFinalDouble(field))
         {
            set(field, new Double(value));
         }
      }
      variables = null;
   }

   private String getInitValue(Properties props, Field field)
   {
      String sValue = props.getProperty(field.getName());
      if (sValue == null)
      {
         Default defaultValue = field.getAnnotation(Default.class);
         if (defaultValue == null)
         {
            // This is definitely a programming error.
            throw new RuntimeException("Missing @Default annotation on "
                  + field.getName());
         }
         sValue = defaultValue.value();
      }
      return replaceVariables(sValue);
   }
   
   private String replaceVariables(String input)
   {
      int index = input.indexOf('$');
      while (index >= 0)
      {
         int end = input.indexOf('/', index);
         if (end < index)
         {
            end = input.length();
         }
         String key = input.substring(index + 1, end);
         String value = variables.get(key);
         if (value != null)
         {
            String prefix = input.substring(0, index);
            input = prefix + value + input.substring(end);
            index = input.indexOf('$', prefix.length());
         }
         else
         {
            index = input.indexOf('$', end);
         }
      }
      return input;
   }
   
   private void set(Field field, Object value)
   {
      try
      {
         field.setAccessible(true);
         field.set(this, value);
         if (value instanceof String)
         {
            variables.put(field.getName(), value.toString());
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

//   private void set(Field field, Integer value)
//   {
//      try
//      {
//         field.setAccessible(true);
//         field.set(this, value);
//      }
//      catch (IllegalArgumentException e)
//      {
//         e.printStackTrace();
//      }
//      catch (IllegalAccessException e)
//      {
//         e.printStackTrace();
//      }
//   }
   
//   private void set(Field field, int value)
//   {
//      try
//      {
//         field.setAccessible(true);
//         field.set(this, value);
//         System.out.println("Set " + field.getName() + " to " + field.getInt(this));
//      }
//      catch (IllegalArgumentException e)
//      {
//         e.printStackTrace();
//      }
//      catch (IllegalAccessException e)
//      {
//         e.printStackTrace();
//      }
//   }

   protected static boolean isPublicFinalString(Field field)
   {
      return isType(String.class, field);
   }

   protected static boolean isPublicFinalInteger(Field field)
   {
      return isType(Integer.class, field);
   }

   protected static boolean isPublicFinalDouble(Field field)
   {
      return isType(Double.class, field);
   }
   
   protected static boolean isPublicFinalFloat(Field field)
   {
      return isType(Float.class, field);
   }
   
   protected static boolean isPublicFinalInt(Field field)
   {
      return isType(int.class, field);
   }
   
   private static boolean isType(Class<?> theClass, Field field)
   {
      int flags = field.getModifiers();
      return field.getType().equals(theClass) && Modifier.isPublic(flags)
            && Modifier.isFinal(flags) && !Modifier.isStatic(flags);
   }
}
