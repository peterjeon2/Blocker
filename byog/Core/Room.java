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
    private static final long SEED =113;
    public static final Random RANDOM = new Random(SEED);

    public Room() {

    }

    public static Room makeFirstRoom(TETile[][] world) {
        Room r = new Room();
        makeShape(r);
        r.botLeftCorn = new Position(25, 25);
        r.botRightCorn = new Position(r.botLeftCorn.x + r.width - 1, r.botLeftCorn.y);
        r.uppLeftCorn = new Position(r.botLeftCorn.x, r.botLeftCorn.y + r.height - 1);
        r.uppRightCorn = new Position(r.botRightCorn.x, r.uppLeftCorn.y);
        r.exitDoor = makeDoor(r);
        Position.checkOverlap(world, r);
        return r;
    }

    public static Room makeRoom(TETile[][] world, Door entranceDoor) {
        Room r = new Room();
        makeShape(r);
        int xOffset = RandomUtils.uniform(RANDOM, r.width/2);
        int yOffset = RandomUtils.uniform(RANDOM, r.height/2);
        if (entranceDoor.orientation.equals("Bottom")) {
            r.uppLeftCorn = new Position (entranceDoor.doorP.x - xOffset, entranceDoor.doorP.y - 1);
            r.uppRightCorn = new Position (r.uppLeftCorn.x + r.width - 1, r.uppLeftCorn.y);
            r.botLeftCorn = new Position (r.uppLeftCorn.x, r.uppLeftCorn.y - r.height);
            r.botRightCorn = new Position (r.uppRightCorn.x, r.botLeftCorn.y);
            r.exitDoor = makeDoor(r);
            while (r.exitDoor.doorP == new Position(entranceDoor.doorP.x, entranceDoor.doorP.y - 1)) {
                r.exitDoor = makeDoor(r);
            }
        }
        entranceDoor.connected = true;
        return r;
    }

    public static void drawRoom(TETile[][] world, Room room) {
        Position p = room.botLeftCorn;
        for (int x = 0; x < room.width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < room.height; y += 1) {
                int yCoord = p.y + y;
                if ((xCoord == p.x) || (xCoord == (p.x + room.width - 1)) ||
                        (yCoord == p.y) || (yCoord == (p.y + room.height - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
        if (room.entranceDoor != null) {
            world[room.entranceDoor.doorP.x][room.entranceDoor.doorP.y] = Tileset.FLOOR;
        }
        if (room.exitDoor != null) {
            world[room.exitDoor.doorP.x][room.exitDoor.doorP.y] = Tileset.FLOOR;
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


    public static Door makeDoor(Room room){
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0:
                int x0 = RandomUtils.uniform(RANDOM, room.botLeftCorn.x + 1, room.botRightCorn.x - 1);
                int y0 = room.botLeftCorn.y;
                return new Door("Bottom", new Position(x0, y0));
            case 1:
                int x1= RandomUtils.uniform(RANDOM, room.botLeftCorn.x + 1, room.botRightCorn.x - 1);
                int y1 = room.uppLeftCorn.y;
                return new Door("Top", new Position(x1, y1));
            case 2:
                int x2 = room.botLeftCorn.x;
                int y2 = RandomUtils.uniform(RANDOM, room.botLeftCorn.y + 1, room.uppLeftCorn.y - 1);
                return new Door("Left", new Position(x2, y2));
            case 3:
                int x3 = room.botRightCorn.x;
                int y3 = RandomUtils.uniform(RANDOM, room.botLeftCorn.y + 1, room.uppLeftCorn.y - 1);
                return new Door("Right", new Position(x3,y3));
            default:
                return null;
            }
        }
}

