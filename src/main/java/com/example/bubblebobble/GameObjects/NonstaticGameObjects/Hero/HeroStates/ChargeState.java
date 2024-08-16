package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.HeroStates;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.SoundEffect.SoundEffect;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.Bubble;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;

/**
 * ChargeState handles the update of a hero when it's charging bubble.
 * <p>
 * The update of a hero in this state includes its colliding with enemy
 * and enemy's weapon, its charge process, its change of state according to
 * the keyboard input and how to draw it on the canvas on the scene.
 * </p>
 * <p>
 * The hero will not be able to move, get shielded or shoot projectiles when
 * it's in the process of charging.
 *</p>
 **/
public class ChargeState implements HeroState{
    Hero hero;
    private int chargeTimer;

    public ChargeState(Hero hero){
        this.hero=hero;
        chargeTimer=0;
    }


    /**
     * Handles colliding with enemy's weapon when the hero is charging.
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
     * Handles colliding with enemy when the hero is charging.
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
     * wall's left side; otherwise, it will be moved to the wall's right side.
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
     * Update {@code chargeTimer}.
     * If the {@code chargeTimer} has reached the value of {@code PREPARE_TIME},
     * the hero will then charge a bubble and the corresponding sound effect
     * will be played. Then {@code chargeTimer} and hero's {@code ableToCharge}
     * will be reset. Otherwise, increase {@code chargeTimer}.
     */
    @Override
    public void update() {
        int PREPARE_TIME = 60;
        if ((chargeTimer>= PREPARE_TIME)&&(hero.ableToCharge)) {
            hero.world.addBubble(new Bubble(hero.world, hero.getX(), hero.getY()));
            SoundEffect.EXPLODE.setToLoud();
            SoundEffect.EXPLODE.play();
            chargeTimer=0;

            hero.ableToCharge=false;
        }
        else {
            chargeTimer++;
        }
    }

    /**
     * Draw method of hero in this state.
     * Call the draw method in the {@code mapFactory} in the hero's world.
     * @param gc
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        hero.world.getMapFactory().drawChargeHero(hero,gc);
    }


    /**
     * Handles charging hero's action when the keyboard input is 'Q'.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void pressQ() {

    }

    /**
     * Handles chargig hero's action when the keyboard input is 'W'.
     * Nothing happen as the hero is already charging.
     */
    @Override
    public void pressW() {
    }

    /**
     * Handles charging hero's action when the keyboard input is 'E'.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void pressE() {

    }

    /**
     * Handles charging hero's action when the keyboard input is 'Up'.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void pressUp() {

    }

    /**
     * Handles charging hero's action when the keyboard input is 'Left'.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void pressLeft() {

    }

    /**
     * Handles charging hero's action when the keyboard input is 'Right'.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void pressRight() {

    }

    /**
     * Handles charging hero's action when the keyboard input is 'Space'.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void pressSpace() {

    }

    /**
     * Handles charging hero's action when 'Q' is released.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void releaseQ() {

    }

    /**
     * Handles charging hero's action when 'W' is released.
     * {@code chargeTimer} will be reset and the {@code currentState}
     * of hero will be switched to {@code activeState}.
     */
    @Override
    public void releaseW() {
        chargeTimer=0;
        hero.setState(hero.getActiveState());
    }

    /**
     * Handles charging hero's action when 'E' is released.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void releaseE() {

    }

    /**
     * Handles charging hero's action when 'Left' is released.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void releaseLeft() {

    }

    /**
     * Handles charging hero's action when 'Right' is released.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void releaseRight() {

    }

    /**
     * Handles charging hero's action when 'Space' is released.
     * Nothing happen as the hero is charging.
     */
    @Override
    public void releaseSpace() {

    }
}
