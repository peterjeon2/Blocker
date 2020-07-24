package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

/**
 * Implements the player class, which the user will use to interact with the explorable world.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 123L;
    private Position prevPos;
    private Position currPos;
    private Position nextPos;
    private TETile tile;

    /**
     * Instantiates the player.
     * @param world
     * @param startPos
     */
    public Player(TETile[][] world, Position startPos) {
        currPos = startPos;
        tile = Tileset.PLAYER;
        world[currPos.getX()][currPos.getY()] = tile;
    }

    /**
     * Retrieves the player's current position.
     * @return
     */
    public Position getCurrPos() {
        return currPos;
    }

    /**
     * Moves the player up one space.
     * @param world
     */
    public void moveUp(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX(), currPos.getY() + 1);
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = tile;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    /**
     * Moves the player left one space.
     * @param world
     */
    public void moveLeft(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX() - 1, currPos.getY());
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = tile;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    /**
     * Moves the player right one space.
     * @param world
     */
    public void moveRight(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX() + 1, currPos.getY());
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = tile;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    /**
     * Moves the player down one space.
     * @param world
     */
    public void moveDown(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX(), currPos.getY() - 1);
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = tile;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    /**
     * Helper funcion that returns whether a player can move to the next position.
     * @param world
     * @param p
     * @return
     */
    public boolean checkNextPos(TETile[][] world,  Position p) {
        String tileType = world[p.getX()][p.getY()].description();
        return tileType.equals("floor") || tileType.equals("locked door");
    }

    /*
    private static void movePlayer(TETile[][] world, Position prev, Position curr, Position next) {
        curr = next;
        world[curr.getX()][curr.getY()] = Tileset.PLAYER;
        world[prev.getX()][prev.getY()] = Tileset.FLOOR;
    }

     */
}
