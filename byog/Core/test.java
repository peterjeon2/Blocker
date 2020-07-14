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
        room.drawRoom(world);
        Hallway h = Hallway.makeHallway(world, room.exitDoor);
        Hallway.drawHallway(world, h);
        Hallway h2 = Hallway.makeHallway(world, h.exitDoor);
        Hallway.drawHallway(world, h2);
        Hallway h3 = Hallway.makeHallway(world, h2.exitDoor);
        Hallway.drawHallway(world, h3);
        Room room2 = Room.makeRoom(world, h3.exitDoor);
        room2.drawRoom(world);
        Hallway h4 = Hallway.makeHallway(world, room2.exitDoor);
        Hallway.drawHallway(world, h4);
        Hallway h5= Hallway.makeHallway(world, h4.exitDoor);
        Hallway.drawHallway(world, h5);
        Hallway h6 = Hallway.makeHallway(world, h5.exitDoor);
        Hallway.drawHallway(world, h6);








        /*
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
        */
        ter.renderFrame(world);

    }
}
