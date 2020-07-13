package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Hallway extends Room {
    private int width;
    private int height;
    private Position middle;
    private Position nextToMiddle;


    public Hallway(Room.Door someDoor) {
        entranceDoor = someDoor;
    }


    public static Hallway makeHallway(TETile[][] world, Door entranceDoor) {
        Hallway h = new Hallway(entranceDoor);
        /* Connect a Room to a Hallway */
        try {
            if (entranceDoor.orientation.equals("Bottom")) {
                makeDownHallway(h, entranceDoor);
            } else if (entranceDoor.orientation.equals("Top")) {
                makeUpHallway(h, entranceDoor);
            } else if (entranceDoor.orientation.equals("Left")) {
                makeLeftHallway(h, entranceDoor);
            } else if (entranceDoor.orientation.equals("Right")) {
                makeRightHallway(h, entranceDoor);
                /* Make a Corner to connect Two Hallways */
            } else {
                makeCornerHallway(h, entranceDoor);
            }
        } catch (NullPointerException e) {
            return null;
        }
        entranceDoor.connected = true;
        Position.checkOverlap(world, h);
        return h;
    }

    public static void drawHallway(TETile[][] world, Hallway h) {
        if (h.entranceDoor.orientation.equals("Bottom")) {
            drawVerticalHallway(world, h);
        } else if (h.entranceDoor.orientation.equals("Top")) {
            drawVerticalHallway(world, h);
        } else if (h.entranceDoor.orientation.equals("Left")) {
            drawHorizontalHallway(world, h);
        } else if (h.entranceDoor.orientation.equals("Right")) {
            drawHorizontalHallway(world, h);
        } else {
            drawCornerHallway(world, h);
        }
    }

    public static void makeLeftHallway(Hallway h, Door entranceDoor) {
        Random r = new Random();
        h.width = RandomUtils.uniform(r, 2, 6);
        h.height = 3;
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x - h.width, p.y - 1);
        h.botRightCorn = new Position (p.x - 1,p.y - 1);
        h.uppLeftCorn = new Position (p.x - h.width, p.y + 1);
        h.uppRightCorn = new Position (p.x - 1, p.y + 1);
        h.exitDoor = new Door("corLeft", new Position(h.botLeftCorn.x, p.y));
    }

    public static void makeRightHallway(Hallway h, Door entranceDoor) {
        Random r = new Random();
        h.width = RandomUtils.uniform(r, 2, 7);
        h.height = 3;
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x + 1, p.y - 1);
        h.botRightCorn = new Position (p.x + h.width, p.y - 1);
        h.uppLeftCorn = new Position (p.x + 1,p.y + 1);
        h.uppRightCorn = new Position (p.x + h.width, p.y + 1);
        h.exitDoor = new Door("corRight", new Position(h.botRightCorn.x, p.y));
    }

    public static void makeDownHallway(Hallway h, Door entranceDoor) {
        Random r = new Random();
        h.width = 3;
        h.height = RandomUtils.uniform(r, 2, 7);
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x - 1, p.y - h.height);
        h.botRightCorn = new Position (p.x + 1, p.y - h.height);
        h.uppLeftCorn = new Position (p.x - 1, p.y - 1);
        h.uppRightCorn = new Position (p.x + 1, p.y - 1);
        h.exitDoor = new Door("corDown", new Position(p.x, h.botLeftCorn.y));
    }

    public static void makeUpHallway(Hallway h, Door entranceDoor) {
        Random r = new Random();
        h.width = 3;
        h.height = RandomUtils.uniform(r, 2, 7);
        Position p = entranceDoor.doorP;
        h.botLeftCorn = new Position (p.x - 1, p.y + 1);
        h.botRightCorn = new Position (p.x + 1, p.y + 1);
        h.uppLeftCorn = new Position (p.x - 1,p.y + h.height);
        h.uppRightCorn = new Position (p.x + 1, p.y + h.height);
        h.exitDoor = new Door("corUp", new Position(p.x, h.uppLeftCorn.y));

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

    public static void drawCornerHallway(TETile[][] world, Hallway hallway) {
        Position p = hallway.botLeftCorn;
        for (int x = 0; x < hallway.width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < hallway.height; y += 1) {
                int yCoord = p.y + y;
                world[xCoord][yCoord] = Tileset.WALL;
            }
        }
        world[hallway.exitDoor.doorP.x][hallway.exitDoor.doorP.y] = Tileset.FLOOR;
        world[hallway.middle.x][hallway.middle.y] = Tileset.FLOOR;
        world[hallway.nextToMiddle.x][hallway.nextToMiddle.y] = Tileset.FLOOR;
    }


    public static void makeCornerHallway(Hallway h, Door entranceDoor) {
        h.width = 3;
        h.height = 3;
        double n = RandomUtils.uniform(RANDOM, 1);
        /* make square or rectangle shape*/
        Position p = entranceDoor.doorP;
        if (entranceDoor.orientation.equals("corLeft")) {
            h.botLeftCorn = new Position(p.x - h.width, p.y - 1);
            h.botRightCorn = new Position(p.x - 1, p.y - 1);
            h.uppLeftCorn = new Position(p.x - h.width, p.y + 1);
            h.uppRightCorn = new Position(p.x - 1, p.y + 1);
            h.middle = new Position(p.x - 2, p.y);
            h.nextToMiddle = new Position(p.x - 1, p.y);
            h.exitDoor = new Door("Top", new Position(p.x - 2, p.y + 1));
        } else if (entranceDoor.orientation.equals("corRight")) {
            h.botLeftCorn = new Position(p.x + 1, p.y - 1);
            h.botRightCorn = new Position(p.x + h.width, p.y - 1);
            h.uppLeftCorn = new Position(p.x + 1, p.y + 1);
            h.uppRightCorn = new Position(p.x + h.width, p.y + 1);
            h.middle = new Position(p.x + 2, p.y);
            h.nextToMiddle = new Position(p.x + 1, p.y);
            h.exitDoor = new Door("Bottom", new Position(p.x + 2, p.y - 1));
        } else if (entranceDoor.orientation.equals("corDown")) {
            h.botLeftCorn = new Position(p.x - 1, p.y - h.height);
            h.botRightCorn = new Position(p.x + 1, p.y - h.height);
            h.uppLeftCorn = new Position(p.x - 1, p.y - 1);
            h.uppRightCorn = new Position(p.x + 1, p.y - 1);
            h.middle = new Position(p.x, p.y - 2);
            h.nextToMiddle = new Position(p.x, p.y - 1);
            h.exitDoor = new Door("Left", new Position(p.x - 1, p.y - 2));
        } else {
            h.botLeftCorn = new Position(p.x - 1, p.y + 1);
            h.botRightCorn = new Position(p.x + 1, p.y + 1);
            h.uppLeftCorn = new Position(p.x - 1, p.y + h.height);
            h.uppRightCorn = new Position(p.x + 1, p.y + h.height);
            h.middle = new Position(p.x, p.y + 2);
            h.nextToMiddle = new Position(p.x, p.y + 1);
            h.exitDoor = new Door("Right", new Position(p.x + 1, p.y + 2));
        }
        }
}
