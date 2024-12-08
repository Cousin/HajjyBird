package edu.mvcc.jyaghy09.hajjybird.renderable;

import edu.mvcc.jyaghy09.hajjybird.GameTask;
import edu.mvcc.jyaghy09.hajjybird.HajjyBird;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Renderable implementation to render the background
 */
public class Background implements Renderable {

    private final BufferedImage backgroundImage;

    public Background() {
        this.backgroundImage =
        HajjyBird.resize(
                HajjyBird.loadImageFromResources("background"),
                HajjyBird.GAME_WIDTH, HajjyBird.GAME_HEIGHT
        );
    }

    @Override
    public void render(GameTask gameTask, Graphics2D graphics2D, int currentTick) {
        graphics2D.drawImage(
                backgroundImage,0, 0, null
        );
    }

}
