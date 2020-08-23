
/**
 * An implementation of the Basher spaceship behavior.
 * @author avivz
 */
public class Basher implements Behavior {

    private static final double SHIELD_DISTANCE = 0.2;

    /**
     * does the action of the Basher spaceship for this round.
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
        
        if(ship.getPhysics().distanceFrom(OtherShip.getPhysics())<=
           SHIELD_DISTANCE){
            ship.shieldOn();
        }
    }

}
