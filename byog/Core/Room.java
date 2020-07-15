package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Arrays;
import java.util.Random;

public class Room {

    private int width;
    private int height;
    public Position botLeftCorn;
    public Position botRightCorn;
    public Position uppLeftCorn;
    public Position uppRightCorn;
    public Door door;
    public static Room[] neighbors;
    public static Room[] connected;
    public int neighborCount;
    private static final long SEED = 36711121;
    public static final Random RANDOM = new Random(SEED);

    public Room() {
        neighbors = new Room[3];
    }
    /*
    public static Room Expression(TETile[][] world, Room room){
        int n = RandomUtils.uniform(RANDOM, 2);
        if (n == 0) {
            Room r = makeRoom(world, room.door);
            room.drawRoom(world);
            return r;
        } else {
            Hallway r = Hallway.makeHallway(world, room.door);
            r.drawHallway(world);
            return r;
        }
    }
     */

    public void addNeighbor(Room room) {
        neighborCount = 0;
        while (neighborCount < neighbors.length) {
            this.neighbors[neighborCount] = room;
            neighborCount ++;
        }
    }

    public static Room[] getN() {
        return neighbors;
    }

    public static Room makeRoom(TETile[][] world) {
        Room r = new Room();
        makeShape(r);
        r.botLeftCorn = new Position(RandomUtils.uniform(RANDOM, 60), RandomUtils.uniform(RANDOM, 40));
        r.setCorners();
        r.door = r.makeDoor();
        Position.checkOverlap(world, r);
        return r;
    }

    public void setCorners() {
        botRightCorn = new Position(botLeftCorn.x + width - 1, botLeftCorn.y);
        uppLeftCorn = new Position(botLeftCorn.x, botLeftCorn.y + height - 1);
        uppRightCorn = new Position(botRightCorn.x, uppLeftCorn.y);
    }

    public void addConnectPoint(Room neighborRoom, TETile[][] world) {
        /*
        if (Arrays.asList(connected).contains(this) == false && Arrays.asList(neighbors).contains(neighborRoom) == false) {

         */
            int posX = door.doorP.x;
            int posY = neighborRoom.door.doorP.y;
            if (door.doorP.x == neighborRoom.door.doorP.y) {
                posX = neighborRoom.door.doorP.x;
            }
            Game.recordConnection(this);
            Game.recordConnection(neighborRoom);
            Hallway.drawCornerHallway(world, new Position(posX,posY));
            Hallway.drawHorizontalHallway(world, new Position(posX, posY), neighborRoom.door.doorP);
            Hallway.drawVerticalHallway(world, new Position(posX, posY), door.doorP);
            world[posX][posY] = Tileset.WATER;

            /*
        } else {
            return;
        }
             */
    }




    /*
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

     */

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
        world[door.doorP.x][door.doorP.y] = Tileset.WATER;
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
        int x0 = botLeftCorn.x + RandomUtils.uniform(RANDOM, 1, width - 1);
        int y0 = botLeftCorn.y + RandomUtils.uniform(RANDOM, 1, height - 1);
        return new Door("Door", new Position(x0, y0));
    }
}
