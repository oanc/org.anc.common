package org.anc.resource;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A wrapper for checked exceptions caught by a {@link ResourceManager}. 
 * Instances store references to the resource that threw the exception and the
 * exception that was thrown. Multiple exceptions caught by the <code>ResourceManager</code>
 * are linked together with the {@link #next} field.
 * 
 * @author Keith Suderman
 * @since 2.2.0
 */

public class ResourceManagerCheckedException extends Exception
{
   /** The resource that threw the exception. */
   protected Object resource;
   /** The exception that was thrown. */
   protected Exception ex;
   /** The next exception in the chain if the ResourceManger caught more than
    *  one exception. 
    */
   protected Exception next;
   
   public ResourceManagerCheckedException(Object resource, Exception ex)
   {
      this(resource, ex, null);
   }

   public ResourceManagerCheckedException(Object resource, Exception ex, Exception next)
   {
      this.resource = resource;
      this.ex = ex;
      this.next = next;
   }
   
   /** Returns the resource that threw the exception. */
   public Object getResource() { return resource; }
   /** Returns the exception that was thrown. */
   public Exception getException() { return ex; }
   /** Returns the next excpetion in the chain, or null if there is no 
    *  such exception.
    */
   public Exception getNextException() { return next; }
   
   @Override
   public void printStackTrace()
   {
      printStackTrace(System.err);
   }
   
   @Override 
   public void printStackTrace(PrintStream out)
   {
      ex.printStackTrace(out);
      if (next != null)
      {
         next.printStackTrace(out);
      }
   }
   
   @Override
   public void printStackTrace(PrintWriter out)
   {
      ex.printStackTrace(out);
      if (next != null)
      {
         next.printStackTrace(out);
      }
   }   
}

