package com.example.bubblebobble.GameObjects.Weapons;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The EnemyProjectile class handles the specificities with the projectile
 * being shot from an enemy or the boss.
 * The enemy's projectile looks different from projectile of a hero.
 */
public class EnemyProjectile extends Projectile {
	private static final int SPEED = 8;
	private static final int SIZE = 20;

	public EnemyProjectile(InteractableWorld world, int x, int y, int direction) {
		super(x, y, SIZE, SIZE, world);
		this.direction = direction;
		TERMINAL_VELOCITY_Y=2.5;

		xVelocity = SPEED;
		yVelocity = 0;
		xAccel = 0.08;
		yAccel = 0.1;

		activeFrames = 48;
		timer = activeFrames;
	}


	/**
	 * Draw the enemy's projectile on the canvas on the game scene
	 * by calling the draw method in {@code mapFactory} in the world.
	 * @param gc The graphic context of the canvas on the game scene.
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		world.getMapFactory().drawEnemyProjectile(this,gc);
	}


	/**
	 * Handles colliding with hero.
	 * If the enemy projectile is active and the hero is not shielded, mark
	 * it to be removed.
	 * @param hero The hero that the enemy projectile has interaction to.
	 */
	public void collideWith(Hero hero) {
		if(this.overlaps(hero) && hero.getState()!= hero.getShieldState()&&this.getActive()) {
			markToRemove();
		}
	}
}
