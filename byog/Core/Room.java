package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * This class provides methods that construct and draw
 * randomly generated rooms in the world.
 * The game class calls these methods to build out the world.
 */
public class Room {
    private int width;
    private int height;
    private Position botLeftCorn;
    private Position botRightCorn;
    private Position uppLeftCorn;
    private Position uppRightCorn;
    private Position[] corners;
    private Door door;
    private static Room[] neighbors;
    private int neighborCount;

    public Room() {
        neighbors = new Room[4];
        neighborCount = 0;
        corners = new Position[4];
    }

    public class Door {
        private Position doorP;
        public Door(Position p) {
            doorP = p;
        }
        public Position getDoorP() {
            return doorP;
        }
    }

    public Position getBotLeftCorn() {
        return botLeftCorn;
    }
    public Position[] getCorners() {
        return corners;
    }

    public Door getDoor() {
        return door;
    }

    public Room[] getNeighbors() {
        return neighbors;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /* Creates a door in a room. A door is used as a starting
        position to connect to another room's door. */
    public void setDoor(Random random) {
        int x0 = getBotLeftCorn().getX() + RandomUtils.uniform(random, 1, width - 1);
        int y0 = getBotLeftCorn().getY() + RandomUtils.uniform(random, 1, height - 1);
        door = new Door(new Position(x0, y0));
    }

    public void setCorners() {
        botRightCorn = new Position(botLeftCorn.getX() + width - 1, botLeftCorn.getY());
        uppLeftCorn = new Position(botLeftCorn.getX(), botLeftCorn.getY() + height - 1);
        uppRightCorn = new Position(botRightCorn.getX(), uppLeftCorn.getY());
    }

    public static Room makeRoom(TETile[][] world, Random random) {
        Room r = new Room();
        r.makeShape(random);
        r.botLeftCorn = new Position(RandomUtils.uniform(random, 80),
                RandomUtils.uniform(random, 45));
        r.setCorners();
        r.corners = new Position[] {r.botLeftCorn, r.botRightCorn, r.uppLeftCorn, r.uppRightCorn};
        r.setDoor(random);
        Position.checkOverlap(world, r);
        return r;
    }

    public void addNeighbor(Room room) {
        /* Finds and stores neighboring rooms. */
        while (neighborCount < neighbors.length) {
            neighbors[neighborCount] = room;
            neighborCount++;
        }
    }

    public void connectRooms(TETile[][] world, Room neighborRoom, Random random) {
        /* Creates a connection between a room and a neighboring room.
        * It can also create a second connection randomly
        */
        connectRoomHelper(world, this, neighborRoom);
        int n = RandomUtils.uniform(random, 2);
        if (n == 1) {
            connectRoomHelper(world, neighborRoom, this);
        }
    }

    public void connectRoomHelper(TETile[][] world, Room room, Room neighborRoom) {
        /* This function draws a hallway between a room and a neighboring room. */
        int posX = room.getDoor().getDoorP().getX();
        int posY = neighborRoom.getDoor().getDoorP().getY();
        if (room.getDoor().getDoorP().getY() == neighborRoom.getDoor().getDoorP().getY()) {
            posX = neighborRoom.getDoor().getDoorP().getX();
        }
        Position connectPoint = new Position(posX, posY);
        Hallway.drawHallways(world, connectPoint, room, neighborRoom);
        world[posX][posY] = Tileset.FLOOR;
    }

    public void drawRoom(TETile[][] world) {
        Position p = getBotLeftCorn();
        for (int x = 0; x < getWidth(); x++) {
            int xCoord = p.getX() + x;
            for (int y = 0; y < getHeight(); y++) {
                int yCoord = p.getY() + y;
                if ((xCoord == p.getX())
                        || (xCoord == (p.getX() + getWidth() - 1))
                        || (yCoord == p.getY())
                        || (yCoord == (p.getY() + getHeight() - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
        world[getDoor().getDoorP().getX()][getDoor().getDoorP().getY()] = Tileset.FLOOR;
    }

    public void makeShape(Random random) {
        /* Randomly chooses square or rectangle shaped room */
        int tileNum = random.nextInt(2);
        if (tileNum == 0) {
            width = RandomUtils.uniform(random, 4, 12);
            height = getWidth();
        } else {
            width = RandomUtils.uniform(random, 4, 12);
            height = RandomUtils.uniform(random, 4, 12);
        }
    }
}
