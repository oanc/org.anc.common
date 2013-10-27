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
package org.anc.ssl;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509Certificate;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;

public class ANCSSL
{
   public static void install(String username, String password)
   {
      ANCPasswordAuthenticator.install(username, password);
      ANCProvider.install();
   }
}

class ANCPasswordAuthenticator extends Authenticator
{
//   private String username;
//   private char[] password;
   private PasswordAuthentication authentication;

   private ANCPasswordAuthenticator(String username, String password)
   {
//      this.username = username;
//      this.password = password.toCharArray();
      authentication = new PasswordAuthentication(username, password.toCharArray());
   }

   /**
    * @see java.net.Authenticator#getPasswordAuthentication()
    */
   @Override
   protected PasswordAuthentication getPasswordAuthentication()
   {
//      return new PasswordAuthentication(username, password);
      return authentication;
   }

   public static void install(String username, String password)
   {
      Authenticator
            .setDefault(new ANCPasswordAuthenticator(username, password));
   }
}

/*
 * The following code disables certificate checking. Use the
 * Security.addProvider and Security.setProperty calls to enable it
 */
class ANCProvider extends Provider
{
   public ANCProvider()
   {
      super("ANCProvider", 1.0, "Trust certificates");
      put("TrustManagerFactory.TrustAllCertificates",
            MyTrustManagerFactory.class.getName());
   }

   public static void install()
   {
      Security.addProvider(new ANCProvider());
      Security.setProperty("ssl.TrustManagerFactory.algorithm",
            "TrustAllCertificates");
   }

   protected static class MyTrustManagerFactory extends TrustManagerFactorySpi
   {
      public MyTrustManagerFactory()
      {
      }

      @Override
      protected void engineInit(KeyStore keystore)
      {
      }

      @Override
      protected void engineInit(ManagerFactoryParameters mgrparams)
      {
      }

      @Override
      protected TrustManager[] engineGetTrustManagers()
      {
         return new TrustManager[] { new MyX509TrustManager() };
      }
   }

   protected static class MyX509TrustManager implements X509TrustManager
   {
      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType)
      {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType)
      {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers()
      {
         return null;
      }
   }
}
