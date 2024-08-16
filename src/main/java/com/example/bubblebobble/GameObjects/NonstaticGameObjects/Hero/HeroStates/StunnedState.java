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
 * StunnedState handles the update of a hero when it's stunned.
 * <p>
 * The update of a hero in this state includes its collding with enemy and
 * enemy's weapon, its change of state, and how to draw it on the canvas on
 * the scene.
 * </p>
 * <p>The hero will not be able to do anything when it's stunned.</p>
 **/
public class StunnedState implements HeroState{
    Hero hero;
    private int stunTimer;
    public StunnedState(Hero h){
        this.hero=h;
        stunTimer=250;
    }

    /**
     * Handles colliding with enemy's weapon when the hero is stunned.
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
     * Handles colliding with enemy when the hero is stunned.
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
     * Update the state of hero.
     * if hero's {@code stunTimer} is less than 0, then the hero's
     * {@code currentState} will switch to {@code activeState}. And the
     * hero's {@code shieldTimer} will be reset to {@code SHIELD_TIME}.
     */
    @Override
    public void update() {
        stunTimer -= 1;
        if (stunTimer <= 0) {
            stunTimer = 250;
            hero.shieldTimer = Hero.SHIELD_TIME;
            hero.setState(hero.getActiveState());
        }
    }

    /**
     * Draw method of hero in this state.
     * Call the draw method in the {@code mapFactory} in the hero's world.
     * @param gc
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        hero.world.getMapFactory().drawStunnedHero(hero,gc);

    }

    /**
     * Handles stunned hero's action when the keyboard input is 'Q'.
     * Nothing happen since the hero is stunned.
     */
    @Override
    public void pressQ() {
    }

    /**
     * Handles stunned hero's action when the keyboard input is 'W'.
     * Nothing happen since the hero is stunned.
     */
    @Override
    public void pressW() {
    }

    /**
     * Handles stunned hero's action when the keyboard input is 'E'.
     * Nothing happen since the hero is stunned.
     */
    @Override
    public void pressE() {

    }

    /**
     * Handles stunned hero's action when the keyboard input is 'Up'.
     * Nothing happen since the hero is stunned.
     */
    @Override
    public void pressUp() {

    }

    /**
     * Handles stunned hero's action when the keyboard input is 'Left'.
     * Nothing happen since the hero is stunned.
     */
    @Override
    public void pressLeft() {

    }

    /**
     * Handles stunned hero's action when the keyboard input is 'Right'.
     * Nothing happen since the hero is stunned.
     */
    @Override
    public void pressRight() {

    }

    /**
     * Handles stunned hero's action when the keyboard input is 'Space'.
     * Nothing happen since the hero is stunned.
     */
    @Override
    public void pressSpace() {

    }

    /**
     * Handles stunned hero's action when 'Q' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseQ() {

    }

    /**
     * Handles stunned hero's action when 'W' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseW() {

    }

    /**
     * Handles stunned hero's action when 'E' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseE() {

    }

    /**
     * Handles stunned hero's action when 'Left' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseLeft() {

    }

    /**
     * Handles stunned hero's action when 'Right' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseRight() {

    }

    /**
     * Handles stunned hero's action when 'Space' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseSpace() {

    }
}
