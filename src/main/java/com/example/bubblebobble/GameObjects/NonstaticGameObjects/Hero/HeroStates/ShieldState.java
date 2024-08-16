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
 * ShieldState handles the update of a hero when it's shielded.
 * <p>
 * The update of a hero in this state includes its change of state according to
 * the keyboard input, and how to draw it on the canvas on the scene.
 * </p>
 * <p>
 * The hero will not be able to move when it's already shielded.
 * </p>
 **/
public class ShieldState implements HeroState{
    Hero hero;
    public ShieldState(Hero h){
        this.hero=h;
    }


    /**
     * Handles colliding with enemy's weapon in this state.
     * Nothing happen since the hero is shielded.
     * @param weapon The weapon that the hero has interaction to.
     */
    @Override
    public void collideWithWeapon(Weapon weapon) {

    }

    /**
     * Handles colliding with enemy in this state.
     * Nothing happen since the hero is shielded.
     * @param enemy The weapon that the hero has interaction to.
     */
    @Override
    public void collideWithEnemy(Enemy enemy) {

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
            //obj.collideWithCeiling();
        }
    }


    /**
     * Update the state of hero.
     * if hero's {@code shieldTimer} is less than 0, then the hero's
     * {@code currentState} will switch to {@code stunnedState}.
     */
    @Override
    public void update() {
        hero.shieldTimer -= 1;
        if (hero.shieldTimer <= 0) {
            hero.shieldTimer = 0;
            hero.setState(hero.getStunnedState());
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
        hero.world.getMapFactory().drawShieldHero(hero,gc);
    }


    /**
     * Handles shielded hero's action when the keyboard input is 'Q'.
     * Set the {@code xVelocity} and {@code xAccel} to 0 since the
     * hero cannot move when shielded.
     */
    @Override
    public void pressQ() {
        hero.xVelocity = 0;
        hero.xAccel = 0;
    }

    /**
     * Handles shielded hero's action when the keyboard input is 'W'.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void pressW() {
    }

    /**
     * Handles shielded hero's action when the keyboard input is 'E'.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void pressE() {
    }

    /**
     * Handles shielded hero's action when the keyboard input is 'Up'.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void pressUp() {
    }

    /**
     * Handles shielded hero's action when the keyboard input is 'Left'.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void pressLeft() {
    }

    /**
     * Handles shielded hero's action when the keyboard input is 'Right'.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void pressRight() {
    }

    /**
     * Handles shielded hero's action when the keyboard input is 'Space'.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void pressSpace() {
    }

    /**
     * Handles shielded hero's action when 'Q' is released.
     * Set {@code currentState} of hero to its {@code activeState}.
     */
    @Override
    public void releaseQ() {
        hero.setState(hero.getActiveState());
    }

    /**
     * Handles active hero's action when 'W' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseW() {
    }

    /**
     * Handles active hero's action when 'E' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseE() {

    }

    /**
     * Handles active hero's action when 'Left' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseLeft() {

    }

    /**
     * Handles active hero's action when 'Right' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseRight() {

    }

    /**
     * Handles active hero's action when 'Space' is released.
     * Nothing happen as the hero is shielded.
     */
    @Override
    public void releaseSpace() {

    }
}
