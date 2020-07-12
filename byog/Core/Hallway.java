package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Hallway {
    private int width;
    private int height;
    private Position botLeftCorn;
    private Position botRightCorn;
    private Position connection;
    private Position uppLeftCorn;
    private Position uppRightCorn;
    private room2.Door entranceDoor;
    private static final long SEED = 2873124;
    private static final Random RANDOM = new Random(SEED);

    public Hallway(room2.Door someDoor) {
        entranceDoor = someDoor;
    }


    public static void makeHallway(TETile[][] world, room2.Door entranceDoor) {
        Hallway h = new Hallway(entranceDoor);
        if (entranceDoor.orientation.equals("Bottom")) {
            makeDownHallway(h, entranceDoor);
            drawVerticalHallway(world, h);
        } else if (entranceDoor.orientation.equals("Top")) {
            makeUpHallway(h, entranceDoor);
            drawVerticalHallway(world, h);
        } else if (entranceDoor.orientation.equals("Left")) {
            makeLeftHallway(h, entranceDoor);
            drawHorizontalHallway(world, h);
        } else {
            makeRightHallway(h, entranceDoor);
            drawHorizontalHallway(world, h);
        }
    }

    public static void makeLeftHallway(Hallway h, room2.Door entranceDoor) {
        h.width = RandomUtils.uniform(RANDOM, 1, 10);
        h.height = 3;
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x - h.width, p.y - 1);
        h.botRightCorn = new Position (p.x - h.width,p.y + 1);
        h.uppRightCorn = new Position (p.x - 1, p.y + 1);

    }

    public static void makeRightHallway(Hallway h, room2.Door entranceDoor) {
        h.width = RandomUtils.uniform(RANDOM, 1, 10);
        h.height = 3;
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x + 1, p.y - 1);
        h.botRightCorn = new Position (p.x + h.width, p.y - 1);
        h.uppLeftCorn = new Position (p.x + 1,p.y + 1);
        h.uppRightCorn = new Position (p.x + h.width, p.y + 1);

    }

    public static void makeDownHallway(Hallway h, room2.Door entranceDoor) {
        h.width = 3;
        h.height = RandomUtils.uniform(RANDOM, 1, 10);
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x - 1, p.y - h.height);
        h.botRightCorn = new Position (p.x + 1, p.y - h.height);
        h.uppLeftCorn = new Position (p.x - 1, p.y - 1);
        h.uppRightCorn = new Position (p.x + 1, p.y - 1);

    }

    public static void makeUpHallway(Hallway h, room2.Door entranceDoor) {
        h.width = 3;
        h.height = RandomUtils.uniform(RANDOM, 1, 10);
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x - 1, p.y + 1);
        h.botRightCorn = new Position (p.x + 1, p.y + 1);
        h.uppLeftCorn = new Position (p.x - 1,p.y + h.height);
        h.uppRightCorn = new Position (p.x + 1, p.y + h.height);
    }

    public static void drawHorizontalHallway(TETile[][] world, Hallway hallway){
        Position p = hallway.botLeftCorn;
        for (int x = 0; x < hallway.width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < hallway.height; y += 1) {
                int yCoord = p.y + y;
                if ((yCoord == p.y) || (yCoord == (p.y + hallway.height - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
    }

    public static void drawVerticalHallway(TETile[][] world, Hallway hallway) {
        Position p = hallway.botLeftCorn;
        for (int x = 0; x < hallway.width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < hallway.height; y += 1) {
                int yCoord = p.y + y;
                if ((xCoord == p.x) || (xCoord == (p.x + hallway.width - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
    }
}
