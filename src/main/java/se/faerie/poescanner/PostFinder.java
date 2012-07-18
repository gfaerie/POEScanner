package se.faerie.poescanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostFinder
{
  private HashSet<String> existingPosts = new HashSet<String>();
  private String mainUrl = "http://www.pathofexile.com/forum/view-forum/52";
  private String postBaseUrl = "http://www.pathofexile.com/forum/view-thread/";

  private Pattern postPattern = Pattern.compile("<divclass=\"title\"><ahref=\"/forum/view-thread/(\\d*)\">", 
    32);

  public List<String> getNewPosts() throws Exception {
    List<String> returnList = new ArrayList<String>();
    String body = HttpUtils.doGet(this.mainUrl, null);
    Matcher matcher = this.postPattern.matcher(body);
    while (matcher.find()) {
      String url = matcher.group(1);
      if (!this.existingPosts.contains(url)) {
        returnList.add(this.postBaseUrl + url);
        this.existingPosts.add(url);
      }
    }
    return returnList;
  }
}