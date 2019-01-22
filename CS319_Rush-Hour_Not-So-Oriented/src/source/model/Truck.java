package source.model;

/**
 * It is child class of vehicle to implement vehicles with length 3.
 */
public class Truck extends Vehicle
{

    /**
     *
     Constructor that initializes values to their specified initial values.
     * @param x the position on x axis.
     * @param y the position on y axis.
     * @param direction a string that represents the direction of the object.
     * @param player a boolean that checks whether it is a player's vehicle or not.
     */
    public Truck(int x, int y, String direction, boolean player) {
        super(x, y, 3, direction, player);
        if (player) {
            super.setType("Player");
        } else {
            super.setType("Truck");
        }
    }
}
