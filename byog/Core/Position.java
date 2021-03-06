package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;


/**
* Class used to mark the coordinates of a tile on the grid.
 */
public class Position implements Serializable {
    private static final long serialVersionUID = 123L;
    private int x;
    private int y;

    public Position(int xi, int yi) {
        this.x = xi;
        this.y = yi;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     *
     * @param world
     * @param room
     * Checks whether a room is out of bounds or overlaps another room.
     */
    public static void checkOverlap(TETile[][] world, Room room) {
        for (Position p : room.getCorners()) {
            if (world[p.getX()][p.getY()] != Tileset.NOTHING) {
                throw new RuntimeException("Room can't overlap with another room.");
            }
            if (p.getX() <= 3
                    || p.getX() > Game.WIDTH - 3
                    || p.getY() < 5
                    || p.getY() > Game.HEIGHT - 2) {
                throw new RuntimeException("Room can't be out of bounds");
            }
        }
    }
}
