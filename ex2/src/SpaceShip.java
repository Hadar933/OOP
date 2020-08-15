import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game.
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 * a base class for the other spaceships or any other option you will choose.
 *
 * @author oop
 */
public class SpaceShip {

    /**
     * the image of the spaceship, that is being provided
     */
    public Image spaceShipImage;
    /**
     * the representation of position, direction and velocity of the ship
     */
    private SpaceShipPhysics physics;

    /**
     * The maximal energy level (starts at 210)
     */
    public int maxEnergy;

    /**
     * A current energy level, which is between 0 and the maximal energy level.
     */
    public int energy;
    /**
     * A Health level between 0 and 22.
     */
    private int health;

    /**
    /**
     * the energy of initialization
     */
    private final int defaultEnergy = 210;

    /**
     * the maximum health level (when initialized)
     */
    private static final int defaultHealth = 22;

    /*
     * constructor for a space ship instance
     */
    SpaceShip() {
        this.spaceShipImage = GameGUI.ENEMY_SPACESHIP_IMAGE;
        this.physics = new SpaceShipPhysics();
        this.energy = defaultEnergy;
        this.health = defaultHealth;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {

    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {

    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {

    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return false;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return null;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {

    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        return null;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {

    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {

    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {

    }

}
