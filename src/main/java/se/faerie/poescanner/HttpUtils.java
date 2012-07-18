package se.faerie.poescanner;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils
{
  public static String doGet(String stringUrl, String cookie)
    throws Exception
  {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(stringUrl);
      connection = (HttpURLConnection)url.openConnection();
      if (cookie != null) {
        connection.setRequestProperty("Cookie", cookie);
      }
      connection.connect();
      String feedback = StringUtils.copyToString(new InputStreamReader(
        connection.getInputStream()));
      return StringUtils.trimWhitespaces(feedback);
    } finally {
      connection.disconnect();
    }
  }
}