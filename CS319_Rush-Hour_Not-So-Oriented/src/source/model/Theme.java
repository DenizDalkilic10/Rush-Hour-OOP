package source.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Theme class is used to hold information of a theme.
 */
public class Theme
{
    private ArrayList<BufferedImage> shortVehicleImageArray;
    private ArrayList<BufferedImage> longVehicleImageArray;
    private BufferedImage playerImage;
    private BufferedImage specialPlayer;
    private BufferedImage obstacle;
    private BufferedImage background;
    private BufferedImage gamePanelBackground;
    private BufferedImage popupBackground;
    private BufferedImage obstacleDisabled;
    private BufferedImage longDisabled;
    private BufferedImage shortDisabled;
    private String themeSong;
    private String buttonClick;
    private String selectionSound;
    private String endOfLevelSound;
    private String poof;
    private  String shrink;
    private String path;
    private boolean unlocked;
    public String name;


    /**
     * Constructor that initializes values to their specified initial values.
     * @param theme represents the theme of the game.
     */
   public Theme(String theme) {
      shortVehicleImageArray = new ArrayList<>();
      longVehicleImageArray = new ArrayList<>();
      path = "image/theme_" + theme + "/";
      name = theme;
      initializeAttributes();
   }

    /**
     * Initializes attributes for this theme.
     */
   private void initializeAttributes() { //update yaparsak işimize yarar diye ayırdım ama update gerekmicek %99.9
      setSounds();
      setImages();
   }

    /**
     *  Sets the sounds for this theme.
     */
   private void setSounds() {
      buttonClick ="/"+ path + "buttonClick.wav";
      themeSong = "/" +path + "theme.wav";
      selectionSound = "/"+path + "selectionSound.wav";
      endOfLevelSound ="/"+ path + "success.wav";
      poof = "/"+ path + "poof.wav";
      shrink = "/"+path + "shrink.wav";
   }

    /**
     * Sets the images for this theme.
     */
   private void setImages() {
      initializeVehicleImages();
      setPlayerImage();
      setSpecialPlayer();
      setBackground();
      setObstacleImage();
      setDisabled();
      rescaleImages();
   }

    /**
     * It is for initializing the vehicle images
     */
   private void initializeVehicleImages() {
      for (int i = 0; i < 2; i++) //size 2, 2 farklı renk var çünkü her vehicle için
      {
         longVehicleImageArray.add(LoadImage(path + "long" + i + ".png"));
         shortVehicleImageArray.add(LoadImage(path + "short" + i + ".png"));
      }
   }

    /**
     * Setter for obstacle images.
     */
   private void setObstacleImage() {
      obstacle = LoadImage(path + "obstacle.png");
   }

    /**
     * Setter for player images.
     */
   private void setPlayerImage() {
      playerImage = LoadImage(path + "player.png");
   }

    /**
     * It is setter for disabling some obstacles.
     */
   private void setDisabled(){
       obstacleDisabled = LoadImage(path + "obstacleDisabled.png");
       longDisabled = LoadImage(path+"longDisabled.png");
       shortDisabled = LoadImage(path + "shortDisabled.png");
    }


    /**
     * Setter for the background.
     */
    private void setBackground() {
      background = LoadImage(path + "background.png");
      popupBackground = LoadImage(path + "popUpBackground.png");
      gamePanelBackground = LoadImage(path + "gameBackground.png");
   }

    /**
     * Setter for special player vehicle of the theme.
     */
   private void setSpecialPlayer() {
      specialPlayer = LoadImage(path + "special.png");
   }

    /**
     * Getter of long vehicle images.
     * @return long vehicle images
     */
   public BufferedImage getLongVehicleImage() {
      return longVehicleImageArray.get((int) (Math.random() * 2)); //test lazım ama çalışır
   }

    /**
     * Getter for short vehicle image.
     * @return the short vehicle image.
     */
   public BufferedImage getShortVehicleImage() {
      return shortVehicleImageArray.get((int) (Math.random() * 2));
   }

    /**
     * Getter for player's vehicle's image.
     * @return  player's vehicle's image.
     */
   public BufferedImage getPlayerImage() {
      return playerImage;
   }

    /**
     * Getter for the special player's vehicle image.
     * @return the desired special player's vehicle image.
     */
   public BufferedImage getSpecialPlayerImage() {
      return specialPlayer;
   }

