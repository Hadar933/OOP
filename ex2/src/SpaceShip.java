import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. It is your decision whether SpaceShip.java
 * will be an interface, an abstract class, a base class for the other spaceships or any other option you will
 * choose.
 * @author oop
 */
public class SpaceShip {

	/** energy increases/ decreases when colliding: */
	final private static int SHIELD_COLLISION = 18;
	final private static int NO_SHIELD_COLLISION = 10;

	/** the time added to cool down when exceeding consecutive shots threshold */
	final private static int COOL_DOWN_TIME = 7;

	/** represents 0 health */
	final private static int DEAD = 0;

	/** value to decrease from health */
	final private static int HEALTH_LOSS = 1;

	/** these are the energy "prices" of each action */
	final private static int SHOT_PRICE = 19;
	final private static int TELEPORT_PRICE = 140;
	final private static int SHIELD_PRICE = 3;

	/** the energy and health of initialization */
	private final static int DEFAULT_ENERGY = 210;
	private static final int DEFAULT_HEALTH = 22;

	/** the image of the spaceship with and without a shield */
	public Image image;
	public Image shieldImage;

	/** the representation of position, direction and velocity of the ship */
	private SpaceShipPhysics physics;

	/** The maximal energy level (starts at 210) */
	private int maxEnergy;

	/** current energy and health level */
	private int energy;
	private int health;

	/** the cooldown between two consecutive shots */
	private int coolDown;

	/** true = the shield is on. false = shield is off */
	private boolean shield;


	/** constructor for a space ship instance */
	SpaceShip() {
		this.image = GameGUI.ENEMY_SPACESHIP_IMAGE;
		this.shieldImage = GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
		this.physics = new SpaceShipPhysics();
		this.maxEnergy = DEFAULT_ENERGY;
		this.energy = maxEnergy;
		this.health = DEFAULT_HEALTH;
		this.shield = false;
		this.coolDown = 0;
	}

	/**
	 * getter for shield
	 * @return shield
	 */
	public boolean isShield() {
		return shield;
	}

	/**
	 * setter for shield
	 * @param shield - some boolean value representing shield
	 */
	public void setShield(boolean shield) {
		this.shield = shield;
	}

	/**
	 * getter for cool down time
	 * @return cool down
	 */
	public int getCoolDown() {
		return coolDown;
	}

	/**
	 * sets cool down time to some given time
	 * @param coolDown - some time to set cool down time to
	 */
	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
	}

	/**
	 * setter for energy
	 * @param energy - some energy to assign to this.energy
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * getter for energy
	 * @return energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * getter for the maximum energy
	 * @return - max energy
	 */
	public int getMaxEnergy() {
		return maxEnergy;
	}

	/**
	 * Does the actions of this ship for this round: 1. disables shield (and updates GUI) 2. checks the
	 * shooting cool-down This is called once per round by the SpaceWars game driver. note these actions are
	 * relevant for all ships BUT human (which we initialize with SPACESHIP_IMAGE)
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
		if (shield) {
			shield = false;
			image = GameGUI.ENEMY_SPACESHIP_IMAGE;
		}
		if (coolDown > 0) {
			coolDown--;
		}
	}

	/**
	 * This method is called every time a collision with this ship occurs
	 */
	public void collidedWithAnotherShip() {
		if (shield) {
			maxEnergy += SHIELD_COLLISION;
			energy += SHIELD_COLLISION;
		} else {
			maxEnergy -= NO_SHIELD_COLLISION;
			if (energy > maxEnergy) {
				energy = maxEnergy;
				health--;
			}
		}
	}

	/**
	 * This method is called whenever a ship has died. It resets the ship's attributes, and starts it at a
	 * new
	 * random position.
	 */
	public void reset() {
		this.physics = new SpaceShipPhysics();
		this.maxEnergy = DEFAULT_ENERGY;
		this.energy = maxEnergy;
		this.health = DEFAULT_HEALTH;
		this.shield = false;
		this.coolDown = 0;
	}

	/**
	 * Checks if this ship is dead.
	 * @return true if the ship is dead. false otherwise.
	 */
	public boolean isDead() {
		return health <= DEAD;
	}

	/**
	 * Gets the physics object that controls this ship.
	 * @return the physics object that controls the ship.
	 */
	public SpaceShipPhysics getPhysics() {
		return physics;
	}

	/**
	 * This method is called by the SpaceWars game object when ever this ship gets hit by a shot.
	 */
	public void gotHit() {
		if (!shield) {
			maxEnergy -= NO_SHIELD_COLLISION;
			health -= HEALTH_LOSS;
		}
	}

	/**
	 * Gets the image of this ship. This method should return the image of the ship with or without the
	 * shield. This will be displayed on the GUI at the end of the round.
	 * @return the image of this ship.
	 */
	public Image getImage() {
		if(shield){
			return shieldImage;
		}
		return image;
	}

	/**
	 * Attempts to fire a shot.
	 * @param game the game object.
	 */
	public void fire(SpaceWars game) {
		if (energy >= SHOT_PRICE && coolDown <= 0) {
			game.addShot(physics);
			energy -= SHOT_PRICE;
			coolDown = COOL_DOWN_TIME;
		}
	}

	/**
	 * Attempts to turn on the shield.
	 */
	public void shieldOn() {
		if (energy >= SHIELD_PRICE) {
			shield = true;
			energy -= SHIELD_PRICE;
			image = GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
		}
	}

	/**
	 * Attempts to teleport.
	 */
	public void teleport() {
		if (energy >= TELEPORT_PRICE) {
			physics = new SpaceShipPhysics();
			energy -= TELEPORT_PRICE;
		}
	}

}
