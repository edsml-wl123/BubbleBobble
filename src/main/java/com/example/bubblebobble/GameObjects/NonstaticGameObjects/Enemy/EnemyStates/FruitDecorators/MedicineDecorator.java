package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates.FruitDecorators;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates.FruitState;
import com.example.bubblebobble.Main;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.SoundEffect.SoundEffect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


/**
 * MedicineDecorator decorates an enemy in its fruit state to have special functions.
 * <p>
 * When the enemy fruit is decorated and collected by the hero, it adds 2 score
 * to {@code scoreBoard} in the hero's world and enables the hero to have 1 more
 * life.
 * </p>
 */
public class MedicineDecorator extends FruitState {
    Image medicine;

    public MedicineDecorator(Enemy e) {
        super(e);
        medicine=new Image(Main.class.getResourceAsStream("/Pic/medicine.png"));
    }

    /**
     * Draw a fruit and decorated enemy on the canvas on the game scene.
     * @param gc The graphic context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc){
        gc.drawImage(medicine, enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
    }

    /**
     * Handles colliding with the hero when the fruit enemy is decorated.
     * If the enemy overlaps hero and it's ready to remove and cannot be
     * removed, play the corresponding sound effect, add one life to
     * {@code lifeHandler} to hero's world and add 2 scores to the {@code scoreBoard}
     * to the hero's world. Then mark {@code canRemove} of enemy to true.
     * @param hero The hero that the enemy has interaction to.
     */
    @Override
    public void collideWithHero(Hero hero) {
        if (enemy.overlaps(hero) && readyToCollect&&!enemy.canRemove) {
            SoundEffect.FRUIT2.setToMoreLoud();
            SoundEffect.FRUIT2.play();

            hero.world.getLifeHandler().addLife();
            hero.world.getScoreBoard().addScore(2);
            enemy.markToRemove();
        }
    }
}
