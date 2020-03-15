package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;

public class PillComponent implements Component
{
    private boolean isEaten = false;
    private boolean isBig;

    public boolean isBig()
    {
        return isBig;
    }

    public void setBig(boolean isBig)
    {
        this.isBig = isBig;
    }

    public boolean isEaten()
    {
        return isEaten;
    }

    public void setEaten(boolean isEaten)
    {
        this.isEaten = isEaten;
    }
}
