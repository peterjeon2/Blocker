package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Random;

public class Game {
    /* Create a pseudorandom world */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private TETile[][] finalWorldFrame;
    private World newWorld;
    private TERenderer ter = new TERenderer();
    private boolean gameOver;
    private Long seed;
    private Player player1;
    private Position startPos;
    private Position stairCase;
    /* Feel free to change the width and height. */


    public Game() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }


    /**
     * Returns a seed from a string input.
     * @param input
     * @return
     */
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
        return seed;
    }

    public Long generateRandomSeed(){
        return (long) (Math.random() * 1000000 + 1);
    }

    /**
     * Starts a pseudorandomly generated new game.
     * @param seed
     */
    public void newGame(Long seed) {
        StdDraw.clear();
        System.out.println(seed);
        newWorld = new World(WIDTH, HEIGHT, seed);
        finalWorldFrame = newWorld.generateWorld(ter, seed);
        startPos = newWorld.getPlayerStartPos();
        stairCase = newWorld.getStairCase();
        player1 = new Player(finalWorldFrame, startPos, stairCase);
    }

    /**
     * Loads the most recent saved state of a game.
     */
    private void loadWorld() {
        File f = new File("world.txt");
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
    }

    private void loadPlayer() {
        File f = new File("player.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                player1 = (Player) os.readObject();
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
    }

    /**
     * Saves game state through serialization.
     */
    private void saveWorld() {
        File f = new File("world.txt");
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

    private void savePlayer() {
        File f = new File("Player.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(player1);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }


    /**
     * Draws text onto the screen.
     * @param s
     */
    public void drawFrame(String s) {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        StdDraw.clear();
        StdDraw.clear(Color.black);

    /*
        if (!gameOver) {
            Font smallFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(smallFont);
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            StdDraw.text(midWidth, height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }
     */

        StdDraw.text(midWidth, midHeight, s);
        StdDraw.show();
    }

    public void showMousePosition() {
            StdDraw.setFont(new Font("Monaco", Font.BOLD, 25));
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY();
            StdDraw.setXscale(0, 100);
            StdDraw.setYscale(0, 55);
            TETile tile = finalWorldFrame[x][y];
            if (tile == Tileset.FLOOR) {
                StdDraw.text(5, 53, "Floor");
            } else if (tile == Tileset.WALL) {
                StdDraw.text(5, 53, "Wall");
            } else if (tile == Tileset.PLAYER) {
                StdDraw.text(5, 53, "Player");
            } else if (tile == Tileset.NOTHING) {
                StdDraw.text(5, 53, "Nothing");
            }
            ter.renderFrame(finalWorldFrame);
            StdDraw.show();
    }

    /**
     * Sets up the Main Menu display.
     */
    private void setUpMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 60));
        StdDraw.text(WIDTH/2, 40, "CS61B: THE GAME");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(WIDTH/2, 25, "New Game (N)");
        StdDraw.text(WIDTH/2, 23, "Load Game (L)");
        StdDraw.text(WIDTH/2, 21, "Quit (Q)");
        StdDraw.show();
    }

    /**
     * Prompts a user to enter a seed;
     */
    private String chooseSeed() {
        String seed = "";
        char key = ' ';
        StdDraw.clear(Color.BLACK);
        StdDraw.text(WIDTH/2, 40, "Enter a Random Seed. Press S to start the game.");
        StdDraw.show();

        while (key != 's' || key != 's') {
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                seed += String.valueOf(key);
                drawFrame(seed);
            }
        }
        return seed;
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        gameOver = false;
        setUpMenu();

        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                switch (key) {
                    case 'n':
                        seed = processInput(chooseSeed());
                        ter.initialize(WIDTH, HEIGHT, 0, -3);
                        newGame(seed);
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case 'l':
                        ter.initialize(WIDTH, HEIGHT,0, -3);
                        loadWorld();
                        loadPlayer();
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case ':':
                        saveWorld();
                        savePlayer();
                        break;
                    case 'w':
                        if (finalWorldFrame[player1.getCurrPos().getX()][player1.getCurrPos().getY() + 1].description().equals("tree")){
                            newGame(generateRandomSeed());
                        } else {
                            player1.moveUp(finalWorldFrame);
                        }
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case 's':
                        if (finalWorldFrame[player1.getCurrPos().getX()][player1.getCurrPos().getY() - 1].description().equals("tree")){
                            newGame(generateRandomSeed());
                        } else {
                            player1.moveDown(finalWorldFrame);
                        }
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case 'a':
                        if (finalWorldFrame[player1.getCurrPos().getX() - 1][player1.getCurrPos().getY()].description().equals("tree")){
                            newGame(generateRandomSeed());
                        } else {
                            player1.moveLeft(finalWorldFrame);
                        }
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case 'd':
                        if (finalWorldFrame[player1.getCurrPos().getX() + 1][player1.getCurrPos().getY()].description().equals("tree")){
                            newGame(generateRandomSeed());
                        } else {
                            player1.moveRight(finalWorldFrame);
                        }
                        ter.renderFrame(finalWorldFrame);
                        break;
                    default:
                        break;
                }
            }


        }
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
        } else if (first == 'L' || first == 'l') {
            loadWorld();
        }
        if (input.contains(":Q") || input.contains(":q")) {
            saveWorld();
        }
        return finalWorldFrame;
    }
}
