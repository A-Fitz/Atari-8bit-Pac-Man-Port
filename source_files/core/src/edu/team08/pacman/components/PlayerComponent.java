package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    private int hp = 1;

    public int getHp() { return hp; }
}
