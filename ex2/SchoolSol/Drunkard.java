import java.util.*;

/**
 * An implementation of the Drunkard spaceship behavior.  
 * @author avivz
 */
public class Drunkard implements Behavior {

	/** The percentage that the ship will move forward */
	private static final int MOVE_PERCENTAGE = 60;
	private static final int TURN_LEFT_PERCENTAGE = 5;
	private static final int TURN_RIGHT_PERCENTAGE = 90;
	private static final int SHOOT_PERCENTAGE = 2;
	
    /**
     * Does the action of the Drunkard spaceship (which does nothing).
     */
    public void doAction(SpaceShip ship, SpaceWars game) {
    	Random generator = new Random();
    	boolean move = generator.nextInt(100) < MOVE_PERCENTAGE;
    	int directionDistribution = generator.nextInt(100);
    	int directionFlag = 0;
    	if(directionDistribution < TURN_LEFT_PERCENTAGE)
    		directionFlag = 1;
    	else if(directionDistribution < TURN_LEFT_PERCENTAGE+TURN_RIGHT_PERCENTAGE)
    		directionFlag = -1;
    	ship.getPhysics().move(move, directionFlag);
    	if(generator.nextInt(100) < SHOOT_PERCENTAGE)
    		ship.fire(game);
    }
}
