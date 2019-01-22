package source.model;


/**
 * It is child class of vehicle to implement vehicles with length 2.
 */
public class Car extends Vehicle
{


	/**
	 *
	 Constructor that initializes values to their specified initial values.
	 * @param x the position on x axis.
	 * @param y the position on y axis.
	 * @param direction a string that represents the direction of the object.
	 * @param player a boolean that checks whether it is a player's vehicle or not.
	 */
	public Car(int x, int y, String direction, boolean player)
	{
		super(x, y, 2, direction, player);
		if (player)
		{
			super.setType("Player");
		}
		else
		{
			super.setType("Car");
		}
	}

	public Car(Vehicle tempVehicle)
	{
		super((int)tempVehicle.transform.position.x, (int)tempVehicle.transform.position.y, 2, tempVehicle.transform.direction,  false);
		super.setType("Car");
	}

}
