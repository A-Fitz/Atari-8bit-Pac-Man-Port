package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;

public class PillComponent implements Component
{
    private boolean isEatan = false;
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
        return isEatan;
    }

    public void setEaten(boolean isEaten)
    {
        this.isEatan = isEaten;
    }
}
