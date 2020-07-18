package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.*;
import java.util.Random;

public class Game implements Serializable{
    /* Create a pseudorandom world */
    private static long SEED;
    static Random RANDOM;
    private static Room[] rooms;
    private static int size;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 55;
    private static TETile[][] finalWorldFrame;


    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */



    public static Long processInput(String input) {
        Long seed;
        int i = 0;
        int cutoff = 0;
        int end = input.length();
        while (i < end) {
            char current = input.charAt(i);
            if (current == 'N' || current == 'n') {
                cutoff = i + 1;
            } else if (current == 'L' || current == 'l') {
                cutoff = i + 1;
            } else if (!Character.isDigit(current)) {
                end = i;
            }
            i++;
        }

        seed = Long.parseLong(input.substring(cutoff, end));
        System.out.println(seed);
        return seed;
    }

    public static void setRandom(Long seed) {
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

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        fillEmpty(world);
        setRandom(seed);
        generateRooms(world);
        findNeighbors();
        generateHallways(world);
        return world;

    }

    public TETile[][] newGame(Long seed) {
        ter.initialize(WIDTH, HEIGHT);
        return generateWorld(ter, seed);
    }

/*
    public static String makeWorldStringName(Long seed) {
        String string1 = "world";
        String string2 = seed.toString();
        return string1 + string2;
    }

 */

    public static void saveGame() throws IOException {
        File f = new File("World.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(f));
        objectOutputStream.writeObject(finalWorldFrame);
        objectOutputStream.close();

    }

    public static TETile[][] loadGame() throws IOException, ClassNotFoundException {
        /*
        File f = new File("World.ser");

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(f));
        TETile[][] world = (TETile[][]) objectInputStream.readObject();
        objectInputStream.close();
        return world;

         */
        return finalWorldFrame;

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
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        char first = input.charAt(0);

        if (first == 'N' || first == 'n') {
            Long seed = processInput(input);
            finalWorldFrame = newGame(seed);
            ter.renderFrame(finalWorldFrame);

        } else if (first == 'L' || first == 'l') {
            Long seed = processInput(input);
            finalWorldFrame = newGame(seed);

            try {
                finalWorldFrame = loadGame();
                ter.renderFrame(finalWorldFrame);
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("World not found");
            }


        } else {
            System.out.println("Need to enter a valid letter");
            return null;
        }

        if (input.contains(":Q") || input.contains(":q")) {
            try {
                saveGame();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return finalWorldFrame;
    }
}
