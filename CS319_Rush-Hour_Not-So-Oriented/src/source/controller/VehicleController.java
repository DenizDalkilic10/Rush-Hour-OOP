package source.controller;

import source.model.*;

import java.awt.*;

/**
 * VehicleController class is responsible for controlling the vehicles on the map.
 */
@SuppressWarnings("Duplicates")
public class VehicleController extends Controller
{
   private static VehicleController instance = null;

   private Map map;
   private Vehicle selectedVehicle;
   private Vehicle slidingVehicle;
   private SoundManager soundManager;
   private int numberOfMoves;
   private boolean changed = false;
   private boolean isMoving = false;
   boolean isExitReachable = false;
   private boolean isPlayerMoving = false;
   private boolean isSelectedVehicleSliding = false;
   private Point destination = new Point();

   enum CONTROL
   {
      SLIDE, KEYBOARD,
   }

   private CONTROL currentControl;

   private double[] vehicleOriginPosition;
   private int[] mouseOriginPosition;
   private int[] oldPos;

   /**
    * Empty constructor that initializes values to their specified initial values.
    */
   private VehicleController()
   {
      numberOfMoves = 0;
//      soundManager = GameEngine.getInstance().soundManager;
      currentControl = CONTROL.SLIDE;

      mouseOriginPosition = new int[2];
      vehicleOriginPosition = new double[2];
      oldPos = new int[2];
   }

   /**
    * Returns a new instance of the VehicleController class
    * @return new VehicleController object
    */
   public static VehicleController getInstance()
   {
      if(instance == null) {
         instance = new VehicleController();
      }
      return instance;
   }

   /**
    * Setter for a map
    * @param _map desired map.
    */
   public void setMap(Map _map)
   {
      map = _map;
   }

