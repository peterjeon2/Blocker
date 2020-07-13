package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Position {
    public int x;
    public int y;
    public TETile tile;

    public Position(int xi, int yi){
        this.x = xi;
        this.y = yi;
    }

    public static void checkOverlap(TETile[][] world, Room room) {
        Position[] corners = new Position[]{room.botRightCorn, room.botLeftCorn, room.uppLeftCorn, room.uppRightCorn};
        for (Position p : corners) {
            if (world[p.x][p.y] != Tileset.NOTHING) {
                throw new RuntimeException("Room can't overlap with another room.");
            }
            if (p.x < 10 || p.y <= 10 || p.x > test.WIDTH - 10 || p.y > test.HEIGHT - 10) {
                throw new RuntimeException("Room can't be out of bounds");
            }
        }
    }
}
