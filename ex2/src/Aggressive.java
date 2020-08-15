/**
 * a class that represents a spaceship of type Aggresive
 */
public class Aggressive extends SpaceShip {

    private final static double SHOOTING_ANGLE = 0.21;
    /**
     *  enables the ship to pursue other ships and fire at them when the angle
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
        getPhysics().move(true,angle);
        if(Math.abs(angleBetweenShips)<=SHOOTING_ANGLE){
            fire(game);
        }

    }
}
