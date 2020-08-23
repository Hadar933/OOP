/**
 * The interface that represents the behavior of a spaceship.
 * @author oop
 */
public interface Behavior {
    
    /**
     * does the action of the spaceship for this round.
     * @param ship the spaceship that is acting. 
     * @param game the spacewars game we are playing in.
     */
    public void doAction(SpaceShip ship, SpaceWars game);
}
