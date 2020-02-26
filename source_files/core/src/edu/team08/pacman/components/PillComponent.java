package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;

public class PillComponent implements Component {
    private boolean isEatan;
    private boolean isBig;

    public PillComponent(boolean isBig)
    {
        this.isBig = isBig;
        isEatan = false;
    }

    public boolean isBig() {
        return isBig;
    }

    public boolean isEatan() {
        return isEatan;
    }

    public void setEatan(boolean eatan) {
        isEatan = eatan;
    }
}
