package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    private boolean alive;

    public PlayerComponent()
    {
        this.alive = true;
    }

    public boolean isAlive()
    {
        return this.alive;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }
}
