package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.HeroStates;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.SoundEffect.SoundEffect;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;


/**
 * ActiveState handles the update of a hero when it's active.
 * <p>
 * The update of a hero in this state includes its colliding with enemy
 * and enemy's weapon, its change of speed, position or state according to
 * the keyboard input and how to draw it on the canvas on the scene.
 * </p>
 **/
public class ActiveState implements HeroState{
    Hero hero;
    boolean[] spaceHolded = {false};

    public ActiveState(Hero h){
        this.hero=h;
    }


    /**
     * Handles colliding with enemy's weapon when the hero is active.
     * If the hero overlaps with the weapon, corresponding alert will
     * be called and the hero will die.
     * @param weapon The weapon that the hero has interaction to.
     */
    @Override
    public void collideWithWeapon(Weapon weapon) {
        if(hero.overlaps(weapon)&& weapon.getActive()&&!hero.unbeatable){
            world.getAlertHandler().callAlert("enemy's projectile");
            hero.die();
        }
    }

    /**
     * Handles colliding with enemy when the hero is active.
     * If the hero overlaps with the enemy, corresponding alert will
     * be called and the hero will die.
     * @param enemy The weapon that the hero has interaction to.
     */
    @Override
    public void collideWithEnemy(Enemy enemy) {
        if(hero.overlaps(enemy)&& enemy.getState()== enemy.getActiveState()&&!hero.unbeatable){
            world.getAlertHandler().callAlert("enemy");
            hero.die();
        }
    }


    /**
     * Handles colliding with wall units in this state.
     * If the hero collides with the wall's left side, it will be moved to the
     * wall's left side; otherwise, it will moved to the wall's right side.
     * Also, the {@code xVelocity} of the hero will become 0 to avoid further
     * colliding.
     * @param wallUnit The wall that the hero has interaction to.
     */
    @Override
    public void collideWithWall(WallUnit wallUnit) {
        if(hero.overlaps(wallUnit)){
            hero.xVelocity=0;
            double thisCenter = (hero.getHitbox().getMaxX()+hero.getHitbox().getMinX())/2;
            double center = (wallUnit.getHitbox().getMaxX()+wallUnit.getHitbox().getMinX())/2;

            if (center > thisCenter) {
                hero.moveToLeft(wallUnit);
            }
            else if (center <= thisCenter){
                hero.moveToRight(wallUnit);
            }
            else {
                hero.moveToBelow(wallUnit);
            }
        }
    }


    /**
     * Handles colliding with floor units in this state.
     * It the hero collides with a floor unit, it will be moved onto
     * the floor units if its most body has overlapped with the floor,
     * and the corresponding sound effect will be played.
     * Otherwise, it will be moved below to the floor unit, and its
     * {@code yVelocity} will become 0 immediately since it can not
     * move up anymore.
     * @param floorUnit The floor unit which the enemy has interaction to.
     */
    @Override
    public void collideWithFloor(FloorUnit floorUnit) {
        double top = hero.getY();
        double bottom = top - hero.getHeight();
        if (hero.overlaps(floorUnit) && hero.yVelocity > 0) {
            if (bottom < floorUnit.getHeight() + floorUnit.getY()) {
                hero.moveToAbove(floorUnit);
                hero.yVelocity = 0;
                if (!hero.isOnAPlatform) {
                    hero.isOnAPlatform = true;
                    SoundEffect.LAND.play();
                }
            }
            if (top > floorUnit.getY()){
                hero.moveToBelow(floorUnit);
            }
        }
    }


    /**
     * Handles colliding with ceiling units in this state.
     * If the hero collide with a ceiling, it will be moved below to the ceiling.
     * @param ceilingUnit The ceiling that the hero has interaction to.
     */
    @Override
    public void collideWithCeiling(CeilingUnit ceilingUnit) {
        if (hero.overlaps(ceilingUnit)) {
            hero.moveToBelow(ceilingUnit);
        }
    }

    /**
     * Update {@code shieldTimer} and {@code ableToChargeTimer} of the hero.
     */
    @Override
    public void update() {
        if(hero.shieldTimer< Hero.SHIELD_TIME) {
            hero.shieldTimer += 1;
        }
        if(hero.ableToChargeTimer< Hero.ABLE_TO_CHARGE_TIME){
            hero.ableToChargeTimer++;
        }
        else {
            hero.ableToCharge=true;
        }
    }


