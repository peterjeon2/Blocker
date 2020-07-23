package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class NPC extends Player{
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
        super(world, startPos);
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
    public void moveNPCS(TETile[][] world, Player p1) {
        String direction = calcPath(currPos, p1.getCurrPos());
        switch (direction) {
            case "up":
                if (checkNextPos(world, new Position(currPos.getX(), currPos.getY() + 1))) {
                    moveUp(world);
                }
                break;
            case "right":
                if (checkNextPos(world, new Position(currPos.getX() + 1, currPos.getY()))) {
                    moveRight(world);
                }
                break;
            case "down":
                if (checkNextPos(world, new Position(currPos.getX(), currPos.getY() - 1)) && canMoveDown) {
                    moveDown(world);
                }
                break;
            case "left":
                if (checkNextPos(world, new Position(currPos.getX() - 1, currPos.getY())) && canMoveLeft) {
                    moveLeft(world);
                }
                break;
            default:
                break;
        }
    }

    public boolean checkNextPos(TETile[][] world, Position p) {
        String tileType = world[p.getX()][p.getY()].description();
        return tileType.equals("floor");
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
