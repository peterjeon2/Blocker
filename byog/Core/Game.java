package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.*;
import java.util.Random;

public class Game implements Serializable{
    /* Create a pseudorandom world */
    private static long SEED;
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


    public void newGame(Long seed) {
        ter.initialize(WIDTH, HEIGHT);
        World newWorld = new World(WIDTH , HEIGHT, seed);
        finalWorldFrame = newWorld.generateWorld(ter, seed);

    }


    private void loadWorld() {
        File f = new File("world.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                finalWorldFrame = (TETile[][]) os.readObject();
                os.close();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        /* In the case no World has been saved yet, we return a new one. */
    }

    private static void saveWorld() {
        File f = new File("world.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(finalWorldFrame);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }


    /*
    public static String makeWorldStringName(Long seed) {
        String string1 = "world";
        String string2 = seed.toString();
        return string1 + string2;
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
            newGame(seed);
            System.out.println(finalWorldFrame);


        } else if (first == 'L' || first == 'l') {
            loadWorld();
        }
        if (input.contains(":Q") || input.contains(":q")) {
            saveWorld();
            }

        ter.renderFrame(finalWorldFrame);
        System.out.println(finalWorldFrame);
        return finalWorldFrame;
    }
}
