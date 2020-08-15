import oop.ex2.GameGUI;

import java.util.zip.ZipEntry;

/**
 * a class that represents a spaceship of type human
 */
public class Human extends SpaceShip {
    Human() {
        this.image = GameGUI.SPACESHIP_IMAGE;
    }

    /**
     * performs all possible actions of a human ship
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        GameGUI gui = game.getGUI();
        int angle = 0;
        boolean accelerate = false;
        if (shield) {
            shield = false;
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
        if (coolDown > ZERO_COOLDOWN) {
            coolDown--;
        }
        if(energy < maxEnergy){
            energy++;
        }
        getPhysics().move(accelerate, angle);
    }
}
