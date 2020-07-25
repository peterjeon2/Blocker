package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
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

    public Player(TETile[][] world, Position startPos) {
        currPos = startPos;
        tile = Tileset.PLAYER;
        world[currPos.getX()][currPos.getY()] = tile;
    }

    public Position getCurrPos() {
        return currPos;
    }

    /**
     * Checks whether a player can physically move to the next position.
     *
     * @param world
     * @param p
     * @return
     */
    public boolean checkNextPos(TETile[][] world, Position p) {
        String tileType = world[p.getX()][p.getY()].description();
        return tileType.equals("floor") || tileType.equals("locked door");
    }

    /**
     * Moves the player to the next position.
     * @param world
     * @param newPosition
     */
    public void move(TETile[][] world, Position newPosition) {
        prevPos = currPos;
        nextPos = newPosition;
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = tile;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }
}
