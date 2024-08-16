package com.example.bubblebobble.InteractableWorld;

import com.example.bubblebobble.GameObjects.GameObject;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.Boss;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.StaticGameObjects.*;
import com.example.bubblebobble.InteractableWorld.Factory.LevelFactory;
import com.example.bubblebobble.InteractableWorld.Factory.MapFactory;
import com.example.bubblebobble.InteractableWorld.ScoreBoard.ScoreBoard;
import com.example.bubblebobble.Main;
import com.example.bubblebobble.Handlers.*;

import com.example.bubblebobble.GameObjects.Weapons.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

/**
 * InteractableWorld handles all of the game's operations.
 * updating positions, checking for collisions, removing objects and
 * whether to restart the current game.
 */
public class InteractableWorld extends Canvas {

	/**
	 * Apply Singleton pattern to class {@code InteractableWorld}.
	 */
	static InteractableWorld worldInstance=new InteractableWorld(Main.WIDTH * Main.UNIT_SIZE, Main.HEIGHT * Main.UNIT_SIZE);
	public static InteractableWorld getWorldInstance(){return worldInstance;}

	Canvas canvas;

	private String map;
	MapFactory mapFactory;
	LevelFactory levelFactory;
	ScoreBoard scoreBoard;
	static LifeHandler lifeHandler;
	AlertHandler alertHandler;

	private ArrayList<CeilingUnit> ceilingUnits;
	private ArrayList<FloorUnit> floorUnits;
	private ArrayList<WallUnit> wallUnits;
	private ArrayList<Portal> portals;

	Hero hero;
	private Boss boss;
	private ArrayList<Enemy> enemies;

	private ArrayList<HeroProjectile> heroProjectiles;
	private ArrayList<EnemyProjectile> enemyProjectiles;
	private ArrayList<Bubble> bubbles;

	private ArrayList<GameObject> toBeRemoved;
	private ArrayList<Enemy> deadEnemies;

	protected boolean readyToReset;
	protected boolean frozen;

	private int numOfDeadEnemy;


	/**
	 * Initialize variables in the world.
	 * Initialize ArrayLists of different game objects, factories and handlers
	 * for the world. Initialize the canvas which the world will be drawn on to.
	 * @param width Parameter used to initialize {@code width}.
	 * @param height Parameter used to initialize {@code Height}.
	 */
	public InteractableWorld(int width, int height) {
		map="World4.txt";//by default
		mapFactory=new MapFactory(this);

		levelFactory=new LevelFactory(this);
		lifeHandler=new LifeHandler(3);
		scoreBoard=new ScoreBoard(this);
		alertHandler=new AlertHandler();

		ceilingUnits = new ArrayList<CeilingUnit>();
		floorUnits = new ArrayList<FloorUnit>();
		wallUnits = new ArrayList<WallUnit>();
		portals=new ArrayList<>();

		enemies = new ArrayList<Enemy>();
		heroProjectiles = new ArrayList<HeroProjectile>();
		enemyProjectiles = new ArrayList<EnemyProjectile>();
		bubbles = new ArrayList<Bubble>();

		toBeRemoved = new ArrayList<GameObject>();
		deadEnemies=new ArrayList<>();

		readyToReset = false;
		frozen=false;
		numOfDeadEnemy=0;

		this.setWidth(width);
		this.setHeight(height);
	}

	/**
	 * Set method of {@code canvas}.
	 * @param canvas Set {@code canvas}.
	 */
	public void setCanvas(Canvas canvas){
		this.canvas=canvas;
	}

	/**
	 * Get method of {@code canvas}.
	 * @return {@code this.canvas}.
	 */
	public Canvas getCanvas(){return canvas;}

	/**
	 * Get method of {@code frozen}.
	 * @return The value of {@code frozen}.
	 */
	public boolean getFrozen(){return frozen;}

