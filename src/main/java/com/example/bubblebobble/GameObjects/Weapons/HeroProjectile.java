package com.example.bubblebobble.GameObjects.Weapons;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.Boss;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The HeroProjectile class handles the specificities with the projectile
 * being shot from the hero.
 * The hero's projectile looks different from projectile of an enemy or the boss.
 */
public class HeroProjectile extends Projectile {
	private static final int SIZE = 20;
	private static final int SPEED = 12;

	public HeroProjectile(InteractableWorld world, int x, int y, int direction) {
		super(x, y, SIZE, SIZE, world);

		this.direction = direction;
		TERMINAL_VELOCITY_Y=7;

		xVelocity = SPEED;
		yVelocity = 0;
		xAccel = 0.25;
		yAccel = 0.1;

		activeFrames = 35;
		timer = activeFrames;
	}


	/**
	 * Draw the hero's projectile on the canvas on the game scene
	 * by calling the draw method in {@code mapFactory} in the world.
	 * @param gc The graphic context of the canvas on the game scene.
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		world.getMapFactory().drawHeroProjectile(this,gc);
		gc.setFill(Color.BLACK);
	}


	/**
	 * Handles colliding with enemy.
	 * If the hero projectile is active and the enemy is in its {@code activeState},
	 * set it to be inactive.
	 * @param enemy The enemy that the hero projectile has interaction to.
	 */
	public void collideWith(Enemy enemy) {
		if (this.overlaps(enemy) && enemy.getState()==enemy.getActiveState()) {
			this.setActive(false);
		}
	}

	/**
	 * Handles colliding with the boss.
	 * If the hero projectile is active and the boss is in its {@code activeState},
	 * set it to be inactive.
	 * @param boss The boss that the hero projectile has interaction to.
	 */
	public void collideWith(Boss boss){
		if(this.overlaps(boss)&&boss.getState()==boss.getActiveState()){
			this.setActive(false);
		}
	}

}
