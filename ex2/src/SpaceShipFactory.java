import oop.ex2.*;

public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] allShips = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "h":
                    Human humanShip = new Human();
                    allShips[i] = humanShip;
                    break;
                case "r":
                    Runner runnerShip = new Runner();
                    allShips[i] = runnerShip;
                    break;
                case "b":
                    Basher basherShip = new Basher();
                    allShips[i] = basherShip;
                    break;
                case "a":
                    Aggressive aggressiveShip = new Aggressive();
                    allShips[i] = aggressiveShip;
                    break;
                case "d":
                    Drunkard drunkShip = new Drunkard();
                    allShips[i] = drunkShip;
                    break;
                case "s":
                    Special specialShip = new Special();
                    allShips[i] = specialShip;
                    break;
                default:
                    allShips[i] = null;
                    break;
            }
        }
        return allShips;
    }
}
