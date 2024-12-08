package edu.mvcc.jyaghy09.hajjybird.renderable;

import edu.mvcc.jyaghy09.hajjybird.GameTask;
import edu.mvcc.jyaghy09.hajjybird.HajjyBird;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Renderable implementation to render and calculate bird display
 * Handles gravity, rotation, collision etc
 */
public class Bird implements Renderable {

    private final BufferedImage[] birdImages = new BufferedImage[4];

    private int birdY;

    private int gravityScale = 1;

    public Bird() {
        this.birdY = 100;

        // Load our bird sprite sheet
        BufferedImage birdSprites = HajjyBird.loadImageFromResources("bird");

        // Cut out the sprites into an array, to create an animation
        for (int i = 0; i < birdImages.length; i++) {
            birdImages[i] =
                    HajjyBird.resize(
                            birdSprites.getSubimage(i * 28, 0, 28, 28),
                            28 * 2,
                            28 * 2
                    );
        }
    }

    @Override
    public void render(GameTask gameTask, Graphics2D graphics2D, int currentTick) {
        // Every 4 ticks, increment the gravity so the bird gradually falls faster
        if (currentTick % 4 == 0) {
            gravityScale++;
        }

        // Adjust the bird Y level
        birdY += gravityScale;

        // Consider going out of bounds a collision
        if (birdY < -20 || birdY > HajjyBird.GAME_HEIGHT) {
            gameTask.handleCollision();
        }

        // Calculate rotation degrees based on the gravity scale
        // The faster the bird is falling the more it will rotate
        final int degrees = gravityScale * 3;

        // Draw the rotated bird
        graphics2D.drawImage(
                HajjyBird.rotate( // Rotates the image
                        degrees,
                        birdImages[(currentTick % 20) / 5] // Chooses the correct sprite based on tick, to create smooth animation
                ),
                100,
                birdY,
                null
        );
    }

    /**
     * Called when a jump is initiated by the player
     */
    public void jump() {
        // Sets gravity to negative to create flying up effect
        gravityScale = -5;
    }

    public BufferedImage[] getBirdImages() {
        return birdImages;
    }

    public int getBirdY() {
        return birdY;
    }

    public int getGravityScale() {
        return gravityScale;
    }
}
