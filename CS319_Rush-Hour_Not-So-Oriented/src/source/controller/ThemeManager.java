package source.controller;

import source.model.GameObject;
import source.model.Theme;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * It is a class to manage themes.
 */
public class ThemeManager extends Controller
{
   private static ThemeManager instance = null;

   public Theme currentTheme;
   public Theme classic;
   public Theme minimalistic;
   public Theme safari;
   public Theme space;

   /**
    * Getter for themes array
    * @return The themes array as a String[]
    */
   public String[] getThemes() {
      return themes;
   }

   private String themes[] = {"minimalistic", "classic" , "safari", "space"};

   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   private ThemeManager()
   { //String theme parametresi ekleyip aşağıda hangi themese current theme o olucak oyun başlarken
      instance = this;
      minimalistic = new Theme("minimalistic");
      classic = new Theme("classic");
      safari = new Theme("safari");
      space = new Theme("space");
   }

   /**
    * Returns a new instance of the ThemeManager class
    * @return new ThemeManager object
    */
   public static ThemeManager getInstance()
   {
      if(instance == null) {
         instance = new ThemeManager();
      }
      return instance;
   }

   /**
    * Finds theme by the specific name.
    * @param theme a theme that is desired as string.
    * @return the desired theme.
    */
   private Theme findThemeByName(String theme)
   {
      if ( theme.equals("classic") )
      {
         return classic;
      }
      else if ( theme.equals("minimalistic") )
      {
         return minimalistic;
      }
      else if ( theme.equals("safari") )
      {
         return safari;
      }
      else if ( theme.equals("space") )
      {
         return space;
      }
      else
      {
         System.out.println("Theme is null");
         return null;
      }
   }

   /**
    * Getter for long vehicle image.
    * @return the image of the long vehicle.
    */
   public BufferedImage getLongVehicleImage()
   {
      return currentTheme.getLongVehicleImage();
   }


   /**
    * Getter for the short vehicle image.
    * @return the image of short vehicle.
    */
   public BufferedImage getShortVehicleImage()
   {
      return currentTheme.getShortVehicleImage();
   }


   /**
    * Getter for the background image.
    * @return the image of background.
    */
   public BufferedImage getBackgroundImage()
   {
      return currentTheme.getBackgroundImage();
   }


   /**
    * Getter for the game panel's background image.
    * @return the image game panel's of background.
    */
   public BufferedImage getGamePanelBackgroundImage()
   {
      return currentTheme.getGamePanelBackgroundImage();
   }


   /**
    * Getter for the pop-up's background image.
    * @return the image pop-up's of background.
    */
   public BufferedImage getPopupBackgroundImage()
   {
      return currentTheme.getPopupBackgroundImage();
   }


   /**
    * Getter for the obstacle image.
    * @return the image of obstacle.
    */
   public BufferedImage getObstacleImage()
   {
      return currentTheme.getObstacleImage();
   }


   /**
    * Getter for the disabled image.
    * @return the image of disabled.
    */
   public BufferedImage getDisabledImage(String type)
   {
      if (type.equals("obstacle")){
       return currentTheme.getObstacleDisabledImage();}
      else if (type.equals("long")){
         return currentTheme.getLongDisabledImage();
      }
      else if (type.equals("short")){
         return currentTheme.getShortDisabledImage();
      }
      else {
         System.out.println("Disabled image not found");
         return null;
      }
   }

   /**
    * Getter for the current theme's player image.
    * @return the image of current theme's player.
    */
   public BufferedImage getPlayerImage()
   {
      return currentTheme.getPlayerImage();
   }


   /**
    * Getter for the current theme's special player image.
    * @return the image of current theme's special player.
    */
   public BufferedImage getSpecialPlayerImage()
   {
      return currentTheme.getSpecialPlayerImage();
   }


   /**
    * Getter for the button's click sound.
    * @return the button's click sound.
    */
   String getButtonClickSound()
   {
      return currentTheme.getButtonClickSound();
   }


   /**
    * Getter for the theme song.
    * @return the theme song.
    */
   String getThemeSong()
   {
      return currentTheme.getThemeSong();
   }


   /**
    * Getter for the current theme's end of level sound.
    * @return the current theme's end of level sound.
    */
   String getEndOfLevelSound(){
   return currentTheme.getEndOfLevelSound();
}


