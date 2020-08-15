import java.util.Random;

/**
 * a class that represents a spaceship of type Special
 */
public class Special extends SpaceShip {
	private final static double MIN_DISTANCE = 0.35;

	/**
	 * the special ship has a defence mode in which when ever some ship is too close, it automatically
	 * recognises it and shoots towards the ship. other then that, it moves freely
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		super.doAction(game);
		int[] angleOptions = {-1, 0, 1};
		int index = new Random().nextInt(angleOptions.length);
		getPhysics().move(true, angleOptions[index]);

		SpaceShip other = game.getClosestShipTo(this);
		double distance = getPhysics().distanceFrom(other.getPhysics());
		double angleBetweenShips = getPhysics().angleTo(other.getPhysics());
		int newAngle = 0;
		if (distance <= MIN_DISTANCE) {

			if (angleBetweenShips > 0) {
				newAngle++;
			} else if (angleBetweenShips < 0) {
				newAngle--;
			}
			getPhysics().move(true, newAngle);
			fire(game);
		}
	}
}
