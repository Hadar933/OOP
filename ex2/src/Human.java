import oop.ex2.GameGUI;

/**
 * a class that represents a spaceship of type human
 */
public class Human extends SpaceShip {
    /**
     * performs all possible actions of a human ship
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        GameGUI gui = game.getGUI();
        image = GameGUI.SPACESHIP_IMAGE;
        int angle = 0;
        boolean accelerate = false;
        if (isShield()) {
            setShield(false);
            image = GameGUI.SPACESHIP_IMAGE;
        }
        if (gui.isShieldsPressed()) {
            shieldOn();
            image = GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        if (gui.isTeleportPressed()) {
            teleport();
        }
        if (gui.isShotPressed()) {
            fire(game);
        }
        if (gui.isRightPressed()) {
            angle--;
        }
        if (gui.isLeftPressed()) {
            angle++;
        }
        if(gui.isUpPressed()){
            accelerate = true;
        }
        if (getCoolDown() > 0) {
            setCoolDown(getCoolDown()-1);
        }
        if(getEnergy() < getMaxEnergy()){
            setEnergy(getEnergy()+1);
        }
        getPhysics().move(accelerate, angle);
    }
}
