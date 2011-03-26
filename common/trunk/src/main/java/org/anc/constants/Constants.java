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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Properties;

/**
 * The base class used for declaring project wide constant values.
 * 
 * <p>Each class that extends Constants defines <tt>public final</tt> fields that will
 * initialized with values from a properties file. The initial, or default, values to use
 * if the properties file does not exist as specified with @Default annotations on each 
 * field.
 * <pre>
 * package org.anc.example;
 * public class MyConstants extends Constants
 * {
 *    @Default("Hello world.")
 *    public final String HELLO_WORLD = null;
 *    
 *    public MyConstants()
 *    {
 *       super.init();
 *    }
 * }
 * </pre>
 * <p>Each public field in the subclass should be initialized to null and the subclass
 * constructor(s) must call <tt>super.init()</tt>. An instance of the subclass can then 
 * be created (usually as a static final field of the application object) to access the 
 * defined constants.
 * <pre>
 * public class Application
 * {
 *    public static final Constants CONST = new Constants();
 *    
 *    public void run()
 *    {
 *       System.out.println(CONST.HELLO_WORLD);
 *    }
 * }
 * </pre>
 * <p>The Constants class allows projects to "hard-code" constant values, but still permit
 * the values to be initialized in a system dependent way without changing any source
 * code.
 * <p>The Constants' <tt>init</tt> method will try to load the properties file from the 
 * class path using the name "conf/machineName/subClassName.properties", where "machineName"
 * is the name returned by <tt>System.getenv("HOSTNAME")</tt> or <tt>System.getenv("COMPUTERNAME")</tt>.
 * For example, if the above MyConstants class is used on the machine SCOTTY the class will 
 * be initialized with values from
 * <tt>conf/scotty/org.anc.example.MyConstants.properties</tt>.
 * 
 * @author Keith Suderman
 *
 */
public abstract class Constants
{
   private static final long serialVersionUID = 1L;

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
         if (isPublicFinalString(field)) 
         {
            String name = field.getName();
            String value = null;
            try
            {
               value = field.get(this).toString();
               props.put(name, value);
            }
            catch (Exception e)
            {
               // Ignore.
            } 
         }
         else if (isPublicFinalInt(field))
         {
            String name = field.getName();
            try
            {
               int value = field.getInt(this);
               props.put(name, Integer.toString(value));
            }
            catch (Exception e)
            {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
      OutputStream os = new FileOutputStream(file);
      try
      {
         props.store(os, "Executor constants.");
      }
      catch (IOException e)
      {
         
      }
   }
   
   protected String getName()
   {
      Class<? extends Constants> subclass = this.getClass();     
      String name = System.getenv("COMPUTERNAME");
      if (name == null)
      {
         name = System.getenv("HOSTNAME");
      }
      if (name == null)
      {
         name = "default";
      }
      return "conf/" + name.toLowerCase() + "/" + subclass.getName() + ".properties";
   }
   
   
   protected void init()
   {
      String name = getName();
      init(name);
   }
   
   protected void init(String name)
   {
      Properties props = new Properties();
      InputStream is = ClassLoader.getSystemResourceAsStream(name);
      if (is != null)
      {
         try
         {
            props.load(is);
         }
         catch (IOException e)
         {
            // Silently ignore.
         }
      }
      Class<? extends Constants> subclass = this.getClass();
      Field[] fields = subclass.getDeclaredFields();
      for (Field field : fields)
      {
         if (isPublicFinalString(field))
         {
            String value = props.getProperty(field.getName());
            if (value == null)
            {
               Default defaultValue = field.getAnnotation(Default.class);
               if (defaultValue == null)
               {
                  throw new RuntimeException("Mission @Default annotation on "
                        + field.getName());
               }
               value = defaultValue.value();
            }
            set(field, value);
         }
         else if (isPublicFinalInt(field))
         {
            String sValue = props.getProperty(field.getName());
            if (sValue == null)
            {
               Default defaultValue = field.getAnnotation(Default.class);
               if (defaultValue == null)
               {
                  throw new RuntimeException("Mission @Default annotation on "
                        + field.getName());
               }
//               System.out.println("Using default value for " + field.getName());
               sValue = defaultValue.value();
            }
            set(field, Integer.valueOf(sValue));
         }
         
      }
   }

   private void set(Field field, String value)
   {
      try
      {
         field.setAccessible(true);
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

   private void set(Field field, int value)
   {
      try
      {
         field.setAccessible(true);
         field.set(this, value);
//         System.out.println("Set " + field.getName() + " to " + value);
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

   private static boolean isPublicFinalString(Field field)
   {
      int flags = field.getModifiers();
      return field.getType().equals(String.class) && Modifier.isPublic(flags)
            && Modifier.isFinal(flags) && !Modifier.isStatic(flags);
   }

   private static boolean isPublicFinalInt(Field field)
   {
      int flags = field.getModifiers();
      return field.getType().equals(Integer.class) && Modifier.isPublic(flags)
            && Modifier.isFinal(flags) && !Modifier.isStatic(flags);
   }
}
