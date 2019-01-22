package source.controller;

import interfaces.MapDao;
import source.model.*;

import java.util.ArrayList;

/**
 * MapController class is responsible for loading, saving levels.
 * Updating the current map and holding information about the level.
 */
public class MapController extends Controller
{
   private static MapController instance = null;

   private MapDao mapDao;
   private Map map;

   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   private MapController()
   {
      mapDao = new MapDaoImpl();
   }

   /**
    * Returns a new instance of the MapController class
    * @return new MapController object
    */
   public static MapController getInstance()
   {
      if(instance == null) {
         instance = new MapController();
      }
      return instance;
   }

   /**
    * Load the level given the level number.
    * @param level given level number.
    */
   void loadLevel(int level)
   {
      Player currentPlayer = PlayerManager.getInstance().getCurrentPlayer();
      map = mapDao.extractMap(level, currentPlayer, false);
   }

   /**
    * Loads original level.
    * @param level given level number.
    */
   void loadOriginalLevel(int level)
   {
      map = mapDao.extractMap(level, null, true);
   }


   /**
    * Getter for a map
    * @return the map.
    */
   public Map getMap()
   {
      return map;
   }


   /**
    * Updates map with the given game objects.
    * @param gameObjects objects of the game.
    */
   void updateMap(ArrayList<GameObject> gameObjects)
   {
      map.formMap(gameObjects);
   }


   /**
    * Updates the map.
    */
   //Currently testing / May even crate a method called formMap() with no arguments
   void updateMap()
   {
      map.formMap(map.getGameObjects());
   }

   private GameObject getGameObjectBySelectedCell(int x, int y)
   {
      int[] occupiedCells;
      int cellNumber = ( map.getMapSize() * y ) + x;
      GameObject selectedObject = null;
      for ( GameObject gameObject : map.getGameObjects() )
      {
         occupiedCells = gameObject.getOccupiedCells();
         for ( int i = 0; i < occupiedCells.length; i++ )
         {
            if ( cellNumber == occupiedCells[i] )
            {
               selectedObject = gameObject;
               break;
            }
         }
      }

      return selectedObject;
   }


   /**
    * Getter for vehicle in selected cell
    * @param x coordinates on x-axis
    * @param y coordinates on y-axis
    * @return the vehicle in selected cell.
    */
   Vehicle getVehicleBySelectedCell(int x, int y)
   {
      GameObject temp = getGameObjectBySelectedCell(x, y);

      if ( temp instanceof Vehicle )
      {
         return (Vehicle) temp;
      }
      else
      {
         return null;
      }
   }


   /**
    * Getter for obstacle in selected cell
    * @param x coordinates on x-axis
    * @param y coordinates on y-axis
    * @return the obstacle in selected cell.
    */
   Obstacle getObstacleBySelectedCell(int x, int y)
   {
      GameObject temp = getGameObjectBySelectedCell(x, y);

      if ( temp instanceof Obstacle )
      {
         return (Obstacle) temp;
      }
      else
      {
         return null;
      }
   }


   /**
    * Removes the desired object.
    * @param gameObject the desired game object.
    */
   void removeGameObject(GameObject gameObject)
   {
      map.getGameObjects().remove(gameObject);
   }


   /**
    * Adds the desired object.
    * @param gameObject the desired game object.
    */
   void addGameObject(GameObject gameObject)
   {
      map.getGameObjects().add(gameObject);
   }


   /**
    * Highlights the obstacles.
    */
   void highlightObstacles()
   {
      for ( GameObject gameObject : map.getGameObjects() )
      {
         if ( gameObject instanceof Vehicle )
         {
            gameObject.showBlackForeground();
         }
      }
   }


   /**
    * Highlights the long vehicles.
    */
   void highlightLongs()
   {
      for ( GameObject gameObject : map.getGameObjects() )
      {
         if ( gameObject instanceof Car )
         {
            gameObject.showBlackForeground();
         }
         else if ( gameObject instanceof Obstacle )
         {
            gameObject.showBlackForeground();
         }
      }
   }


   /**
    * Clears the highlights.
    */
   void clearHighlights()
   {
      for ( GameObject gameObject : map.getGameObjects() )
      {
         gameObject.hideBlackForeground();
      }
   }

   /**
    * This method checks if the player is at the last cell he can go
    * One more move will make him get out of the grid and finish the game
    * Map should hold a reference to the player car so we don't have to check every game object every move.
    * @return true if it is at exit, false otherwise.
    */
   boolean isPlayerAtExit()
   {
      Vehicle player = getPlayerVehicle();

      if ( player == null )
      {
         return false;
      }
      return player.transform.position.gridX + player.transform.length == map.getMapSize();
   }


   /**
    * Transforms maps to strings.
    * @return strings that are transformed maps
    */
   // String builder kullansak daha guzel olcak
   String mapToString()
   {
      String[][] mapStr = new String[map.getMapSize()][map.getMapSize()];
      StringBuilder mapString = new StringBuilder();

      ArrayList<GameObject> gameObjects = map.getGameObjects();
      for ( GameObject gameObject : gameObjects )
      {
         if ( gameObject instanceof Vehicle )
         {
            int[] cells = gameObject.getOccupiedCells();
            for ( int i = 0; i < cells.length; i++ )
            {
               int x = cells[i] / mapStr.length;
               int y = cells[i] % mapStr.length;
               if ( i == 0 )
               {
                  if ( ( (Vehicle) gameObject ).isPlayer() )
                  {
                     mapStr[x][y] = "PC";
                  }
                  else
                  {
                     mapStr[x][y] = gameObject.getType().substring(0, 1).toUpperCase() + gameObject.transform.getDirection().substring(0, 1).toUpperCase();
                  }
               }
               else
               {
                  mapStr[x][y] = "XX";
               }
            }
         }
         else if ( gameObject instanceof Obstacle )
         {
            int cell = gameObject.getOccupiedCells()[0];
            int x = cell / mapStr.length;
            int y = cell % mapStr.length;
            mapStr[x][y] = "OO";
         }
      }

      for ( int i = 0; i < mapStr.length; i++ )
      {
         for ( int j = 0; j < mapStr.length; j++ )
         {
            if (mapStr[i][j] == null)
            {
               mapStr[i][j] = "SS";
            }
            mapString.append(mapStr[i][j]).append(" ");
         }
         mapString.append("| ");
      }

//      System.out.println("MAP TO STRING: " + mapString.substring(0, mapString.length() - 2));
      return mapString.substring(0, mapString.length() - 2);
   }


   /**
    * Getter for the player's vehicle.
    * @return the player's vehicle.
    */
   Vehicle getPlayerVehicle()
   {
      Vehicle player = null;
      Vehicle temp;

      for ( GameObject gameObject : map.getGameObjects() )
      {
         if ( gameObject instanceof Vehicle )
         {
            temp = (Vehicle) gameObject;
            if ( temp.isPlayer() )
            {
               player = temp;
               break;
            }
         }
      }
      return player;
   }
}
