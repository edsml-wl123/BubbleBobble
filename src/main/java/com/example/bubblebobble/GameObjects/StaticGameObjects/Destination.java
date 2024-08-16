package com.example.bubblebobble.GameObjects.StaticGameObjects;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;

/**
 * The Destination class creates destination for a portal.
 * <p>
 * Each destination will have one portal.
 * </p>
 */
public class Destination extends StaticGameObject {
    public Destination(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum, Main.UNIT_SIZE, Main.UNIT_SIZE);
    }
}
