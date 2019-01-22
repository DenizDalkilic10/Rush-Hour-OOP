package source.model;

/**
 * LevelInformation class holds all information about a particular level such as
 * the level no, number of moves and the amount of stars collected on that level.
 */
public class LevelInformation
{
   private int stars; // Represents the stars in that level.
   private String status; // Represents the state of the level. For example: Completed, NeverPlayed and such.
   private int levelNo; // Represents the level with a number.
   private int maxNumberOfMovesForThreeStars; // Holds the number of moves to get a three star in this particular level.
   private int maxNumberOfMovesForTwoStars; // Holds the number of moves to a two star in this particular level.
   private int currentNumberOfMoves; /* If the level is not completed and is in a saved state,
   holds the current number of moves the user has made in that level.*/
   private boolean unlocked; // To determine whether the level is locked or unlocked.
   private String map; // Holds the current state of the map as a string so it can be saved to a text file later.
   private int time; // Represent the planned time for the level.


   /**
    * Constructor that initializes values to their specified initial values.
    * @param stars Represents the stars in that level.
    * @param status Represents the state of the level. For example: Completed, NeverPlayed and such.
    * @param levelNo Represents the level with a number.
    * @param maxNumberOfMovesForThreeStars Holds the number of moves to get a three star in this particular level.
    * @param maxNumberOfMovesForTwoStars Holds the number of moves to a two star in this particular level.
    * @param currentNumberOfMoves If the level is not completed and is in a saved state,
    *     holds the current number of moves the user has made in that level.
    * @param unlocked To determine whether the level is locked or unlocked.
    * @param map Holds the current state of the map as a string so it can be saved to a text file later.
    * @param time Represent the planned time for the level.
    */
   public LevelInformation(int stars, String status, int levelNo, int maxNumberOfMovesForThreeStars, int maxNumberOfMovesForTwoStars, int currentNumberOfMoves, boolean unlocked, String map, int time)
   {
      this.levelNo = levelNo;
      this.maxNumberOfMovesForThreeStars = maxNumberOfMovesForThreeStars;
      this.maxNumberOfMovesForTwoStars = maxNumberOfMovesForTwoStars;
      this.currentNumberOfMoves = currentNumberOfMoves;
      this.stars = stars;
      this.status = status;
      this.unlocked = unlocked;
      this.map = map;
      this.time = time;
   }

   /**
    * Getter for the stars
    * @return the amount of stars
    */
   public int getStars()
   {
      return stars;
   }

   /**
    * Setter for the stars
    * @param stars the amount of stars that level
    */
   public void setStars(int stars)
   {
      this.stars = stars;
   }


   /**
    * Getter for the status
    * @return the status of the level.
    */
   public String getStatus()
   {
      return status;
   }


   /**
    * Setter for the status
    * @param status the status of level.
    */
   public void setStatus(String status)
   {
      this.status = status;
   }


   /**
    * Getter for the current number of moves.
    * @return the current number of moves.
    */
   public int getCurrentNumberOfMoves()
   {
      return currentNumberOfMoves;
   }


   /**
    * Setter for the current number of moves
    * @param currentNumberOfMoves the current number of moves
    */
   public void setCurrentNumberOfMoves(int currentNumberOfMoves)
   {
      this.currentNumberOfMoves = currentNumberOfMoves;
   }


   /**
    * Checks whether the level is locked or not
    * @return the boolean unlocked to understand the condition.
    */
   public boolean isUnlocked()
   {
      return unlocked;
   }

   /**
    * Method to unlock the level.
    */
   public void unlock()
   {
      this.unlocked = true;
   }


   /**
    * Getter for a maximum number of moves for three stars.
    * @return an integer that indicates the limit for earning three stars.
    */
   public int getMaxNumberOfMovesForThreeStars()
   {
      return maxNumberOfMovesForThreeStars;
   }


   /**
    * Getter for a maximum number of moves for two stars.
    * @return an integer that indicates the limit for earning two stars.
    */
   public int getMaxNumberOfMovesForTwoStars()
   {
      return maxNumberOfMovesForTwoStars;
   }


   /**
    * Getter for the map of the level.
    * @return the desired map.
    */
   public String getMap()
   {
      return map;
   }


   /**
    * Setter for the map
    * @param map the map for the level.
    */
   public void setMap(String map)
   {
      this.map = map;
   }


   /**
    * Getter for the time.
    * @return the total remaining time for the level.
    */
   public int getTime() {
      return time;
   }


   /**
    * Setter for the time.
    * @param time total remaining time.
    */
   public void setTime(int time) {
      this.time = time;
   }
}
