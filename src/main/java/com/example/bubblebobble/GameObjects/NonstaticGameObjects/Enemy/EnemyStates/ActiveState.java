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
 * ActiveState handles the update of an enemy when it's active.
 * <p>
 * The update of a enemy in this state includes its colliding with hero
 * and hero's weapon, its change of speed and position and how to draw
 * it on the canvas on the scene.
 * </p>
 **/
public class ActiveState implements EnemyState{
    Enemy enemy;
    private int turningAwayCount;
    private boolean turningAwayFromShield;

    public ActiveState(Enemy enemy){
        this.enemy=enemy;
        turningAwayCount=10;
        turningAwayFromShield = false;

    }


    /**
     * Update positions of the enemy.
     * The enemy will jump, reverse its direction or shoot projectiles
     * randomly when it's active.
     */
    public void update(){
        if (Math.random() < Enemy.CHANGE_MOVEMENT_CHANCE) {
            enemy.jump();
        }
        if (Math.random() < Enemy.CHANGE_MOVEMENT_CHANCE) {
            enemy.reverseDirection();
        }
        if(Math.random()<Enemy.CHANCE_OF_CHARGE){
            enemy.shootProjectile();
        }
    }


    /**
     * Draw an active enemy on the canvas on the game scene.
     * @param gc The graphic context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        enemy.world.getMapFactory().drawActiveEnemy(enemy,gc);
    }

    /**
     * Handles colliding with a hero's weapon when the enemy is active.
     * If the enemy overlaps an active weapon, it will be bubbled as it
     * cannot move freely. Then {@code currentState} of the enemy will
     * switch to the enemy's {@code bubbledState}.
     * @param heroWeapon The hero's weapon that the enemy has interaction to.
     */
    @Override
    public void collideWithWeapon(Weapon heroWeapon) {
        if(heroWeapon.overlaps(enemy)&& heroWeapon.getActive()) {
            //System.out.println("collide with bubble");
            SoundEffect.BUBBLED.setToLoud();
            SoundEffect.BUBBLED.play();
            //isBubbled = true;
            enemy.yVelocity = 0;
            enemy.xAccel = 0;
            enemy.yAccel = -0.1;
            enemy.setState(enemy.getBubbledState());
        }
    }


    /**
     * Handles colliding with hero in this state.
     * If the enemy overlaps hero and the hero has shielded, then it will
     * turn away. The enemy can at most turn away for 10 times when the
     * hero is shielded.
     * @param hero The hero that the enemy has interaction to.
     */
    @Override
    public void collideWithHero(Hero hero) {
        if(enemy.overlaps(hero)) {
            if (hero.getState()== hero.getShieldState() && !turningAwayFromShield) {
                turningAwayFromShield = true;
                enemy.reverseDirection();//make this method public
            }
            if (turningAwayFromShield) {
                if (turningAwayCount <= 0) {
                    turningAwayCount = 10;
                    turningAwayFromShield = false;
                }
                turningAwayCount -= 1;
            }
        }
    }


    /**
     * Handles colliding with wall units in this state.
     * If the enemy collides with the wall's left side, it will be moved
     * to the wall's left side; otherwise, it will be moved to the wall's
     * right side. Also, the {@code xVelocity} of enemy will become 0 and
     * it will reverse direction to avoid further colliding.
     * @param wallUnit The wall that the enemy has interaction to.
     */
    @Override
    public void collideWithWall(WallUnit wallUnit) {
        if (enemy.overlaps(wallUnit)) {
            double thisCenter = (enemy.getHitbox().getMaxX()+enemy.getHitbox().getMinX())/2;
            double center = (wallUnit.getHitbox().getMaxX()+wallUnit.getHitbox().getMinX())/2;

            if (center > thisCenter) {
                enemy.moveToLeft(wallUnit);
                enemy.xVelocity=0;
                enemy.reverseDirection();
            }
            else if (center <= thisCenter){
                enemy.moveToRight(wallUnit);
                enemy.xVelocity=0;
                enemy.reverseDirection();
            }
            else {
                enemy.moveToBelow(wallUnit);
                enemy.reverseDirection();
            }
        }
    }

    /**
     * Handles colliding with floor units in this state.
     * It the enemy collides with a floor unit, it will be moved onto
     * the floor units if its most body has overlapped with the floor.
     * Otherwise, it will be moved below to the floor unit, and its
     * {@code yVelocity} will become 0 immediately since it can not
     * move up anymore.
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
    }

    /**
     * Handles colliding with ceiling units in this state.
     * If the enemy collide with a ceiling, it will be moved below to the ceiling
     * and its {@code yVelocity} will become 0 immediately since it can not
     * move up anymore.
     * @param ceilingUnit The ceiling that the enemy has interaction to.
     */
    @Override
    public void collideWithCeiling(CeilingUnit ceilingUnit) {
        if (enemy.overlaps(ceilingUnit)) {
            enemy.moveToBelow(ceilingUnit);
            //obj.collideWithCeiling();
            enemy.yVelocity = 0;
        }
    }
}
