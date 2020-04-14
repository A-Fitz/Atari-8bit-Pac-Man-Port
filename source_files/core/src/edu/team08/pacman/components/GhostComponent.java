package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class GhostComponent implements Component {
    private boolean alive;
    private boolean weaken;

    public GhostComponent(){
        this.alive = true;
        this.weaken = false;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public void setAlive(boolean isAlive){
        this.alive = isAlive;
    }

    public boolean isWeaken() {
        return weaken;
    }

    public void setWeaken(boolean weaken) {
        this.weaken = weaken;
    }
}
