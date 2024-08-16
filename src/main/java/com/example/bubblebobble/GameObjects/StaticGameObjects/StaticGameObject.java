package com.example.bubblebobble.GameObjects.StaticGameObjects;

import com.example.bubblebobble.GameObjects.GameObject;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * StaticGameObject is a abstract class which define methods for static
 * game objects.
 * Its child class includes {@code WallUnit}, {@code FloorUnit}, {@code CeilingUnit},
 * {@code Portal} and {@code Destination}.
 */
public abstract class StaticGameObject implements GameObject {
    public InteractableWorld world;
    protected int x, y;
    protected int width, height;

    public StaticGameObject(InteractableWorld world, int colNum, int rowNum, int width, int height) {
        this.world = world;
        x = colNum * Main.UNIT_SIZE; //change the variable?
        y = rowNum * Main.UNIT_SIZE;
        this.width = width;
        this.height = height;
    }

    /**
     * Draw method which draws the static game object on the canvas on the
     * game scene.
     * Call the draw method of {@code mapFactory} in the world to draw the
     * specific images according to different maps.
     * @param gc The graphic context of the canvas on the game scene.
     */
    public void drawOn(GraphicsContext gc) {
        world.getMapFactory().drawOnStaticObject(this,gc);
    }


    /**
     * Get method of {@code x} of the static game object.
     * @return x coordinate of upper left corner of the static game object.
     */
    public int getX() {
        return x;
    }

    /**
     * Get method of {@code y} of the static game object.
     * @return y coordinate of upper left corner of the static game object.
     */
    public int getY() {
        return y;
    }

    /**
     * Get method of {@code width} of the static game object.
     * @return Width of the static game object.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get method of {@code height} of the static game object.
     * @return Height of the static game object.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the hitbox for the static game object.
     * @return The hit box of the static game object.
     */
    public Rectangle2D getHitbox(){
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * Check whether two game objects overlap.
     * @param obj The game object that this static game object has interaction to.
     * @return The boolean value of whether two game objects overlap.
     */
    public boolean overlaps(GameObject obj) {
        return this.getHitbox().intersects(obj.getHitbox());
    }

}
