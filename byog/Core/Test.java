package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Test {

    public static void main(String[] args) {

        Game game = new Game();
        TETile[][] worldState = game.playWithInputString("Ld:Q");
        TERenderer te = new TERenderer();
        te.initialize(80, 50);
        te.renderFrame(worldState);
        System.out.println(TETile.toString(worldState));






        /*
        TERenderer ter = new TERenderer();
        ter.initialize(100,55);
        Game game = new Game();
        game.newGame(Game.processInput("1234"));
        TETile[][] world = World.generateWorld
        /*
        World world = new World(100, 55, Game.processInput("12345"));
        TETile[][] world2 = world.generateWorld(ter, Game.processInput("12345"));

        Player player = new Player();
        world2[player.getCurrPos().getX()][player.getCurrPos().getY()] = Tileset.PLAYER;


        ter.renderFrame(Game.finalWorldFrame);

 */






    }
}
