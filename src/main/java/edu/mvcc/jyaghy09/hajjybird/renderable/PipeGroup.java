package edu.mvcc.jyaghy09.hajjybird.renderable;

import edu.mvcc.jyaghy09.hajjybird.GameTask;
import edu.mvcc.jyaghy09.hajjybird.HajjyBird;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Renderable implementation which represents a top and bottom pipe
 * Automatically rendered based on pipe sprite and inputted size
 */
public class PipeGroup implements Renderable {

    /**
     * The full pipe sprite
     */
    private static final BufferedImage fullPipeImage = HajjyBird.loadImageFromResources("pipe");

    /**
     * The head section of the pipe
     */
    private static final BufferedImage pipeHead = HajjyBird.resize(fullPipeImage.getSubimage(460, 708, 310, 132), 310 / 5, 123 / 5);

    /**
     * The body section of the pipe, multiplied together to create bigger pipes
     */
    private static final BufferedImage pipeBody = HajjyBird.resize(fullPipeImage.getSubimage(470, 650, 290, 30), 290 / 5, 30 / 5);

    private final int topLength;

    private final int bottomLength;

    private int x;

    public PipeGroup(int topLength, int bottomLength) {
        this.topLength = topLength;
        this.bottomLength = bottomLength;
        this.x = HajjyBird.GAME_WIDTH;
    }

    @Override
    public void render(GameTask gameTask, Graphics2D graphics2D, int currentTick) {
        // Dynamically draw body parts for top pipe size
        for (int i = 0; i < topLength; i++) {
            graphics2D.drawImage(pipeBody, x, (i * 6), null);
        }
        // Draw head at bottom
        graphics2D.drawImage(pipeHead, x - 2, topLength * 6, null);

        // Dynamically draw body parts for bottom pipe size
        for (int i = 0; i < bottomLength; i++) {
            graphics2D.drawImage(pipeBody, x, HajjyBird.GAME_HEIGHT - (i * 6), null);
        }
        // Draw head at top
        graphics2D.drawImage(pipeHead, x - 2, HajjyBird.GAME_HEIGHT - bottomLength * 6, null);

        // Check if bird is not within the safe zone of this pipe
        // If not, call a collision
        if (!isWithinSafeZone(100 + (28 * 2), gameTask.getBird().getBirdY())) {
            gameTask.handleCollision();
        }

        // Subtract x by 3 to make them move left
        x -= 3;

        // If the pipe makes it to x50, consider the bird went through and increment score
        if (x == 50 && !gameTask.isDead()) {
            gameTask.getScore().incrementScore();
        }
    }

    /**
     * Get the lowest y to be considered safe
     */
    private int getMinYSafe() {
        return (topLength * 6) + 6;
    }

    /**
     * Get the highest y to be considered safe
     */
    private int getMaxYSafe() {
        return (HajjyBird.GAME_HEIGHT - bottomLength * 6) - 6;
    }

    /**
     * Checks if a game coordinate is within the safe zone / not intercepting the pipes
     */
    private boolean isWithinSafeZone(int x, int y) {
        if (!(x < this.x || x > this.x + 310 / 5)) {
            return (y > getMinYSafe() && y < getMaxYSafe());
        }

        return true;
    }

    public int getX() {
        return x;
    }
}