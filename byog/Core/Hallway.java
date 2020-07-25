package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * The purpose of this class is to draw hallways that connect randomly generated rooms.
 */

public class Hallway {
    /**
     * Utilizes three different helper functions to draw a hallway between two rooms.
     * @param world
     * @param conPos
     * @param room
     * @param room2
     */
    public static void drawHallways(TETile[][] world, Position conPos, Room room, Room room2) {
        drawCornerHallway(world, conPos);
        drawHorizontalHallway(world, conPos, room2.getDoor().getDoorP());
        drawVerticalHallway(world, conPos, room.getDoor().getDoorP());
    }

    private static void drawHorizontalHallway(TETile[][] world, Position conPos, Position startPos) {
        int width = Math.abs(conPos.getX() - startPos.getX());
        int height = 3;
        Position p;
        if (conPos.getX() > startPos.getX()) {
            p = new Position(startPos.getX(), startPos.getY() - 1);
        } else {
            p = new Position(conPos.getX(), conPos.getY() - 1);
        }
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.getX() + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.getY() + y;
                if (world[xCoord][yCoord] == Tileset.FLOOR) {
                    continue;
                } else {
                    if ((yCoord == p.getY()) || (yCoord == (p.getY() + height - 1))) {
                        world[xCoord][yCoord] = Tileset.WALL;
                    } else {
                        world[xCoord][yCoord] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    private static void drawVerticalHallway(TETile[][] world, Position conPos, Position startPos) {
        int height = Math.abs(conPos.getY() - startPos.getY());
        int width = 3;
        Position p;
        if (conPos.getY() > startPos.getY()) {
            p = new Position(startPos.getX() - 1, startPos.getY());
        } else {
            p = new Position(conPos.getX() - 1, conPos.getY());
        }
        for (int x = 0; x < width; x += 1) {
            int xCoord = p.getX() + x;
            for (int y = 0; y < height; y += 1) {
                int yCoord = p.getY() + y;
                if (world[xCoord][yCoord] == Tileset.FLOOR) {
                    continue;
                } else {
                    if ((xCoord == p.getX()) || (xCoord == (p.getX() + width - 1))) {
                        world[xCoord][yCoord] = Tileset.WALL;
                    } else {
                        world[xCoord][yCoord] = Tileset.FLOOR;
                    }
                }
            }
        }
    }

    /**
     * Draws a corner hallway.
     * A corner hallway is a 3x3 hallway that connects vertical and horizontal hallways.
     * Corner hallways are utilized to draw L-Shaped Hallways.
     * @param world
     * @param connectPos
     */
    private static void drawCornerHallway(TETile[][] world, Position connectPos) {
        Position p = new Position(connectPos.getX() - 1, connectPos.getY() - 1);
        for (int x = 0; x < 3; x += 1) {
            int xCoord = p.getX() + x;
            for (int y = 0; y < 3; y += 1) {
                int yCoord = p.getY() + y;
                if (world[xCoord][yCoord] == Tileset.FLOOR) {
                    continue;
                } else {
                    world[xCoord][yCoord] = Tileset.WALL;
                }
            }
        }
        world[connectPos.getX()][connectPos.getY()] = Tileset.FLOOR;
    }
}
