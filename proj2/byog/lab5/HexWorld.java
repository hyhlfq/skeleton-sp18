package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static class Position {

        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void shift(int dx, int dy) {
            x += dx;
            y += dy;
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        int num = s;
        for (int i = 0; i < s; i++) {
            addHexRow(world, p, num, t);
            p.shift(-1, 1);
            num += 2;
        }
        p.x++;
        for (int i = 0; i < s; i++) {
            num -= 2;
            addHexRow(world, p, num, t);
            p.shift(1, 1);
        }
    }

    public static void addHexRow(TETile[][] world, Position p, int num, TETile t) {
        for (int i = 0; i < num; i++) {
            world[p.x + i][p.y] = t; // TETile.colorVariant(t, 255, 255, 255, RANDOM);
        }
    }

    public static void main(String[] args) {

        int WIDTH = 27;
        int HEIGHT = 30;

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon(world, new Position(2, 6), 3, Tileset.GRASS);
        addHexagon(world, new Position(2, 12), 3, Tileset.GRASS);
        addHexagon(world, new Position(2, 18), 3, Tileset.MOUNTAIN);

        addHexagon(world, new Position(7, 3), 3, Tileset.FLOWER);
        addHexagon(world, new Position(7, 9), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(7, 15), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(7, 21), 3, Tileset.GRASS);

        addHexagon(world, new Position(12, 0), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(12, 6), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(12, 12), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(12, 18), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(12, 24), 3, Tileset.TREE);

        addHexagon(world, new Position(17, 3), 3, Tileset.MOUNTAIN);
        addHexagon(world, new Position(17, 9), 3, Tileset.TREE);
        addHexagon(world, new Position(17, 15), 3, Tileset.SAND);
        addHexagon(world, new Position(17, 21), 3, Tileset.FLOWER);

        addHexagon(world, new Position(22, 6), 3, Tileset.SAND);
        addHexagon(world, new Position(22, 12), 3, Tileset.TREE);
        addHexagon(world, new Position(22, 18), 3, Tileset.FLOWER);

        ter.renderFrame(world);
    }
}
