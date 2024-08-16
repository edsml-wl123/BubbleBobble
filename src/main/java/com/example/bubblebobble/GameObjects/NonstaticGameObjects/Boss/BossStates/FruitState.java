package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.BossStates;

import com.example.bubblebobble.*;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.Boss;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.SoundEffect.SoundEffect;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Objects;

/**
 * FruitState handles the update of a boss when it has become a fruit.
 * <p>
 * The update of a boss in this state includes its change of location,
 * its colliding with hero and how to draw it on the canvas on the scene.
 * </p>
 */
public class FruitState implements BossState{

    private static final double TERMINAL_VELOCITY_Y = 10;
    private static final double GRAVITY=9.8;
    Boss boss;
    Image win;

    /** Constructor of class FruitState.
     * Initialize the value of boss, win. Set the value of {@code xVelocity},
     * {@code yAccel} and {@code yVelocity} to 0, and set the value of
     * {@code yAccel} to {@code GRAVITY}, since the boss in fruit state cannot
     * move by itself.
     * @param boss Initialize the value of the boss variable in this class.
     */
    public FruitState(Boss boss){
        this.boss=boss;
        win=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/win.png")));

        boss.terminal_yVelocity = TERMINAL_VELOCITY_Y;
        boss.xVelocity=0;
        boss.xAccel=0;
        boss.yVelocity=0;
        boss.yAccel=GRAVITY;
    }

    /**
     * Handles colliding with hero's weapon.
     * Nothing happen since the boss has already becomes fruit.
     * @param heroWeapon The weapon which the boss has interaction to.
     */
    @Override
    public void collideWithWeapon(Weapon heroWeapon) {

    }

    /**
     * Handles colliding with hero.
     * If the boss overlaps hero and it cannot be removed, then play
     * the corresponding audio clip in class SoundEffect. Then add 5
     * scores to {@code boss.world.getScoreBoard()}, and mark {@code canRemove}
     * of boss to true.
     * @param hero The hero which the boss has interaction to.
     */
    @Override
    public void collideWithHero(Hero hero) {
        if(boss.overlaps(hero)&& !boss.getCanRemove()) {
            SoundEffect.WIN.setToMoreLoud();
            SoundEffect.WIN.play();
            boss.world.getScoreBoard().addScore(5);
            boss.markToRemove();
        }
    }


    /**
     * Update the position of the boss.
     * Nothing happen since the boss cannot move by itself in fruit state.
     */
    @Override
    public void update() {

    }

    /**
     * Draw boss on the canvas on the game scene when it has become fruit.
     * @param gc The graphic context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        gc.drawImage(win,boss.getX(),boss.getY()+ boss.getHeight()-40,26,40);
    }
}
