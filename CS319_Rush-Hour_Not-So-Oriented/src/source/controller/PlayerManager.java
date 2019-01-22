package source.controller;

import interfaces.PlayerDao;
import source.model.LevelInformation;
import source.model.Player;
import source.model.Settings;

import java.util.ArrayList;

/**
 * PlayerManager is responsible for handling the updates of
 * player information, creating, changing and deleting users
 */
public class PlayerManager extends Controller
{
   private static PlayerManager instance = null;

   private Player currentPlayer;
   private ArrayList<Player> players;
   public int numberOfPlayers;

   private PlayerDao playerDao;

   /**
    *  Empty constructor that initializes values to their specified initial values.
    */
   private PlayerManager()
   {
      playerDao = new PlayerDaoImpl();
      extractPlayers();
   }

   /**
    * Returns a new instance of the PlayerManager class
    * @return new PlayerManager object
    */
   public static PlayerManager getInstance()
   {
      if(instance == null) {
         instance = new PlayerManager();
      }
      return instance;
   }

   /**
    * Extracts and stores the extracted players in an arraylist with the help of player extractor.
    */
   private void extractPlayers()
   {
      players = playerDao.extractPlayers();
      String lastPlayerName = playerDao.extractLastPlayerName();
      if ( players.size() == 0)
      {
         createPlayer("default");
      }

      if ( players.size() == 1 )
      {
         currentPlayer = players.get(0);
      }
      else
      {
         for ( Player player : players )
         {
            if ( player.getPlayerName().equals(lastPlayerName) )
            {
               currentPlayer = player;
               break;
            }
         }
      }
   }

   /**
    * Getter for the currently selected player.
    * @return the currently selected player.
    */
   public Player getCurrentPlayer()
   {
      return currentPlayer;
   }

   /**
    * Getter for all the players in an arraylist.
    * @return all the players in an arraylist.
    */
   public ArrayList<Player> getPlayers()
   {
      return players;
   }

   /**
    * Creates a new fresh player with
    * the given name and adds the player to the players list. 
    * @param playerName the given name of the player.
    * @return 0 if creation successful, 1 if the name already exists
    */
   public int createPlayer(String playerName)
   {
      if ( players == null )
      {
         players = new ArrayList<>();
      }

      //checks if a player with the same name exists
      if (checkIfPlayerExistsByName(playerName))
      {
         return 1;
      }
      //adds the new player to players and sets it as current player
      boolean initialMusic;
      boolean initialSfx;

      if ( players.size() == 0 )
      {
         initialMusic = true;
         initialSfx = true;
      }
      else
      {
         initialMusic = SoundManager.getInstance().isThemeSongEnabled();
         initialSfx = SoundManager.getInstance().isEffectsEnabled();
      }

      Settings settings = new Settings(initialMusic, initialSfx);
      Player newPlayer = playerDao.createPlayer(playerName, settings);
      playerDao.saveLastActivePlayer(playerName);
      players.add(newPlayer);
      currentPlayer = newPlayer;

      ThemeManager.getInstance().setTheme("minimalistic");
      VehicleController.getInstance().setCurrentControl(VehicleController.CONTROL.SLIDE);

      return 0;
   }

   /**
    * Deletes all the data of the given user and removes it from the players list.
    * @param name the name of the player.
    * @return the index of the deleted player.
    */
   public int deletePlayer(String name)
   {
      if ( players.size() == 1 )
      {
         return -1;
      }
      boolean deleted = false;
      int deleteIndex = 0;
      for ( int i = 0; i < players.size(); i++ )
      {
         if ( players.get(i).getPlayerName().equals(name) && currentPlayer != players.get(i) )
         {
            if ( playerDao.deletePlayer(players.get(i)) )
            {
               players.remove(i);
               deleted = true;
            }
            deleteIndex = i;
            break;
         }
      }
      if ( deleted )
      {
         //decrementPlayerNumber();

         return deleteIndex;

      }
      return -1;
   }


