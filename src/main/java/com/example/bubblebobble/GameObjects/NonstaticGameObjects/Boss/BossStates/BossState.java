package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss.BossStates;

import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.Hero;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface of different states of the boss.
 */
public interface BossState {
    void collideWithWeapon(Weapon heroWeapon);
    void collideWithHero(Hero hero);

    void update();
    void drawOn(GraphicsContext gc);
}
