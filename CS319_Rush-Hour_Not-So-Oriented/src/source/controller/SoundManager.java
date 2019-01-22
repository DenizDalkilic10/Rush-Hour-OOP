package source.controller;




import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * SoundManager class is responsible for all the sounds
 * that can be heard in the game.
 */
@SuppressWarnings("Duplicates")
public class SoundManager extends Controller
{
   private static SoundManager instance = null;


   private Clip theme;
   private  Clip effect;
   private boolean isThemeEnabled;
   private boolean isEffectsEnabled;

   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   private SoundManager()
   {
      //isThemeEnabled = GameEngine.getInstance().playerManager.getCurrentPlayer().getSettings().getMusic();
      //isEffectsEnabled = GameEngine.getInstance().playerManager.getCurrentPlayer().getSettings().getSfx();
   }

   /**
    * Returns a new instance of the SoundManager class
    * @return new SoundManager object
    */
   public static SoundManager getInstance()
   {
      if(instance == null) {
         instance = new SoundManager();
      }
      return instance;
   }

   /**
    * It initializes the clip.
    */
   private void initializeClip()
   {
      try
      {
         String themeSong = ThemeManager.getInstance().getThemeSong();
         theme = AudioSystem.getClip();
         URL url = this.getClass().getResource(themeSong);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         theme.open(audioInputStream);
         theme.start();
         theme.loop(Clip.LOOP_CONTINUOUSLY);
      } catch (Exception a)
      {
         a.printStackTrace();
      }
   }

   /**
    * Plays the background music
    */
   public void background()
   {
      if ( isThemeEnabled )
      {
         initializeClip();
      }
   }

   /**
    * Plays the vehicle horn sound effect
    */
   void vehicleHorn()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getSelectionSound());
      }
   }

   /**
    * Updates the theme for the sounds.
    */
   public void updateTheme()
   {
      if ( theme != null )
      {
         theme.close();
      }
      background();
   }

   /**
    * Plays the button click sound.
    */
   public void buttonClick()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getButtonClickSound());
      }
   }

   /**
    * Plays poof sound after the poof effect.
    */
   public void poofSound()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getShrinkSound());
      }
   }

   /**
    * Plays shrink sound after the shrink effect.
    */
   public void shrinkSound()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getShrinkSound());
      }
   }

   /**
    * Plays the success sound.
    */
   public void successSound()
   {
      if ( isEffectsEnabled )
      {
         playSound(ThemeManager.getInstance().getEndOfLevelSound());
      }
   }

   /**
    * Toggles the state of the theme song, on or off.
    */
   public void themeSongToggle()
   {
      isThemeEnabled = !isThemeEnabled;
      if ( isThemeEnabled )
      {
         if ( theme == null )
         {
            initializeClip();
         }
         else
         {
            theme.start();
         }
      }
      else
      {
         theme.stop();
      }
      System.out.println(isThemeEnabled);

   }

   private void playSound(String soundIn){
      try
      {
         String sound = soundIn;
         effect = AudioSystem.getClip();
         URL url = this.getClass().getResource(sound);
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         effect.open(audioInputStream);
         effect.start();
      } catch (Exception a)
      {
         System.out.println("Not Found: "+soundIn+" Sound");
      }
   }

   /**
    * Toggles the state of the sound effects, on or off.
    */
   public void effectsToggle()
   {
      isEffectsEnabled = !isEffectsEnabled;
   }

   /**
    * Checks whether theme song is enabled or not.
    * @return true if it is enabled, false otherwise.
    */
   boolean isThemeSongEnabled()
   {
      return isThemeEnabled;
   }


   /**
    * Checks whether effects is enabled or not.
    * @return true if it is enabled, false otherwise.
    */
   boolean isEffectsEnabled()
   {
      return isEffectsEnabled;
   }


   /**
    * Setter for theme song
    * @param themeEnabled Sets if the theme enabled.
    */
   void setThemeSong(boolean themeEnabled)
   {
      if ( this.isThemeEnabled != themeEnabled )
      {
         themeSongToggle();
      }

   }


   /**
    * Setter for effects.
    * @param effectsEnabled Sets if the effects enabled.
    */
   void setEffects(boolean effectsEnabled)
   {
      if ( this.isEffectsEnabled != effectsEnabled )
      {
         effectsToggle();
      }
   }


   /**
    * Starter for sound manager.
    */
   public void start()
   {
      isThemeEnabled = PlayerManager.getInstance().getCurrentPlayer().getSettings().getMusic();
      isEffectsEnabled = PlayerManager.getInstance().getCurrentPlayer().getSettings().getSfx();
      background();
   }
}
