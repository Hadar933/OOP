
/**
 * Represents the aggressive spaceship behavior. 
 * @author avivz
 */
public class Aggressive implements Behavior {

    /** the difference angle at which the ship opens fire. */
    private static final double FIRING_ANGLE = 0.2;

    /**
     * performs the actions of the aggressive ship for this round.
     */
    public void doAction(SpaceShip ship, SpaceWars game) {
        SpaceShip OtherShip = game.getClosestShipTo(ship);
        double angle = ship.getPhysics().angleTo(OtherShip.getPhysics());
        
        int turn=0;
        if (angle<0){
            turn--;
        } 
        else if (angle>0){
            turn ++;
        }
        ship.getPhysics().move(true, turn);
        
        if(Math.abs(angle)<FIRING_ANGLE){
            ship.fire(game);
        }
    }
}
