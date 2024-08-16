package com.example.bubblebobble.GameObjects.Weapons;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import javafx.scene.canvas.GraphicsContext;

/**
 * The Bubble class handles everything with {@code Hero}'s special ability, named
 * the bubble.
 * It begins at the hero, and grows covering the whole screen.Once it collides
 * with an enemy, that enemy is bubbled. Once it collides with the boss, the boss
 * loses 1 life.
 */
public class Bubble extends Weapon {
    private int accel;

    public Bubble(InteractableWorld world, int x, int y) {
        super(x,y,0,0,world); // make the order of variables corresponding to super methods
        accel = 1;
    }

    /**
     * Update the speed and size of the bubble.
     */
    @Override
    public void update() {
        if (width >= 2500) {
            markToRemove();
        }
        x -= accel / 2;
        y -= accel / 2;
        width += accel;
        height += accel;
        accel += 1;
    }

    /**
     * Draw the bubble on the canvas on the game scene by calling
     * the draw method of {@code mapFactory} in the bubble's world.
     * @param gc The graphic context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        world.getMapFactory().drawBubble(this,gc);
    }
}
