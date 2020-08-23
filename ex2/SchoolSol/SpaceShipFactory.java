import oop.ex2.*;

public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
	SpaceShip[] ships=new SpaceShip[args.length];
	for(int i=0;i<ships.length;i++) {
	    if(args[i].equals("h")) {
		ships[i] = new SpaceShip(GameGUI.SPACESHIP_IMAGE,
				     GameGUI.SPACESHIP_IMAGE_SHIELD,
				     new HumanControl());
	    }
	    else if(args[i].equals("d")) {
		ships[i] = new SpaceShip(GameGUI.ENEMY_SPACESHIP_IMAGE,
				     GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD,
				     new Drunkard());
	    }
	    else if(args[i].equals("r")) {
		ships[i] = new SpaceShip(GameGUI.ENEMY_SPACESHIP_IMAGE,
				     GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD,
				     new Runner());
	    }
	    else if(args[i].equals("b")) {
		ships[i] = new SpaceShip(GameGUI.ENEMY_SPACESHIP_IMAGE,
				     GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD,
				     new Basher());
	    }
	    else if(args[i].equals("a")) {
		ships[i] = new SpaceShip(GameGUI.ENEMY_SPACESHIP_IMAGE,
				     GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD,
				     new Aggressive());
	    }
	    else if(args[i].equals("s")) {
		ships[i] = null;
	    }
	}
	return ships;
    }
}
