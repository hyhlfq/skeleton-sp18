package byog.Core;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class World implements Serializable {
    private static final int MINROOMSIZE = 2;
    private static final int MAXROOMSIZE = 8;
    private Random RANDOM;
    private TETile[][] world;
    private Room[] rooms;
    private int roomsNum;
    private Position player;

    private class Position implements Serializable {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

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
        player = addPlayer();
    }

    private void addRooms() {
        rooms = new Room[roomsNum];
        for (int i = 0; i < roomsNum; i++) {
            int width = RandomUtils.uniform(RANDOM, MINROOMSIZE, MAXROOMSIZE);
            int height = RandomUtils.uniform(RANDOM, MINROOMSIZE, MAXROOMSIZE);
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

        Arrays.sort(rooms, (o1, o2) -> o1.getX() - o2.getX());

        for (Room room : rooms) {
            for (int x = room.getX(); x < room.getX() + room.getWidth(); x++) {
                for (int y = room.getY(); y < room.getY() + room.getHeight(); y++) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    private void addhallways() {
        for (int i = 0; i < roomsNum - 1; i++) {
            int x1 = RandomUtils.uniform(RANDOM,
                rooms[i].getX(), rooms[i].getX() + rooms[i].getWidth());
            int y1 = RandomUtils.uniform(RANDOM,
                rooms[i].getY(), rooms[i].getY() + rooms[i].getHeight());
            int x2 = RandomUtils.uniform(RANDOM,
                rooms[i + 1].getX(), rooms[i + 1].getX() + rooms[i + 1].getWidth());
            int y2 = RandomUtils.uniform(RANDOM,
                rooms[i + 1].getY(), rooms[i + 1].getY() + rooms[i + 1].getHeight());
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
                if (world[x][y].equals(Tileset.FLOOR)) {
                    if (world[x - 1][y].equals(Tileset.NOTHING)) {
                        world[x - 1][y] = Tileset.WALL;
                    }
                    if (world[x + 1][y].equals(Tileset.NOTHING)) {
                        world[x + 1][y] = Tileset.WALL;
                    }
                    if (world[x][y - 1].equals(Tileset.NOTHING)) {
                        world[x][y - 1] = Tileset.WALL;
                    }
                    if (world[x][y + 1].equals(Tileset.NOTHING)) {
                        world[x][y + 1] = Tileset.WALL;
                    }
                    if (world[x - 1][y - 1].equals(Tileset.NOTHING)) {
                        world[x - 1][y - 1] = Tileset.WALL;
                    }
                    if (world[x + 1][y - 1].equals(Tileset.NOTHING)) {
                        world[x + 1][y - 1] = Tileset.WALL;
                    }
                    if (world[x - 1][y + 1].equals(Tileset.NOTHING)) {
                        world[x - 1][y + 1] = Tileset.WALL;
                    }
                    if (world[x + 1][y + 1].equals(Tileset.NOTHING)) {
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
            if (world[doorX][doorY].equals(Tileset.WALL)) {
                if (world[doorX - 1][doorY].equals(Tileset.FLOOR)
                    && world[doorX + 1][doorY].equals(Tileset.NOTHING)) {
                    world[doorX][doorY] = Tileset.LOCKED_DOOR;
                    return;
                }
                if (world[doorX][doorY - 1].equals(Tileset.FLOOR)
                    && world[doorX][doorY + 1].equals(Tileset.NOTHING)) {
                    world[doorX][doorY] = Tileset.LOCKED_DOOR;
                    return;
                }
            }
        }
    }

    private Position addPlayer() {
        while (true) {
            int playerX = RandomUtils.uniform(RANDOM, 1, world.length - 1);
            int playerY = RandomUtils.uniform(RANDOM, 1, world[0].length - 1);
            if (world[playerX][playerY].equals(Tileset.FLOOR)) {
                world[playerX][playerY] = Tileset.PLAYER;
                return new Position(playerX, playerY);
            }
        }
    }

    public int movePlayer(char key) {
        int x = player.x;
        int y = player.y;
        if (key == 'w') {
            y += 1;
        } else if (key == 'a') {
            x -= 1;
        } else if (key == 's') {
            y -= 1;
        } else if (key == 'd') {
            x += 1;
        }
        if (world[x][y].equals(Tileset.FLOOR)) {
            world[player.x][player.y] = Tileset.FLOOR;
            world[x][y] = Tileset.PLAYER;
            player.x = x;
            player.y = y;
            return 0;
        } else if (world[x][y].equals(Tileset.LOCKED_DOOR)) {
            world[player.x][player.y] = Tileset.FLOOR;
            world[x][y] = Tileset.PLAYER;
            player.x = x;
            player.y = y;
            return 1;
        }
        return -1;
    }

    public TETile[][] getFrame() {
        return world;
    }
}
