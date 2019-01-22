package source.model;

import java.util.ArrayList;

/**
 * Map class is used to represent the map that the game is played on.
 * It holds the vehicles that are part of the level.
 */
public class Map
{
   private ArrayList<GameObject> gameObjects; //Holds the gameObjects that are currently part of the level.
   private String[][] grid; //Representation of the map in textual form.
   private int mapSize = 8; /* Represents the size of the map.
   A size of 8 means that the map is 8 by 8.*/

   public Map()
   {

   }

   /**
    * Constructor that initializes values to their specified initial values.
    * @param gameObjects Holds the gameObjects that are currently part of the level.
    */
   public Map(ArrayList<GameObject> gameObjects)
   {
      this.gameObjects = gameObjects;
      formMap(gameObjects);
   }

   /**
    * Forms the textual form of the map from the given array list of vehicles.
    * Updates the textual form of the map from the given array list of vehicles.
    * @param _gameObjects Holds the gameObjects that are currently part of the level.
    */
   public void formMap(ArrayList<GameObject> _gameObjects)
   {
      gameObjects = _gameObjects;

      grid = new String[mapSize][mapSize];

      for ( GameObject gameObject : gameObjects )
      {
         String name = gameObject.getType();
         for ( int i = 0; i < gameObject.transform.length; i++ )
         {
            grid[gameObject.getOccupiedCells()[i] / mapSize][gameObject.getOccupiedCells()[i] % mapSize] = name;
         }
      }

      for ( int i = 0; i < mapSize; i++ )
      {
         for ( int j = 0; j < mapSize; j++ )
         {
            if ( grid[i][j] == null)
            {
               grid[i][j] = "Space";
            }
         }
      }

   }

   /**
    * Getter for game objects.
    * @return the vehicles on the map as ArrayList.
    */
   public ArrayList<GameObject> getGameObjects()
   {
      return gameObjects;
   }

   /**
    * Getter for the grid.
    * @return the textual representation of the map as 2d String array.
    */
   public String[][] getGrid()
   {
      return grid;
   }


   /**
    * Getter for a map size.
    * @return the size of the map.
    */
   public int getMapSize()
   {
      return mapSize;
   }

}
