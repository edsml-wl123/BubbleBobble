package com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero.HeroStates;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy.Enemy;
import com.example.bubblebobble.GameObjects.StaticGameObjects.CeilingUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.FloorUnit;
import com.example.bubblebobble.GameObjects.StaticGameObjects.WallUnit;
import com.example.bubblebobble.GameObjects.Weapons.Weapon;
import javafx.scene.canvas.GraphicsContext;

public interface HeroState {
    InteractableWorld world=InteractableWorld.getWorldInstance();

    void collideWithWeapon(Weapon weapon);
    void collideWithEnemy(Enemy enemy);

    void collideWithWall(WallUnit wallUnit);
    void collideWithFloor(FloorUnit floorUnit);
    void collideWithCeiling(CeilingUnit ceilingUnit);

    void update();
    void drawOn(GraphicsContext gc);

    void pressQ();
    void pressW();
    void pressE();
    void pressUp();
    void pressLeft();
    void pressRight();
    void pressSpace();
    void releaseQ();
    void releaseW();
    void releaseE();
    //void releaseUp();
    void releaseLeft();
    void releaseRight();
    void releaseSpace();
}
