package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {

    private int width;
    private int height;
    public Position botLeftCorn;
    public Position botRightCorn;
    public Position uppLeftCorn;
    public Position uppRightCorn;
    public Door entranceDoor;
    public Door exitDoor;
    private Position doorPos;
    private static final long SEED = 561343;
    public static final Random RANDOM = new Random(SEED);

    public Room() {

    }

    public static Room makeFirstRoom(TETile[][] world) {
        Room r = new Room();
        makeShape(r);
        r.botLeftCorn = new Position(25, 25);
        r.setCorners();
        r.exitDoor = r.makeDoor();
        Position.checkOverlap(world, r);
        return r;
    }

    public void setCorners() {
        botRightCorn = new Position(botLeftCorn.x + width - 1, botLeftCorn.y);
        uppLeftCorn = new Position(botLeftCorn.x, botLeftCorn.y + height - 1);
        uppRightCorn = new Position(botRightCorn.x, uppLeftCorn.y);
    }

    public static Room makeRoom(TETile[][] world, Door entranceDoor) {
        Room r = new Room();
        makeShape(r);
        int xOffset = RandomUtils.uniform(RANDOM, r.width/2);
        int yOffset = RandomUtils.uniform(RANDOM, r.height/2);
        if (entranceDoor.orientation.equals("corBot")) {
            r.entranceDoor = new Door("Top", new Position(entranceDoor.doorP.x,entranceDoor.doorP.y - 1));
            r.botLeftCorn = new Position(r.entranceDoor.doorP.x - xOffset - 1, r.entranceDoor.doorP.y - r.height + 1);
        } else if (entranceDoor.orientation.equals("corRight")) {
            r.entranceDoor = new Door("Left", new Position(entranceDoor.doorP.x + 1, entranceDoor.doorP.y));
            r.botLeftCorn = new Position(r.entranceDoor.doorP.x, r.entranceDoor.doorP.y - yOffset - 1);
        } else if (entranceDoor.orientation.equals("corLeft")) {
            r.entranceDoor = new Door("Right", new Position(entranceDoor.doorP.x - 1, entranceDoor.doorP.y));
            r.botLeftCorn = new Position(r.entranceDoor.doorP.x - r.width + 1, r.entranceDoor.doorP.y - yOffset - 1);
        } else {
            r.entranceDoor = new Door("Bottom", new Position(entranceDoor.doorP.x, entranceDoor.doorP.y + 1));
            r.botLeftCorn = new Position(r.entranceDoor.doorP.x - xOffset - 1, r.entranceDoor.doorP.y);
        }
        r.setCorners();
        r.exitDoor = r.makeDoor();
        while (r.exitDoor.doorP.x == r.entranceDoor.doorP.x && r.exitDoor.doorP.y == r.entranceDoor.doorP.y) {
            r.exitDoor = r.makeDoor();
        }
        entranceDoor.connected = true;
        Position.checkOverlap(world, r);
        return r;
    }

    public void drawRoom(TETile[][] world) {
        Position p = botLeftCorn;
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.y + y;
                if ((xCoord == p.x) || (xCoord == (p.x + width - 1)) ||
                        (yCoord == p.y) || (yCoord == (p.y + height - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
        if (entranceDoor != null) {
            world[entranceDoor.doorP.x][entranceDoor.doorP.y] = Tileset.FLOOR;
        }
        if (exitDoor != null) {
            world[exitDoor.doorP.x][exitDoor.doorP.y] = Tileset.FLOOR;
        }

    }

    public static void makeShape(Room room) {
        int tileNum = RANDOM.nextInt(2);
        /* make square or rectangle shape*/
        if (tileNum == 0) {
            room.width = RandomUtils.uniform(RANDOM, 4, 8);
            room.height = room.width;
        } else {
            room.width = RandomUtils.uniform(RANDOM, 4, 8);
            room.height = RandomUtils.uniform(RANDOM, 4, 8);
        }
    }


    public static class Door {
        public Position doorP;
        public String orientation;
        public boolean connected;
        public Door(String o, Position p) {
            doorP = p;
            orientation = o;
            connected = false;
        }
    }


    public Door makeDoor(){
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0:
                int x0 = RandomUtils.uniform(RANDOM, botLeftCorn.x + 1, botRightCorn.x - 1);
                int y0 = botLeftCorn.y;
                return new Door("Bottom", new Position(x0, y0));
            case 1:
                int x1= RandomUtils.uniform(RANDOM, botLeftCorn.x + 1, botRightCorn.x - 1);
                int y1 = uppLeftCorn.y;
                return new Door("Top", new Position(x1, y1));
            case 2:
                int x2 = botLeftCorn.x;
                int y2 = RandomUtils.uniform(RANDOM, botLeftCorn.y + 1, uppLeftCorn.y - 1);
                return new Door("Left", new Position(x2, y2));
            case 3:
                int x3 = botRightCorn.x;
                int y3 = RandomUtils.uniform(RANDOM, botLeftCorn.y + 1, uppLeftCorn.y - 1);
                return new Door("Right", new Position(x3,y3));
            default:
                return null;
            }
        }
}

