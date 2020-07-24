package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;

public class NPC implements Serializable{
    private static final long serialVersionUID = 123L;
    private Position prevPos;
    private Position currPos;
    private Position nextPos;
    private TETile tile;
    private boolean canMoveUp = true;
    private boolean canMoveRight = true;
    private boolean canMoveDown = true;
    private boolean canMoveLeft = true;


    /**
     * Instantiates the NPC class.
     *
     * @param world
     * @param startPos
     */
    public NPC(TETile[][] world, Position startPos) {
        currPos = startPos;
        tile = Tileset.UNLOCKED_DOOR;
        world[currPos.getX()][currPos.getY()] = tile;
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
    public Boolean moveNPCS(TETile[][] world, Player p1) {
        String direction = calcPath(currPos, p1.getCurrPos());
        switch (direction) {
            case "up":
                nextPos = new Position(currPos.getX(), currPos.getY() + 1);
                if (checkNextPos(world, nextPos) && canMoveUp) {
                    moveUp(world);
                }
                return collidesWithPlayer(world, nextPos);
            case "right":
                nextPos = new Position(currPos.getX() + 1, currPos.getY());
                if (checkNextPos(world, nextPos) && canMoveRight) {
                    moveRight(world);
                }
                return collidesWithPlayer(world, nextPos);
            case "down":
                nextPos = new Position(currPos.getX(), currPos.getY() - 1);
                if (checkNextPos(world, nextPos) && canMoveDown) {
                    moveDown(world);
                }
                return collidesWithPlayer(world, nextPos);
            case "left":
                nextPos = new Position(currPos.getX() - 1, currPos.getY());
                if (checkNextPos(world, nextPos) && canMoveLeft) {
                    moveLeft(world);
                }
                return collidesWithPlayer(world, nextPos);
            default:
                return false;
        }
    }

    public boolean checkNextPos(TETile[][] world, Position p) {
        String tileType = world[p.getX()][p.getY()].description();
        return tileType.equals("floor");
    }

    public boolean collidesWithPlayer(TETile[][] world, Position p) {
        String tileType = world[p.getX()][p.getY()].description();
        return tileType.equals("floor") || tileType.equals("player");
    }


    public String calcPath(Position npcPos, Position playerPos) {
        if (playerPos.getX() - npcPos.getX() > 0) {
            return "right";
        } else if (playerPos.getX() - npcPos.getX() < 0){
            return "left";
        } else if (playerPos.getY() - npcPos.getY() > 0) {
            return "up";
        } else {
            return ("down");
        }
    }
}