   /**
    * Updates the vehicle.
    */
   public void update()
   {
      if ( !GameManager.getInstance().isGameActive )
      {
         return;
      }

      if ( PowerUpManager.getInstance().isPowerUpActive() )
      {
         return;
      }

      if ( currentControl == CONTROL.SLIDE )
      {
         // mouse released and vehicle is sliding
         if (slidingVehicle != null)
         {
            slidingVehicle.slideToPoint(destination.x,destination.y);
            if (!slidingVehicle.isSliding)
            {
               GameManager.getInstance().autoSave();
               slidingVehicle = null;
            }
            return;
         }

         if ( Input.getMouseButtonPressed(0) )
         {
            Vehicle temp = MapController.getInstance().getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               setSelectedVehicle(temp);
               vehicleOriginPosition[0] = selectedVehicle.transform.position.x;
               vehicleOriginPosition[1] = selectedVehicle.transform.position.y;
               mouseOriginPosition = Input.getMousePosition();

               oldPos[0] = selectedVehicle.transform.position.gridX;
               oldPos[1] = selectedVehicle.transform.position.gridY;
            }
         }

         if ( Input.getMouseButtonReleased(0) )
         {
            if ( selectedVehicle != null )
            {
//               selectedVehicle.isSliding = true;

               int gridPositionX = (int) ( selectedVehicle.transform.position.x + 0.5 );
               int gridPositionY = (int) ( selectedVehicle.transform.position.y + 0.5 );

               selectedVehicle.moveToPoint(gridPositionX, gridPositionY);
               MapController.getInstance().updateMap(map.getGameObjects());
               destination.x = selectedVehicle.transform.position.gridX;
               destination.y = selectedVehicle.transform.position.gridY;

               if ( !( oldPos[0] == selectedVehicle.transform.position.gridX && oldPos[1] == selectedVehicle.transform.position.gridY ) )
               {
                  numberOfMoves++;
               }

               slidingVehicle = selectedVehicle;
               slidingVehicle.isSliding = true;
               selectedVehicle = null;
            }
         }

         //Moving while holding the mouse down
         if ( selectedVehicle != null )
         {
            if ( selectedVehicle.transform.axis.equals("Horizontal") )
            {
               //get mouse diff
               int mouseDifference = ( Input.getMousePosition()[0] - mouseOriginPosition[0] );

               int testPositionX = (int) vehicleOriginPosition[0];
               int testPositionY = (int) vehicleOriginPosition[1];

               if ( mouseDifference > 0 ) // vehicle right
               {
                  testPositionX += selectedVehicle.transform.length;// (int) ( vehicleOriginPosition[0] + ( ( Input.getMousePosition()[0] - mouseOriginPosition[0] ) / (double) 60 ) );
               }
               else if ( mouseDifference < 0 ) // vehicle left
               {
                  testPositionX -= 1;
               }

               // if there is difference
               if ( mouseDifference != 0 )
               {
                  //clamp diff between -1 and 1
                  double difference = clamp(mouseDifference, -1, 1);
                  if ( testPositionX >= 0 && testPositionX < map.getMapSize() )
                  {
                     if ( map.getGrid()[testPositionY][testPositionX].equals("Space") )
                     {
                        selectedVehicle.transform.position.x = vehicleOriginPosition[0] + difference;
                     }
                  }

                  //change grid position based on difference
                  if ( difference == 1 || difference == -1 )
                  {
                     changeGridPosition();
                  }
               }
            }
            else // Vertical
            {
               int mouseDifference = ( Input.getMousePosition()[1] - mouseOriginPosition[1] );

               int testPositionX = (int) vehicleOriginPosition[0];
               int testPositionY = (int) vehicleOriginPosition[1];

               if ( mouseDifference > 0 ) // up
               {
                  testPositionY += selectedVehicle.transform.length;
               }
               else if ( mouseDifference < 0 ) // down
               {
                  testPositionY -= 1;
               }

               if ( mouseDifference != 0 )
               {
                  double difference = clamp(mouseDifference, -1, 1);
                  if ( testPositionY >= 0 && testPositionY < map.getMapSize() )
                  {
                     if ( map.getGrid()[testPositionY][testPositionX].equals("Space") )
                     {
                        selectedVehicle.transform.position.y = vehicleOriginPosition[1] + difference;
                     }
                  }

                  if ( difference == 1 || difference == -1 )
                  {
                     changeGridPosition();
                  }
               }
            }
         }
      }
      else if ( currentControl == CONTROL.KEYBOARD )
      {

         if ( Input.getMouseButtonPressed(0) && !isMoving )
         {
            Vehicle temp = MapController.getInstance().getVehicleBySelectedCell(Input.getMouseMatrixPosition()[0], Input.getMouseMatrixPosition()[1]);

            if ( temp != null )
            {
               setSelectedVehicle(temp);
               System.out.println("Selected vehicle: " + selectedVehicle.transform.position.x + ", " + selectedVehicle.transform.position.y);
            }
         }

         if ( !isExitReachable )
         {
            checkExitPath();
         }
         else
         {

            if ( !MapController.getInstance().isPlayerAtExit() && selectedVehicle == MapController.getInstance().getPlayerVehicle() )
            {
               isPlayerMoving = true;
               selectedVehicle.move(0.1);
            }
            else if ( MapController.getInstance().isPlayerAtExit() )
            {
               selectedVehicle = null;
               isMoving = false;
               isPlayerMoving = false;
               GameManager.getInstance().endMap();

            }
         }
//         System.out.println(isMoving + "," + isPlayerMoving);
         if ( selectedVehicle != null && !isMoving && !isPlayerMoving )
         {

            selectedVehicle.velocity = 0.1;
            if ( Input.getKeyPressed("w") )
            {
               isMoving = tryMove("Upwards");

               destination.y = selectedVehicle.transform.position.gridY - 1;
               destination.x = selectedVehicle.transform.position.gridX;
            }
            else if ( Input.getKeyPressed("a") )
            {
               isMoving = tryMove("Left");

               destination.y = selectedVehicle.transform.position.gridY;
               destination.x = selectedVehicle.transform.position.gridX - 1;
            }
            else if ( Input.getKeyPressed("s") )
            {
               isMoving = tryMove("Downwards");

               destination.y = selectedVehicle.transform.position.gridY + 1;
               destination.x = selectedVehicle.transform.position.gridX;
            }
            else if ( Input.getKeyPressed("d") )
            {
               isMoving = tryMove("Right");

               destination.y = selectedVehicle.transform.position.gridY;
               destination.x = selectedVehicle.transform.position.gridX + 1;
            }
         }
         if ( selectedVehicle != null && isMoving )
         {

            selectedVehicle.isSliding = true;
            selectedVehicle.slideToPoint(destination.x, destination.y);
            isSelectedVehicleSliding = selectedVehicle.isSliding;
            if ( !isSelectedVehicleSliding )
            {
               selectedVehicle.moveToPoint(destination.x, destination.y);
               MapController.getInstance().updateMap(map.getGameObjects());
               changed = true;
               isMoving = false;
               isPlayerMoving = false;
               GameManager.getInstance().autoSave();

            }
         }

      }
      if ( MapController.getInstance().isPlayerAtExit() )
      {
         selectedVehicle = null;
         isMoving = false;
         isPlayerMoving = false;
         GameManager.getInstance().endMap();
      }
   }

   /**
    * Setter for a selected vehicle.
    * @param _selectedVehicle a vwhile that is selected by the player.
    */
   private void setSelectedVehicle(Vehicle _selectedVehicle)
   {
      if ( _selectedVehicle != selectedVehicle )
      {
         if ( changed )
         {
            numberOfMoves++;
            GameManager.getInstance().autoSave();
            changed = false;
         }
      }
      selectedVehicle = _selectedVehicle;
      if ( soundManager == null )
      {
         return;
      }
      soundManager.vehicleHorn();
   }


   /**
    * Changes the grid positions of the vehicle.
    */
   private void changeGridPosition()
   {
      int gridPositionX = (int) ( selectedVehicle.transform.position.x + 0.5 );
      int gridPositionY = (int) ( selectedVehicle.transform.position.y + 0.5 );

      selectedVehicle.moveToPoint(gridPositionX, gridPositionY);
      MapController.getInstance().updateMap(map.getGameObjects());

      vehicleOriginPosition[0] = selectedVehicle.transform.position.x;
      vehicleOriginPosition[1] = selectedVehicle.transform.position.y;
      mouseOriginPosition = Input.getMousePosition();

//      if ( MapController.getInstance().isPlayerAtExit() )
//      {
//         GameManager.getInstance().endMap();
//         selectedVehicle = null;
//      }
   }


   /**
    * Checks whether the player can move the vehicle or not.
    * @param direction The direction of the movement.
    * @return true if it moves, false otherwise.
    */
   private boolean tryMove(String direction)
   {
      System.out.println("In here??");
      String vehicleAxis = selectedVehicle.transform.axis;

      int moveCheck = 0;

      if ( vehicleAxis.equals("Horizontal") )
      {
         if ( direction.equals("Left") )
         {
            moveCheck = -1;
         }
         else if ( direction.equals("Right") )
         {
            moveCheck = selectedVehicle.transform.length;
         }
         if ( selectedVehicle.transform.position.x + moveCheck < 0 || selectedVehicle.transform.position.x + moveCheck >= map.getMapSize() )
         {
            return false;
         }

         if ( map.getGrid()[selectedVehicle.transform.position.gridY][selectedVehicle.transform.position.gridX + moveCheck].equals("Space") )
         {
            selectedVehicle.isMoving = true;
            return true;
         }
      }
      if ( vehicleAxis.equals("Vertical") )
      {
         if ( direction.equals("Upwards") )
         {
            moveCheck = -1;
         }
         else if ( direction.equals("Downwards") )
         {
            moveCheck = selectedVehicle.transform.length;
         }
         if ( selectedVehicle.transform.position.y + moveCheck < 0 || selectedVehicle.transform.position.y + moveCheck >= map.getMapSize() )
         {
            return false;
         }

         if ( map.getGrid()[( selectedVehicle.transform.position.gridY ) + moveCheck][selectedVehicle.transform.position.gridX].equals("Space") )
         {
            selectedVehicle.isMoving = true;
            return true;
         }
      }

      return false;
   }


   /**
    * Getter for number of moves.
    * @return the number of moves.
    */
   public int getNumberOfMoves()
   {
      return numberOfMoves;
   }


   /**
    * Setter for number of moves.
    * @param _moves number of moves to be setted.
    */
   void setNumberOfMoves(int _moves)
   {
      numberOfMoves = _moves;
   }


   /**
    * Checks the exit path for player's vehicle.
    */
   private void checkExitPath()
   {
      Vehicle player = MapController.getInstance().getPlayerVehicle();
      boolean temp = true;
      for ( int i = 0; i < ( map.getMapSize() - player.transform.position.gridX ) - player.transform.length; i++ )
      {
         if ( !( map.getGrid()[( player.transform.position.gridY )][player.transform.position.gridX + i + player.transform.length].equals("Space") ) )
         {
            temp = false;
         }
      }
      isExitReachable = temp;
      //System.out.println(isExitReachable);
   }


   /**
    * It is a function of clamp
    * @param value indicates entered value
    * @param min minimum value
    * @param max maximum value
    * @return the difference
    */
   private double clamp(double value, int min, int max)
   {
      double difference;
      if ( value / (double) 60 > max )
      {
         difference = max;
      }
      else if ( value / (double) 60 < min )
      {
         difference = min;
      }
      else
      {
         difference = value / (double) 60;
      }
      return difference;
   }

   /**
    * Setter for current control.
    * @param type represents the control.
    */
   void setCurrentControl(CONTROL type)
   {
      currentControl = type;
   }


   /**
    * Toggles current control.
    */
   void toggleCurrentControl()
   {
      if ( currentControl == CONTROL.SLIDE )
      {
         currentControl = CONTROL.KEYBOARD;
      }
      else
      {
         currentControl = CONTROL.SLIDE;
      }
      selectedVehicle = null;
   }


   /**
    * It initializes the current control.
    */
   private void initializeCurrentControl()
   {
      if ( PlayerManager.getInstance().getCurrentPlayer().getSettings().getControlPreference().equals("Slide") )
      {
         currentControl = CONTROL.SLIDE;
      }
      else
      {
         currentControl = CONTROL.KEYBOARD;
      }
   }

   /**
    * Caller of the initializeCurrentControl.
    */
   public void start()
   {
      soundManager = SoundManager.getInstance();
      initializeCurrentControl();
   }
}
