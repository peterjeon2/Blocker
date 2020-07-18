package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class World implements Serializable{
    private static final long serialVersionUID = 123123123123123L;
    private static int WIDTH;
    private static int HEIGHT;
    private static TETile[][] world;
    static Random RANDOM;
    private static Room[] rooms;
    private static int size;

    public World(int w, int h, Long seed) {
        WIDTH = w;
        HEIGHT = h;
        RANDOM = new Random(seed);
    }

    public static void fillEmpty(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void generateRooms(TETile[][] world) {
        size = RandomUtils.uniform(RANDOM, 1, 2);
        rooms = new Room[size];
        int numRooms = 0;
        int count = 0;
        int maxTries = 200;
        while (numRooms < size) {
            try {
                Room newRoom = Room.makeRoom(world, RANDOM);
                rooms[numRooms] = newRoom;
                newRoom.drawRoom(world);
                numRooms++;
            } catch (RuntimeException e) {
                count++;
                if (count == maxTries) {
                    throw new RuntimeException("Reached the max amount of tries");
                }
            }
        }
    }

    public static void findNeighbors() {
        for (Room room : rooms) {
            int neighborCount = room.getNeighborCount();
            for (Room room2 : rooms) {
                Position pos1 = room.getDoor().getDoorP();
                Position pos2 = room2.getDoor().getDoorP();
                if ((Math.abs(pos1.getX() - pos2.getX()) < 10)
                        && (Math.abs(pos1.getY() - pos2.getY()) < 10)) {
                    room.addNeighbor(room2);
                }
            }
        }
    }

    public static void generateHallways(TETile[][] world) {
        for (Room room : rooms) {
            for (Room neighborRoom : room.getNeighbors()) {
                room.connectRooms(world, neighborRoom);
            }
        }
    }

    public static TETile[][] generateWorld(TERenderer ter, Long seed) {
        world = new TETile[WIDTH][HEIGHT];
        fillEmpty(world);
        generateRooms(world);
        findNeighbors();
        generateHallways(world);
        return world;
    }

    public TETile[][] getWorld() {
        return world;
    }
}
