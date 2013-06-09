package org.anc.resource;

import java.io.Closeable;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A wrapper for runtime exceptions caught by a {@link ResourceManager}. 
 * Instances store references to the resource that threw the exception and the
 * exception that was thrown. Multiple exceptions caught by the <code>ResourceManager</code>
 * are linked together with the {@link #next} field.
 * <p>
 * If the <code>ResourceManager</code> also caught any checked exceptions they
 * will be accessible with the {@link #getCheckedExceptions()} method.
 * 
 * @author Keith Suderman
 * @since 2.2.0
 */
public class ResourceManagerRuntimeException extends RuntimeException
{
   /** The resource that threw the exception. */
   protected Closeable resource;
   /** The exception that was thrown. */
   protected RuntimeException ex;
   /** The next exception in the chain if more than one resource threw an
    *  exception.
    */
   protected RuntimeException next;
   /** A list of checked exceptions that were also thrown by a managed 
    *  resource.
    */
   protected ResourceManagerCheckedException checked = null;
   
   public ResourceManagerRuntimeException(Closeable resource, RuntimeException ex)
   {
      this(resource, ex, null);
   }

   public ResourceManagerRuntimeException(Closeable resource, RuntimeException ex, RuntimeException next)
   {
      this.resource = resource; 
      this.ex = ex;
      this.next = next;
   }
   
   public void addCheckedExceptions(ResourceManagerCheckedException e)
   {
      checked = e;
   }
   
   /** Returns the resource that threw the exception. */
   public Closeable getResource() { return resource; }
   /** Returns the exception that was thrown. */
   public RuntimeException getException() { return ex; }
   /** Returns the next exception in the chain, or null if there is no
    *  such exception.
    */
   public RuntimeException getNextException() { return next; }

   /**
    * Returns any checked exceptions that were also caught by the
    * ResourceManager.
    */
   public ResourceManagerCheckedException getCheckedExceptions() { return checked; }
   
   /** Prints a stack trace to System.err. */
   @Override
   public void printStackTrace()
   {
      printStackTrace(System.err);
   }
   
   @Override 
   public void printStackTrace(PrintStream out)
   {
      out.println("Runtime Exceptions");
      ex.printStackTrace(out);
      if (next != null)
      {
         next.printStackTrace(out);
      }
      out.println();
      out.println("Checked Exceptions");
      checked.printStackTrace(out);
   }
   
   @Override
   public void printStackTrace(PrintWriter out)
   {
      out.println("Runtime Exceptions");
      ex.printStackTrace(out);
      if (next != null)
      {
         next.printStackTrace(out);
      }
      out.println();
      out.println("Checked Exceptions");
      checked.printStackTrace(out);
   }
}