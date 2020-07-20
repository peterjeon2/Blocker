package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

public class Player {
    private Position prevPos;
    private Position currPos;
    private Position nextPos;
    private TETile avatar;

    public Player() {
        prevPos = new Position(20, 20);
        currPos = new Position(20, 20);
    }

    public Position getCurrPos() {
        return currPos;
    }
    public void movePlayer(TETile[][] world) {
        prevPos = currPos;
        if (StdDraw.hasNextKeyTyped()) {
            char key = Character.toLowerCase(StdDraw.nextKeyTyped());
            switch (key) {
                case 'w':
                    nextPos = new Position(currPos.getX(), currPos.getY() + 1);
                    if (checkNextPos(world, nextPos)) {
                        currPos = nextPos;
                        world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
                    }
                case 'a':
                    nextPos = new Position(currPos.getX() - 1, currPos.getY());
                    if (checkNextPos(world, nextPos)) {
                        currPos = nextPos;
                        world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
                    }
                case 's':
                    nextPos = new Position(currPos.getX(), currPos.getY() - 1);
                    if (checkNextPos(world, nextPos)) {
                        currPos = nextPos;
                        world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
                    }
                case 'd':
                    nextPos = new Position(currPos.getX() + 1, currPos.getY());
                    if (checkNextPos(world, nextPos)) {
                        currPos = nextPos;
                        world[currPos.getX()][currPos.getY()] = Tileset.PLAYER;
                    }
            }
        }
    }

    public boolean checkNextPos(TETile[][] world,  Position p){
        return world[p.getX()][p.getY()]==Tileset.FLOOR;
        }
}
