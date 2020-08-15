public class Runner extends SpaceShip {
    private final static double TELEPORT_ANGLE = 0.23;
    private final static double TELEPORT_RANGE = 0.25;

    @Override
    public void doAction(SpaceWars game) {
        super.doAction(game);
        int angle = 0;
        SpaceShip other = game.getClosestShipTo(this);
        double angleBetweenShips = getPhysics().angleTo(other.getPhysics());
        double distanceBetweenShips = getPhysics().distanceFrom(other.getPhysics());
        if (angleBetweenShips > 0) {
            angle--;
        } else if (angleBetweenShips < 0) {
            angle++;
        }
        getPhysics().move(true, angle);
        if (Math.abs(angleBetweenShips) <= TELEPORT_ANGLE && distanceBetweenShips <= TELEPORT_RANGE) {
            teleport();
        }
    }
}
