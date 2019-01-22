package source.controller;

import java.util.ArrayList;
/**
 * GameEngine class is responsible for handling the game loop and every class that is related to the game logic.
 */
public class GameEngine
{
   
   private static GameEngine instance = null;

   private ArrayList<Controller> controllers;
   public ThemeManager themeManager;
   public SoundManager soundManager;
   public VehicleController vehicleController;
   public MapController mapController;
   public PlayerManager playerManager;
   public GameManager gameManager;
   public PowerUpManager powerUpManager;


   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   private GameEngine()
   {
      themeManager = ThemeManager.getInstance();
      soundManager = SoundManager.getInstance();
      mapController = MapController.getInstance();
      powerUpManager = PowerUpManager.getInstance();
      vehicleController = VehicleController.getInstance();
      playerManager = PlayerManager.getInstance();
      gameManager = GameManager.getInstance();

      controllers = new ArrayList<>();

      controllers.add(themeManager);
      controllers.add(soundManager);
      controllers.add(mapController);
      controllers.add(vehicleController);
      controllers.add(playerManager);
      controllers.add(gameManager);
      controllers.add(powerUpManager);

      for ( Controller controller : controllers )
      {
         controller.start();
      }
   }

   public static GameEngine getInstance()
   {
      if(instance == null) {
         instance = new GameEngine();
      }
      return instance;
   }

   
   /**
    * Calls the update methods of every Manager object that is inside managers array.
    */
   public void run()
   {
      // this method is executed over and over from main
      // calls the update method of other classes that needs to be updated
      for ( Controller controller : controllers )
      {
         controller.update();
      }

      Input.reset();
   }
}
