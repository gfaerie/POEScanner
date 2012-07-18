package se.faerie.poescanner;

import java.applet.Applet;
import java.applet.AudioClip;


public class UserInteraction
{
  public void alertNewCdKey(String key)
  {
    AudioClip clip = Applet.newAudioClip(getClass().getResource(
      "/alert.wav"));
    clip.play();
    System.out.println("Possible valid key found: " + key);
  }

  public void alertError(String message) {
    playAlert();
    System.err.println("An error occured: " + message);
  }

  public void log(String log) {
    System.out.println(log);
  }

  private void playAlert() {
    AudioClip clip = Applet.newAudioClip(getClass().getResource(
      "/alert.wav"));
    clip.play();
  }
}