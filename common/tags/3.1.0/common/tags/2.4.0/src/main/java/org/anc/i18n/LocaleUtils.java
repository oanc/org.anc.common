/*-
 * Copyright 2010 The American National Corpus
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class LocaleUtils
{
   /** Maps two letter ISO codes to the java.util.Locale. */
   protected static final Map<String, Locale> localeMap = new HashMap<String, Locale>();
   
   // Initialize the above map with available locales. This should be made
   // less JVM dependent.
   static 
   {
      Locale[] locales = Locale.getAvailableLocales();
      for (Locale locale : locales)
      {
         String code = locale.getCountry().toLowerCase();
         localeMap.put(code, locale);
      }
      // Google used "ja" for Japanese instead of "jp" which is what
      // java.util.Local.JAPAN.getCountry returns.
      localeMap.put("ja", Locale.JAPAN);
   }
   
   public static Locale getLocaleForCode(String isocode)
   {
      return localeMap.get(isocode);
   }
   
   public static String getLanguage(String isocode)
   {
      Locale locale = localeMap.get(isocode);
      if (locale == null)
      {
         return isocode;
      }
      return locale.getDisplayLanguage();
   }
   
   public static String getCountry(String isocode)
   {
      Locale locale = localeMap.get(isocode);
      if (locale == null)
      {
         return isocode;
      }
      return locale.getDisplayCountry();
   }
}
