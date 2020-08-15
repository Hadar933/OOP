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
    final private static int SHIELD_ADDITION = 18;
    final private static int SHIELD_SUBSTITUTION = 10;
    final public static int ZERO_COOLDOWN = 0;
    final public static int DEAD = 0;
    final public static int HEALTH_LOSS = 1;
    final public static int SHOT_PRICE = 19;
    final public static int TELEPORT_PRICE = 140;
    final public static int SHIELD_PRICE = 3;
    final public static int COOL_DOWN_TIME = 7;


    /**
     * the image of the spaceship, that is being provided
     */
    public Image image;
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
     * /**
     * the energy of initialization
     */
    private final int defaultEnergy = 210;

    /**
     * the maximum health level (when initialized)
     */
    private static final int defaultHealth = 22;

    /**
     * the cooldown between two consecutive shots
     */
    public int coolDown;

    /**
     * true = the shield is on. false = shield is off
     */
    public boolean shield;

    /*
     * constructor for a space ship instance
     */
    SpaceShip() {
        this.image = GameGUI.ENEMY_SPACESHIP_IMAGE;
        this.physics = new SpaceShipPhysics();
        this.energy = defaultEnergy;
        this.health = defaultHealth;
        this.shield = false;
        this.coolDown = ZERO_COOLDOWN;
    }

    /**
     * Does the actions of this ship for this round:
     * 1. disables shield (and updates GUI)
     * 2. checks the shooting cool-down
     * This is called once per round by the SpaceWars game driver.
     * note these actions are relevant for all ships BUT human (which we initialize
     * with SPACESHIP_IMAGE)
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        if (shield) {
            shield = false;
            image = GameGUI.ENEMY_SPACESHIP_IMAGE;
        }
        if (coolDown > ZERO_COOLDOWN) {
            coolDown--;
        }
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (shield) {
            maxEnergy += SHIELD_ADDITION;
            energy += SHIELD_ADDITION;
        } else {
            maxEnergy -= SHIELD_SUBSTITUTION;
            if (energy > maxEnergy) {
                energy = maxEnergy;
                health--;
            }
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        physics = new SpaceShipPhysics();
        energy = defaultEnergy;
        health = defaultHealth;
        shield = false;
        coolDown = ZERO_COOLDOWN;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return health <= DEAD;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return physics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!shield) {
            maxEnergy -= SHIELD_SUBSTITUTION;
            health -= HEALTH_LOSS;
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (energy >= SHOT_PRICE && coolDown == ZERO_COOLDOWN) {
            game.addShot(physics);
            energy -= SHOT_PRICE;
            coolDown += COOL_DOWN_TIME;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(energy >= SHIELD_PRICE){
            shield = true;
            energy -= SHIELD_PRICE;
            image = GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }

    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if(energy >= TELEPORT_PRICE){
            physics = new SpaceShipPhysics();
            energy -= TELEPORT_PRICE;
        }
    }

}
