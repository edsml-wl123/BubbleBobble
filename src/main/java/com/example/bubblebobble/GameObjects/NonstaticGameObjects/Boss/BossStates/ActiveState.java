package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.BossStates;

import com.example.bubblebobble.*;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.Boss;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;


/**
 * ActiveState handles the update of a boss when it's active.
 * <p>
 * The update of a boss in this state includes its change of
 * location, speed, its colliding with hero and hero's weapon
 * (hero projectiles and bubble) and how to draw it on the canvas
 * on the scene.
 * </p>
 */
public class ActiveState implements BossState{

    //images which picture different life conditions of the boss
    Image fullBlood,halfBlood1,halfBlood2;
    Boss boss;
    Weapon collidedWeapon;

    private static final double CHANGE_MOVEMENT_CHANCE=0.01;
    private static final double CHANCE_OF_CHARGE=0.004;

    public ActiveState(Boss boss){
        this.boss=boss;
        fullBlood=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/fullBlood.png")));
        halfBlood1=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/halfBlood1.png")));
        halfBlood2=new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/Pic/halfBlood2.png")));
    }

    /**
     * Handles colliding with hero's weapons when the boss is active.
     * {@code collideWeapon} is used to check whether the hero weapon
     * has already collided to the boss. This avoids continuous colliding
     * between the same weapon and the boss.
     * If the boss overlaps an active hero's weapon, it will lose one life.
     * If it already has no lives to lose, {@code fruitState} in class
     * {@link Boss}
     * will be initialized, and the boss's state will switch to fruit.
     * @param heroWeapon The weapon which the boss has interaction to.
     * @author Wenxin Li
     */
    @Override
    public void collideWithWeapon(Weapon heroWeapon) {
        if(heroWeapon==collidedWeapon){
            return;
        }
        if(boss.overlaps(heroWeapon)&& heroWeapon.getActive()) {
            collidedWeapon=heroWeapon;
            if (boss.getLifeHandler().getLife() > -1) {
                if (boss.overlaps(heroWeapon) && heroWeapon.getActive()) {
                    boss.getLifeHandler().loseLife();
                }
                if (boss.getLifeHandler().getLife() == -1) {
                    boss.setFruitState();
                    boss.setState(boss.getFruitState());
                }
            }
        }
    }

    /**Handles the colliding with hero.
     * Nothing will happen to boss.
     * @param hero The hero which the boss has interaction to.
     */
    @Override
    public void collideWithHero(Hero hero) {
    }

    /**
     * Update positions of the boss.
     * The boss will jump, reverse its direction or shoot projectiles
     * randomly.
     */
    @Override
    public void update() {
        if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
            boss.jump();
        }
        if (Math.random() < CHANGE_MOVEMENT_CHANCE) {
            boss.reverseDirection();
        }
        if(Math.random()< CHANCE_OF_CHARGE){
            boss.shootProjectile();
        }
    }

    /**
     * Draw the boss on the canvas on the game scene.
     * It will first draw the life condition of the boss according to
     * the value of {@code boss.getLifeHandler.getLife()}. Then draw
     * different images of the boss according to different maps by calling
     * the draw methods in {@code boss.world.getMapFactory}.
     * @param gc The graphics context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc) {
        if(boss.getLifeHandler().getLife()>-1) {
            if (boss.getLifeHandler().getLife() == 2) {
                gc.drawImage(fullBlood, boss.getX() - 1, boss.getY() - 10, boss.getWidth() + 2, 10);
            } else if (boss.getLifeHandler().getLife() == 1) {
                gc.drawImage(halfBlood1, boss.getX() - 1, boss.getY() - 10, boss.getWidth() + 2, 10);
            } else  {
                gc.drawImage(halfBlood2, boss.getX() - 1, boss.getY() - 10, boss.getWidth() + 2, 10);
            }
            boss.world.getMapFactory().drawBoss(boss, gc);
        }
    }
}
