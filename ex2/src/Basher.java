/**
 *  * a class that represents a spaceship of type Basher
 */
public class Basher extends SpaceShip {
    private final static double SHIELD_RANGE = 0.19;
    /**
     *  enables the ship to pursue other ships and use shield once close
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        super.doAction(game);
        int angle = 0;
        SpaceShip other = game.getClosestShipTo(this);
        double angleBetweenShips = getPhysics().angleTo(other.getPhysics());
        if(angleBetweenShips>0){
            angle++;
        }
        else if(angleBetweenShips<0){
            angle--;
        }
        getPhysics().move(true, angle);
        if(getPhysics().distanceFrom(other.getPhysics()) <= SHIELD_RANGE){
            shieldOn();
        }


    }
}