    /**
     * Getter for the obstacles of the game image.
     * @return the obstacle image.
     */
   public BufferedImage getObstacleImage() {
      return obstacle;
   }

    /**
     * Getter for the long, disabled vehicle image.
     * @return the long, disabled vehicle image.
     */
    public BufferedImage getLongDisabledImage() {
        return longDisabled;
    }

    /**
     * Getter for short, disabled vehicle image.
     * @return short, disabled vehicle image.
     */
    public BufferedImage getShortDisabledImage() {
        return shortDisabled;
    }

    /**
     * Getter for the disabled obstacle image.
     * @return the disabled obstacle image.
     */
    public BufferedImage getObstacleDisabledImage() {
        return obstacleDisabled;
    }

    /**
     * Getter for game panel's background image.
     * @return game panel's background image.
     */
    public BufferedImage getGamePanelBackgroundImage() {
      return gamePanelBackground;
   }

    /**
     * Getter for total background image.
     * @return background image.
     */
    public BufferedImage getBackgroundImage() {
        return background;
    }

    /**
     * Getter for pop-up's background image.
     * @return pop-up's background image.
     */
    public BufferedImage getPopupBackgroundImage() {
      return popupBackground;
   }

    /**
     * Getter for the button's click sound.
     * @return the button's click sound.
     */
    public String getButtonClickSound() {
      return buttonClick;
   }

    /**
     * Getter for the theme song
     * @return the theme song
     */
    public String getThemeSong() {
      return themeSong;
   }

    /**
     * Getter for the sound effect of the end of level.
     * @return sound effect of the end of level.
     */
    public String getEndOfLevelSound(){
       return endOfLevelSound;
    }

    /**
     * Getter for the sound effect of selection of any button.
     * @return the sound effect of selection.
     */
    public String getSelectionSound() {
      return selectionSound;
   }

    /**
     * Getter for the "poof" sound effect.
     * @return the "poof" sound effect.
     */
    public String getPoofSound(){
        return poof;
    }

    /**
     * Getter of the shrink sound effect.
     * @return the shrink sound effect.
     */
    public String getShrinkSound() {
        return shrink;
    }

    /**
     * It is a function to rescale the images for the theme
     */
   @SuppressWarnings("Duplicates")
   private void rescaleImages() {
      Image scaledImage;
      Graphics2D bGr;
      for (int i = 0; i < shortVehicleImageArray.size(); i++) {
         scaledImage = shortVehicleImageArray.get(i).getScaledInstance(60, 120, Image.SCALE_DEFAULT);
         shortVehicleImageArray.set(i, new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB));
         bGr = shortVehicleImageArray.get(i).createGraphics();
         bGr.drawImage(scaledImage, 0, 0, null);
         bGr.dispose();
      }

      for (int i = 0; i < longVehicleImageArray.size(); i++) {
         scaledImage = longVehicleImageArray.get(i).getScaledInstance(60, 180, Image.SCALE_DEFAULT);
         longVehicleImageArray.set(i, new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB));
         bGr = longVehicleImageArray.get(i).createGraphics();
         bGr.drawImage(scaledImage, 0, 0, null);
         bGr.dispose();
      }

      scaledImage = specialPlayer.getScaledInstance(60, 120, Image.SCALE_DEFAULT);
      specialPlayer = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      bGr = specialPlayer.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();

      scaledImage = playerImage.getScaledInstance(60, 120, Image.SCALE_DEFAULT);
      playerImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      bGr = playerImage.createGraphics();
      bGr.drawImage(scaledImage, 0, 0, null);
      bGr.dispose();
   }

    /**
     * It is function to load images.
     * @param fileName the name of the desired file.
     * @return the desired image.
     */
    public BufferedImage LoadImage(String fileName) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(Theme.class.getClassLoader().getResourceAsStream(fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        BufferedInputStream image = new BufferedInputStream(
                getClass().getResourceAsStream(path));
        */return image;
    }

    /**
     * Checks whether the theme is unlocked or not
     * @return the boolean to understand the condition, true for unlocked, false for locked.
     */
    public boolean isUnlocked()
    {
        return unlocked;
    }

    /**
     * Setter for locking or unlocking the theme
     * @param unlocked boolean condition, true for unlocked, false for locked.
     */
    public void setUnlocked(boolean unlocked)
    {
        this.unlocked = unlocked;
    }

}
