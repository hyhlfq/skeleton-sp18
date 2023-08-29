package byog.Core;

public class Room {
    public int width;
    public int height;
    public int x;
    public int y;

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
}
