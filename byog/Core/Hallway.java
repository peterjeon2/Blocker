package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;


public class Hallway extends Room {
    private int width;
    private int height;
    private Position middle;
    private Position nextToMiddle;


    public Hallway(Room.Door someDoor) {
        door = someDoor;
    }

/*
    public void drawHallway(TETile[][] world) {
        if (door.orientation.equals("Bottom")) {
            drawVerticalHallway(world);
        } else if (door.orientation.equals("Top")) {
            drawVerticalHallway(world);
        } else if (door.orientation.equals("Left")) {
            drawHorizontalHallway(world);
        } else if (door.orientation.equals("Right")) {
            drawHorizontalHallway(world);
        } else {
            drawCornerHallway(world);
        }
    }

 */

    public void setCorners() {
        botRightCorn = new Position(botLeftCorn.x + width - 1, botLeftCorn.y);
        uppLeftCorn = new Position(botLeftCorn.x, botLeftCorn.y + height - 1);
        uppRightCorn = new Position(botRightCorn.x, uppLeftCorn.y);
        middle = new Position(botLeftCorn.x + 1, botLeftCorn.y + 1);
    }

    public static void drawHorizontalHallway(TETile[][] world, Position connectPos, Position startPos) {
        int width = Math.abs(connectPos.x - startPos.x);
        int height = 3;
        Position p;
        if (connectPos.x > startPos.x) {
            p = new Position(startPos.x, startPos.y - 1);
        } else {
            p = new Position(connectPos.x, connectPos.y - 1);
        }

        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.y + y;
                if (world[xCoord][yCoord] == Tileset.FLOOR) {
                } else {
                    if ((yCoord == p.y) || (yCoord == (p.y + height - 1))) {
                        world[xCoord][yCoord] = Tileset.WALL;
                    } else {
                        world[xCoord][yCoord] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    public static void drawVerticalHallway(TETile[][] world, Position connectPos, Position startPos) {
        int height = Math.abs(connectPos.y - startPos.y);
        int width = 3;
        Position p;
        if (connectPos.y > startPos.y) {
            p = new Position(startPos.x - 1, startPos.y);
        } else {
            p = new Position(connectPos.x - 1, connectPos.y);
        }
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.y + y;
                if (world[xCoord][yCoord] == Tileset.FLOOR) {
                } else {
                    if ((xCoord == p.x) || (xCoord == (p.x + width - 1))) {
                        world[xCoord][yCoord] = Tileset.WALL;
                    } else {
                        world[xCoord][yCoord] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    public static void drawCornerHallway(TETile[][] world, Position connectPoint) {
        Position p = new Position(connectPoint.x - 1, connectPoint.y - 1);
        for (int x = 0; x < 3; x += 1) {
            int xCoord = p.x + x;
            for (int y = 0; y < 3; y += 1) {
                int yCoord = p.y + y;
                if (world[xCoord][yCoord] == Tileset.FLOOR) {
                } else {
                    world[xCoord][yCoord] = Tileset.WALL;
                }
            }
        }
        world[connectPoint.x][connectPoint.y] = Tileset.FLOOR;
    }

}


