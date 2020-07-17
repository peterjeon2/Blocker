package byog.Core;

import byog.TileEngine.TETile;

public class Test {

    public static void main(String[] args) {

        Game game = new Game();
        TETile[][] world = game.playWithInputString("1188345");
        game.ter.renderFrame(world);

    }
}
