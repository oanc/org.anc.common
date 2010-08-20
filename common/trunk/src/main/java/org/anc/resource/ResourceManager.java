package org.anc.resource;

import java.io.*;
import java.util.*;

/**
 * Try to provide a RAII (Resource Acquisition Is Initialization) mechanism for
 * Java. Instances of ResourceManager can be used to ensure a group of resources
 * are all closed when one, or more, of them throws an exception. Calling the
 * {@link ResourceManager#close()} method ensures that all managed resources
 * will have their <code>close()</code> method called. Any exceptions thrown by
 * resources are caught, wrapped in a {@link ResourceManagerCheckedException} or
 * a {@link ResourceManagerRuntimeException} and re-thrown only after all
 * resources have been closed.
 * <p>
 * When throwing exceptions runtime exceptions take precedence and a
 * ResourceManagerRuntimeException will be thrown even if some resources threw
 * checked exceptions. If any resources did throw checked exceptions they will
 * be available with
 * {@link ResourceManagerRuntimeException#getCheckedExceptions()}.
 * <p>
 * There are three flavors of the close method:
 * <ol>
 * <li>{@link #close()} : will re-throw runtime and checked exceptions.
 * <li>{@link #closeQuiety()} : will discard any checked exceptions and re-throw
 * runtime exceptions.
 * <li>{@link #closeAbruptly()} : ignores all exceptions.
 * </ol>
 * All of the <code>close*()</code> methods catch any exceptions thrown and
 * only propagate the exceptions after all resources have had their
 * <code>close()</code> methods called. 
 * <p>
 * <b>EXAMPLE</b>
 * 
 * <pre>
 * ResourceManager manager = new ResourceManager();
 * try
 * {
 *    InputStream istream = manager.add(new FileInputStream(...));
 *    OutputStream ostream = manager.add(new FileOutputStream(...));
 *    Closeable dbConnection = manager.add(openDatabaseConnection());
 *    ...
 * }
 * finally
 * {
 *    manager.closeQuietly();
 * }
 * </pre>
 * 
 * @author Keith Suderman
 * @since 2.2.0
 * @param <T> a resource type that implements the 
 * {@link java.io.Closeable Closeable} interface.
 * @see <a href="http://en.wikipedia.org/wiki/Resource_Acquisition_Is_Initialization">Resource Acquisition Is Initialization</a>
 */
public class ResourceManager<T extends Closeable>
{
   protected LinkedList<T> resources = new LinkedList<T>();
   
   /** Add a resource to the resource manager. */
   public T add(T resource)
   {
      if (resource == null)
      {
         return null;
      }
      resources.addFirst(resource);
      return resource;
   }
   
   
   public void close() throws ResourceManagerCheckedException
   {
      // Checked exceptions thrown during the close operations.
      ResourceManagerCheckedException exception = null;
      // Runtime exceptions thrown during the close operations.
      ResourceManagerRuntimeException runtime = null;
      
      // Attempt to close each resource. Exceptions are caught and re-thrown
      // after all resources have been closed.
      for (T resource : resources)
      {
         try
         {
            resource.close();
         }
         catch (RuntimeException e)
         {
            if (runtime == null)
            {
               runtime = new ResourceManagerRuntimeException(resource, e);
            }
            else
            {
               runtime = new ResourceManagerRuntimeException(resource, e, runtime);
            }
         }
         catch (Exception e)
         {
            if (exception == null)
            {
               exception = new ResourceManagerCheckedException(resource, e);
            }
            else
            {
               exception = new ResourceManagerCheckedException(resource, e, exception);
            }
         }
      }
      
      // Runtime exceptions are considered to be more severe and take precedence
      // over checked exceptions.
      if (runtime != null)
      {
         // But we don't want to lose the checked exceptions so we include
         // them as well for the user to examine if needed.
         if (exception != null)
         {
            runtime.addCheckedExceptions(exception);
         }
         throw runtime;
      }
      
      
      if (exception != null)
      {
         throw exception;
      }
   }

   /** Close all resources. All exceptions are caught here. Checked exceptions
    *  are ignored and runtime exceptions are propagated only after all 
    *  resources have been closed.
    */
   public void closeQuietly()
   {
      ResourceManagerRuntimeException exception = null;
      for (T resource : resources)
      {
         try
         {
            resource.close();
         }
         catch (RuntimeException e)
         {
            if (exception != null)
            {
               exception = new ResourceManagerRuntimeException(resource, e, exception);
            }
            else
            {
               exception = new ResourceManagerRuntimeException(resource, e);
            }
         }
         catch (Exception e)
         {
            // Ignore
         }
      }
      if (exception != null)
      {
         throw exception;
      }
   }
   
   /**
    * All resources are closed and all exceptions are ignored.
    */
   public void closeAbruptly()
   {
      for (T resource : resources)
      {
         try
         {
            resource.close();
         }
         catch(Exception e)
         {
            // Ignore.
         }
      }
   }
}