   /**
    * Selects desired player among other players by considering the name input
    * @param name the name of the player.
    * @return a boolean to understand whether the operation is successful or not.
    */
   public boolean selectPlayer(String name)
   {
      boolean selected = false;
      for ( int i = 0; i < players.size(); i++ )
      {
         if ( players.get(i).getPlayerName().equals(name) && currentPlayer != players.get(i) )
         {
            currentPlayer = players.get(i);
            selected = true;
            break;
         }
      }
      if ( selected )
      {
         SoundManager.getInstance().setThemeSong(currentPlayer.getSettings().getMusic());
         SoundManager.getInstance().setEffects(currentPlayer.getSettings().getSfx());
         ThemeManager.getInstance().setTheme(currentPlayer.getSettings().getActiveTheme());
         playerDao.saveLastActivePlayer(name);
         return true;
      }
      return false;

   }


   /**
    * Edits the desired player's name with the new one.
    * @param oldName the old name of the player
    * @param newName desired name to be replaced with the old one.
    * @return an integer to indicate whether the operation is successful or not.
    */
   public int editPlayer(String oldName, String newName)
   {
      if (checkIfPlayerExistsByName(newName))
      {
         return -1;
      }

      for (int i = 0; i < players.size(); i++)
      {
         Player player = players.get(i);
         if (player.getPlayerName().equals(oldName))
         {
            player.setPlayerName(newName);
            playerDao.changePlayerName(player);
            if (player == currentPlayer)
            {
               playerDao.saveLastActivePlayer(player.getPlayerName());
            }
            return i;
         }
      }
      return -1;
   }


   /**
    * Checks the condition whether the player with a given name exists or not
    * @param playerName the name of the checked player
    * @return a boolean to check whether the player exists or not.
    */
   private boolean checkIfPlayerExistsByName(String playerName)
   {
      for ( int i = 0; i < players.size(); i++ )
      {
         if ( players.get(i).getPlayerName().equals(playerName) )
         {
            return true;
         }
      }
      return false;
   }


   /**
    * Updates the last level that is played by the player as finished.
    * @param levelNo the last level that is played by the player.
    * @param starAmount the star that is earned on that level.
    */
   //These 2 methods will have to change with gameManager to not include other controllers
   void updateLevelAtTheEnd(int levelNo, int starAmount)
   {
      LevelInformation currentLevel = currentPlayer.getLevels().get(levelNo - 1);
      setLevelStatus(levelNo, "finished");
      currentLevel.setMap("");
      if ( starAmount > currentLevel.getStars() )
      {
         currentPlayer.setStarAmount(currentPlayer.getStarAmount() + ( starAmount - currentLevel.getStars() ));
         currentLevel.setStars(starAmount);
         //playerDao.savePlayerInfo(currentPlayer);
         playerDao.savePlayer(currentPlayer);
      }
      //playerDao.saveLevel(levelNo, currentPlayer);
      playerDao.savePlayer(currentPlayer);
      currentPlayer.getLevels().get(levelNo - 1).setCurrentNumberOfMoves(0);

   }


