package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 123L;
    private Position prevPos;
    private Position currPos;
    private Position nextPos;
    private Position stairCase;
    private TETile avatar;

    public Player(TETile[][] world, Position startPos, Position stair) {
        currPos = startPos;
        world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
        stairCase = stair;
        world[stairCase.getX()][stairCase.getY()] = Tileset.TREE;
    }

    public Position getCurrPos() {
        return currPos;
    }

    public void moveUp(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX(), currPos.getY() + 1);
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    public void moveLeft(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX() - 1, currPos.getY());
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    public void moveRight(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX() + 1, currPos.getY());
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    public void moveDown(TETile[][] world) {
        prevPos = currPos;
        nextPos = new Position(currPos.getX(), currPos.getY() - 1);
        if (checkNextPos(world, nextPos)) {
            currPos = nextPos;
            world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
            world[prevPos.getX()][prevPos.getY()] = Tileset.FLOOR;
        }
    }

    private boolean checkNextPos(TETile[][] world,  Position p){
        String tileType = world[p.getX()][p.getY()].description();
        return tileType.equals("floor") || tileType.equals("tree");
    }

    /*
    private static void movePlayer(TETile[][] world, Position prev, Position curr, Position next) {
        curr = next;
        world[curr.getX()][curr.getY()] = Tileset.PLAYER;
        world[prev.getX()][prev.getY()] = Tileset.FLOOR;
    }

     */
}
