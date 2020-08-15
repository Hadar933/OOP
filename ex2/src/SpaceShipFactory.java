import oop.ex2.*;

public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] allShips = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("h")){
                Human humanShip = new Human();
                allShips[i] = humanShip;
            }
            else if(args[i].equals("r")){
                Runner runnerShip = new Runner();
                allShips[i] = runnerShip;
            }
            else if(args[i].equals("b")){
                Basher basherShip = new Basher();
                allShips[i] = basherShip;
            }
            else if(args[i].equals("a")){
                Aggressive aggressiveShip = new Aggressive();
                allShips[i] = aggressiveShip;
            }
            else if(args[i].equals("d")){
                Drunkard drunkShip = new Drunkard();
                allShips[i] = drunkShip;
            }
            else if(args[i].equals("s")){
                Special specialShip = new Special();
                allShips[i] = specialShip;
            }
            else{
                allShips[i] = null;
            }
        }
        return allShips;
    }
}
