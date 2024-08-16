package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.SoundEffect.SoundEffect;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;

/**
 * BubbledState handles the update of an enemy when it's bubbled.
 * <p>
 * The update of an enemy in this state includes its colliding with hero,
 * its change of speed and position and how to draw it on the canvas
 * on the scene.
 * </p>
 **/
public class BubbledState implements EnemyState{
    Enemy enemy;
    private static final int GRAVITY=1;

    public BubbledState(Enemy e){this.enemy=e;}

    /**
     * Update {@code bubbledTimer} of the enemy.
     * If the {@code bubbledTimer} has run out. The enemy will move freely
     * again and its {@code currentState} will switch to {@code activeState}.
     */
    public void update(){
        enemy.setBubbleTimer(enemy.getBubbleTimer()-1);
        if (enemy.getBubbleTimer() <= 0) {
            enemy.xAccel = 1.5;
            enemy.direction = 1;
            if (Math.random() < 0.5) {
                enemy.reverseDirection();
            }
            enemy.yAccel = GRAVITY;
            enemy.setState(enemy.getActiveState());
            enemy.setBubbleTimer(300);
        }
    }

    /**
     * Draw a bubbled enemy on the canvas on the game scene.
     * @param gc The graphic context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc) {
       enemy.world.getMapFactory().drawBubbledEnemy(enemy,gc);
    }

    /**
     * Handles colliding with a hero's weapon in this state.
     * Nothing will happen since the enemy is already bubbled.
     * @param heroWeapon The weapon that the enemy has interaction to.
     */
    @Override
    public void collideWithWeapon(Weapon heroWeapon) {
    }

    /**
     * Handles colliding with hero in this state.
     * If the enemy overlaps hero, corresponding sound effect in
     * {@link com.example.bubblebobble.SoundEffect.SoundEffect} will be
     * played and the enemy's {@code currentState} will switch to{@code fruitState}.
     * @param hero The hero that the enemy has interaction to.
     */
    @Override
    public void collideWithHero(Hero hero) {
        if(enemy.overlaps(hero)) {
            SoundEffect.POP.play();
            enemy.setFruitState(enemy);
            enemy.setState(enemy.getFruitState()); //is now fruit
        }
    }


    /**
     * Handles colliding with wall units in this state.
     * If the enemy collides with the wall's left side, it will be moved to
     * the wall's left side; otherwise, it will be moved to the wall's right
     * side. Also, the {@code xVelocity} and {@code yAccel} of the enemy
     * will become 0 to avoid further colliding and quick movement.
     * @param wallUnit The wall that the enemy has interaction to.
     */
    @Override
    public void collideWithWall(WallUnit wallUnit) {
        if (enemy.overlaps(wallUnit)) {
            enemy.yAccel = 0;
            enemy.xVelocity=0;
            double thisCenter = (enemy.getHitbox().getMaxX()+enemy.getHitbox().getMinX())/2;
            double center = (wallUnit.getHitbox().getMaxX()+wallUnit.getHitbox().getMinX())/2;

            if (center > thisCenter) {
                enemy.moveToLeft(wallUnit);
            }
            else if (center <= thisCenter){
                enemy.moveToRight(wallUnit);
            }
        }
    }

    /**
     * Handles colliding with floor units in this state.
     * When the enemy is bubbled, it will be stopped by the floor unit
     * above it or drop on the floor unit below it.
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
                if (!enemy.isOnAPlatform) {
                    enemy.isOnAPlatform = true;
                }
            }
            if (top > floorUnit.getY()){
                enemy.moveToBelow(floorUnit);
                enemy.yVelocity = 0;
            }
        }
        if(enemy.overlaps(floorUnit)){
            enemy.yVelocity = 0;
            enemy.yAccel = 0;
        }
    }

    /**
     * Handles colliding with ceiling units in this state.
     * If the enemy collide with a ceiling, it will be moved below to the ceiling
     * and its {@code yVelocity} and {@code yAccel }will become 0 immediately,
     * thus it will flow on the ceiling.
     * @param ceilingUnit The ceiling that the enemy has interaction to.
     */
    @Override
    public void collideWithCeiling(CeilingUnit ceilingUnit) {
        if (enemy.overlaps(ceilingUnit)) {
            enemy.moveToBelow(ceilingUnit);
            //obj.collideWithCeiling();
            enemy.yVelocity = 0;
            enemy.yAccel = 0;
        }
    }
}
