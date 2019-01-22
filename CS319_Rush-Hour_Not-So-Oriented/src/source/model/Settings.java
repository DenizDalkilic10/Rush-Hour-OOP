package source.model;

import java.util.HashMap;

/**
 * Settings class is used to hold settings information of a player
 */
public class Settings
{
   private boolean music; //  To determine whether the music is on or off.
   private boolean sfx; // To determine whether the sound effects are on or off.
   private String controlPreference; // Represents the selected theme by the player.

   private HashMap<String, Boolean> themes; // Represents the collection off the themes.
   private String activeTheme; // Represents the active theme.

   /**
    * Constructor that initializes values to their specified initial values.
    * @param music To determine whether the music is on or off.
    * @param sfx To determine whether the sound effects are on or off.
    */
   public Settings(boolean music, boolean sfx) {
      this.music = music;
      this.sfx = sfx;
      themes = new HashMap<>();

      themes.put("minimalistic", true);
      themes.put("classic", false);
      themes.put("safari", false);
      themes.put("space", false);

      activeTheme = "minimalistic";
      controlPreference = "Slide";
   }

   /**
    * Getter for the collection of themes
    * @return the collection of themes
    */
   public HashMap<String,Boolean> getThemes() {
      return themes;
   }

   /**
    * Getter for an active theme
    * @return the active theme of the game.
    */
   public String getActiveTheme() {
      return activeTheme;
   }

   /**
    * Setter fot the active theme of the game.
    * @param activeTheme the active theme of the game.
    */
   public void setActiveTheme(String activeTheme) {
      this.activeTheme = activeTheme;
   }

   /**
    * Getter for the music of the game
    * @return the desired music of the game.
    */
   public boolean getMusic() {
      return music;
   }


   /**
    * Getter for the sfx of the game.
    * @return the sfx of the game
    */
   public boolean getSfx() {
      return sfx;
   }

   /**
    * It toggles the state of the music.
    */
   public void toggleMusic() {
      music = !music;
   }

   /**
    * Toggles the state of the sfx.
    */
   public void toggleSfx() {
      sfx = !sfx;
   }

   /**
    * Toggles the control preferences.
    */
   public void toggleControlPreference()
   {
      if (controlPreference.equals("Slide"))
      {
         controlPreference = "Keyboard";
      }
      else
      {
         controlPreference = "Slide";
      }
   }

   /**
    * Getter for a control preference.
    * @return the control preference.
    */
   public String getControlPreference()
   {
      return controlPreference;
   }
}
