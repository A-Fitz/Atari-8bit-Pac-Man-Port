package edu.team08.pacman.components;

import com.badlogic.gdx.physics.box2d.Body;

public class GhostComponent {
    private boolean alive;

    public GhostComponent(){
        this.alive = true;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public void setAlive(boolean isAlive){
        this.alive = isAlive;
    }
}
