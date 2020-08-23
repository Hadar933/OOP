import oop.ex2.*;

/**
 * An implementation of the behavior of a human controlled spaceship.  This
 * implementation takes the actions from the user via the GameGUI.
 * @author oop
 */
public class HumanControl implements Behavior {

    /**
     * Does the actions for the round according to the keys pressed.
     */
    public void doAction(SpaceShip ship, SpaceWars game) {
        GameGUI gui = game.getGUI();
        
        //teleporting:
        if(gui.isTeleportPressed()){
            ship.teleport();
        }

        //deciding on the move:
        int turn = 0;
        if(gui.isLeftPressed()){
            turn++;
        }
        if(gui.isRightPressed()){
            turn--;
        }        
        ship.getPhysics().move(gui.isUpPressed(), turn);
                
        //checking if the shield gets raised:
        if(gui.isShieldsPressed()){
            ship.shieldOn();
        }
        
        //checking if the user wants to fire:
        if(gui.isShotPressed()){
            ship.fire(game);
        }
    }
}
