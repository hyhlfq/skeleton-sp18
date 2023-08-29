package byog.Core;

import java.util.Arrays;
import java.util.Random;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class World {
    private static final int minRoomSize = 2;
    private static final int maxRoomSize = 8;
    private Random RANDOM;
    private TETile[][] world;
    public Room[] rooms;
    private int roomsNum;

    public World(long seed, int width, int height) {
        RANDOM = new Random(seed);
        roomsNum = RandomUtils.uniform(RANDOM, 15, 25);
        world = new TETile[width][height];
        fillWorld();
    }

    private void fillWorld() {
        for (int x = 0; x < world.length; x += 1) {
            for (int y = 0; y < world[0].length; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        addRooms();
        addhallways();
        fillWalls();
        addDoor();
    }

    private void addRooms() {
        rooms = new Room[roomsNum];
        for (int i = 0; i < roomsNum; i++) {
            int width = RandomUtils.uniform(RANDOM, minRoomSize, maxRoomSize);
            int height = RandomUtils.uniform(RANDOM, minRoomSize, maxRoomSize);
            int x = RandomUtils.uniform(RANDOM, 1, world.length - width);
            int y = RandomUtils.uniform(RANDOM, 1, world[0].length - height);
            rooms[i] = new Room(x, y, width, height);
            for (int j = 0; j < i; j++) {
                if (rooms[i].isOverlap(rooms[j])) {
                    i--;
                    break;
                }
            }
        }

        Arrays.sort(rooms, (o1, o2) -> o1.x - o2.x);

        for (Room room : rooms) {
            for (int x = room.x; x < room.x + room.width; x++) {
                for (int y = room.y; y < room.y + room.height; y++) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    private void addhallways() {
        for (int i = 0; i < roomsNum - 1; i++) {
            int x1 = RandomUtils.uniform(RANDOM, rooms[i].x, rooms[i].x + rooms[i].width);
            int y1 = RandomUtils.uniform(RANDOM, rooms[i].y, rooms[i].y + rooms[i].height);
            int x2 = RandomUtils.uniform(RANDOM, rooms[i + 1].x, rooms[i + 1].x + rooms[i + 1].width);
            int y2 = RandomUtils.uniform(RANDOM, rooms[i + 1].y, rooms[i + 1].y + rooms[i + 1].height);
            int start = Math.min(x1, x2);
            int end = Math.max(x1, x2);
            for (int x = start; x <= end; x++) {
                if (world[x][y1] != Tileset.FLOOR) {
                    world[x][y1] = Tileset.FLOOR;
                }
            }
            start = Math.min(y1, y2);
            end = Math.max(y1, y2);
            for (int y = start; y <= end; y++) {
                if (world[x2][y] != Tileset.FLOOR) {
                    world[x2][y] = Tileset.FLOOR;
                }
            }
        }
    }

    private void fillWalls() {
        for (int x = 1; x < world.length; x += 1) {
            for (int y = 1; y < world[0].length; y += 1) {
                if (world[x][y] == Tileset.FLOOR) {
                    if (world[x - 1][y] == Tileset.NOTHING) {
                        world[x - 1][y] = Tileset.WALL;
                    }
                    if (world[x + 1][y] == Tileset.NOTHING) {
                        world[x + 1][y] = Tileset.WALL;
                    }
                    if (world[x][y - 1] == Tileset.NOTHING) {
                        world[x][y - 1] = Tileset.WALL;
                    }
                    if (world[x][y + 1] == Tileset.NOTHING) {
                        world[x][y + 1] = Tileset.WALL;
                    }
                    if (world[x - 1][y - 1] == Tileset.NOTHING) {
                        world[x - 1][y - 1] = Tileset.WALL;
                    }
                    if (world[x + 1][y - 1] == Tileset.NOTHING) {
                        world[x + 1][y - 1] = Tileset.WALL;
                    }
                    if (world[x - 1][y + 1] == Tileset.NOTHING) {
                        world[x - 1][y + 1] = Tileset.WALL;
                    }
                    if (world[x + 1][y + 1] == Tileset.NOTHING) {
                        world[x + 1][y + 1] = Tileset.WALL;
                    }
                }
            }
        }
    }

   private void addDoor() {
        while (true) {
            int doorX = RandomUtils.uniform(RANDOM, 1, world.length - 1);
            int doorY = RandomUtils.uniform(RANDOM, 1, world[0].length - 1);
            if (world[doorX][doorY] == Tileset.WALL) {
                if (world[doorX - 1][doorY] == Tileset.FLOOR && world[doorX + 1][doorY] == Tileset.NOTHING) {
                    world[doorX][doorY] = Tileset.LOCKED_DOOR;
                    break;
                }
                if (world[doorX][doorY - 1] == Tileset.FLOOR && world[doorX][doorY + 1] == Tileset.NOTHING) {
                    world[doorX][doorY] = Tileset.LOCKED_DOOR;
                    break;
                }
            }
        }
    }

    public TETile[][] getFrame() {
        return world;
    }
}
