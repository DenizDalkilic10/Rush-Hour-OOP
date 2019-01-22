package source.controller;

import com.google.gson.Gson;
import interfaces.MapDao;
import source.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * It is responsible for extracting the map data from the local directory into the game.
 */
class MapDaoImpl implements MapDao
{
   private Gson gson = new Gson();
   private ArrayList<GameObject> gameObjects = new ArrayList<>();


   /**
    * Extracts the map of the given level from the directory of the given player if it is original.
    * @param level the given level
    * @param player the given player
    * @param original a boolean that indicates whether the map is original or not.
    * @return the map of the game.
    */
   @SuppressWarnings("StatementWithEmptyBody")
   @Override
   public Map extractMap(int level, Player player, boolean original)
   {
      Map map = new Map();
      gameObjects.clear();

      String mapStr;
      if ( !original )
      {
         mapStr = player.getLevels().get(level - 1).getMap();
      }
      else
      {
         InputStream input = PlayerDaoImpl.class.getClassLoader().getResourceAsStream("data/levels/level" + level + ".json");
         InputStreamReader reader = new InputStreamReader(input);
         mapStr = gson.fromJson(reader, OriginalLevel.class).map;
      }

      int x = 0;
      int y = 0;
      Scanner scan = new Scanner(mapStr);
      scan.useDelimiter(" ");

      //Object Codes are TU TD TR TL CU CD CR CL SS OO
      while ( scan.hasNext() )
      {
         String objectCode = scan.next();
         if (objectCode.equals("|"))
         {
            y++;
            x = 0;
            continue;
         }
         if ( objectCode.equals("TU") )
         {
            gameObjects.add(new Truck(x, y, "Upwards", false));
         }
         else if ( objectCode.equals("TD") )
         {
            gameObjects.add(new Truck(x, y, "Downwards", false));
         }
         else if ( objectCode.equals("TR") )
         {
            gameObjects.add(new Truck(x, y, "Right", false));
         }
         else if ( objectCode.equals("TL") )
         {
            gameObjects.add(new Truck(x, y, "Left", false));
         }
         else if ( objectCode.equals("CU") )
         {
            gameObjects.add(new Car(x, y, "Upwards", false));
         }
         else if ( objectCode.equals("CD") )
         {
            gameObjects.add(new Car(x, y, "Downwards", false));
         }
         else if ( objectCode.equals("CR") )
         {
            gameObjects.add(new Car(x, y, "Right", false));
         }
         else if ( objectCode.equals("CL") )
         {
            gameObjects.add(new Car(x, y, "Left", false));
         }
         else if ( objectCode.equals("PC") )
         {
            gameObjects.add(new Car(x, y, "Left", true));
         }
         else if ( objectCode.equals("PT") )
         {
            gameObjects.add(new Truck(x, y, "Right", true));
         }
         else if ( objectCode.equals("OO") )
         {
            gameObjects.add(new Obstacle(x, y, 1, "Right"));
         }
         x++;
      }

      System.out.println(gameObjects.toString());
      map.formMap(gameObjects);
      return map;
   }
}
