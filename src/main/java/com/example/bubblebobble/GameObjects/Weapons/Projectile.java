package com.example.bubblebobble.GameObjects.Weapons;

import com.example.bubblebobble.GameObjects.GameObject;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.StaticGameObject;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Projectile class defines methods used by both {@code EnemyProjectile}
 * and {@code HeroProjectile}.
 * <p>
 * Every projectile will have a period which it's active. Also, a projectile will
 * lost its activeness once it collides with another projectile from its rival.
 * </p>
 */
public abstract class Projectile extends Weapon {

    private static final int TERMINAL_VELOCITY_X=0;
    protected double TERMINAL_VELOCITY_Y;
    private static final double REMAIN_TIME=150;

    public double xVelocity, yVelocity; //can be negative or positive according to different directions
    public double xAccel, yAccel;

    public int direction;

    protected int activeFrames;
    protected int timer;

    public Projectile(int x, int y, int width, int height, InteractableWorld world) {
        super(x,y,width,height,world);
        direction = -1;
    }

    /**
     * Update the speed of this projectile.
     * Check whether the projectile has lost its activeness after a period.
     * If yes, the projectile will be marked to be able to remove.
     */
    @Override
    public void update() {
        y += yVelocity;
        x += xVelocity * direction;
        updateVelocity();

        if(y < 25) {
            y = 25;
        }

        if (timer < 0) {
            this.setActive(false);
        }

        if (timer < -REMAIN_TIME) {
            markToRemove();
        }
        timer -= 1;
    }

    /**
     * Update the speed of the projectile.
     */
    private void updateVelocity() {
        if (xVelocity > TERMINAL_VELOCITY_X) {
            xVelocity -= xAccel;
        } else {
            xVelocity = TERMINAL_VELOCITY_X;
        }

        if (Math.abs(yVelocity) < TERMINAL_VELOCITY_Y && !getActive()) {
            yVelocity -= yAccel;
        }
    }



    /**
     * Handles colliding with floor units.
     * It the projectile collides with a floor unit, it will be moved onto
     * the floor units if its most body has overlapped with the floor.
     * Otherwise, it will be moved below to the floor unit
     * @param floorUnit The floor unit which the projectile has interaction to.
     */
    public void collideWith(FloorUnit floorUnit) {
        double top = y;
        double bottom = y - height;

        if (this.overlaps(floorUnit) && yVelocity > 0) {
            if (bottom < floorUnit.getY() + floorUnit.getHeight()) {
                moveToAbove(floorUnit);
            }
            if (top > floorUnit.getY()) {
                moveToBelow(floorUnit);
            }
        }
    }

    /**
     * Handles colliding with ceiling units.
     * If the projectile collides with a ceiling, it will be moved below to
     * the ceiling.
     * @param ceilingUnit The ceiling that the projectile has interaction to.
     */
    public void collideWith(CeilingUnit ceilingUnit) {
        if (this.overlaps(ceilingUnit)) {
            moveToBelow(ceilingUnit);
        }
    }

    /**
     * Handles colliding with wall units.
     * If the projectile collides with the wall's left side, it will be moved
     * to the left side of the wall; otherwise, it will be moved to the wall's
     * right side.
     * @param wallUnit The wall that the projectile has interaction to.
     */
    public void collideWith(WallUnit wallUnit) {
        if (this.overlaps(wallUnit)) {
            double thisCenter = (this.getHitbox().getMaxX() + this.getHitbox().getMinX()) / 2;
            double center = (wallUnit.getHitbox().getMaxX() + wallUnit.getHitbox().getMinX()) / 2;

            if (center > thisCenter) {
                moveToLeft(wallUnit);
            } else if (center <= thisCenter) {
                moveToRight(wallUnit);
            } else {
                moveToBelow(wallUnit);
            }
        }
    }



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
     * Move this projectile to a certain point on the canvas.
     * @param point The position on the canvas that this projectile is going to move to.
     */
    public void moveTo(Point2D point) {
        this.x = (int) point.getX();
        this.y = (int) point.getY();
    }

}
