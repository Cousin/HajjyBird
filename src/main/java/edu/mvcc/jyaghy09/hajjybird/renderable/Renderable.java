package edu.mvcc.jyaghy09.hajjybird.renderable;

import edu.mvcc.jyaghy09.hajjybird.GameTask;

import java.awt.*;

/**
 * Interface which represents a component that is rendered to the screen
 */
public interface Renderable {

    /**
     * Called each tick when the game is rendering
     * @param gameTask the game task instance
     * @param graphics2D the graphics2D instance
     * @param currentTick the current game tick
     */
    void render(GameTask gameTask, Graphics2D graphics2D, int currentTick);

}
