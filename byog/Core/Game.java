package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Game {
    /* Create a pseudorandom world */
    private static final long SEED = 909088295;
    private static final Random RANDOM = new Random(SEED);
    private static Room[] rooms;
    private static Room[] connected;
    private static Position[] connectPoints;
    private static int size;

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */

    public static void fillEmpty(TETile[][] world) {
        for (int x = 0; x < test.WIDTH; x += 1) {
            for (int y = 0; y < test.HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void generateRooms(TETile[][] world) {
        size = RandomUtils.uniform(RANDOM, 19, 20);
        rooms = new Room[size];
        connected = new Room[size];
        int numRooms = 0;
        int count = 0;
        int maxTries = 100;

        while (numRooms < size) {
            try {
                Room newRoom = Room.makeRoom(world);
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

    public static void getNeighbors() {
        for (Room room : rooms) {
            room.neighborCount = 0;
            for (Room room2: rooms) {
                Position pos1 = room.door.doorP;
                Position pos2 = room2.door.doorP;
                if ((Math.abs(pos1.x - pos2.x) <7) && (Math.abs(pos1.y - pos2.y) < 7)) {
                    room.addNeighbor(room2);
                }
            }
        }
    }


    public static void populateConnectPoints(TETile[][] world) {
        for (Room room: rooms) {
            for (Room neighborRoom: room.neighbors) {
                room.addConnectPoint(neighborRoom, world);
            }
        }
    }

    public static void recordConnection(Room room) {
        int count = 0;
        if (count <  connected.length) {
            connected[count] = room;
            count ++;
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
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
