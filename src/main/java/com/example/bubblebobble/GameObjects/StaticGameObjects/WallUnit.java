package com.example.bubblebobble.GameObjects.StaticGameObjects;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;

/**
 * The WallUnit class creates wall units to be used for the world.
 * <p>
 * A wall unit is an unit shaped like a square that is treated as a wall,
 * with collision on all four sides.
 * The wall collides with any nonstatic objects and projectiles.
 * </p>
 */
public class WallUnit extends StaticGameObject {
	public WallUnit(InteractableWorld world, int colNum, int rowNum) {
		super(world, colNum, rowNum, Main.UNIT_SIZE, Main.UNIT_SIZE);
	}
}
