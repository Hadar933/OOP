import oop.ex2.GameGUI;

import java.util.zip.ZipEntry;

public class Human extends SpaceShip {
    Human(){
        this.image = GameGUI.SPACESHIP_IMAGE;
    }

    @Override
    public void doAction(SpaceWars game) {
        if(shield){
            shield = false;
            image = GameGUI.SPACESHIP_IMAGE;
        }
        GameGUI gui = game.getGUI();
        if(coolDown> ZERO_COOLDOWN){
            coolDown--;
        }
    }
}
