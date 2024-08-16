package com.example.bubblebobble.GameObjects.StaticGameObjects;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;

/**
 * The FloorUnit class creates floor units to be used for the world.
 * <p>
 * A floor unit is a unit shaped like a square that is treated as a floor,
 * with collision on the top, left, and right sides.
 * The floor can be collided with any kind of nonstatic game object and projectiles.
 * </p>
 */
public class FloorUnit extends StaticGameObject {
	public FloorUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum, Main.UNIT_SIZE, Main.UNIT_SIZE);
	}
}
