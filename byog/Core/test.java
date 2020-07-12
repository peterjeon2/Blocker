package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.Core.Room;

import java.util.Random;

public class test {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Game.fillEmpty(world);
        room2 room = new room2();
        room2.drawRoom(world, room);
        Hallway.makeHallway(world, room.door);
        ter.renderFrame(world);
    }
}