	/**
	 * Set method of {@code frozen}.
	 * @param frozen Parameter used to set the value of {@code frozen}.
	 */
	public void setFrozen(boolean frozen){this.frozen=frozen;}

	/**
	 * Set method of {@code map}.
	 * @param map Parameter used to set the value of {@code map}.
	 */
	public void setMap(String map){this.map=map;}

	/**
	 * Get method of {@code map}.
	 * @return The value of {@code map}.
	 */
	public String getMap(){return map;}

	/**
	 * Get method of {@code alertHandler}.
	 * @return The value of {@code alertHandler}.
	 */
	public AlertHandler getAlertHandler(){return alertHandler;}

	/**
	 * Get method of {@code scoreBoard}.
	 * @return The value of {@code scoreBoard}.
	 */
	public ScoreBoard getScoreBoard(){return scoreBoard;}

	/**
	 * Get method of {@code mapFactory}.
	 * @return The value of {@code mapFactory}.
	 */
	public MapFactory getMapFactory(){return mapFactory;}

	/**
	 * Get method of {@code levelFactory}.
	 * @return The value of {@code levelFactory}.
	 */
	public LevelFactory getLevelFactory(){return levelFactory;}

	/**
	 * Get method of {@code lifeHandler}.
	 * @return The value of {@code lifeHandler}.
	 */
	public LifeHandler getLifeHandler(){return lifeHandler;}

	/**
	 * Get method of {@code boss}.
	 * @return The value of {@code boss}.
	 */
	public Boss getBoss(){return boss;}

	/**
	 * Get method of {@code enemies}.
	 * @return The value of {@code enemies}.
	 */
	public ArrayList<Enemy> getEnemies(){return enemies;}

	/**
	 * Get method of {@code portals}.
	 * @return The value of {@code portals}.
	 */
	public ArrayList<Portal> getPortals(){return portals;}

	/**
	 * Set method of {@code numOfDeadEnemy}.
	 * @param n Parameter used to set the value of {@code numOfDeadEnemy}.
	 */
	public void setNumOfDeadEnemy(int n){numOfDeadEnemy=n;}

	/**
	 * Get method of {@code numOfDeadEnemy}.
	 * @return The value of {@code numOfDeadEnemy}.
	 */
	public int getNumOfDeadEnemy(){return numOfDeadEnemy;}


	/**
	 * Paint node onto a canvas.
	 * Call the {@code drawOn()} method of every game object in the world.
	 * If the world is frozen, then stop painting the objects.
	 * @param gc The graphic context of the canvas on the game scene.
	 */
	public void paintObject(GraphicsContext gc) {

		if(frozen){return;}

		for (CeilingUnit ceilingUnit : ceilingUnits) {
			ceilingUnit.drawOn(gc);
		}
		for (FloorUnit floorUnit : floorUnits) {
			floorUnit.drawOn(gc);
		}
		for (WallUnit wallUnit : wallUnits) {
			wallUnit.drawOn(gc);
		}
		hero.drawOn(gc);

		for(Enemy enemy : enemies){
			enemy.drawOn(gc);
		}
		for (EnemyProjectile enemyProjectile : enemyProjectiles) {
			enemyProjectile.drawOn(gc);
		}
		for (HeroProjectile heroProjectile : heroProjectiles) {
			heroProjectile.drawOn(gc);
		}
		for (Bubble bubble : bubbles) {
			bubble.drawOn(gc);
		}
		for(Portal portal:portals){
			portal.drawOn(gc);
		}

		if(boss!=null) {
			boss.drawOn(gc);
		}
	}

