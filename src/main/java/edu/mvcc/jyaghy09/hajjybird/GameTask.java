package edu.mvcc.jyaghy09.hajjybird;

import edu.mvcc.jyaghy09.hajjybird.renderable.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Runnable implementation which handles all of our game logic and rendering
 */
public class GameTask implements Runnable {

    /**
     * The expected frames per second to render
     * Since our game and render loop is handled together, increasing this will increase the speed of the entire game
     * This is the render and game tick speed
     */
    private final long MAX_FPS = 60;

    /**
     * The base BufferedImage used to render our game on to
     */
    private final BufferedImage baseImage = new BufferedImage(HajjyBird.GAME_WIDTH, HajjyBird.GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    /**
     * Our {@link Renderable} object instances
     * These are not final as they are reset completely when the game restarts
     * TODO potentially add a reset method within Renderable
     */
    private Background background;
    private Bird bird;
    private List<PipeGroup> pipes;
    private Score score;
    private DeadScreen deadScreen;

    /**
     * Another Renderable component which represents the title screen
     */
    private final TitleScreen titleScreen = new TitleScreen();

    /**
     * The ImageView reference where our game display is set into
     */
    private final ImageView imageView;

    /**
     * The Graphics2D reference of our BufferedImage where we render everything to
     */
    private final Graphics2D graphics2D;

    /**
     * The current tick count, this increments 1 per frame and does not reset until the game restarts
     */
    private int tick = 0;

    /**
     * State of if the bird is dead/game is over
     */
    private boolean dead = false;

    /**
     * State of if the game was started after the title screen, only set once at application start
     */
    private boolean started = false;

    /**
     * Constructor to pass our ImageView in and start the game
     */
    public GameTask(ImageView imageView) {
        this.imageView = imageView;
        this.graphics2D = baseImage.createGraphics();

        resetGame();
    }

    /**
     * Called every tick, handles everything to render the game
     */
    private void render() {
        // Render the background component first
        background.render(this, graphics2D, tick);

        if (!started) {
            // If the game hasn't started yet, render the title screen
            titleScreen.render(this, graphics2D, tick);
        } else {
            // Render pipes, these display even after you lose
            for (PipeGroup pipe : pipes) {
                pipe.render(this, graphics2D, tick);
            }

            if (dead) {
                // If dead, render dead screen
                deadScreen.render(this, graphics2D, tick);
            } else {
                // If alive, render the bird
                bird.render(this, graphics2D, tick);
            }

            // Render the score display
            score.render(this, graphics2D, tick);

            // Remove pipes that are off screen
            pipes.removeIf(p -> p.getX() < -50);

            // Every 75 ticks, create new pipe of random size
            if (tick % 75 == 0) {
                final int maxLength = 100;

                int topLength = ThreadLocalRandom.current().nextInt(0, 60);

                pipes.add(
                        new PipeGroup(
                                topLength,
                                maxLength - topLength - 20
                        )
                );
            }
        }

        // Put the new rendered image into the ImageView
        imageView.setImage(SwingFXUtils.toFXImage(baseImage, null));
    }

    /**
     * Called when a collision occurs
     */
    public void handleCollision() {
        dead = true;
    }

    /**
     * Fully reset the game
     */
    public void resetGame() {
        background = new Background();
        bird = new Bird();
        pipes = new ArrayList<>();
        score = new Score();
        deadScreen = new DeadScreen();

        dead = false;

        tick = 0;
    }

    @Override
    public void run() {
        // Infinitely run
        while (true) {
            // Render game
            render();

            // Increment tick
            tick++;

            // Sleep enough to handle the fps we expect
            // TODO calculate how long the frame took?
            try {
                Thread.sleep((1000 / MAX_FPS));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Getters & Setters
     * vvvvvvvvvvvvvvvvv
     */

    public Bird getBird() {
        return bird;
    }

    public Score getScore() {
        return score;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

}
