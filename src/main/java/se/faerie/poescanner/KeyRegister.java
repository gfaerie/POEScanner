package se.faerie.poescanner;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

public class KeyRegister
{
  private String loginUrl = "https://www.pathofexile.com/login";
  private String loginParams = "login_email=<login>&login_password=<password>&location=http%3A%2F%2Fwww.pathofexile.com&login_submit_from_login_area=1&redir=%2Flogout&x=48&y=18";
  private String registerUrl = "http://www.pathofexile.com/my-account/activate-game-login";
  private String registerParams = "activation_key=%s&x=52&y=13";
  private String login;
  private String password;

  public KeyRegister(String login, String password)
  {
    this.login = login;
    this.password = password;
  }

  public void tryLogin() throws Exception {
    getLoginCookie();
  }

  public boolean tryRegisterKey(String key) throws Exception {
    String cookie = getLoginCookie();
    URL url = new URL(this.registerUrl);
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Cookie", cookie);
      connection.setRequestProperty("Content-Type", 
        "application/x-www-form-urlencoded");
      connection.setDoOutput(true);
      connection.setDoInput(true);
      OutputStreamWriter writer = new OutputStreamWriter(
        connection.getOutputStream());
      writer.write(String.format(this.registerParams, new Object[] { key }));
      writer.flush();
      String output = StringUtils.copyToString(new InputStreamReader(
        connection.getInputStream()));
      return !output.contains("Invalid activation key");
    } finally {
      if (connection != null)
        connection.disconnect();
    }
  }

  private String getLoginCookie() throws Exception {
    URL url = new URL(this.loginUrl);
    HttpsURLConnection connection = null;
    try {
      connection = (HttpsURLConnection)url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", 
        "application/x-www-form-urlencoded");
      connection.setDoOutput(true);
      connection.setDoInput(true);

      OutputStreamWriter writer = new OutputStreamWriter(
        connection.getOutputStream());
      writer.write(this.loginParams.replace("<login>", 
        URLEncoder.encode(this.login, Charset.defaultCharset().name()))
        .replace(
        "<password>", 
        URLEncoder.encode(this.password, 
        Charset.defaultCharset().name())));
      writer.flush();
      connection.connect();
      String headerName = null;
      String setCookie = null;
      String location = null;
      for (int i = 1; (headerName = connection.getHeaderFieldKey(i)) != null; i++) {
        if (headerName.equals("Set-Cookie"))
          setCookie = connection.getHeaderField(i);
        else if (headerName.equals("Location")) {
          location = connection.getHeaderField(i);
        }
      }

      if ((location != null) && (setCookie != null)) {
        URLConnection urlConnection = new URL(location)
          .openConnection();
        urlConnection.setRequestProperty("Cookie", setCookie);
        urlConnection.connect();

        if (StringUtils.copyToString(
          new InputStreamReader(urlConnection.getInputStream()))
          .contains("Logged in")) {
          String str1 = setCookie;
          return str1;
        }
      }
      throw new IllegalArgumentException("Unable to login using username: " + this.login + ". Unknwon login page contents");
    }
    finally {
      if (connection != null)
        connection.disconnect();
    }
  }
}