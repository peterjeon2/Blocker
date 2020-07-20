package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Test {

    public static void main(String[] args) {

        Game game = new Game();
        game.playWithKeyboard();




/*
        TERenderer ter = new TERenderer();
        ter.initialize(100,55);
        World world = new World(100, 55, Game.processInput("345"));
        TETile[][] world2 = world.generateWorld(ter, Game.processInput("12345"));
        Player player = new Player();
        world2[player.getCurrPos().getX()][player.getCurrPos().getY()] = Tileset.PLAYER;
        ter.renderFrame(world2);


 */



    }
}
