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
        Game.getNeighbors();
        Game.populateConnectPoints(world);

/*
        Hallway h = Hallway.makeHallway(world, room.exitDoor);
        h.drawHallway(world);
        /*
        Hallway h2 = Hallway.makeHallway(world, h.exitDoor);
        h2.drawHallway(world);
        Hallway h3 = Hallway.makeHallway(world, h2.exitDoor);
        h3.drawHallway(world);
        Room room2 = Room.makeRoom(world, h3.exitDoor);
        room2.drawRoom(world);
        Hallway h4 = Hallway.makeHallway(world, room2.exitDoor);
        h4.drawHallway(world);
        Hallway h5= Hallway.makeHallway(world, h4.exitDoor);
        h5.drawHallway(world);
        Hallway h6 = Hallway.makeHallway(world, h5.exitDoor);
        for int i
        h6.drawHallway(world);
         */








/*
        int count = 0;
        int maxTries = 100;
        boolean cond = false;

        for (int i = 0; i < 20; i++) {
            while (!cond) {
                try {
                    Room r = Room.makeFirstRoom(world);
                    r.drawRoom(world);
                    cond = true;
                    /*
                    if (room instanceof Hallway) {
                        Room r = Room.Expression(world, room);
                        cond = true;
                        room = r;
                    } else {
                        Hallway h = Hallway.makeHallway(world, room.exitDoor);
                        h.drawHallway(world);
                        room = h;
                        cond = true;
                    }

                    /*
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
