package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.BossStates.ActiveState;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.BossStates.BossState;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.BossStates.FruitState;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Handlers.LifeHandler;
import com.example.bubblebobble.GameObjects.Weapons.EnemyProjectile;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;


/**
 * Boss is a {@code NonstaticGameObject} which cannot be controlled.
 * <p>It's the king of the enemy, which will appear in level 3 in the game.</p>
 * <p>
 * </p>
 * The boss will have 3 lives. Each time it is hit by a hero's
 * weapon, it will lose one life. When it loses all of its lives,
 * it will die and become a fruit. The hero's shield will not work
 * to a boss.
 * </p>
 */
public class Boss extends NonstaticGameObject {

    public static final int SIZE=40;
    public static final double TERMINAL_VELOCITY_X = 2;
    // public static final double CHANGE_MOVEMENT_CHANCE=0.01;
    //public static final double CHANCE_OF_CHARGE=0.005;
    public static final double X_ACCEL=1.3;
    //private static final double TERMINAL_VELOCITY_Y = 10;
    private final LifeHandler lifeHandler;
    //private final int jumpSpeed;

    private BossState activeState;
    private BossState fruitState;
    private BossState currentState;


    /**
     * Constructor of class Boss.
     * Initialize the value of {@code lifeHandler}, which handles the life
     * condition of boss. Initialize the speed and direction of the boss, and
     * different states of the boss.
     * @param world Initialize the value of the world that the boss is in.
     * @param colNum Initialize the position of the boss.
     * @param rowNum Initialize the position of the boss.
     */
    public Boss(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum,SIZE,SIZE);
        lifeHandler=new LifeHandler(2);
        isOnAPlatform = false;
        jumpSpeed = JUMP_SPEED;
        terminal_xVelocity = TERMINAL_VELOCITY_X;
        xAccel = X_ACCEL;//decrease, so that the enemy moves slower
        direction = 1;

        activeState=new ActiveState(this);
        currentState=activeState;
    }

    /**
     * Set the current state of boss to active or fruit.
     * @param state The state which is used to set the value of boss's
     *              current state.
     */
    public void setState(BossState state){
        currentState=state;
    }

    /**
     * Get method of {@code currentState;}
     * @return The value of {@code currentState;}
     */
    public BossState getState(){
        return currentState;
    }

    /**
     * Initialize {@code fruitState}.
     * This Method will be called when the boss has lost all of its
     * lives, as when the constructors of {@link FruitState} is called,
     * the speed of the boss will be changed and it cannot move anymore.
     */
    public void setFruitState(){
        fruitState=new FruitState(this);
    }

    /**
     * Get method of {@code activeState;}
     * @return The value of {@code activeState;}
     */
    public BossState getActiveState(){return activeState;}

    /**
     * Get method of {@code fruitState;}
     * @return The value of {@code fruitState;}
     */
    public BossState getFruitState(){return fruitState;}


    /**
     * Set the width and height of the boss.
     * @param width The value used to set the width of boss.
     * @param height The value used to set the height of boss.
     */
    public void setSize(int width,int height){
        this.width=width;
        this.height=height;
    }

    /**
     * Get method of {@code canRemove}
     * @return The value of {@code canRemove;}
     */
    public boolean getCanRemove(){return canRemove;}

    /**
     * Update the position of boss.
     * Inherit the {@code update()} methods in its superclass
     * {@link com.example.bubblebobble.GameObjects.NonstaticGameObjects.NonstaticGameObject},
     * then call the {@code update()} methods
     * in {@code currentState}.
     */
    @Override
    public void update(){
        super.update();
        currentState.update();
    }


    /**
     * The boss shoots a projectile.
     * Add a new enemy's projectile to the world which the boss is in.
     */
    public void shootProjectile(){
        world.addEnemyProjectile(new EnemyProjectile(world,x-5,y+5, direction));
    }

    /**
     * Handles colliding with the hero.
     * Call the collide methods of {@code currentState}.
     * @param hero
     */
    public void collideWith(Hero hero){
        currentState.collideWithHero(hero);
    }

    /**
     * Draw the boss on the canvas on the game scene.
     * Call the draw methos of {@code currentStaet}.
     * @param gc
     */
    @Override
    public void drawOn(GraphicsContext gc) {
       currentState.drawOn(gc);
    }


    /**
     * Handles colliding with hero's weapon.
     * Call the collide methods with hero's weapon in {@code currentState}.
     * @param heroWeapon
     */
    public void collideWith(Weapon heroWeapon){
        currentState.collideWithWeapon(heroWeapon);
    }

    /**
     * Get method of {@code lifeHandler}.
     * @return The value of {@code lifeHandler}.
     */
    public LifeHandler getLifeHandler(){return lifeHandler;}

    /**
     * Handles colliding with floor units.
     * It the boss collides with a floor unit, it will be moved onto
     * the floor units if its most body has overlapped with the floor.
     * Otherwise, it will be moved below to the floor unit, and its
     * {@code yVelocity} will become 0 immediately since it can not
     * move up anymore.
     * @param floorUnit The floor unit which the boss has interaction to.
     */
    @Override
    public void collideWith(FloorUnit floorUnit) {
        double top = this.getY();
        double bottom = top - this.getHeight();
        if (this.overlaps(floorUnit) && this.yVelocity > 0) {
            if (bottom < floorUnit.getY() + floorUnit.getHeight()) {
                this.moveToAbove(floorUnit);
                this.yVelocity = 0;
                if (!this.isOnAPlatform) {
                    this.isOnAPlatform = true;
                }
            }
            if (top > floorUnit.getY()){
                this.moveToBelow(floorUnit);
                this.yVelocity = 0;
            }
        }
    }

    /**
     * Handles colliding with ceiling units.
     * If the boss collides with a ceiling, it will be moved below to the ceiling
     * and its {@code yVelocity} will become 0 immediately since it can not
     * move up anymore.
     * @param ceilingUnit The ceiling that the boss has interaction to.
     */
    @Override
    public void collideWith(CeilingUnit ceilingUnit) {
        if (this.overlaps(ceilingUnit)) {
            this.moveToBelow(ceilingUnit);
            this.yVelocity = 0;
        }
    }

    /**
     * Handles colliding with wall units.
     * If the boss collides with the wall's left side, it will be moved to the
     * wall's left side; otherwise, it will be moved to the wall's right side.
     * Also, the {@code xVelocity} of boss will become 0 and it will reverse
     * direction to avoid further colliding.
     * @param wallUnit The wall that the boss has interaction to.
     */
    @Override
    public void collideWith(WallUnit wallUnit) {
        if (this.overlaps(wallUnit)) {
            double thisCenter = (this.getHitbox().getMaxX()+this.getHitbox().getMinX())/2;
            double center = (wallUnit.getHitbox().getMaxX()+wallUnit.getHitbox().getMinX())/2;
            if (center > thisCenter) {
                this.moveToLeft(wallUnit);
                this.xVelocity=0;
                this.reverseDirection();
            }
            else if (center <= thisCenter){
                this.moveToRight(wallUnit);
                this.xVelocity=0;
                this.reverseDirection();
            }
            else {
                this.moveToBelow(wallUnit);
                this.reverseDirection();
            }
        }
    }
}
