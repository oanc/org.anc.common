package org.anc.common;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{
   /*
    * (non-Javadoc)
    * 
    * @see
    * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
    */
   @Override
   public void start(BundleContext context) throws Exception
   {
      System.out.println("Started ANC Commons Service.");
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
    */
   @Override
   public void stop(BundleContext context) throws Exception
   {
      System.out.println("Stopped ANC Commons Service.");
   }

}