	/**
	 * Paint the objects which extends {@link com.example.bubblebobble.GameObjects.StaticGameObjects.StaticGameObject}.
	 * If the world is frozen, then stops drawing.
	 * @param gc The graphic context of the canvas on the game scene.
	 */
	public void paintPureWorld(GraphicsContext gc){
		if(frozen){return;}

		mapFactory.drawBackground(gc);

		for (Bubble bubble : bubbles) {
			bubble.drawOn(gc);
		}
		for (CeilingUnit ceilingUnit : ceilingUnits) {
			ceilingUnit.drawOn(gc);
		}
		for (FloorUnit floorUnit : floorUnits) {
			floorUnit.drawOn(gc);
		}
		for (WallUnit wallUnit : wallUnits) {
			wallUnit.drawOn(gc);
		}
		for(Portal portal:portals){
			portal.drawOn(gc);
		}
	}


	/**
	 * Update the world.
	 * Update the level in {@code levelFactory} when all the enemies and boss
	 * are beaten in the current level. End the game if the current level is
	 * already the third level; Otherwise, call the {@code startGame()} method
	 * in {@code mapFactory}.
	 * If the world is ready to reset, then call the {@code startGame()} method
	 * in {@code mapFactory} according to the current level.
	 * Update the position of nonstatic game objects and weapons, update the
	 * colliding between game objects except for static game objects, and update the
	 * colliding between static game objects and nonstatic game objects
	 * as well as weapons.
	 * Remove the game objects which are set to be removed.
	 * If the world is frozen, this method will stop updating.
	 */
	public void update(){
		if(frozen){return;}

		if(enemies.size()==0&&boss==null){
			levelFactory.addLevel();

			alertHandler.callAlert();
			mapFactory.startGame(map);
			levelFactory.updateLevel();
		}
		if (readyToReset) {
			mapFactory.startGame(map);
			levelFactory.updateLevel();
			readyToReset = false;
		}

		updatePosition();
		collideWithStaticObject();
		collideBetweenGameObject();
		removeGameObjects();
	}

	/**
	 * Update the position of game objects which extends
	 * {@link com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject}
	 * and {@link com.example.bubblebobble.GameObjects.Weapons.Weapon} by calling
	 * each object's {@code update()} method.
	 * Add the object to {@code ToBeRemoved} if it is mark to be removed.
	 */
	public void updatePosition() {
		hero.update();

		for (Enemy enemy : enemies) {
			enemy.update();

			if(enemy.canRemove) {
				deadEnemies.add(enemy);
			}
		}

		for (EnemyProjectile enemyProjectile : enemyProjectiles) {
			enemyProjectile.update();

			if (enemyProjectile.canRemove) {
				toBeRemoved.add(enemyProjectile);
			}
		}

		if(boss!=null){
			boss.update();
		}

		for (HeroProjectile heroProjectile : heroProjectiles) {
			heroProjectile.update();

			if (heroProjectile.canRemove) {
				toBeRemoved.add(heroProjectile);
			}
		}

		for (Bubble bubble : bubbles) {
			bubble.update();

			if (bubble.canRemove) {
				toBeRemoved.add(bubble);
			}
		}
	}


