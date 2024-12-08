package edu.mvcc.jyaghy09.hajjybird;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Main Application class to run the game
 */
public class HajjyBird extends Application {

    /**
     * Constant for the width of the game
     */
    public static final int GAME_WIDTH = 1600 / 2;

    /**
     * Constant for the height of the game
     */
    public static final int GAME_HEIGHT = 1203 / 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Do not allow the window to be resized
        primaryStage.setResizable(false);

        // Create base pane to be our root component
        Pane pane = new Pane();

        // Create ImageView to host our rendered game
        ImageView imageView = new ImageView();

        // Add the ImageView to the pane
        pane.getChildren().add(imageView);

        // Set the pane size
        pane.setPrefSize(GAME_WIDTH, GAME_HEIGHT);

        // Create the base scene which contains the pane
        Scene scene = new Scene(pane);

        // Set the scene in the stage and display the window
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create a new GameTask, and run it on a new thread
        GameTask gameTask = new GameTask(imageView);
        new Thread(gameTask).start();

        // Our keybind handler
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                gameTask.getBird().jump();
            } else if (event.getCode() == KeyCode.ENTER) {
                if (gameTask.isDead()) {
                    gameTask.resetGame();
                } else if (!gameTask.isStarted()) {
                    gameTask.setStarted(true);
                }
            }
        });

    }

    /**
     * Resizes a BufferedImage and returns the new resized image
     * @param img original image
     * @param newW new width
     * @param newH new height
     * @return the new resized BufferedImage
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    /**
     * Rotates a BufferedImage by a certain amount of degrees
     * @param degrees the degrees to rotate by
     * @param original the original image
     * @return the new resized BufferedImage
     */
    public static BufferedImage rotate(int degrees, BufferedImage original) {
        if (degrees == 0) {
            return original;
        }

        // Convert degrees to radians
        double radians = Math.toRadians(degrees);

        // Get the original dimensions of the image
        int width = original.getWidth();
        int height = original.getHeight();

        // Create a new BufferedImage to hold the rotated image
        BufferedImage rotatedImage = new BufferedImage(width, height, original.getType());

        // Get the Graphics2D object for drawing on the rotated image
        Graphics2D g2d = rotatedImage.createGraphics();

        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // Calculate the center of the image
        int centerX = width / 2;
        int centerY = height / 2;

        // Rotate the image around the center
        g2d.rotate(radians, centerX, centerY);

        // Draw the original image on the rotated image
        // The (0, 0) coordinate here is where the original image will be placed
        g2d.drawImage(original, 0, 0, null);

        // Dispose of the Graphics2D object to release resources
        g2d.dispose();

        return rotatedImage;
    }

    /**
     * Load a BufferedImage from the resources folder by the name
     * @param filename the file name, excluding extension
     * @return the loaded BufferedImage
     */
    public static BufferedImage loadImageFromResources(String filename) {
        try {
            return ImageIO.read(HajjyBird.class.getResource("/" + filename + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
