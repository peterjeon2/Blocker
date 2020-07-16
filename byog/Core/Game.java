package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Game {
    /* Create a pseudorandom world */
    private static long SEED;
    private static Random RANDOM;
    private static Room[] rooms;
    private static int size;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 55;


    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */

    public static void setSeed(String s) {
        SEED = Long.parseLong(s);
        RANDOM = new Random(SEED);
    }
    public static void fillEmpty(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void generateRooms(TETile[][] world) {
        size = RandomUtils.uniform(RANDOM, 18, 24);
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
            for (Room room2: rooms) {
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
        for (Room room: rooms) {
            for (Room neighborRoom: room.getNeighbors()) {
                room.connectRooms(world, neighborRoom, RANDOM);
            }
        }
    }



    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        ter.initialize(WIDTH, HEIGHT);
        Game.setSeed(input);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        Game.fillEmpty(world);
        Game.generateRooms(world);
        Game.findNeighbors();
        Game.generateHallways(world);
        return world;
    }
}
