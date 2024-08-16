package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy;

import com.example.bubblebobble.*;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates.*;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates.FruitDecorators.MedicineDecorator;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates.FruitDecorators.SunDecorator;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.EnemyProjectile;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import javafx.scene.canvas.GraphicsContext;


/**
 * An Enemy is a non-controllable {@code NonstaticGameObject} that kills the Hero whenever it or its projectile comes in contact.
 * Enemies are able to be bubbled and free themselves from these bubbles after a period of time.
 * Enemies change direction at random intervals, when hitting a wall, and when hitting the Hero's shield.
 * Enemies jump at random intervals as well.
 */
public class Enemy extends NonstaticGameObject {
	//Different states of enemies
	EnemyState activeState;
	EnemyState bubbledState;
	EnemyState fruitState;
    EnemyState currentState;

	public static final int WIDTH = Main.UNIT_SIZE + 10;
	public static final int HEIGHT = Main.UNIT_SIZE + 10;
	public static double TERMINAL_VELOCITY_X = 2.5;
	public static double X_ACCEL=1.22;
	public static final int BUBBLED_FRAMES = 300;
	public static final double CHANGE_MOVEMENT_CHANCE = 0.01;
	public static double CHANCE_OF_CHARGE=0.0015;

	int bubbleTimer;

	public Enemy(InteractableWorld world, int colNum, int rowNum) {

		super(world, colNum, rowNum, WIDTH, HEIGHT);
		isOnAPlatform = false;
		terminal_xVelocity = TERMINAL_VELOCITY_X;


		xAccel=X_ACCEL;
		direction = 1;
		if (Math.random() < 0.5) {
			reverseDirection();
		}

		bubbleTimer = BUBBLED_FRAMES;

		activeState=new ActiveState(this);
		bubbledState=new BubbledState(this);
		currentState=activeState;
	}


	/**
	 * Set method of {@code bubbledTimer}.
	 * @param bubbleTimer The value used to set the value of {@code bubbledTimer}.
	 */
	public void setBubbleTimer(int bubbleTimer){this.bubbleTimer=bubbleTimer;}

	/**
	 * Get Method of {@code bubbleTimer}.
	 * @return The value of {@code bubbleTimer}
	 */
	public int getBubbleTimer(){return bubbleTimer;}


	/**
	 * Get method of the enemy's current state.
	 * @return The current state of enemy.
	 */
	public EnemyState getState(){return currentState;}

	/**
	 * Get method of the enemy's active state.
	 * @return The active state of enemy.
	 */
	public EnemyState getActiveState(){return activeState;}

	/**
	 * Get method of the enemy's bubbled state.
	 * @return The bubbled state of enemy.
	 */
	public EnemyState getBubbledState(){return bubbledState;}

	/**
	 * Get method of the enemy's fruit state.
	 * @return The fruit state of enemy.
	 */
	public EnemyState getFruitState(){return fruitState;}

	/**
	 * Set the current state of the enemy.
	 * @param state Set the value of {@code currentState}.
	 */
	public void setState(EnemyState state){currentState=state;}


	/**
	 * Initialize the value of {@code fruitState}.
	 * The fruit will have certain possibilities to be decorated and
	 * become special fruit, which have special functions.
	 * @param e The enemy used to initialize {@code fruitState}.
	 */
	public void setFruitState(Enemy e){
		fruitState=new FruitState(e);
		double random=Math.random();
		if(random<0.25){
			fruitState=new MedicineDecorator(e);
		}
		else if(random<0.48){
			fruitState=new SunDecorator(e);
		}
	}



	/**
	 * Get method of {@code canRemove}.
	 * @return The value of {@code canRemove}.
	 */
	public boolean getCanRemove(){return canRemove;}


	/**
	 * Draw the enemy to the canvas on the game scene.
	 * Call the draw methods in the enemy's {@code currentState}.
	 * @param gc The graphic context of the canvas on the game scene.
	 */
	@Override
	public void drawOn(GraphicsContext gc) {
		currentState.drawOn(gc);
	}

	/**
	 * The enemy shoots a projectile.
	 * This adds one {@link com.example.bubblebobble.GameObjects.Weapons.EnemyProjectile}
	 * to the world.
	 */
	public void shootProjectile(){
		world.addEnemyProjectile(new EnemyProjectile(world,x-5,y+5, direction));
	}

	/**
	 * Update the position of the enemy.
	 * This inherits the {@code update()} method in its superclass
	 * {@link com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject},
	 * and calls the {@code update()} in {@code currentState}.
	 */
	@Override
	public void update() {
		//updates enemy, handling movement
		super.update();
		currentState.update();
	}



	/**
	 * Handles colliding with hero's weapons.
	 * Call the collide method in {@code currentState}.
	 * @param heroWeapon The weapon which the enemy has interaction to.
	 */
	public void collideWith(Weapon heroWeapon){
		currentState.collideWithWeapon(heroWeapon);
	}


	/**
	 * Handles colliding with hero.
	 * Call the collide method in {@code currentState}.
	 * @param hero The hero which the enemy has interaction to.
	 */
	public void collideWith(Hero hero) {
		currentState.collideWithHero(hero);
	}

	/**
	 * Handles colliding with a ceiling unit.
	 * Call the collide method in {@code currentState}.
	 * @param unit The ceiling which the enemy has interaction to.
	 */
	public void collideWith(CeilingUnit unit) {
		currentState.collideWithCeiling(unit);
	}


	/**
	 * Handles colliding with a floor unit.
	 * Call the collide method in {@code currentState}.
	 * @param floorUnit The floor which the enemy has interaction to.
	 */
	public void collideWith(FloorUnit floorUnit) {
		currentState.collideWithFloor(floorUnit);
	}

	/**
	 * Handles colliding with a wall unit.
	 * Call the collide method in {@code currentState}.
	 * @param wallUnit The wall which the enemy has interaction to.
	 */
	public void collideWith(WallUnit wallUnit) {
		currentState.collideWithWall(wallUnit);
	}
}
