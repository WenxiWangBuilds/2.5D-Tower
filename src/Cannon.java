import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Cannon extends Building implements Defense {
    public static final int FRAME_COUNT = 23; // 0001.png to 0023.png
    public static final double DEGREES_PER_FRAME = 360.0 / FRAME_COUNT;
    String pictureFolders = "src/Images/TurretLevel1/";

    private static Vector offSet = new Vector(-128, -64); // offset of the building
    private static Vector dimensions = new Vector(3, 3); // dimensions
    private static final double range = 4.0; // range of attack

    private static final int SWAY_STEPS = 8; // frames per sweep
    private static final double SWAY_STEP_INTERVAL_MS = 120; // ms per frame step
    private static final double SWAY_REST_MS = 1500; // rest duration between sweeps


    private BufferedImage[] frames = loadFrames();

    private int currentFrame = 0;
    private boolean resting = true;
    private double elapsedMs = 0;
    private int stepsRemaining = 0;
    private int swayDirection = 1;

    /**
     * Creates a turret facing frame 0.
     *
     * @param position grid coordinate of the anchor tile
     */
    public Cannon(Vector position) {
        super(offSet, position, dimensions);
    }

    public void setPictureFolders(String pictureFolders) {
        this.pictureFolders = pictureFolders;
        frames = loadFrames();
    }

    private BufferedImage[] loadFrames() {
        this.frames = new BufferedImage[FRAME_COUNT];
        BufferedImage[] loaded = new BufferedImage[FRAME_COUNT];
        for (int i = 0; i < FRAME_COUNT; i++) {
            String path = String.format("%s%04d.png", this.pictureFolders, i + 1);
            try {
                loaded[i] = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.err.println("Failed to load turret frame: " + path + " (" + e.getMessage() + ")");
            }
        }
        return loaded;
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

    public void idleAnimation(double deltaMs) {
        if (deltaMs <= 0) {
            return;
        }

        elapsedMs += deltaMs;

        //full stop idle
        if (resting) {
            if (elapsedMs >= SWAY_REST_MS) {
                resting = false;
                elapsedMs = 0;
                stepsRemaining = (int) (SWAY_STEPS * Math.random() + 2);
            }
            return;
        }

        //moving

        while (stepsRemaining > 0 && elapsedMs >= SWAY_STEP_INTERVAL_MS) {
            elapsedMs -= SWAY_STEP_INTERVAL_MS;
            currentFrame = (currentFrame + swayDirection + FRAME_COUNT) % FRAME_COUNT;  //Preventing outOfBounds errors
            stepsRemaining--;
        }

        if (stepsRemaining == 0) {
            resting = true;
            elapsedMs = 0;
            swayDirection = (Math.random() >= 0.5) ?  -1 : 1;
        }
    }

    @Override
    public void update() {
        idleAnimation(10);
    }

    @Override
    public void draw(Graphics g, Vector v) {
        BufferedImage sprite = frames[currentFrame];
        int drawX = (int) (v.x + offset.x);
        int drawY = (int) (v.y + 32 - Tile.HEIGHT + offset.y);

        if (sprite != null) {
            g.drawImage(sprite, drawX, drawY, null);
        } else {
            // Fallback box if a sprite failed to load
            g.setColor(Color.MAGENTA);
            g.fillRect(drawX, drawY, 64, 64);
        }
    }

    @Override
    public void draw_Exta(Graphics g, Vector v) {
        if(is_selected) {
            double semiX = (double) 4.0F * Math.sqrt((double) 2.0F) * (double) 128.0F / (double) 2.0F;
            double semiY = (double) 4.0F * Math.sqrt((double) 2.0F) * (double) 64.0F / (double) 2.0F;
            g.setColor(Color.BLACK);
            new GradientPaint(0.0F, 0.0F, Color.RED, 300.0F, 300.0F, Color.BLUE);
            g.setColor(Color.BLACK);
            g.drawOval((int) ((double) v.x - semiX - (double) this.offset.x / (double) 2.0F), (int) ((double) v.y - semiY), (int) (semiX * (double) 2.0F), (int) (semiY * (double) 2.0F));
        }

    }
}