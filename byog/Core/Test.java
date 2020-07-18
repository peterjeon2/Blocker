package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Test {

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        Game game = new Game();
        ter.renderFrame(game.playWithInputString("L442123:Q"));

    }
}
