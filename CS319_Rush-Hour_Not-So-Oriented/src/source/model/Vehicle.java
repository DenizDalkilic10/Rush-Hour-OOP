package source.model;

import source.controller.ThemeManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Vehicle class is used to represent the vehicles in our game.
 * They are the main game object in the game Rush Hour.
 */
public class Vehicle extends GameObject// implements Drawable
{
   private String type; // we may not need this
   public boolean isMoving; // we may not need this
   private boolean player;
   private BufferedImage image;
   private BufferedImage blackedOutImage;
   public boolean isSliding = false;
   public double velocity;

   /**
    * Constructor that initializes values to their specified initial values
    * @param x the position on x axis
    * @param y the position on y axis
    * @param length an integer that represents the length of the object
    * @param direction a string that represents the direction of the object.
    * @param player a boolean that checks whether it is a player's vehicle or not.
    */
   Vehicle(int x, int y, int length, String direction, boolean player) //theme i sil burdan
   {
      super(x, y, length, direction);
      this.player = player;
      isMoving = false;
      velocity = 0.05;
      updateImages();
   }

   /**
    * Moves the amount that is given according to its axis inside the Transform property.
    * Moves backwards when the value is negative.
    * @param moveAxis the amount which is given to transform.
    */
   public void move(double moveAxis)
   {
      if ( transform.axis.equals("Vertical") )
      {
         transform.position.y -= moveAxis;
         if (!isSliding) {
            transform.position.gridY = (int) ((transform.position.y + 0.1) / 1); //değerler double a döndüğü için direk typecast etmek mantıklı / 1 yaptım olay anlaşılsın diye
         }
      }
      else if ( transform.axis.equals("Horizontal") )
      {
         transform.position.x += moveAxis;
         if (!isSliding) {
            transform.position.gridX = (int) ((transform.position.x + 0.1) / 1);
         }
      }
      findOccupiedCells();
   }


   /**
    * Slides the vehicle to the given point instantly.
    * @param x the position on x axis
    * @param y the position on y axis
    */
   public void slideToPoint(int x, int y) // tam sayılara gitmesini istiyoruz, eğer başka bir feature gelirse double la değiştirin
   {
      if ( transform.axis.equals("Vertical") )
      {
         if (y == (int)(transform.position.y) && Math.abs(transform.position.y-y) <= 0.1)
         {
            transform.position.y = (int)(transform.position.y+0.1);
            isSliding = false;
         }
         else if (y > transform.position.y)
         {
            move(-velocity);
         }
         else if (y < transform.position.y)
         {
            move(velocity);
         }
      }
      else if ( transform.axis.equals("Horizontal") )
      {
         if (x == (int)transform.position.x && Math.abs(transform.position.x-x) <=0.1)
         {
            transform.position.x = (int)(transform.position.x+0.1);
            isSliding = false;
         }
         else if (x > transform.position.x)
         {
            move(velocity);
         }
         else if (x < transform.position.x)
         {
            move(-velocity);
         }
      }
   }


   /**
    * Updates the images of the vehicle according to the current theme.
    */
   @Override
   public void updateImages()
   {
      if ( !player && transform.length == 2 )
      {
         image = ThemeManager.getInstance().getShortVehicleImage();
         blackedOutImage = ThemeManager.getInstance().getDisabledImage("short");
      }
      else if ( !player && transform.length == 3 )
      {
         image = ThemeManager.getInstance().getLongVehicleImage();
         blackedOutImage = ThemeManager.getInstance().getDisabledImage("long");
      }
      else if ( player )
      {
         image = ThemeManager.getInstance().getPlayerImage();
         blackedOutImage = ThemeManager.getInstance().getDisabledImage("short");
      }
   }


   /**
    * Moves the vehicle to the given point instantly.
    * @param x the position on x axis
    * @param y the position on y axis
    */
   public void moveToPoint(int x, int y)
   {
      transform.position.gridX = x;
      transform.position.gridY = y;
      findOccupiedCells();
   }

   /**
    * Getter for a type of a vehicle
    * @return the type of a vehicle
    */
   public String getType()
   {
      return type;
   }


   /**
    * Setter for a type of a vehicle.
    * @param type its for setting to the desired type of vehicle
    */
   void setType(String type)
   {
      this.type = type;
   }


   /**
    * Returns whether this vehicle is the player vehicle.
    * @return confirms if it is player's vehicle
    */
   public boolean isPlayer()
   {
      return player;
   }


   /**
    * It is method to draw the vehicles.
    * @param graphics the representation of the graphics
    */
   @Override
   public void draw(Graphics2D graphics)
   {

      AffineTransform at;

      int gridPixelSize = 60;
      at = AffineTransform.getTranslateInstance(transform.position.x * gridPixelSize, transform.position.y * gridPixelSize);

      //for drawing the black out
      Graphics2D temp = (Graphics2D) graphics.create();
      Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
      temp.setComposite(composite);

      if ( transform.direction.equals("Upwards") )
      {
         //do nothing
      }
      else if ( transform.direction.equals("Downwards") )
      {
         at.rotate(Math.toRadians(180), image.getWidth() / 2.0, image.getHeight() / 2.0);
      }
      else if ( transform.direction.equals("Left") )
      {
         at.rotate(Math.toRadians(90), image.getWidth() / 2.0, image.getHeight() / 2.0 / transform.length);
         at.translate(0, -60 * ( transform.length - 1 ));
      }
      else
      {
         at.rotate(Math.toRadians(270), image.getWidth() / 2.0, image.getHeight() / 2.0 / transform.length);
      }
      graphics.drawImage(image, at, null);
      if (isBlackedOut)
      {
         temp.drawImage(blackedOutImage,at, null);
      }
   }
}
