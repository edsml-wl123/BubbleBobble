package com.example.bubblebobble.GameObjects;

import javafx.geometry.Rectangle2D;

/**
 * The interface of game objects.
 */
public interface GameObject {
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    Rectangle2D getHitbox();

    boolean overlaps(GameObject obj);

}
