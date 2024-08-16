package com.example.bubblebobble.GameObjects.StaticGameObjects;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;
import com.example.bubblebobble.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * The Portal class creates portals to be used for the world.
 * <p>
 * Each portal has one destination, and it can transfer any nonstatic
 * game objects colliding to it to its destination.
 * </p>
 */
public class Portal extends StaticGameObject {
    private Destination des;
    Image door,openDoor;

    public Portal(InteractableWorld world, int colNum, int rowNum) {
        super(world, colNum, rowNum, Main.UNIT_SIZE, Main.UNIT_SIZE);
        door=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pic/BarbieObjects/door.png")));
        openDoor=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pic/openDoor.png")));
    }

    /**
     * Set method of {@code des}.
     * @param des The parameter used to set the value of {@code des}.
     */
    public void setDes(Destination des) {
        this.des = des;
    }

    /**
     * Get method of {@code des}.
     * @return The destination of this portal.
     */
    public Destination getDes() {
        return des;
    }


    /**
     * Draw method of the portal.
     * First call the draw method in {@code mapFactory} of the portal's world,
     * which draws a general static game object on the world's canvas.
     * Then draw the corresponding image to the portal's position and its
     * destination.
     * @param gc The graphic context of the canvas on the game scene.
     */
    @Override
    public void drawOn(GraphicsContext gc){
        world.getMapFactory().drawOnStaticObject(this,gc);
        gc.drawImage(openDoor,des.getX()-1,des.getY()-des.getHeight()-5,des.getWidth()+2,des.getHeight()+5);
        gc.drawImage(door, x-1, y-height-5, width+2, height+5);
    }
}
