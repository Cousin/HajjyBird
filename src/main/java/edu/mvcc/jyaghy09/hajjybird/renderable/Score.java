package edu.mvcc.jyaghy09.hajjybird.renderable;

import edu.mvcc.jyaghy09.hajjybird.GameTask;
import edu.mvcc.jyaghy09.hajjybird.HajjyBird;

import java.awt.*;

/**
 * Renderable implementation to render the score as text
 */
public class Score implements Renderable {

    private int score = 0;

    @Override
    public void render(GameTask gameTask, Graphics2D graphics2D, int currentTick) {
        graphics2D.setFont(new Font("Arial", Font.PLAIN, 50));
        graphics2D.drawString(
                String.valueOf(score),
                (int) ((HajjyBird.GAME_WIDTH * 0.50) - graphics2D.getFontMetrics().stringWidth(String.valueOf(score)) / 2.0),
                50
        );
    }

    public void incrementScore() {
        score++;
    }

}
