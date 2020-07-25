package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;

/**
 * Creates NPCs that follow the player around the map.
 */
public class NPC implements Serializable {
    private static final long serialVersionUID = 123L;
    private Position prevPos;
    private Position currPos;
    private Position nextPos;
    private TETile tile;


    public NPC(TETile[][] world, Position startPos) {
        currPos = startPos;
        tile = Tileset.ENEMY;
        world[currPos.getX()][currPos.getY()] = tile;
    }

    /**
     * Checks if the next position is a floor tile.
     * @param world
     * @param p
     * @return
     */
    public boolean checkNextPos(TETile[][] world, Position p) {
        String tileType = world[p.getX()][p.getY()].description();
        return tileType.equals("floor");
    }

    /**
     * Checks if the next position is the Player.
     * @param world
     * @param playerPos
     * @return
     */
    public boolean collidesWithPlayer(TETile[][] world, Position playerPos) {
        String tileType = world[playerPos.getX()][playerPos.getY()].description();
        return tileType.equals("floor") || tileType.equals("player");
    }

    /**
     * Returns the direction the NPC should move towards to follow the player.
     * @param npcPos
     * @param playerPos
     * @return
     */
    public String calcPath(Position npcPos, Position playerPos) {
        if (playerPos.getX() - npcPos.getX() > 0) {
            return "right";
        } else if (playerPos.getX() - npcPos.getX() < 0) {
            return "left";
        } else if (playerPos.getY() - npcPos.getY() > 0) {
            return "up";
        } else {
            return ("down");
        }
    }

    public void move(TETile[][] world, Position newPosition) {
        prevPos = currPos;
        nextPos = newPosition;
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = tile;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    /**
     * Moves the NPC towards the Player. Returns true if it catches the player.
     * @param world
     * @param p1
     * @return
     */
    public Boolean moveNPCS(TETile[][] world, Player p1) {
        String direction = calcPath(currPos, p1.getCurrPos());
        switch (direction) {
            case "up":
                nextPos = new Position(currPos.getX(), currPos.getY() + 1);
                move(world, nextPos);
                return collidesWithPlayer(world, nextPos);
            case "right":
                nextPos = new Position(currPos.getX() + 1, currPos.getY());
                move(world, nextPos);
                return collidesWithPlayer(world, nextPos);
            case "down":
                nextPos = new Position(currPos.getX(), currPos.getY() - 1);
                move(world, nextPos);
                return collidesWithPlayer(world, nextPos);
            case "left":
                nextPos = new Position(currPos.getX() - 1, currPos.getY());
                move(world, nextPos);
                return collidesWithPlayer(world, nextPos);
            default:
                return false;
        }
    }
}