    /**
     * Draw method of hero in this state.
     * Call the draw method in the {@code mapFactory} in the hero's world.
     * @param gc
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        hero.world.getMapFactory().drawActiveHero(hero,gc);
    }


    /**
     * Handles active hero's action when the keyboard input is 'Q'.
     * The hero's {@code currentState} will switch to {@code shieldState}.
     */
    @Override
    public void pressQ() {
        hero.setState(hero.getShieldState());
    }

    /**
     * Handles active hero's action when the keyboard input is 'W'.
     * The hero's {@code currentState} will switch to {@code chargeState},
     * and its {@code ableToChargeTimer} will be reset to 0.
     */
    @Override
    public void pressW() {
        hero.ableToChargeTimer=0;
        hero.setState(hero.getChargeState());
    }

    /**
     * Handles active hero's action when the keyboard input is 'E'.
     * The hero will shoot projectile after a short delay.
     */
    @Override
    public void pressE() {
        hero.shootDelay -= 1;
        if (hero.shootDelay <= 0) {
            hero.shootProjectile();
            hero.shootDelay = 10;
        }
    }

    /**
     * Handles active hero's action when the keyboard input is 'Up'.
     * The hero will jump and the corresponding sound effect will
     * be played.
     */
    @Override
    public void pressUp() {
        hero.jump();
        SoundEffect.JUMP.play();
    }

    /**
     * Handles active hero's action when the keyboard input is 'Left'.
     * If 'Space' is holded, the hero will increase speed towards left
     * with {@code RUN_ACCEL}; otherwise, it will increase speed towards
     * left with {@code WALK_ACCEL}.
     */
    @Override
    public void pressLeft() {
        if (spaceHolded[0]) {
            hero.xAccel = -Hero.RUN_ACCEL;
        } else {
            hero.xAccel = -Hero.WALK_ACCEL;
        }
        hero.direction = -1;
    }

    /**
     * Handles active hero's action when the keyboard input is 'Right'.
     * If 'Space' is holded, the hero will increase speed towards right
     * with {@code RUN_ACCEL}; otherwise, it will increase speed towards
     * right with {@code WALK_ACCEL}.
     */
    @Override
    public void pressRight() {
        if (spaceHolded[0]) {
            hero.xAccel = Hero.RUN_ACCEL;
        } else {
            hero.xAccel = Hero.WALK_ACCEL;
        }
        hero.direction = 1;
    }

    /**
     * Handles active hero's action when the keyboard input is 'Space'.
     * Set {@code spaceHolded[0]} to true, and hero's {@code terminal_xVelocity}
     * to {@code RUN}.
     */
    @Override
    public void pressSpace() {
        spaceHolded[0] = true;
        hero.terminal_xVelocity = Hero.RUN;
    }


    /**
     * Handles active hero's action when 'Q' is released.
     * Nothing happen.
     */
    @Override
    public void releaseQ() {
    }

    /**
     * Handles active hero's action when 'w' is released.
     * Nothing happen.
     */
    @Override
    public void releaseW() {
    }

    /**
     * Handles active hero's action when 'E' is released.
     * {@code shootDelay} of hero becomes 0.
     */
    @Override
    public void releaseE() {
        hero.shootDelay = 0;
    }

    /**
     * Handles active hero's action when 'Left' is released.
     * {@code xAccel} of hero becomes 0 so it will stop by itself.
     */
    @Override
    public void releaseLeft() {
        hero.xAccel = 0;
    }

    /**
     * Handles active hero's action when 'Right' is released.
     * {@code xAccel} of hero becomes 0 so it will stop by itself.
     */
    @Override
    public void releaseRight() {
        hero.xAccel = 0;
    }

    /**
     * Handles active hero's action when 'Space' is released.
     * {@code spaceHolded[0]} of hero becomes 0 and its {@code terminal_xVelocity}
     * becomes {@code WALK}.
     */
    @Override
    public void releaseSpace() {
        spaceHolded[0] = false;
        hero.terminal_xVelocity = hero.WALK;
    }
}
