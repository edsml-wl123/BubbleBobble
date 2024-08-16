package com.example.bubblebobble.GameObjects.NonstaticGameObjects;

import com.example.bubblebobble.GameObjects.GameObject;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;
import com.example.bubblebobble.GameObjects.StaticGameObjects.StaticGameObject;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.Portal;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;


/**
 * NonstaticGameObject is a abstract class which define methods for nonstatic
 * game objects.
 * Its child class includes {@code Hero}, {@code Enemy} and {@code Boss}.
 */
public abstract class NonstaticGameObject implements GameObject {
    public static final double STATIC_FRICTION = 0.1;
    public static final int GRAVITY = 1;
    public static final int TERMINAL_FALL_SPEED = 10;//decrease fall speed
    public static final int JUMP_SPEED = 16;

    public InteractableWorld world;
    protected int x, y;
    protected int width, height;

    public int jumpSpeed;
    public double xVelocity, yVelocity; //can be negative or positive according to different directions
    public double xAccel, yAccel;
    public double terminal_xVelocity, terminal_yVelocity;

    public boolean canRemove;
    public int direction;
    public boolean isOnAPlatform;

    public NonstaticGameObject(InteractableWorld world, int colNum, int rowNum, int width, int height) {
        this.world = world;
        x = colNum * Main.UNIT_SIZE;
        y = rowNum * Main.UNIT_SIZE;
        this.width = width;
        this.height = height;

        jumpSpeed=JUMP_SPEED;
        xVelocity = 0;
        yVelocity = 0;
        xAccel = 0;
        yAccel = GRAVITY;
        terminal_xVelocity = 0;
        terminal_yVelocity = TERMINAL_FALL_SPEED;
        canRemove = false;
        direction = -1;
        isOnAPlatform=false;
    }

    public NonstaticGameObject(int x, int y, int width, int height, InteractableWorld world) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;

