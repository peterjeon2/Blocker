package byog.Core;

import byog.TileEngine.TERenderer;

public class Test {

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(100, 55);
        Game game = new Game();
        ter.renderFrame(game.playWithInputString("L:Q"));

    }
}
