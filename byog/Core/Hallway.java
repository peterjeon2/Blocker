package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * The purpose of this class is to draw hallways connecting randomly generated rooms.
 */

public class Hallway {
    public static void drawHorizontalHallway(TETile[][] world, Position connectPos, Position startPos) {
        int width = Math.abs(connectPos.getX() - startPos.getX());
        int height = 3;
        Position p;
        if (connectPos.getX() > startPos.getX()) {
            p = new Position(startPos.getX(), startPos.getY() - 1);
        } else {
            p = new Position(connectPos.getX(), connectPos.getY() - 1);
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

    public static void drawVerticalHallway(TETile[][] world, Position connectPos, Position startPos) {
        int height = Math.abs(connectPos.getY() - startPos.getY());
        int width = 3;
        Position p;
        if (connectPos.getY() > startPos.getY()) {
            p = new Position(startPos.getX() - 1, startPos.getY());
        } else {
            p = new Position(connectPos.getX() - 1, connectPos.getY());
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

    public static void drawCornerHallway(TETile[][] world, Position connectPoint) {
        Position p = new Position(connectPoint.getX() - 1, connectPoint.getY() - 1);
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
        world[connectPoint.getX()][connectPoint.getY()] = Tileset.FLOOR;
    }

    public static void drawHallways(TETile[][] world, Position connectPoint, Room room, Room room2) {
        drawCornerHallway(world, connectPoint);
        drawHorizontalHallway(world, connectPoint, room2.getDoor().getDoorP());
        drawVerticalHallway(world, connectPoint, room.getDoor().getDoorP());
    }
}

