import java.util.Random;

/**
 * a class that represents a spaceship of type Drunkard
 */
public class Drunkard extends SpaceShip {
	/**
	 * will make the ship move in the other direction from the closest ship and shoot randomly
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		super.doAction(game);
		int angle = 0;
		SpaceShip other = game.getClosestShipTo(this);
		double angleBetweenShips = getPhysics().angleTo(other.getPhysics());
		if (angleBetweenShips > 0) {
			angle--;
		} else if (angleBetweenShips < 0) {
			angle++;
		}
		getPhysics().move(true, angle);
		Random distance = new Random();
		if (getPhysics().distanceFrom(other.getPhysics()) < distance.nextDouble()) {
			fire(game);
		}
	}
}
