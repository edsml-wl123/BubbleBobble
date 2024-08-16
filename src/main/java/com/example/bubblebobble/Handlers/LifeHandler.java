package com.example.bubblebobble.Handlers;

import com.example.bubblebobble.InteractableWorld.InteractableWorld;

/**
 * LifeHandler class handles the lives of a nonstatic game object.
 * The object will be dead if it has lost all its lives.
 */
public class LifeHandler {
    private int life;
    InteractableWorld world;

    public LifeHandler(int life){
        this.world=InteractableWorld.getWorldInstance();
        this.life=life;
    }

    /**
     * Decrease the lives of the object by 1.
     */
    public void loseLife(){
        if(life>-1){
            life--;
        }
    }

    /**
     * Add the lives of the object by 1.
     */
    public void addLife(){
        life++;
    }

    /**
     * Get method of {@code life}.
     * @return The value fo {@code life}.
     */
    public int getLife(){return life;}

    /**
     * Set method of {@code life}.
     * @param life The value used to set {@code life}.
     */
    public void resetLife(int life){this.life=life;}
}