	/**
	 * Handles colliding between static game objects and other game objects.
	 * Static game objects which extends {@link com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject},
	 * including {@code Portal}, {@code WallUnit}, {@code FloorUnit} and
	 * {@code CeilingUnit}.
	 */
	public void collideWithStaticObject(){
		if(boss!=null) {
			for (WallUnit wallUnit:wallUnits){
				boss.collideWith(wallUnit);
			}
			for (CeilingUnit ceilingUnit:ceilingUnits){
				boss.collideWith(ceilingUnit);
			}
			for (FloorUnit floorUnit:floorUnits){
				boss.collideWith(floorUnit);
			}
			for (Portal portal:portals){
				boss.reachPortal(portal);
			}
		}

		for (Enemy enemy : enemies) {
			for(FloorUnit floorUnit:floorUnits){
				enemy.collideWith(floorUnit);
			}
			for(WallUnit wallUnit :wallUnits){
				enemy.collideWith(wallUnit);
			}
			for (CeilingUnit ceilingUnit : ceilingUnits){
				enemy.collideWith(ceilingUnit);
			}
			for(Portal portal:portals){
				enemy.reachPortal(portal);
			}
		}

		//for(Hero hero : heroes){
		for(WallUnit wallUnit:wallUnits){
			hero.collideWith(wallUnit);
		}
		for(CeilingUnit ceilingUnit : ceilingUnits){
			hero.collideWith(ceilingUnit);
		}
		for(FloorUnit floorUnit:floorUnits){
			hero.collideWith(floorUnit);
		}
		for (Portal portal:portals){
			hero.reachPortal(portal);
		}


		for(HeroProjectile heroProjectile : heroProjectiles){
			for(WallUnit wallUnit:wallUnits){
				heroProjectile.collideWith(wallUnit);
			}
			for(CeilingUnit ceilingUnit : ceilingUnits){
				heroProjectile.collideWith(ceilingUnit);
			}
			for(FloorUnit floorUnit:floorUnits){
				heroProjectile.collideWith(floorUnit);
			}
		}

		for(EnemyProjectile enemyProjectile : enemyProjectiles){
			for(WallUnit wallUnit:wallUnits){
				enemyProjectile.collideWith(wallUnit);
			}
			for(CeilingUnit ceilingUnit : ceilingUnits){
				enemyProjectile.collideWith(ceilingUnit);
			}
			for(FloorUnit floorUnit:floorUnits){
				enemyProjectile.collideWith(floorUnit);
			}
		}
	}


	/**
	 * Handles colliding between different weapons, different nonstatic
	 * game objects and colliding between weapon and nonstatic game object.
	 */
	public void collideBetweenGameObject(){
		if(boss!=null) {
			for (Bubble bubble : bubbles) {
				boss.collideWith(bubble);
			}
			boss.collideWith(hero);
		}

		for (Enemy enemy : enemies) {
			enemy.collideWith(hero);

			for (Projectile heroProjectile:heroProjectiles){
				enemy.collideWith(heroProjectile);
			}
			for (Weapon bubble:bubbles){
				enemy.collideWith(bubble);
			}
		}


		for(Enemy enemy:enemies){
			hero.collideWith(enemy);
		}
		for (EnemyProjectile enemyProjectile:enemyProjectiles){
			hero.collideWith(enemyProjectile);
		}
		if(boss!=null) {
			hero.collideWith(boss);
		}


		for(HeroProjectile heroProjectile : heroProjectiles){
			for (EnemyProjectile enemyProjectile:enemyProjectiles ){
				heroProjectile.collideWith(enemyProjectile);
			}
			for(Enemy enemy:enemies){
				heroProjectile.collideWith(enemy);
			}
			if(boss!=null) {
				boss.collideWith(heroProjectile);
				heroProjectile.collideWith(boss);
			}
		}

		for(EnemyProjectile enemyProjectile : enemyProjectiles){
			for(HeroProjectile heroProjectile:heroProjectiles){
				enemyProjectile.collideWith(heroProjectile);
			}
			for (Bubble bubble:bubbles){
				enemyProjectile.collideWith(bubble);
			}
			enemyProjectile.collideWith(hero);
		}

	}


	/**
	 * Remove the game object in {@code ToBeRemoved} and {@code deadEnemies},
	 * and set {@code boss} to null if it can be removed.
	 */
	public void removeGameObjects(){
		for (GameObject obj : toBeRemoved) {
			remove(obj);
		}
		toBeRemoved.removeAll(toBeRemoved);
		for(Enemy deadEnemy:deadEnemies){
			numOfDeadEnemy++;
			remove(deadEnemy);
		}
		deadEnemies.removeAll(deadEnemies);

		if(boss!=null&&boss.canRemove){
			boss=null;
		}
	}

	/**
	 * Add one ceiling unit to {@code ceilingUnits}.
	 * @param ceilingUnit The ceiling unit to be added.
	 */
	public void addCeilingUnit(CeilingUnit ceilingUnit) {
		ceilingUnits.add(ceilingUnit);
	}

