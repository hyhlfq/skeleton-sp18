package byog.Core;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private World world;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawMenu();
        while (true) {
            char key = getNextKey();
            if (key == 'n') {
                drawSeedInput();
                long seed = getSeed();
                world = new World(seed, WIDTH, HEIGHT);
                ter.initialize(WIDTH, HEIGHT);
                ter.renderFrame(world.getFrame());
                break;
            } else if (key == 'l') {
                world = loadGame();
                ter.initialize(WIDTH, HEIGHT);
                ter.renderFrame(world.getFrame());
                break;
            } else if (key == 'q') {
                System.exit(0);
            }
        }
        play();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        if (input.charAt(0) == 'N') {
            int seedEndIndex = getSeedEndIndex(input.substring(1));
            long seed = Long.parseLong(input.substring(1, seedEndIndex));
            world = new World(seed, WIDTH, HEIGHT);
            playByInput(input.substring(seedEndIndex + 2));
            return world.getFrame();
        } else if (input.charAt(0) == 'L') {
            world = loadGame();
            playByInput(input.substring(1));
            return world.getFrame();
        } else {
            return null;
        }
    }

    private int getSeedEndIndex(String input) {
        int i = 0;
        while (i < input.length() && Character.isDigit(input.charAt(i))) {
            i++;
        }
        return i;
    }

    private void playByInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            char key = input.charAt(i);
            if (key == 'W' || key == 'A' || key == 'S' || key == 'D') {
                if (world.movePlayer(Character.toLowerCase(key)) == 1) { // player wins
                    break;
                }
            } else if (key == 'Q') {
                saveGame(world);
                break;
            }
        }
    }

    private void saveGame(World w) {
        File f = new File("./world.ser");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private World loadGame() {
        File f = new File("./world.ser");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                World loadWorld = (World) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return null;
    }

    private void drawMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "CS61B: THE GAME");
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, "Quit (Q)");
        StdDraw.show();
    }

    private void drawSeedInput() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter Seed: ");
        StdDraw.show();
    }

    private long getSeed() {
        String seedInput = "";
        while (true) {
            char key = getNextKey();
            if (key == 's') {
                break;
            } else if (Character.isDigit(key)) {
                seedInput += key;
                StdDraw.clear(StdDraw.BLACK);
                StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter Seed: " + seedInput);
                StdDraw.show();
            }
        }
        if (seedInput.equals("")) {
            return System.currentTimeMillis();
        }
        return Long.parseLong(seedInput);
    }

    private Character getNextKey() {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.pause(10);
        }
        return Character.toLowerCase(StdDraw.nextKeyTyped());
    }

    private void drawWin() {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "You Win!");
        StdDraw.show();
    }

    private void play() {
        while (true) {
            char key = getNextKey();
            if (key == 'w' || key == 'a' || key == 's' || key == 'd') {
                int isWin = world.movePlayer(key);
                ter.renderFrame(world.getFrame());
                if (isWin == 1) {
                    StdDraw.pause(1000);
                    drawWin();
                    StdDraw.pause(1000);
                    System.exit(0);
                }
            } else if (key == 'q') {
                saveGame(world);
                System.exit(0);
            }
        }
    }
}
