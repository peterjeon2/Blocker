package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */

public class HexWorld {
    private static final long SEED = 2873128;
    private static final Random RANDOM = new Random(SEED);
    private static final int LONGESTCOL = 5;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static class Position {
        private int x;
        private int y;

        private Position(int xi, int yi) {
            x = xi;
            y = yi;
        }
    }

    public static int hexRowWidth(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return s + 2 * effectiveI;
    }

    public static int hexRowOffset(int s, int i) {
        int effectiveI = i;
        if (i >= s) {
            effectiveI = 2 * s - 1 - effectiveI;
        }
        return -effectiveI;
    }


    public static void addRow(TETile[][] world, Position p, int width, TETile t) {
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            int yCoord = p.y;
            world[xCoord][yCoord] = TETile.colorVariant(t, 32, 32, 32, RANDOM);
        }
    }


    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int y = 0; y < 2 * s; y += 1) {
            int thisRowY = p.y + y;
            int xRowStart = p.x + hexRowOffset(s, y);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, y);
            addRow(world, rowStartP, rowWidth, t);

        }
    }

    public static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.SAND;
            case 4: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }

    public static void verticalAxisHex(TETile[][] world, Position p, int s, int num) {
        TETile t = randomTile();
        for (int i = 0; i < num; i += 1) {
            addHexagon(world, p, s, t);
            p.y = p.y + 2 * s;
            t = randomTile();
        }

    }

    public static Position createStartPos(Position p, int s, String place) {
        Position nextP = new Position(0, 0);
        nextP.x = p.x + (2 * s) - 1;
        if (place.equals("bottomRight")) {
            nextP.y = p.y - s;
        } else {
            nextP.y = p.y + s;
        }
        return nextP;
    }

    public static void fillEmpty(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void createTesselation(TETile[][] world, Position p, int s, int num, int longestCol) {
        for (int i = 3; i <= longestCol; i += 1) {
            Position nextP = createStartPos(p, 3, "bottomRight");
            verticalAxisHex(world, p, 3, i);
            p = nextP;
        }
        /* Readjust y position. */
        p.y = p.y + 2 * s;
        for (int i = longestCol - 1; i >= num; i -= 1) {
            Position nextP = createStartPos(p, 3, "upperRight");
            verticalAxisHex(world, p, 3, i);
            p = nextP;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        fillEmpty(world);
        Position p = new Position(10, 20);

        createTesselation(world, p, 3, 3, LONGESTCOL);
        ter.renderFrame(world);
    }

}
