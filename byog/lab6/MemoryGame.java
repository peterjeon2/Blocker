package byog.lab6;

import byog.Core.RandomUtils;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int seed;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
        /*
        game.startGame();

         */
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String s = "";
        RandomUtils.shuffle(rand, CHARACTERS);
        for (int i = 0; i < n; i++) {
            s += CHARACTERS[i];
        }
        return s;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show();
        StdDraw.pause(1000);

    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i+1));
            StdDraw.clear();
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        drawFrame("Type in your guess");
        StdDraw.pause(500);
        String userInput = "";
        int i = 0;
        while(i < n) {
            if (StdDraw.hasNextKeyTyped()) {
                userInput += StdDraw.nextKeyTyped();
                drawFrame("Your guess: " + userInput);
            }
            i++;
        }
        return userInput;
    }

    public void startGame() {
        int roundNumber = 1;
        Boolean gameInProgress = true;
        while (gameInProgress == true) {

            drawFrame("Round: " + roundNumber);
            String word = generateRandomString(roundNumber);
            StdDraw.pause(500);
            flashSequence(word);
            StdDraw.clear();
            String guess = solicitNCharsInput(roundNumber);
            if (guess.equals(word)) {
                roundNumber++;
                StdDraw.clear();
                drawFrame("Correct!");
            } else {
                StdDraw.clear();
                drawFrame("Game Over! You made it to round: " + roundNumber);
                gameInProgress = false;
            }
        }

        //TODO: Establish Game loop
    }

}
