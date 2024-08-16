package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.EnemyStates;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface of different states of the enemy.
 */
public interface EnemyState {
    void collideWithWeapon(Weapon heroWeapon);
    void collideWithHero(Hero hero);

    void collideWithWall(WallUnit wallUnit);
    void collideWithFloor(FloorUnit floorUnit);
    void collideWithCeiling(CeilingUnit ceilingUnit);

    void update();
    void drawOn(GraphicsContext gc);
}
