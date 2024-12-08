# HajjyBird

**Course**: CI 245 - Java Programming  
**Topic**: Making your own game in Java

---

## Overview

HajjyBird is a re-make of the popular name Flappy Bird.

HAJJ is an initialism which represents the letters of all our team members first names.

---

## Key Features

* Each frame is drawn to a BufferedImage, which is displayed on a JavaFX ImageView
No FXML is used, or any internal JavaFX components besides the ImageView/required scenes/stages

* Abstract Renderable components used for easy rendering
    ```java
      void render(GameTask gameTask, Graphics2D graphics2D, int currentTick);
    ```
  All of these renderables are called in order from bottom layer to top in the GameTask loop
* Dynamic pipe system which takes a base pipe image and can dynamically generate pipes of different heights
* Collision detection system which checks if the bird has collided with any of the pipes
* Score system which increments the score when the bird passes a pipe
* Game over screen which displays the score and a button to restart the game