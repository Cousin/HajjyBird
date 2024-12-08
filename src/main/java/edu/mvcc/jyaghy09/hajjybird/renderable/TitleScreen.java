package edu.mvcc.jyaghy09.hajjybird.renderable;

import edu.mvcc.jyaghy09.hajjybird.GameTask;
import edu.mvcc.jyaghy09.hajjybird.HajjyBird;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Renderable implementation to display a title screen at the start of the application
 */
public class TitleScreen implements Renderable {

    private final BufferedImage logo;

    private int offset = 0;

    private boolean add = false;

    public TitleScreen() {
        this.logo = HajjyBird.resize(
                HajjyBird.loadImageFromResources("logo"),
                500,
                500
        );
    }

    @Override
    public void render(GameTask gameTask, Graphics2D graphics2D, int currentTick) {

        // Draw logo image on screen at offset to create bouncing animation
        graphics2D.drawImage(logo,HajjyBird.GAME_WIDTH / 2 - 250,
                -150 + offset,
                null
        );

        // If at bounds, reset direction
        if (offset > 25) {
            add = false;
        } else if (offset < -25) {
            add = true;
        }

        // Increment the offset in our desired direction
        offset += 2 * (add ? 1 : -1);

        // Draw text centered on screen

        final String subtitle = "Press enter to play!";

        graphics2D.setFont(new Font("Impact", Font.PLAIN, 32));

        graphics2D.setColor(Color.WHITE);

        graphics2D.drawString(
                subtitle,
                (int) ((HajjyBird.GAME_WIDTH * 0.50) - graphics2D.getFontMetrics().stringWidth(subtitle) / 2.0),
                (int) (HajjyBird.GAME_HEIGHT * 0.40)
        );

    }

}
