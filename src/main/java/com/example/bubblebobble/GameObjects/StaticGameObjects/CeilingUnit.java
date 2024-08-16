package com.example.bubblebobble.GameObjects.StaticGameObjects;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;


/**
 * The CeilingUnit class creates ceiling units to be used for the world.
 * <p>
 * A ceiling unit is a unit shaped like a square that is treated as a ceiling,
 * with collision on all four sides. The ceiling can be collided with any
 * kind of nonstatic game objects and projectiles.
 */

public class CeilingUnit extends StaticGameObject {
	public CeilingUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum, Main.UNIT_SIZE, Main.UNIT_SIZE);
	}
}
