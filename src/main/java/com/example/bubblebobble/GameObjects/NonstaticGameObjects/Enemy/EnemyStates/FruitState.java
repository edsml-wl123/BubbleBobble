package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import com.example.bubblebobble.SoundEffect.SoundEffect;
import javafx.scene.canvas.GraphicsContext;


/**
 * FruitState handles the update of an enemy when it has become a fruit.
 * <p>
 * The update of a enemy in this state includes its colliding with hero
 * and how to draw it on the canvas on the scene.
 * </p>
 **/
public class FruitState implements EnemyState{

    protected Enemy enemy;
    protected boolean readyToCollect;
    public static final int TERMINAL_VELOCITY_Y = 10;
    public static final int SIZE = 20;
    public static final int GRAVITY = 1;
    private int collideCount = 0;

    public FruitState(Enemy e){
        this.enemy=e;
        enemy.setWidth(SIZE);
        enemy.setHeight(SIZE);

        readyToCollect=false;
        enemy.terminal_yVelocity = TERMINAL_VELOCITY_Y;

        // when the enemy becomes fruit, it can not move anymore
        // except falling with gravity
        enemy.xVelocity=0;
        enemy.xAccel=0;
        enemy.yVelocity=0;
        enemy.yAccel=GRAVITY;
    }

    /**
     * Nothing happen since an enemy in its fruit state can not move
     * freely anymore.
     */
    @Override
    public void update(){}


    /**
     * Draw an enemy on the canvas on the game scene in this state.
     * @param gc The graphic context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        enemy.world.getMapFactory().fruitEnemyColor(enemy,gc);
    }

    /**
     * Handles colliding with hero's weapon.
     * Nothing happen since the enemy has already become fruit.
     * @param heroWeapon The weapon that the enemy has interaction to.
     */
    @Override
    public void collideWithWeapon(Weapon heroWeapon) {

    }

    /**
     * Handles colliding with hero in this state.
     * If the enemy overlaps hero, and it is ready to be collected,
     * the corresponding sound effect will be played. 1 score will be
     * added to {@code scoreBoard} in the enemy's world. Then {@code canRemove}
     * of enemy will be marked to true.
     * @param hero The hero that the enemy has interaction to.
     */
    @Override
    public void collideWithHero(Hero hero) {
        if (enemy.overlaps(hero) && readyToCollect && !enemy.getCanRemove()) {
            SoundEffect.FRUIT.setToLoud();
            SoundEffect.FRUIT.play();

            enemy.world.getScoreBoard().addScore(1);
            enemy.markToRemove();
        }
    }


    /**
     * Handles colliding with wall units in this state.
     * If the enemy collides with the wall's left side, it will be moved to
     * the wall's left side; otherwise, it will be moved to the wall's
     * right side.
     * @param wallUnit The wall that the enemy has interaction to.
     */
    @Override
    public void collideWithWall(WallUnit wallUnit) {
        if(enemy.overlaps(wallUnit)){
            double thisCenter = (enemy.getHitbox().getMaxX()+enemy.getHitbox().getMinX())/2;
            double center = (wallUnit.getHitbox().getMaxX()+wallUnit.getHitbox().getMinX())/2;

            if (center > thisCenter) {
                enemy.moveToLeft(wallUnit);
            }
            else if (center <= thisCenter){
                enemy.moveToRight(wallUnit);
            }
            else {
                enemy.moveToBelow(wallUnit);
            }
        }
    }

    /**
     * Handles colliding with floor units in this state.
     * It the enemy collides with a floor unit, it will be moved onto
     * the floor units if its most body has overlapped with the floor.
     * Otherwise, it will be moved below to the floor unit
     * @param floorUnit The floor unit which the enemy has interaction to.
     */
    @Override
    public void collideWithFloor(FloorUnit floorUnit) {
        double top = enemy.getY();
        double bottom = top - enemy.getHeight();

        if (enemy.overlaps(floorUnit) && enemy.yVelocity > 0) {
            if (bottom < floorUnit.getY() + floorUnit.getHeight()) {
                enemy.moveToAbove(floorUnit);
                enemy.yVelocity = 0;
                if (!enemy.canRemove) {
                    if(collideCount==1) {
                        readyToCollect = true;
                    }
                    else {
                        collideCount++;
                    }
                }
            }
            if (top > floorUnit.getY()){
                enemy.moveToBelow(floorUnit);
            }
        }
    }

    /**
     * Handles colliding with ceiling units in this state.
     * If the enemy collides with a ceiling, it will be moved below to
     * the ceiling.
     * @param ceilingUnit The ceiling that the enemy has interaction to.
     */
    @Override
    public void collideWithCeiling(CeilingUnit ceilingUnit) {
        if (enemy.overlaps(ceilingUnit)) {
            enemy.moveToBelow(ceilingUnit);
        }
    }

}
