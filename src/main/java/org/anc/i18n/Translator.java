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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

/** Uses the Google online translation API to translate the given text into
 *  the specified output language.
 * 
 * @see http://translate.google.com
 * @author Keith Suderman
 *
 */
public class Translator
{
   protected final static String TRANSLATOR = "http://ajax.googleapis.com/ajax/services/language/translate?v=1.0&q="; //hello%20world&langpair=en%7Cit
   
   /**
    * Translates the input text into the specified target language. The target
    * language should be he two letter ISO language code for one of the
    * languages supported by Google Translate.
    * 
    * @param text The input text to be translated.
    * @param targetLang a two letter ISO language code
    * @return the translated string
    * @throws IOException
    * @throws JSONException
    */
   public static String translate(String text, String sourceLang, String targetLang) throws IOException, JSONException
   {
      StringBuilder buffer = new StringBuilder();
      String query = TRANSLATOR + encode(text);
      query = setLang(query, sourceLang, targetLang);
      URL url = new URL(query);
      InputStream is = url.openStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      String line = reader.readLine();
      while (line != null)
      {
         System.out.println(line);
         buffer.append(line);
         line = reader.readLine();         
      }
      String translation = buffer.toString();
      JSONObject json = new JSONObject(translation);
      JSONObject data = json.getJSONObject("responseData");
      return data.getString("translatedText");
   }

   /**
    * Performs very basic URI encoding. .
    * 
    * @param text the text to be encoded.
    * @return the encoded text.
    */
   protected static String encode(String text)
   {
      // TODO Replace with a decent URI encoding library... if I could just
      // find one.
      StringBuilder buffer = new StringBuilder();
      for (int i = 0; i < text.length(); i++) 
      {
         char c = text.charAt(i);
         switch (c) 
         {
            case '%':
              buffer.append("%25");
              break;
            case '?':
              buffer.append("%3F");
              break;
            case ';':
              buffer.append("%3B");
              break;
            case '#':
              buffer.append("%23");
              break;
            case ' ':
              buffer.append("%20");
              break;
            default:
              buffer.append(c);
              break;
         }
      }      
      return buffer.toString();
   }
   
   /**
    * Appends the langpair query parameter to the end of the URL. It is assumed
    * that the input language is US English.
    * 
    * @param url
    * @param lang
    * @return
    */
   protected static String setLang(String url, String lang)
   {
//      return url + "&langpair=en%7C" + lang;
      return setLang(url, Locale.US.getLanguage(), lang);
   }

   /** Appends the langpair query to the end of the URL.
    * 
    */
   protected static String setLang(String url, String sourceLang, String targetLang)
   {
      return url + "&langpair=" + sourceLang + "%7C" + targetLang;
   }
}
