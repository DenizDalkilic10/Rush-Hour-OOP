package source.model;

import interfaces.Drawable;

import java.awt.*;

/**
 * GameObject class is used to represent every possible object that is inside the game.
 */
public class GameObject implements Drawable
{
   public Transform transform; /*  Represents all attributes that is related to 2d space.*/
   private int[] occupiedCellNumbers; /* Holds all the cells that this game object currently occupies. */
   boolean isBlackedOut; /* Boolean that checks whether the object is blacked out or not*/

   /**
    * Constructor that initializes values to their specified initial values.
    * @param x the position on x axis
    * @param y the position on y axis
    * @param length an integer that represents the length of the object
    * @param direction a string that represents the direction of the object.
    */
   GameObject(int x, int y, int length, String direction)
   {
      transform = new Transform(x, y, length, direction);
      findOccupiedCells();
      isBlackedOut = false;
   }

   /**
    *Updates the cells that is occupied  by this game object.
    * When a game object is moved it needs to recalculate the positions of
    * its other parts.
    */
   void findOccupiedCells()
   {
      occupiedCellNumbers = new int[transform.length];
      occupiedCellNumbers[0] = transform.position.gridY * 8 + transform.position.gridX;

      if ( transform.axis.equals("Vertical") )
      {
         for ( int i = 1; i < transform.length; i++ )
         {
            occupiedCellNumbers[i] = occupiedCellNumbers[i - 1] + 8;
         }
      }
      else if ( transform.axis.equals("Horizontal") )
      {
         for ( int i = 1; i < transform.length; i++ )
         {
            occupiedCellNumbers[i] = occupiedCellNumbers[i - 1] + 1;
         }
      }
   }

   /**
    * Getter for the occupiedCellNumbers.
    * @return the occupied cells.
    */
   public int[] getOccupiedCells() // for those want to use cell numbers and calculate pivot points
   {
      return occupiedCellNumbers;
   }

   /**
    * Getter for a type
    * @return empty string
    */
   public String getType()
   {
      return "";
   }

   /**
    * Draws the image connected to this game object.
    * @param graphics the representation of the graphics
    */
   @Override
   public void draw(Graphics2D graphics)
   {

   }

   /**
    * Updates the images of the game object according to the current theme.
    */
   public void updateImages()
   {
   }

   /**
    * It is function to show the black foreground.
    */
   public void showBlackForeground()
   {
      isBlackedOut = true;
   }

   /**
    * It is function to hide the black foreground.
    */
   public void hideBlackForeground()
   {
      isBlackedOut = false;
   }
}