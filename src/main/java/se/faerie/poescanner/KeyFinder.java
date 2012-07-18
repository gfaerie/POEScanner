package se.faerie.poescanner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyFinder
{
  private Pattern keyPattern = Pattern.compile("\\w\\w\\w\\w\\w-\\w\\w\\w\\w\\w-\\w\\w\\w\\w\\w-\\w\\w\\w\\w\\w", 
    32);

  public List<String> findCdKeys(String url) throws Exception {
    List<String> returnList = new ArrayList<String>();
    String body = HttpUtils.doGet(url, null);
    Matcher matcher = this.keyPattern.matcher(body);
    while (matcher.find()) {
      String key = matcher.group(0);
      returnList.add(key);
    }
    return returnList;
  }
}