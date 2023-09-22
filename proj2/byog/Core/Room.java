package byog.Core;

import java.io.Serializable;

public class Room implements Serializable{
    private int width;
    private int height;
    private int x;
    private int y;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean isOverlap(Room other) {
        return (Math.min(x + width, other.x + other.width) >= Math.max(x, other.x) + 1)
                && (Math.min(y + height, other.y + other.height) >= Math.max(y, other.y) + 1);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
