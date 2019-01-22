package source.model;

import source.controller.ThemeManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Obstacle class is used to represent the obstacles in our game.
 * They are the secondary game object in the game Rush Hour.
 */
public class Obstacle extends GameObject
{
   private BufferedImage image; //Holds the image that represents this obstacle.
   private BufferedImage blackedOutImage; //Holds the image that represents the blacked out obstacles.

   /**
    * Constructor that initializes values to their specified initial values.
    * @param x the position on x axis
    * @param y the position on y axis
    * @param length an integer that represents the length of the object
    * @param direction a string that represents the direction of the object.
    */
   public Obstacle(int x, int y, int length, String direction)
   {
      super(x, y, length, direction);
      updateImages();
   }


   /**
    * A function to draw the obstacles.
    * @param graphics the representation of the graphics
    */
   @SuppressWarnings("Duplicates")
   @Override
   public void draw(Graphics2D graphics)
   {
      graphics.drawImage(image, (int) transform.position.x * 60, (int) transform.position.y * 60, null);
      if (isBlackedOut)
      {
         Graphics2D temp = (Graphics2D) graphics.create();
         Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
         temp.setComposite(composite);
         temp.drawImage(blackedOutImage,(int) transform.position.x * 60, (int) transform.position.y * 60, null);
      }
   }

   /**
    * Getter for a type of obstacle
    * @return the desired type
    */
   @Override
   public String getType()
   {
      return "OO";
   }


   /**
    * Method to update images
    */
   @Override
   public void updateImages()
   {
      image = ThemeManager.getInstance().getObstacleImage();
      blackedOutImage = ThemeManager.getInstance().getDisabledImage("obstacle");
   }
}
