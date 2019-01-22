package source.controller;

import source.model.LevelInformation;
import source.view.GuiPanelManager;

/**
 * GameManager is a generic class that handles higher aspects of the game.
 */
public class GameManager extends Controller
{
   private static GameManager instance = null;

   public PlayerManager playerManager;
   public int level;
   private int timerStartValue;
   private int remainingTime;
   private boolean isLevelBonus;

   boolean isGameActive = false;

   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   private GameManager()
   {
      playerManager = PlayerManager.getInstance();
      instance = this;
      remainingTime = 0;
      isLevelBonus = false;
   }

   /**
    * Returns a new instance of this class.
    * @return new GameManager object
    */
   public static GameManager getInstance()
   {
      if(instance == null) {
         instance = new GameManager();
      }
      return instance;
   }

   /**
    * Updates the game.
    */
   public void update()
   {
      if ( isLevelBonus & isGameActive)
      {
         remainingTime--;
         if (remainingTime <= 0)
         {
            timeOver();
         }
      }

      if (Input.getKeyPressed("n"))
      {
         endMap();
      }
   }

   /**
    * Auto saves the game.
    */
   void autoSave()
   {
      int moveAmount = VehicleController.getInstance().getNumberOfMoves();
      PlayerManager.getInstance().updateLevelDuringGame(level, moveAmount);
   }

   /**
    * It stops the map.
    */
   public void stopMap()
   {
      isGameActive = false;
      PowerUpManager.getInstance().deactivatePowerUps();
   }

   /**
    * This is called when the level is finished
    * and handles all the action of level being completed.
    */
   void endMap()
   {

//      System.out.println("Map Finished");
      isGameActive = false;
      PowerUpManager.getInstance().deactivatePowerUps();
      VehicleController.getInstance().isExitReachable = false;
      //PlayerManager.getInstance().setLevelStatusFinished(level);

      if ( isNextLevelLocked() )
      {
         unlockNextLevel();
         PlayerManager.getInstance().incrementLastUnlockedLevelNo();
      }

      if (isLevelBonus && PlayerManager.getInstance().getCurrentPlayer().getLevels().get(level - 1).getStars() == 0)
      {
         PlayerManager.getInstance().addShrinkPowerup(2);
         PlayerManager.getInstance().addSpacePowerup(2);
      }

      int starsCollected = calculateStars(level);
//      System.out.println("Stars Collected: " + starsCollected);
      PlayerManager.getInstance().updateLevelAtTheEnd(level, starsCollected);
      GuiPanelManager.getInstance().getGamePanel().showEndOfLevelPopUp(starsCollected);
   }

   /**
    * It indicates time over after time lapses.
    */
   private void timeOver()
   {
      System.out.println("Time Over!");
      isGameActive = false;
      PowerUpManager.getInstance().deactivatePowerUps();
      PlayerManager.getInstance().updateLevelAtTheEnd(level, 0);
      GuiPanelManager.getInstance().getGamePanel().showTimeOverPopUp();
   }

   /**
    * It calculates the stars with considering the moves of the player.
    * @param _level the level that is played.
    * @return an integer between 1-3.
    */
   private int calculateStars(int _level)
   {
      LevelInformation currentLevel = PlayerManager.getInstance().getCurrentPlayer().getLevels().get(_level - 1);
      //bad fix maybe change it later
      if ( VehicleController.getInstance().getNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForThreeStars() )
      {
         return 3;
      }
      else if ( VehicleController.getInstance().getNumberOfMoves() + 1 <= currentLevel.getMaxNumberOfMovesForTwoStars() )
      {
         return 2;
      }
      else
      {
         return 1;
      }
   }

   /**
    * Loads the last level that is played but not finished.
    */
   public void loadLastLevel()
   {
      level = PlayerManager.getInstance().getCurrentPlayer().getLastUnlockedLevelNo();
      loadLevel(level, false);
   }

