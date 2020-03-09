package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component
{
    public static final int GHOST = 0;
    public static final int PILL = 1;
    public static final int OTHER = 2;
    public static final int PLAYER = 3;

    public int type = OTHER;
}
