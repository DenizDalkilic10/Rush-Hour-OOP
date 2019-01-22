package source.model;

/**
 *  Transform class is used to hold every possible information that
 *  every 2 dimensional object could have.
 *  We use position, length and direction to represent the
 *  2d object's position, rotation and scale respectively.
 */
public class Transform
{
   public class Position
   {
      /**
       *Position class is an inner class
       *that used to hold the coordinates of a Transform in two dimensional space.
       */
      public double x; /* Represents the position on the x axis in two dimensional space.*/
      public double y; /* Represents the position on the y axis in two dimensional space.*/
      public int gridX; /* Represents the integer value of x.*/
      public int gridY; /* Represents the integer value of y.*/


      /**
       * It is to initialize the position with the given x and y values on the two dimensional space.
       * @param x the position on the x axis.
       * @param y the position on y axis.
       */
      Position(double x, double y)
      {
         this.x = x;
         this.y = y;
         gridX = (int)x;
         gridY = (int)y;
      }
   }

   public Position position; /* Represents the position of the object in 2d space. */
   public int length;
   public String axis;/* Represents the axis of the object, horizontal or vertical. */
   public String direction;

   /**
    * A constructor that initializes the attributes of the transform
    * @param x position on the x axis.
    * @param y position on the y axis.
    * @param length  Represents the length of the object.
    * @param direction Represents the direction of the object, right, left, up or down.
    */

   Transform(int x, int y, int length, String direction)
   {
      String _axis;
      if ( direction.equals("Upwards") || direction.equals("Downwards") )
      {
         _axis = "Vertical";
      }
      else if ( direction.equals("Right") || direction.equals("Left") )
      {
         _axis = "Horizontal";
      }
      else
      {
         _axis = "";
      }

      position = new Position(x, y);
      this.length = length;
      this.axis = _axis;
      this.direction = direction;
   }

   /**
    * getter for the length attribute.
    * @return an integer represents length.
    */
   public int getLength()
   {
      return length;
   }

   /**
    * getter for the direction attribute
    * @return a string that respresents direction.
    */
   public String getDirection()
   {
      return direction;
   }
}
