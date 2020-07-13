package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class test {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Game.fillEmpty(world);
        Room room = Room.makeFirstRoom(world);
        Room.drawRoom(world, room);


        int count = 0;
        int maxTries = 100;
        boolean cond = false;
        for (int i = 0; i < 3; i++) {
            while (!cond) {
                try {
                    Hallway h = Hallway.makeHallway(world, room.exitDoor);
                    Hallway.drawHallway(world, h);
                    room = h;
                    cond = true;
                } catch (RuntimeException e) {
                    count ++;
                    if (count == maxTries) {
                        throw new RuntimeException("Reached the max amount of tries");
                    }
                }
            }
            cond = false;
        }

        ter.renderFrame(world);

    }
}