        xVelocity = 0;
        yVelocity = 0;
        xAccel = 0;
        yAccel = GRAVITY;
        terminal_xVelocity = 0;
        terminal_yVelocity = TERMINAL_FALL_SPEED;
        canRemove = false;
        direction = -1;
    }

    /**
     * Set method of {@code width} and {@code height} of the nonstatic game
     * object.
     * @param width The parameter used to set {@code width} of the
     *              nonstatic game object.
     * @param height The parameter used to set {@code height} of the
     *               nonstatic game object.
     */
    public void setSize(int width, int height){
        this.width=width;
        this.height=height;
    }

    /**
     * Abstract draw method which draws the nonstatic game object
     * in the child classes of {@code NonstaticGameObject}.
     * @param gc The graphic context of the canvas on the game scene.
     */
    public abstract void drawOn(GraphicsContext gc);

    /**
     * Abstract method which handles colliding with floor unit.
     * @param floorUnit The floor unit which the nonstatic game object
     *                  has interaction to.
     */
    public abstract void collideWith(FloorUnit floorUnit);

    /**
     * Abstract method which handles colliding with ceiling unit.
     * @param ceilingUnit The ceiling unit which the nonstatic game object
     *                    has interaction to.
     */
    public abstract void collideWith(CeilingUnit ceilingUnit);

    /**
     * Abstract method which handles colliding with wall unit.
     * @param wallUnit The wall unit which the nonstatic game object
     *                  has interaction to.
     */
    public abstract void collideWith(WallUnit wallUnit);

    /**
     * Move the nonstatic game object to the above of a static game objet.
     * @param obj The static game object that the nonstatic game object has interaction to.
     */
    public void moveToAbove(StaticGameObject obj) {
        moveTo(new Point2D(x, obj.getY() - height));
    }

    /**
     * Move the nonstatic game object to the below of a static game objet.
     * @param obj The static game object that the nonstatic game object has interaction to.
     */
    public void moveToBelow(StaticGameObject obj) {
        moveTo(new Point2D(x, obj.getY() + obj.getHeight()));
    }

    /**
     * Move the nonstatic game object to the left of a static game objet.
     * @param obj The static game object that the nonstatic game object has interaction to.
     */
    public void moveToLeft(StaticGameObject obj) {
        moveTo(new Point2D(obj.getX() - width, y));
    }

    /**
     * Move the nonstatic game object to the right of a static game objet.
     * @param obj The static game object that the nonstatic game object has interaction to.
     */
    public void moveToRight(StaticGameObject obj) {
        moveTo(new Point2D(obj.getX() + obj.getWidth(), y));
    }


    /**
     * Generally update the speed of the nonstatic game object.
     */
    protected void update() {
        //general update method of every game object
        if (Math.abs(xVelocity) < terminal_xVelocity) {
            xVelocity += xAccel;
        }
        if (Math.abs(xVelocity) > STATIC_FRICTION) {
            if (xVelocity < 0) {
                xVelocity += 1;
            } else {
                xVelocity -= 1;
            }
            x += xVelocity;
        }

        if (yVelocity < terminal_yVelocity) {
            yVelocity += yAccel;
        }
        y += yVelocity;

    }

    /**
     * Handles the change of speed when the nonstatic game object jumps.
     */
    public void jump() {
        if (isOnAPlatform) {
            y -= 1;
            yVelocity = -jumpSpeed;
            isOnAPlatform = false;
        }
    }

    /**
     * Reverse the direction of a nonstatic game object.
     */
    public void reverseDirection() {
        xAccel *= -1;
        direction *= -1;
    }

    /**
     * Get method of {@code x} of the nonstatic game object.
     * @return x coordinate of upper left corner of the nonstatic game object.
     */
    public int getX() {
        return x;
    }

    /**
     * Get method of {@code y} of the nonstatic game object.
     * @return y coordinate of upper left corner of the nonstatic game object.
     */
    public int getY() {
        return y;
    }

    /**
     * Get method of {@code width} of the nonstatic game object.
     * @return Width of the nonstatic game object.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get method of {@code height} of the nonstatic game object.
     * @return Height of the nonstatic game object.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set method of {@code x} of the nonstatic game object.
     * @param x Parameter used to set the {@code x} of the nonstatic game object.
     */
    public void setX(int x){this.x=x;}

    /**
     * Set method of {@code y} of the nonstatic game object.
     * @param y Parameter used to set the {@code y} of the nonstatic game object.
     */
    public void setY(int y){this.y=y;}
    /**
     * Set method of {@code width} of the nonstatic game object.
     * @param w Parameter used to set the {@code width} of the nonstatic game object.
     */
    public void setWidth(int w){this.width=w;}
    /**
     * Set method of {@code height} of the nonstatic game object.
     * @param h Parameter used to set the {@code height} of the nonstatic game object.
     */
    public void setHeight(int h){this.height=h;}


    /**
     * Mark the nonstatic game object to be able to be removed.
     */
    public void markToRemove() {
        canRemove = true;
    }

    /**
     * Set the hitbox for the nonstatic game object.
     * @return The hit box of the nonstatic game object.
     */
    public Rectangle2D getHitbox(){
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * Check whether two game objects overlap.
     * @param obj The game object that this nonstatic game object has interaction to.
     * @return The boolean value of whether two game objects overlap.
     */
    public boolean overlaps(GameObject obj) {
        //checks if two objects overlap or collide
        return this.getHitbox().intersects(obj.getHitbox()); //add this
    }


    /**
     * Move this nonstatic game object to a certain point on the canvas.
     * @param point The position on the canvas that this nonstatic game object is going to move to.
     */
    public void moveTo(Point2D point) {
        this.x = (int) point.getX();
        this.y = (int) point.getY();
    }


    /**
     * Check whether this nonstatic game object has reached a portal.
     * If this nonstatic object overlaps with the portal and is on a floor unit,
     * then it will be transferred to the portal's destination.
     * @param portal The portal that the nonstatic game object has interaction to.
     */
    public void reachPortal(Portal portal){
        if((y+height==portal.getY())&&(x<portal.getX()+portal.getWidth())&&(x+width>portal.getX())&&(isOnAPlatform)){
        this.moveTo(new Point2D(portal.getDes().getX(),portal.getDes().getY()-height));
        }
    }
}