   /**
    * Loads the given level.
    * @param _level desired level.
    * @param reset a boolean that indicates the reset
    */
   public void loadLevel(int _level, boolean reset)
   {
//      System.out.println("Loaded level: " + _level);
//      System.out.println(PlayerManager.getInstance().getCurrentPlayer().getLevels().get(_level - 1));
      level = _level;
      LevelInformation levelToBeLoaded = PlayerManager.getInstance().getCurrentPlayer().getLevels().get(_level - 1);
      isLevelBonus = false;

      if ( levelToBeLoaded.getTime() >= 0 )
      {
         System.out.println("Bonus Map");
         isLevelBonus = true;
         remainingTime = levelToBeLoaded .getTime() * 60;
         timerStartValue = remainingTime;
         MapController.getInstance().loadOriginalLevel(_level);
         VehicleController.getInstance().setMap(MapController.getInstance().getMap());
         VehicleController.getInstance().setNumberOfMoves(0);
      }
      else if ( !levelToBeLoaded.getStatus().equals("inProgress") || reset )
      {
         MapController.getInstance().loadOriginalLevel(_level);
         VehicleController.getInstance().setMap(MapController.getInstance().getMap());
         VehicleController.getInstance().setNumberOfMoves(0);
      }
      else
      {
         MapController.getInstance().loadLevel(_level);
         VehicleController.getInstance().setMap(MapController.getInstance().getMap());
         VehicleController.getInstance().setNumberOfMoves(playerManager.getCurrentPlayer().getLevels().get(_level - 1).getCurrentNumberOfMoves());
      }

      GuiPanelManager.getInstance().getGamePanel().setInnerGamePanelVisible();


      isGameActive = true;
   }

   /**
    *  Loads the next level.
    */
   public void nextLevel()
   {
      level++;
      loadLevel(level, false);
   }

   /**
    * Resets level.
    */
   public void resetLevel()
   {
      PlayerManager.getInstance().updateLevelAtReset(level);
      loadLevel(level, true);
      PowerUpManager.getInstance().deactivatePowerUps();
   }

   /**
    * Checks whether it is last level or not
    * @return true for last level.
    */
   public boolean isLastLevel()
   {
      return level == PlayerManager.getInstance().getCurrentPlayer().getLevels().size();
   }

   /**
    * Getter for level
    * @return the level
    */
   public int getLevel()
   {
      return level;
   }

   /**
    * Checks whether the next level is locked or not.
    * @return true if it is locked, false otherwise.
    */
   private boolean isNextLevelLocked()
   {
      return level < PlayerManager.getInstance().getCurrentPlayer().getLevels().size() && PlayerManager.getInstance().isLevelLocked(level + 1);
   }

   /**
    * Unlocks the next level.
    */
   private void unlockNextLevel()
   {
      PlayerManager.getInstance().unlockLevel(level + 1);
   }

   /**
    * Checks whether shrink power up is usable or not.
    * @return true if it is usable, false otherwise.
    */
   public boolean isShrinkPowerUpUsable()
   {
      return playerManager.getCurrentPlayer().getRemainingShrinkPowerup() > 0;
   }

   /**
    * Checks whether space power up is usable or not.
    * @return true if it is usable, false otherwise.
    */
   public boolean isSpacePowerUpUsable()
   {
      return playerManager.getCurrentPlayer().getRemainingSpacePowerup() > 0;
   }


   /**
    * Getter for remaining time.
    * @return the reaining time.
    */
   public int getRemainingTime()
   {
      return remainingTime;
   }


   /**
    * Getter for the start value of timer.
    * @return timer's start value.
    */
   public int getTimerStartValue()
   {
      return timerStartValue;
   }


   /**
    * Checks if there is a level bonus or not.
    * @return the boolean isLevelBonus, true if it exists, false otherwise.
    */
   public boolean isLevelBonus()
   {
      return isLevelBonus;
   }


   /**
    * Checks if the game is active or not.
    * @return true if it is active, false otherwise.
    */
   public boolean isGameActive()
   {
      return isGameActive;
   }


   /**
    * Toggles control type.
    */
   public void toggleControlType()
   {
      PlayerManager.getInstance().toggleControlPreference();
      VehicleController.getInstance().toggleCurrentControl();
   }
}
