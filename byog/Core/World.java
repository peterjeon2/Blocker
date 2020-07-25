package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

/**
 * The World class creates a randomly generated World.
 */
public class World implements Serializable {
    private static final long serialVersionUID = 123123123123123L;
    private int width;
    private int height;
    private TETile[][] world;
    private Random random;
    private Room[] rooms;
    private NPC[] npcs;
    private int size;
    private Position playerStartPos;
    private Position stairCase;

    public World(int w, int h, Long seed) {
        width = w;
        height = h;
        random = new Random(seed);
    }

    public TETile[][] getWorld() {
        return world;
    }

    public Position getPlayerStartPos() {
        return playerStartPos;
    }

    /**
     * Populates the world with randomly generated rooms and hallways.
     * @param ter
     * @param seed
     * @return
     */
    public TETile[][] generateWorld(TERenderer ter, Long seed) {
        world = new TETile[width][height];
        fillEmpty(world);
        generateRooms(world, random);
        findNeighbors();
        generateHallways(world, random);
        createStairCase();
        return world;
    }

    /**
     * Generates NPCS in the world. The number of NPCs is based off the current
     * level the player is on.
     * @param w
     * @param level
     * @return
     */
    public NPC[] generateNPCS(TETile[][] w, int level) {
        npcs = new NPC[level * 2];
        for (int i = 0; i < level * 2; i += 1) {
            npcs[i] = new NPC(w, rooms[i + 1].getDoor().getDoorP());
        }
        return npcs;
    }

    /**
     * Fills the empty grid with blank tiles.
     * @param w
     */
    private void fillEmpty(TETile[][] w) {
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                w[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Creates and draws a random number of rooms in the world.
     * @param w
     * @param r
     */
    private void generateRooms(TETile[][] w, Random r) {
        size = RandomUtils.uniform(r, 15, 25);
        rooms = new Room[size];
        int numRooms = 0;
        int count = 0;
        int maxTries = 200;
        while (numRooms < size) {
            try {
                Room newRoom = Room.makeRoom(w, r);
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
        playerStartPos = rooms[0].getDoor().getDoorP();
    }

    /**
     * Determines the nearest neighboring rooms for each room in the world.
     */
    private void findNeighbors() {
        for (Room room : rooms) {
            for (Room room2 : rooms) {
                Position pos1 = room.getDoor().getDoorP();
                Position pos2 = room2.getDoor().getDoorP();
                if ((Math.abs(pos1.getX() - pos2.getX()) < 8)
                        && (Math.abs(pos1.getY() - pos2.getY()) < 8)) {
                    room.addNeighbor(room2);
                }
            }
        }
    }

    /**
     * Draws hallways between a room and  its neighbors.
     * @param w
     * @param r
     */
    private void generateHallways(TETile[][] w, Random r) {
        for (Room room : rooms) {
            for (Room neighborRoom : room.getNeighbors()) {
                room.connectRooms(w, neighborRoom, r);
            }
        }
    }

    /** Creates a staircase that can be used to reach the
     * next level of the game.
     */
    private void createStairCase() {
        stairCase = rooms[size - 1].getDoor().getDoorP();
        world[stairCase.getX()][stairCase.getY()] = Tileset.LOCKED_DOOR;
    }
}
