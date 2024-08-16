package com.example.bubblebobble.GameObjects.Weapons;

import com.example.bubblebobble.GameObjects.GameObject;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;


/**
 * Weapon is a abstract class which define methods for weapons of boss,
 * hero and enemy.
 * Its child class includes {@code Bubble} and {@code Projectile}.
 */
public abstract class Weapon implements GameObject {

    public InteractableWorld world;
    protected int x, y;
    protected int width, height;

    public boolean canRemove;
    boolean isActive;

    public Weapon(int x, int y, int width, int height, InteractableWorld world) {
        this.x = x;
        this.y = y;
        this.width = width; // The size of the game object
        this.height = height;
        this.world = world;

        canRemove = false;
        isActive=true;
    }

    /**
     * Get method of whether the weapon is {@code isActive}.
     * @return Whether the weapon is active.
     */
    public boolean getActive(){return isActive;}

    /**
     * Set method of the property {@code isActive} of the weapon.
     * @param active Value to set the value of {@code isActive}.
     */
    public void setActive(boolean active){isActive=active;}

    /**
     * Abstract method which updates the position, speed and activeness of
     * the child classes of {@code Weapon}.
     */
    abstract void update();

    /**
     * Abstract draw method which draws the corresponding weapon in
     * the child classes of {@code Weapon}.
     * @param gc The graphic context of the canvas on the game scene.
     */
    abstract void drawOn(GraphicsContext gc);

    /**
     * Get method of {@code x} of the weapon.
     * @return x coordinate of upper left corner of the weapon.
     */
    public int getX() {
        return x;
    }

    /**
     * Get method of {@code y} of the weapon.
     * @return y coordinate of upper left corner of the weapon.
     */
    public int getY() {
        return y;
    }

    /**
     * Get method of {@code width} of the weapon.
     * @return Width of the weapon.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get method of {@code height} of the weapon.
     * @return Height of the weapon.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set method of {@code x} of the weapon.
     * @param x Parameter used to set the {@code x} of the weapon.
     */
    public void setX(int x){this.x=x;}

    /**
     * Set method of {@code y} of the weapon.
     * @param y Parameter used to set the {@code y} of weapon.
     */
    public void setY(int y){this.y=y;}
    /**
     * Set method of {@code width} of the weapon.
     * @param w Parameter used to set the {@code width} of the weapon.
     */
    public void setWidth(int w){this.width=w;}
    /**
     * Set method of {@code height} of the weapon.
     * @param h Parameter used to set the {@code height} of the weapon.
     */
    public void setHeight(int h){this.height=h;}

    /**
     * Mark the nonstatic weapon to be able to be removed.
     */
    public void markToRemove() {
        canRemove = true;
    }


    /**
     * Set the hitbox for the weapon.
     * @return The hit box of the weapon.
     */
    public Rectangle2D getHitbox(){
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * Check this weapon overlaps another game object.
     * @param obj The game object that this weapon has interaction to.
     * @return The boolean value of whether two game objects overlap.
     */
    public boolean overlaps(GameObject obj) {
        return this.getHitbox().intersects(obj.getHitbox()); //add this
    }


    /**
     * Handles colliding with a weapon of rival.
     * If this weapon overlaps a weapon from its rival, then it will
     * become inactive.
     * @param rivalWeapon The weapon that this weapon has interaction to.
     */
    public void collideWith(Weapon rivalWeapon){
        if(this.overlaps(rivalWeapon)&& rivalWeapon.getActive()){
            this.isActive=false;
        }
    }
}
