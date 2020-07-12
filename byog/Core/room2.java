package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class room2 {

    private int width;
    private int height;
    private Position bottomLeftCorner;
    private Position bottomRightCorner;
    private Position upperLeftCorner;
    private Position upperRightCorner;
    public Door door;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    public room2(){
        makeShape(this);
        bottomLeftCorner = new Position(RandomUtils.uniform(RANDOM, 20),RandomUtils.uniform(RANDOM, 20));
        bottomRightCorner = new Position(bottomLeftCorner.x + width - 1, bottomLeftCorner.y);
        upperLeftCorner = new Position(bottomLeftCorner.x, bottomLeftCorner.y + height - 1);
        upperRightCorner = new Position(bottomRightCorner.x, upperLeftCorner.y);
        door = makeDoor(this);
    }

    public static void drawRoom(TETile[][] world, room2 room) {
        Position p = room.bottomLeftCorner;
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
        world[room.door.doorP.x][room.door.doorP.y] = Tileset.FLOOR;
    }

    public static void makeShape(room2 room) {
        double n =  RandomUtils.uniform(RANDOM, 1);
        /* make square or rectangle shape*/
        if (n < 0.5) {
            room.width = RandomUtils.uniform(RANDOM, 4, 10);
            room.height = room.width;
        } else {
            room.width = RandomUtils.uniform(RANDOM, 4, 10);
            room.height = RandomUtils.uniform(RANDOM, 4, 10);
        }
    }

    public static class Door {
        public Position doorP;
        public String orientation;
        public Door(String o, Position p){
            doorP = p;
            orientation = o;
        }
    }


    public static Door makeDoor(room2 room){
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0:
                int x0 = RandomUtils.uniform(RANDOM, room.bottomLeftCorner.x + 1, room.bottomRightCorner.x - 1);
                int y0 = room.bottomLeftCorner.y;
                return new Door("Bottom", new Position(x0, y0));
            case 1:
                int x1= RandomUtils.uniform(RANDOM, room.bottomLeftCorner.x + 1, room.bottomRightCorner.x - 1);
                int y1 = room.upperLeftCorner.y;
                return new Door("Top", new Position(x1, y1));
            case 2:
                int x2 = room.bottomLeftCorner.x;
                int y2 = RandomUtils.uniform(RANDOM, room.bottomLeftCorner.y + 1, room.upperLeftCorner.y - 1);
                return new Door("Left", new Position(x2, y2));
            case 3:
                int x3 = room.bottomRightCorner.x;
                int y3 = RandomUtils.uniform(RANDOM, room.bottomLeftCorner.y + 1, room.upperLeftCorner.y - 1);
                return new Door("Right", new Position(x3,y3));
            default:
                return null;
            }
        }
}

