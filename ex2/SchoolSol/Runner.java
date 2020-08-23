/**
 * An implementation of the Runner spaceship behavior.
 * @author avivz
 */
public class Runner implements Behavior {

    /** The distance from the closest ship that triggers teleportation.*/
    private final static double TELEPORT_DISTANCE=0.2;
    private static final double TELEPORT_ANGLE = 0.2;
    
    /**
     * Does the actions for this round.
     */
    public void doAction(SpaceShip ship, SpaceWars game) {
        SpaceShip OtherShip = game.getClosestShipTo(ship);

        //check if we want to teleport:
        if(ship.getPhysics().distanceFrom(OtherShip.getPhysics()) <= TELEPORT_DISTANCE && 
        		Math.abs(OtherShip.getPhysics().angleTo(ship.getPhysics())) < TELEPORT_ANGLE){
            ship.teleport();
        }

        //run away from the closes ship:
        double angle = ship.getPhysics().angleTo(OtherShip.getPhysics());
        int turn=0;
        if (angle>0){
            turn--;
        } 
        else if (angle<0){
            turn ++;
        }
        ship.getPhysics().move(true, turn);
        
    }
}