   /**
    * Getter for the current theme's selection sound.
    * @return the current theme's selection sound.
    */
   String getSelectionSound()
   {
      return currentTheme.getSelectionSound();
   }


   /**
    * Getter for the current theme's "poof" sound.
    * @return the current theme's "poof" sound.
    */
   String getPoofSound(){
      return currentTheme.getPoofSound();
   }


   /**
    * Getter for the current theme's shrink sound.
    * @return the current theme's shrink sound.
    */
   String getShrinkSound()
   {
      return currentTheme.getShrinkSound();
   }

//   public Theme getCurrentTheme()
//   {
//      return currentTheme;
//   }

   /**
    * Finds the required stars to unlock the themes.
    * @return required stars to unlock the themes.
    */
   public int findRequiredStars()
   {
      int requiredStars = 100;
      if ( !classic.isUnlocked() )
      {
         requiredStars -= 25;
      }
      if ( !safari.isUnlocked() )
      {
         requiredStars -= 25;
      }
      if ( !space.isUnlocked() )
      {
         requiredStars -= 25;
      }
      if ( requiredStars == 100 )
      {
         return 0;
      }
      return requiredStars;
   }

   /**
    * Getter for the theme's status
    * @param themeName the desired theme's name
    * @return 0 if the theme is locked and not unlockable,
    * 1 if the theme is locked but unlockable,
    * 2 if the the theme is already unlocked
    */
   //Should be used in Settings Panel for the icons od theme buttons
   //returns 0 if the theme is locked and not unlockable
   //returns 1 if the theme is locked but unlockable
   //returns 2 if the the theme is already unlocked
   public int getThemeStatus(String themeName)
   {
      Theme theme = findThemeByName(themeName);

      if ( theme.isUnlocked() )
      {
         return 2;
      }
      else
      {
         if ( PlayerManager.getInstance().getCurrentPlayer().getStarAmount() >= findRequiredStars() )
         {
            return 1;
         }
         else
         {
            return 0;
         }
      }
   }


   /**
    * Starts themes.
    */
   public void start(){
      update();
   }


   /**
    * Changes the theme with considering the themeName.
    * @param themeName the desired theme.
    */
   public void changeTheme(String themeName)
   {
      PlayerManager.getInstance().changeTheme(themeName);
      setTheme(themeName);
   }


   /**
    * Sets the theme with considering the given theme.
    * @param theme the desired theme.
    */
   //This will be called from gameManager
   void setTheme(String theme)
   {
      currentTheme = findThemeByName(theme);
      try
      {
         //Map controller updateMapImages diye bi method olmasi lazim
         // BU methoduda gamemanegerda cagirilcak
         if ( MapController.getInstance().getMap() != null ) //settings panelin previousunu da check edebiliriz
         {
            if ( MapController.getInstance().getMap().getGameObjects() != null )
            {
               for ( GameObject gameObject : MapController.getInstance().getMap().getGameObjects() )
               {
                  gameObject.updateImages();
               }
            }
         }
      } catch (Exception e)
      {
         //e.printStackTrace(); bunun commentini açmayın exception alması doğal halledicem burayı
      }
      SoundManager.getInstance().updateTheme();
//      GuiPanelManager.getInstance().updateImages();
   }

   /**
    * Unlocks the desired theme.
    * @param themeName the desired theme.
    */
   public void unlockTheme(String themeName)
   {
      Theme theme = findThemeByName(themeName);

      theme.setUnlocked(true);
      PlayerManager.getInstance().unlockTheme(themeName);
      setTheme(themeName);
   }


   /**
    * Updates themes.
    */
   public void update()
   {
      HashMap themes = PlayerManager.getInstance().getCurrentPlayer().getSettings().getThemes();
      minimalistic.setUnlocked((boolean) themes.get("minimalistic"));
      classic.setUnlocked((boolean) themes.get("classic"));
      safari.setUnlocked((boolean) themes.get("safari"));
      space.setUnlocked((boolean) themes.get("space"));

      currentTheme = findThemeByName(PlayerManager.getInstance().getCurrentPlayer().getSettings().getActiveTheme());
      findRequiredStars();
   }
}
