package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Random;

public class test {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Game.fillEmpty(world);
        Game.generateRooms(world);
        Game.findNeighbors();
        Game.generateHallways(world);
        ter.renderFrame(world);
    }
}
