package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.HexWorld;

import java.util.Random;

public class Room {
    private int width;
    private int height;
    private Position[] room;
    private static final long SEED = 2873124;
    private static final Random RANDOM = new Random(SEED);



    public static int getRandomNum(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max + 1 - min) + min;
        return randomNumber;
    }

    public static void drawWalls(TETile[][] world, int width, int height, Position p){
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.y + y;
                if ((xCoord == p.x) || (xCoord == (p.x + width - 1)) ||
                        (yCoord == p.y) || (yCoord == (p.y + height - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
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


    public static void addRow(TETile[][] world, Position p, int width) {
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            int yCoord = p.y;
            if (xCoord == p.x || xCoord == p.x + width - 1) {
                world[xCoord][yCoord] = Tileset.WALL;
            } else {
                world[xCoord][yCoord] = Tileset.FLOOR;
            }
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s) {
        if (s < 2) {
            throw new IllegalArgumentException("Hexagon must be at least size 2.");
        }
        for (int y = 0; y < 2 * s; y += 1) {
            int thisRowY = p.y + y;
            int xRowStart = p.x + hexRowOffset(s, y);
            Position rowStartP = new Position(xRowStart, thisRowY);

            int rowWidth = hexRowWidth(s, y);
            if (thisRowY == p.y || thisRowY == p.y + (2 * s) - 1) {
                for (int x = 0; x < rowWidth; x += 1) {
                    int xCoord = p.x + x;
                    world[xCoord][thisRowY] = Tileset.WALL;
                }
            } else {
                addRow(world, rowStartP, rowWidth);
            }
        }
    }

    public static void makeSquareRoom(TETile[][] world, Position p) {
        int width = getRandomNum(3, 10);
        int height = width;
        drawWalls(world, width, height, p);
    }

    public static void makeRectangleRoom(TETile[][] world, Position p) {
        int width = getRandomNum(3, 10);
        int height = getRandomNum(3, 10);
        drawWalls(world, width, height, p);
    }

    public static void makeHexagonRoom(TETile[][] world, Position p) {
        int size = getRandomNum(2, 4);
        addHexagon(world, p, size);
    }

    public static void makeHallway(){

    }

    public static void makeHorizontalHallway(TETile[][] world, Position p){
        int width = getRandomNum(1, 10);
        int height = 3;
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.y + y;
                if ((yCoord == p.y) || (yCoord == (p.y + height - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
    }

    public static void makeVerticalHallway(TETile[][] world, Position p) {
        int height = getRandomNum(1, 10);
        int width = 3;
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.y + y;
                if ((xCoord == p.x) || (xCoord == (p.x + width - 1))) {
                    world[xCoord][yCoord] = Tileset.WALL;
                } else {
                    world[xCoord][yCoord] = Tileset.FLOOR;
                }
            }
        }
    }

    public static void makeRandomRoom(TETile[][] world, Position p) {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: makeSquareRoom(world, p);
            case 1: makeRectangleRoom(world, p);
            case 2: makeHexagonRoom(world, p);
            default: return;
        }
    }

    public static void makeRandomHallway(TETile[][] world, Position p) {
        int tileNum = RANDOM.nextInt(2);
        switch (tileNum) {
            case 0: makeHorizontalHallway(world, p);
            case 1: makeVerticalHallway(world, p);
            default: return;
        }
    }


}