	/**
	 * Add one floor unit to {@code floorUnits}.
	 * @param floorUnit The floor unit to be added.
	 */
	public void addFloorUnit(FloorUnit floorUnit) {
		floorUnits.add(floorUnit);
	}

	/**
	 * Add one wall unit to {@code wallUnits}.
	 * @param wallUnit The wall unit to be added.
	 */
	public void addWallUnit(WallUnit wallUnit) {
		wallUnits.add(wallUnit);
	}

	/**
	 * Add one portal to {@code portals}.
	 * @param portal The portal to be added.
	 */
	public void addPortal(Portal portal){portals.add(portal);}


	/**
	 * Set the value of {@code hero}.
	 * @param hero The parameter used to initialize {@code this.hero}.
	 */
	public void addHero(Hero hero) {
		this.hero=hero;
	}

	/**
	 * Add one enemy to {@code enemies}.
	 * @param enemy The enemy to be added.
	 */
	public void addEnemy(Enemy enemy) {
		//adds a mook to the map
		enemies.add(enemy);
	}

	/**
	 * Add one hero projectile to {@code heroProjectiles}.
	 * @param heroProjectile The hero projectile to be added.
	 */
	public void addHeroProjectile(HeroProjectile heroProjectile) {
		heroProjectiles.add(heroProjectile);
	}

	/**
	 * Add one enemy projectile to {@code enemyProjectiles}.
	 * @param enemyProjectile The enemy projectile to be added.
	 */
	public void addEnemyProjectile(EnemyProjectile enemyProjectile){
		enemyProjectiles.add(enemyProjectile);
	}

	/**
	 * Add one bubble to {@code bubbles}.
	 * @param bubble The bubble to be added.
	 */
	public void addBubble(Bubble bubble) {
		bubbles.add(bubble);
	}

	/**
	 * Set the value of {@code boss}.
	 * @param boss The parameter used to set {@code this.boss}.
	 */
	public void addBoss(Boss boss){
		this.boss=boss;
	}


	/**
	 * Clear the contents of variables which store game objects
	 * in the world.
	 */
	public void clearContents() {
		ceilingUnits.removeAll(ceilingUnits);
		floorUnits.removeAll(floorUnits);
		wallUnits.removeAll(wallUnits);
		enemies.removeAll(enemies);
		enemyProjectiles.removeAll(enemyProjectiles);
		heroProjectiles.removeAll(heroProjectiles);
		portals.removeAll(portals);
		boss=null;
		hero=null;
	}


	/**
	 * Removes a specific object from ArrayLists storing game
	 * objects.
	 * @param obj The game object to be removed.
	 */
	public void remove(GameObject obj) {
		ceilingUnits.remove(obj);
		floorUnits.remove(obj);
		wallUnits.remove(obj);
		enemies.remove(obj);
		enemyProjectiles.remove(obj);
		heroProjectiles.remove(obj);
		bubbles.remove(obj);
	}


	/**
	 * Set the value of {@code readyToReset} to true.
	 */
	public void markToReset() {
		readyToReset = true;
	}

	/**
	 * Set the value of {@code readyToReset} to true.
	 */
	public void markNotToReset(){
		readyToReset=false;
	}


	/**
	 * Restart the current Map.
	 * Reset the level to 1, reset the left lives of hero to 3.
	 * Set {@code readyToReset} to true, and stop the world from
	 * being frozen.
	 */
	public void restartCurrentMap(){
		levelFactory.resetLevel();
		lifeHandler.resetLife(3);
		readyToReset=true;
		frozen=false;
	}

	/**
	 * Restart the current world.
	 * Initialize the world again.
	 */
	public void restart(){
		worldInstance=new InteractableWorld(Main.WIDTH * Main.UNIT_SIZE, Main.HEIGHT * Main.UNIT_SIZE);
	}
}
