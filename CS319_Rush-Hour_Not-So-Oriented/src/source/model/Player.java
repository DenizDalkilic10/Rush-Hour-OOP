package source.model;

import java.util.ArrayList;


/**
 * Player class hold represent the Player who is playing the game.
 * It holds every information about a player such as, level progression, rewards and settings.
 */
public class Player
{
   private String playerName;
   private ArrayList<LevelInformation> levels;
   private int starAmount;
   private String path;
   private int lastUnlockedLevelNo;
   private int remainingShrinkPowerup;
   private int remainingSpacePowerup;

   private Settings settings;


   /**
    * Constructor that initializes values to their specified initial values.
    * @param playerName Represents the name of the player.
    * @param starAmount Represents the total number of stars collected from every level.
    * @param levels Holds the information about every level spesific to the user.
    * @param path Holds the player folders path inside the data folders.
    * @param settings  Holds all information about the players settings.
    * @param remainingShrinkPowerup Holds the amount of shrink power up that the player has.
    * @param remainingSpacePowerup Holds the amount of space power up that the player has
    */
   public Player(String playerName, int starAmount, ArrayList<LevelInformation> levels, String path, Settings settings, int remainingShrinkPowerup, int remainingSpacePowerup)
   {
      this.playerName = playerName;
      this.starAmount = starAmount;
      this.path = path;
      this.levels = levels;
      this.remainingShrinkPowerup = remainingShrinkPowerup;
      this.remainingSpacePowerup = remainingSpacePowerup;
      this.settings = settings;
   }


   /**
    * Getter for the path attribute of the player
    * @return path inside the data folders.
    */
   public String getPath()
   {
      return path;
   }


   /**
    * Setter for the path attribute of the player
    * @param path the path inside the data folders.
    */
   public void setPath(String path)
   {
      this.path = path;
   }


   /**
    * Getter for the name of the player
    * @return player's name
    */
   public String getPlayerName()
   {
      return playerName;
   }


   /**
    * Setter for the name of the player
    * @param playerName stands for the name of the player
    */
   public void setPlayerName(String playerName)
   {
      this.playerName = playerName;
   }


   /**
    * Getter for the amount of stars that are earned by the player from every level.
    * @return amount of stars that are earned by the player from every level.
    */
   public int getStarAmount()
   {
      return starAmount;
   }


   /**
    * Setter for the amount of stars that are earned by the player from every level.
    * @param starAmount Represents the total number of stars collected from every level.
    */
   public void setStarAmount(int starAmount)
   {
      this.starAmount = starAmount;
   }


   /**
    * Getter for the all information about the players settings.
    * @return all information about the players settings.
    */
   public Settings getSettings()
   {
      return settings;
   }


   /**
    * Getter for the level information of the player
    * @return An arraylist of LevelInformation for this player.
    */
   public ArrayList<LevelInformation> getLevels()
   {
      return levels;
   }


   /**
    * Getter for  the last unlocked level by this player.
    * @return  the last unlocked level by this player.
    */
   public int getLastUnlockedLevelNo()
   {
      return lastUnlockedLevelNo;
   }


   /**
    * Resets the level information of the player
    */
   public void resetLastUnlockedLevelNo()
   {
      this.lastUnlockedLevelNo = 1;
   }


   /**
    * Increases the last level by one after player passes to the locked level.
    */
   public void incrementLastUnlockedLevelNo()
   {
      lastUnlockedLevelNo++;
   }

   /**
    * Getter for number of remaining shrink power ups.
    * @return number of remaining shrink power ups.
    */
   public int getRemainingShrinkPowerup()
   {
      return remainingShrinkPowerup;
   }


   /**
    * Getter for number of remaining space power ups.
    * @return number of remaining space power ups.
    */
   public int getRemainingSpacePowerup()
   {
      return remainingSpacePowerup;
   }


   /**
    * Decreases the number of remaining shrink power ups.
    */
   public void decrementRemainingShrinkPowerup()
   {
      remainingShrinkPowerup--;
   }


   /**
    * Decreases the number of remaining space power ups.
    */
   public void decrementRemainingSpacePowerup()
   {
      remainingSpacePowerup--;
   }


   /**
    * Adds shrink power ups to the player.
    * @param amountToBeAdded the amount that is desired to be added.
    */
   public void addShrinkPowerup(int amountToBeAdded)
   {
      remainingShrinkPowerup += amountToBeAdded;
   }


   /**
    * Adds shrink power ups to the player.
    * @param amountToBeAdded the amount that is desired to be added.
    */
   public void addSpacePowerup(int amountToBeAdded)
   {
      remainingSpacePowerup += amountToBeAdded;
   }
}
