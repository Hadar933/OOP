
import java.awt.Image;
import oop.ex2.*;

/**
 * The spaceship object that is used in the SpaceWars game.
 * @author oop
 */
public class SpaceShip{

    /** The initial health of a spaceship */
    private static final int INITIAL_HEALTH = 20;
    
    /** The time that the spaceship must wait between shots. */
    private static final int TIME_BETWEEN_SHOTS = 8;
    
    /** The initial energy of the spaceship. */
    private static final int INITIAL_ENERGY = 200;
    
    /** The amount by which the maximal energy level goes up if the bashes into another
     *  while its shields are up */
    private static final int MAXIMAL_ENERGY_BASHING_INCREAE = 20;
    
    /** The amount by which the maximal energy level goes down if the ship gets hit */
    private static final int MAXIMAL_ENERGY_HIT_REDUCE = 10;
    
    /** The cost of a shot in energy units. */
    private static final int SHOT_ENERGY_COST = 20;
    
    /** The cost of teleporting in energy units. */
    private static final int TELEPORT_ENERGY_COST = 150;
    
    /** The cost of keeping the shield raised in energy units. */
    private static final int SHIELD_ENERGY_COST = 3;

    /** The position and physics of the ship */
    private SpaceShipPhysics pos;
    
    /** The behavior of the spaceship */
    private Behavior behavior;
    
    /** The image of the ship if it is without a shield. */
    private final Image unShieldedImage;
    
    /** The image of the ship with a shield. */
    private final Image shieldImage;
    
    /** The health of the ship. */
    private int health;
    
    /** The energy level of the ship. */
    private int energy;
    
    /** A flag that indicates if the shield has been raised this round. */
    private boolean shieldOn;
    
    /** The time that the ship must wait before its next shot. */
    private int timeToNextShot;
    
    /** The maximal energy level. */
    private int maximalEnergy;
    
    /**
     * Create a new spaceship.
     * 
     * @param unShieldedImage the image of the ship with no shield.
     * @param shieldedImage the image of the ship with a shield.
     * @param behavior the behavior object that controls the ship.
     */
    public SpaceShip(Image unShieldedImage, Image shieldedImage,
                     Behavior behavior){
        this.behavior = behavior;
        this.unShieldedImage = unShieldedImage;
        this.shieldImage = shieldedImage;
        reset();
    }
    
    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        this.shieldOn = false;
        this.behavior.doAction(this, game);
        this.timeToNextShot--;
        if(this.energy<this.maximalEnergy){
            this.energy++;
        }
    }

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
    	if(shieldOn)
    	{
    		this.maximalEnergy += MAXIMAL_ENERGY_BASHING_INCREAE;
    		this.energy += MAXIMAL_ENERGY_BASHING_INCREAE;
    	}    	
        gotHit();
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        this.shieldOn = false;
        this.health = INITIAL_HEALTH;
        this.maximalEnergy = INITIAL_ENERGY;
        this.energy = this.maximalEnergy;
        this.pos = new SpaceShipPhysics();
        this.timeToNextShot = TIME_BETWEEN_SHOTS;
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return this.health<=0;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.pos;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if(!this.shieldOn){
            this.health--;
            this.maximalEnergy -= MAXIMAL_ENERGY_HIT_REDUCE;
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage(){
        return this.shieldOn? this.shieldImage : this.unShieldedImage;
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if(this.timeToNextShot<=0 && this.energy>=SHOT_ENERGY_COST){
            game.addShot(this.pos);
            this.energy -= SHOT_ENERGY_COST;
            this.timeToNextShot = TIME_BETWEEN_SHOTS;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if(this.energy>=SHIELD_ENERGY_COST){
            this.shieldOn = true;
            this.energy -= SHIELD_ENERGY_COST;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if(this.energy>=TELEPORT_ENERGY_COST){
            this.pos = new SpaceShipPhysics();
            this.energy-=TELEPORT_ENERGY_COST;
        }
    }
}
