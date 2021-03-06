package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Game {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    private int yOffSet;
    private int specialMoves = 0;
    private int level;
    private boolean gameOver;
    private TERenderer ter = new TERenderer();
    private Long seed;
    private World newWorld;
    private TETile[][] finalWorldFrame;
    private NPC[] enemies;
    private Player player1;
    private Position startPos;
    private HashMap<String, Integer> highScores;

    /**
     * Constructs a new game!
     */
    public Game() {
    }

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        gameOver = false;
        yOffSet = 3;
        level = 1;
        setStyle();
        setUpMenu();

        /**
         * Starts the game and prompts the user to either start a new game
         * or load a saved game. The game will then start playing based off
         * of interactive keyboard inputs.
         */

        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                switch (key) {
                    case 'n':
                        try {
                            processInput(chooseSeed());
                        } catch (NumberFormatException e) {
                            processInput(chooseSeed());
                        }
                        ter.initialize(WIDTH, HEIGHT + yOffSet, 0, -yOffSet);
                        newGame();
                        break;
                    case 'l':
                        ter.initialize(WIDTH, HEIGHT + yOffSet, 0, -yOffSet);
                        loadWorld();
                        break;
                    default:
                        break;
                }
                ter.renderFrame(finalWorldFrame, mousePosition(),
                        displayLevel(level), displaySpecialMoves(0));
                play();
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
            processInput(input);
            newGame();
        } else if (first == 'L' || first == 'l') {
            loadWorld();
        }
        for (int i = 1; i < input.length(); i++) {
            char key = Character.toLowerCase(input.charAt(i));
            char lastKeyTyped = Character.toLowerCase(input.charAt(i - 1));
            movementKeys(key, lastKeyTyped);
        }
        return finalWorldFrame;
    }

    /**
     * Getter methods that give the coordinates of the tile directly next to
     * the player's current position.
     * @return
     */
    private Position getRightOfPlayer() {
        return new Position(player1.getCurrPos().getX() + 1, player1.getCurrPos().getY());
    }

    private Position getLeftOfPlayer() {
        return new Position(player1.getCurrPos().getX() - 1, player1.getCurrPos().getY());
    }

    private Position getAbovePlayer() {
        return new Position(player1.getCurrPos().getX(), player1.getCurrPos().getY() + 1);
    }

    private Position getBelowPlayer() {
        return new Position(player1.getCurrPos().getX(), player1.getCurrPos().getY() - 1);
    }

    /**
     * Method that sets the style used to write instructions to the user.
     */
    private void setStyle() {
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
     * Plays the game by processing different keyboard inputs.
     * :Q will quit and save the game.
     * W,A,S,D are used as conventional arrow keys.
     * B,E are special abilities that the player can use to interact with the game.
     */
    private void play() {
        char lastKeyTyped = ' ';
        while (!gameOver) {
            ter.renderFrame(finalWorldFrame, mousePosition(), displayLevel(level),
                    displaySpecialMoves(specialMoves));
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                movementKeys(key, lastKeyTyped);
                if (key != 'b' && key != 'e') {
                    moveNPCS();
                }
                lastKeyTyped = key;
            }
        }
        loseScreen(level);
    }

    /**
     * Helper function that process player keyboard inputs,
     * such as movements and special commands.
     * @param key
     * @param lastKeyTyped
     */
    private void movementKeys(char key, char lastKeyTyped) {
        switch (key) {
            case ':':
                saveWorld();
                break;
            case 'q':
                if (lastKeyTyped == ':') {
                    System.exit(0);
                }
                break;
            case 'w':
                movePlayer(lastKeyTyped, getAbovePlayer());
                break;
            case 's':
                movePlayer(lastKeyTyped, getBelowPlayer());
                break;
            case 'a':
                movePlayer(lastKeyTyped, getLeftOfPlayer());
                break;
            case 'd':
                movePlayer(lastKeyTyped, getRightOfPlayer());
                break;
            case 'b':
                if (specialMoves > 0) {
                    specialMoves--;
                }
                break;
            case 'e':
                if (specialMoves > 0) {
                    specialMoves -= 1;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Method that moves the player to the next position.
     * There are three special cases:
     * 1. A player uses a special move to build or destroy a wall.
     * 2. A player reaches the staircase and goes up to the next level.
     * 3. A player moves to the intended position.
     * @param lastKeyTyped
     * @param newPos
     */
    private void movePlayer(char lastKeyTyped, Position newPos) {
        if (lastKeyTyped == 'b' && specialMoves >= 0) {
            finalWorldFrame[newPos.getX()][newPos.getY()] = Tileset.WALL;
        } else if (lastKeyTyped == 'e' && specialMoves >= 0) {
            finalWorldFrame[newPos.getX()][newPos.getY()] = Tileset.FLOOR;
        } else if (finalWorldFrame[newPos.getX()][newPos.getY()].
                description().equals("locked door")) {
            seed = generateRandomSeed();
            newGame();
            level++;
            specialMoves += 2;
        } else {
            player1.move(finalWorldFrame, newPos);
        }
    }

    /**
     * Moves every NPC towards the player using a simple algorithm
     * created in the NPC class.
     */
    private void moveNPCS() {
        for (NPC npc : enemies) {
            if (npc.moveNPCS(finalWorldFrame, player1)) {
                gameOver = true;
            }
        }
    }

    /**
     * Processes the user-given string into a usable seed for the game.
     * @param input
     * @return
     */
    private void processInput(String input) {
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
    }

    /**
     * Generates a random seed that will be used to create a different
     * world each time a player progresses to the next level.
     * @return
     */
    private Long generateRandomSeed() {
        return (long) (Math.random() * 1000000 + 1);
    }

    /**
     * Starts a new pseudo-randomly generated game.
     */
    private void newGame() {
        newWorld = new World(WIDTH, HEIGHT, seed);
        finalWorldFrame = newWorld.generateWorld(ter, seed);
        startPos = newWorld.getPlayerStartPos();
        player1 = new Player(finalWorldFrame, startPos);
        enemies = newWorld.generateNPCS(finalWorldFrame, level);
    }

    /**
     * Loads the most recent saved state of a game using Serialization.
     */
    private void loadWorld() {
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream("world.txt"));
            ObjectInputStream os2 = new ObjectInputStream(new FileInputStream("player.txt"));
            ObjectInputStream os3 = new ObjectInputStream(new FileInputStream("NPCs.txt"));
            ObjectInputStream os4 = new ObjectInputStream(new FileInputStream("level.txt"));
            ObjectInputStream os5 = new ObjectInputStream(new FileInputStream("specMoves.txt"));
            finalWorldFrame = (TETile[][]) os.readObject();
            player1 = (Player) os2.readObject();
            enemies = (NPC[]) os3.readObject();
            level = (Integer) os4.readObject();
            specialMoves = (Integer) os5.readObject();
            os.close();
            os2.close();
            os3.close();
            os4.close();
            os5.close();
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

    /**
     * Saves the state of the game using Serialization.
     */
    private void saveWorld() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("world.txt"));
            os.writeObject(finalWorldFrame);
            ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("player.txt"));
            os2.writeObject(player1);
            ObjectOutputStream os3 = new ObjectOutputStream(new FileOutputStream("NPCs.txt"));
            os3.writeObject(enemies);
            ObjectOutputStream os4 = new ObjectOutputStream(new FileOutputStream("level.txt"));
            os4.writeObject(level);
            ObjectOutputStream os5 = new ObjectOutputStream(new FileOutputStream("specMoves.txt"));
            os5.writeObject(specialMoves);
            os.close();
            os2.close();
            os3.close();
            os4.close();
            os5.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    /**
     * Returns text that describes the tile currently under the mouse pointer.
     * @return
     */
    private String mousePosition() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        try {
            TETile tile = finalWorldFrame[x][y + yOffSet];
            return "Tile: " + tile.description();
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Tile: ";
        }
    }

    private String displayLevel(int n) {
        return "Level: " + n;
    }

    private String displaySpecialMoves(int n) {
        return "Special Moves Available: " + n;
    }

    /**
     * Sets up the Main Menu display.
     */
    private void setUpMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 60));
        StdDraw.text(WIDTH / 2, 40, "CS61B: THE GAME");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(WIDTH / 2, 25, "New Game (N)");
        StdDraw.text(WIDTH / 2, 23, "Load Game (L)");
        StdDraw.text(WIDTH / 2, 21, "Quit (Q)");
        StdDraw.show();
    }

    /**
     * Prompts a user to enter a seed.
     */
    private String chooseSeed() {
        String userSeed = "";
        char key = ' ';
        StdDraw.clear(Color.BLACK);
        StdDraw.text(WIDTH / 2, 40, "Enter a Random Seed. Press S to start the game.");
        StdDraw.text(WIDTH / 2, 37, "It needs to be a valid number.");
        StdDraw.show();
        while (key != 's' || key != 's') {
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                userSeed += String.valueOf(key);
                StdDraw.clear(Color.BLACK);
                StdDraw.text(WIDTH / 2, 25, userSeed);
                StdDraw.show();
            }
        }
        return userSeed;
    }

    /**
     * Method that draws the display that is shown when a player loses the game.
     * @param n
     */
    private void loseScreen(int n) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(40, 45, ("YOU LOST. YOU MADE IT TO LEVEL " + n));
        StdDraw.text(40, 10, "Play Again? (Y)");
        StdDraw.text(40, 7, "Quit (Q)");
        StdDraw.show();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (key == 'y') {
                    playWithKeyboard();
                } else if (key == 'q') {
                    System.exit(0);
                }
            }
        }
    }
}
