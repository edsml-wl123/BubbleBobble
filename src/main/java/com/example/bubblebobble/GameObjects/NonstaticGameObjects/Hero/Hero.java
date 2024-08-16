package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.Boss;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.HeroStates.*;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;
import com.example.bubblebobble.SoundEffect.SoundEffect;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.HeroProjectile;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Objects;


/**
 * A Hero is a {code NonstaticGameObject} that is controllable by the player through
 * keyboard.
 * <p>
 * Hero can shoot {@code HeroProjectiles}, charge {@code Bubble}, get shield from
 * {@code EnemyProjectile} and {@code Enemy}, and collect fruits(dead enemies) to
 * get scores.
 * </p>
 */
public class Hero extends NonstaticGameObject {
    static Hero hero;
    Image wing;

    /**
     * Apply Singleton Pattern to class {@code Hero}.
     * Initialize the static variable {@code hero}.
     * @param colNum Variable used to initialize the {@code colNum} of hero.
     * @param rowNum Variable used to initialize the {@code rowNum} of hero.
     */
    public static void heroInit(int colNum,int rowNum){
        hero=new Hero(InteractableWorld.getWorldInstance(),colNum,rowNum);

    }

    /**
     * Get method of {@code hero}.
     * @return {@code hero}.
     */
    public static Hero getInstance(){
        return hero;
    }

    HeroState currentState;
    HeroState activeState;
    HeroState shieldState;
    HeroState stunnedState;
    HeroState chargeState;

    //public static final int JUMP_SPEED = 20;
    public static final int TERMINAL_VELOCITY_X = 4;
    public static int SIZE = 20;
    public static final int WALK = 2;
    public static final int RUN = 5;

    public static final double WALK_ACCEL=8;
    public static final double RUN_ACCEL = 15;
    public static final int SHIELD_TIME = 100;
    public static int ABLE_TO_CHARGE_TIME=700;
    public static int UNBEATABLE_TIME=240;

    private final DoubleProperty ableToChargeProgressBar= new SimpleDoubleProperty();
    public int ableToChargeTimer;
    public boolean ableToCharge;
    public boolean unbeatable;
    public int shieldTimer;
    public int unbeatableTimer;

    public int shootDelay;
    //private double jumpSpeed;

    private Hero(InteractableWorld world, int colNum, int rowNum) {

        super(world, colNum, rowNum, SIZE, SIZE);

        activeState=new ActiveState(this);
        shieldState=new ShieldState(this);
        stunnedState=new StunnedState(this);
        chargeState=new ChargeState(this);
        currentState=activeState;

        isOnAPlatform = false;
        terminal_xVelocity = TERMINAL_VELOCITY_X;
        jumpSpeed = 20;

        shieldTimer = SHIELD_TIME;
        ableToChargeTimer=0;
        unbeatableTimer=0;
        ableToCharge=false;
        unbeatable=false;
        shootDelay = 0;

        wing=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/wing.png")));
    }


    public int getLeftChargeTime(){return (ABLE_TO_CHARGE_TIME-ableToChargeTimer)/40;}


    /**
     * Get method of {@code currentState}.
     * @return The value of {@code currentState}.
     */
    public HeroState getState(){ return currentState;}

    /**
     * Get method of {@code activeState}.
     * @return The value of {@code activeState}.
     */
    public HeroState getActiveState(){return activeState;}

    /**
     * Get method of {@code shieldState}.
     * @return The value of {@code shieldState}.
     */
    public HeroState getShieldState(){return shieldState;}

    /**
     * Get method of {@code stunnedState}.
     * @return The value of {@code stunnedState}.
     */
    public HeroState getStunnedState(){return stunnedState;}

    /**
     * Get method of {@code chargeState}.
     * @return The value of {@code chargeState}.
     */
    public HeroState getChargeState(){return chargeState;}


    /**
     * Set method of {@code currentState}.
     * @param heroState The parameter used to set the value of {@code currentState}.
     */
    public void setState(HeroState heroState){currentState=heroState;}


    /**
     * Draw the hero on the canvas on the game scene.
     * If the hero is unbeatable, then draw special images before draw the image
     * of hero of {@code currentState}.
     * @param gc
     */
    public void drawOn(GraphicsContext gc) {
        if(unbeatable){
            gc.drawImage(wing,x-12,y-5,width+22,height);
        }
        currentState.drawOn(gc);
    }

    /**
     * The enemy shoots a projectile.
     * Corresponding sound effect will be played, and one
     * one {@link com.example.bubblebobble.GameObjects.Weapons.HeroProjectile}
     * will be added to the world.
     */
    public void shootProjectile() {
        SoundEffect.SHOOT.play();
        world.addHeroProjectile(new HeroProjectile(world, x, y, direction));
    }


    /**
     * Handles colliding with the wall unit.
     * Call the collide method in {@code currentState}.
     * @param wallUnit The wall that hero has interaction to.
     */
    public void collideWith(WallUnit wallUnit) {
        currentState.collideWithWall(wallUnit);
    }

    /**
     * Handles colliding with the floor unit.
     * Call the collide method in {@code currentState}.
     * @param floorUnit The wall that hero has interaction to.
     */
    public void collideWith(FloorUnit floorUnit) {
        currentState.collideWithFloor(floorUnit);
    }

    /**
     * Handles colliding with the ceiling unit.
     * Call the collide method in {@code currentState}.
     * @param ceilingUnit The ceiling unit which the nonstatic game object
     */
    public void collideWith(CeilingUnit ceilingUnit){
        currentState.collideWithCeiling(ceilingUnit);
    }


    /**
     * Handles colliding with the boss.
     * If the hero overlaps an active boss and the hero is not unbeatable,
     * corresponding alert in {@code alertHandler} will be called and the hero
     * will die.
     * @param boss The boss that the hero has interaction to.
     */
    public void collideWith(Boss boss){
        if(this.overlaps(boss)){
            if(boss.getState()== boss.getActiveState() && !this.unbeatable) {
                world.getAlertHandler().callAlert("BOSS");
                die();
            }
        }
    }

    /**
     * Handles colliding with enemy's weapon.
     * Call the collide method in {@code currentState}.
     * @param enemyWeapon The weapon that the hero has interaction to.
     */
    public void collideWith(Weapon enemyWeapon) {
        currentState.collideWithWeapon(enemyWeapon);
    }

    /**
     * Handles colliding with enemy.
     * Call the collide method in {@code currentState}.
     * @param enemy The enemy that hero has interaction to.
     */
    public void collideWith(Enemy enemy){
        currentState.collideWithEnemy(enemy);
    }


    /**
     * If the hero dies, corresponding sound effect will be played.
     * call {@code loseLife()} in {@code world.getLifeHandler()}.
     * If {@code world.getLifeHandler().getLife()} is greater than -1,
     * than the current game will restart.
     */
    public void die(){
        SoundEffect.DEATH.setToLoud();
        SoundEffect.DEATH.play();

        world.getLifeHandler().loseLife();
        if(world.getLifeHandler().getLife()>-1){
            world.getScoreBoard().resetCurrentLevel();
            world.markToReset();
        }
    }


    /**
     * Update the speed and position of hero.
     * Call the {@code update()} in its superclass
     * {@link com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject},
     * update {@code unbeatableTimer} if the hero is unbeatable. Then call the
     * {@code update()} method in {@code currentState}.
     */
    @Override
    public void update() {
        super.update();
        if(unbeatable){
            if(unbeatableTimer<UNBEATABLE_TIME){
                unbeatableTimer++;
            }
            else {
                unbeatable=false;
                unbeatableTimer=0;
            }
        }
        currentState.update();
    }
}