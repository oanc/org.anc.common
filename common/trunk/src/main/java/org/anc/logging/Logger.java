package org.anc.logging;

/**
 * This is a simple, lightweight wrapper for the java.util.logging.Logger API.
 * Logger instances are created by the LoggerFactory class (hence the private
 * constructor).
 * 
 * @author Keith Suderman
 *
 */
public class Logger
{
   private java.util.logging.Logger logger = null;
   
   private Logger(java.util.logging.Logger logger)
   {
      this.logger = logger;
   }
   
   public static Logger getLogger(Class<?> aClass)
   {
      return getLogger(aClass.getName());
   }
   
   public static Logger getLogger(String name)
   {
      java.util.logging.Logger jLogger = java.util.logging.Logger.getLogger(name);
      return new Logger(jLogger);
      
   }
   
   public void trace(String message)
   {
      logger.finer(message);
   }
   
   public void debug(String message)
   {
      logger.fine(message);
   }
   
   public void log(String message)
   {
      logger.info(message);
   }
   
   public void info(String message)
   {
      logger.info(message);
   }
   
   public void warn(String message)
   {
      logger.warning(message);
   }
   
   public void error(String message)
   {
      logger.severe(message);
   }
}
