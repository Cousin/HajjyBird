package edu.mvcc.jyaghy09.hajjybird.renderable;

import edu.mvcc.jyaghy09.hajjybird.GameTask;
import edu.mvcc.jyaghy09.hajjybird.HajjyBird;

import java.awt.*;

/**
 * Renderable implementation to display a 'You died' screen
 */
public class DeadScreen implements Renderable {

    @Override
    public void render(GameTask gameTask, Graphics2D graphics2D, int currentTick) {

        final String title = "You died!";
        final String subtitle = "Press enter to play again!";

        // Draw out text and center

        graphics2D.setFont(new Font("Impact", Font.PLAIN, 64));
        graphics2D.setColor(Color.RED);
        graphics2D.drawString(
                title,
                (int) ((HajjyBird.GAME_WIDTH * 0.50) - graphics2D.getFontMetrics().stringWidth(title) / 2.0),
                (int) (HajjyBird.GAME_HEIGHT * 0.20)
        );

        graphics2D.setFont(new Font("Impact", Font.PLAIN, 32));
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(
                subtitle,
                (int) ((HajjyBird.GAME_WIDTH * 0.50) - graphics2D.getFontMetrics().stringWidth(subtitle) / 2.0),
                (int) (HajjyBird.GAME_HEIGHT * 0.30)
        );

    }

}
