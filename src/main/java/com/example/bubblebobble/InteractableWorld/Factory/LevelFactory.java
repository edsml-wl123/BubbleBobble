package com.example.bubblebobble.InteractableWorld.Factory;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.Boss;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;

/**
 * The LevelFactory class handles everything about different levels in the game.
 * <p>
 * There will be 3 levels in the game. In the second level, one enemy will be added
 * to the world and the speed of enemy increases; in the third level, a boss
 * will be added to the world and again the speed of enemy increases.
 * </p>
 *
 */
public class LevelFactory {
    InteractableWorld world;
    int level;
    double speed;

    public LevelFactory(InteractableWorld world){
        this.world=world;

        //Initialize level to 0
        level=0;
        speed=0.025;
    }

    /**
     * Get method of {@code level}.
     * @return The current level of the game.
     */
    public int getLevel(){return level;}

    /**
     * Add 1 to the current level.
     */
    public void addLevel() {
        level++;
    }

    /**
     * Reset the current level to 0.
     */
    public void resetLevel(){level=0;}


    /**
     * Update the enemies and boss in the game according to different levels.
     * In the second level, one enemy will be added
     * to the world and the speed of enemy increases; in the third level, a boss
     * will be added to the world and again the speed of enemy increases.
     */
    public void updateLevel() {
        if(world.getNumOfDeadEnemy()==0) {
            if (level == 1) {
                Enemy enemy=new Enemy(world, 30, 15);

                world.getMapFactory().setEnemySize(enemy);
                world.addEnemy(enemy);
                Enemy.TERMINAL_VELOCITY_X=2.9;
                Enemy.X_ACCEL=1.26;

            } else if (level == 2) {
                Boss boss=new Boss(world,25,3);
                world.addBoss(boss);
                Enemy.TERMINAL_VELOCITY_X=3.4;
                Enemy.X_ACCEL=1.35;
            }
        }
    }
}