   /**
    * Updates the level that is played by the player as in progress.
    * @param levelNo the level that is played by the player.
    * @param moveAmount number of moves that took action in that level.
    */
   //saveMape kadar oln k�sm� MapController da bir methodla �a�r�labilir
   void updateLevelDuringGame(int levelNo, int moveAmount)
   {
      setLevelStatus(levelNo, "inProgress");

      String map = MapController.getInstance().mapToString();
      currentPlayer.getLevels().get(levelNo - 1).setCurrentNumberOfMoves(moveAmount);
      currentPlayer.getLevels().get(levelNo - 1).setMap(map);

      //playerDao.saveLevel(levelNo, currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }


   /**
    * Updates the level when player wants to restart the level .
    * @param levelNo the level that is played by the player.
    */
   void  updateLevelAtReset(int levelNo)
   {
      if (currentPlayer.getLevels().get(levelNo - 1).getStars() > 0)
      {
         setLevelStatus(levelNo, "finished");
      }
      else
      {
         setLevelStatus(levelNo, "notStarted");
      }
      currentPlayer.getLevels().get(levelNo - 1).setCurrentNumberOfMoves(0);
      playerDao.savePlayer(currentPlayer);
   }

   /**
    * Setter for the status of specific level.
    * @param levelNo the level that is played by the player.
    * @param status the status of the player.
    */
   private void setLevelStatus(int levelNo, String status)
   {
      if ( !currentPlayer.getLevels().get(levelNo - 1).getStatus().equals(status) )
      {
         currentPlayer.getLevels().get(levelNo - 1).setStatus(status);
      }
   }

//   void setLevelStatusFinished(int levelNo)
//   {
//      setLevelStatus(levelNo, "finished");
//      //playerDao.saveLevel(levelNo, currentPlayer);
//      playerDao.savePlayer(currentPlayer);
//
//   }


   /**
    * It unlocks the desired level.
    * @param levelNo the level that is played by the player.
    */
   void unlockLevel(int levelNo)
   {
      currentPlayer.getLevels().get(levelNo - 1).unlock();
      //playerDao.saveLevel(levelNo, currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }


   /**
    * Increases the last unlocked level of the player by 1.
    */
   void incrementLastUnlockedLevelNo()
   {
      currentPlayer.incrementLastUnlockedLevelNo();
   }


   /**
    * Checks whether the desired level is locked or not.
    * @param levelNo the level to be checked.
    * @return a boolean to indicate whether the desired level is locked or not.
    */
   public boolean isLevelLocked(int levelNo)
   {
      return !currentPlayer.getLevels().get(levelNo - 1).isUnlocked();
   }


   /**
    * Toggles music.
    */
   public void toggleMusic()
   {
      currentPlayer.getSettings().toggleMusic();
      //playerDao.saveSettings(currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }


   /**
    * Toggles sfx.
    */
   public void toggleSfx()
   {
      currentPlayer.getSettings().toggleSfx();
      //playerDao.saveSettings(currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }


   /**
    * Changes to the desired theme.
    * @param theme the desired theme.
    */
   void changeTheme(String theme)
   {
      //commented case will be added after testing is done
      if ( !theme.equals(currentPlayer.getSettings().getActiveTheme()) /* && (boolean) currentPlayer.getSettings().getThemes().get(theme) */ )
      {
         currentPlayer.getSettings().setActiveTheme(theme);
         //playerDao.saveSettings(currentPlayer);
         playerDao.savePlayer(currentPlayer);
      }
   }


   /**
    * Unlocks the theme with given theme name.
    * @param themeName the given theme name.
    */
   void unlockTheme(String themeName)
   {
      //check out this
      currentPlayer.getSettings().getThemes().put(themeName, true);
      changeTheme(themeName);
   }


   /**
    * Decreases the remaining shrink power ups.
    */
   void decrementRemainingShrinkPowerup()
   {
      currentPlayer.decrementRemainingShrinkPowerup();
      //playerDao.saveRemainingPowerupAmount(currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }


   /**
    * Decreases the remaining space power ups.
    */
   void decrementRemainingSpacePowerup()
   {
      currentPlayer.decrementRemainingSpacePowerup();
      //playerDao.saveRemainingPowerupAmount(currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }

   /**
    * Adds shrink power ups.
    */
   void addShrinkPowerup(int amountToBeAdded)
   {
      currentPlayer.addShrinkPowerup(amountToBeAdded);
      //playerDao.saveRemainingPowerupAmount(currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }


   /**
    * Adds space power ups.
    */
   void addSpacePowerup(int amountToBeAdded)
   {
      currentPlayer.addSpacePowerup(amountToBeAdded);
      //playerDao.saveRemainingPowerupAmount(currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }


   /**
    * Saves toggle control preferences of the player.
    */
   void  toggleControlPreference()
   {
      currentPlayer.getSettings().toggleControlPreference();
      //playerDao.saveSettings(currentPlayer);
      playerDao.savePlayer(currentPlayer);
   }
}
