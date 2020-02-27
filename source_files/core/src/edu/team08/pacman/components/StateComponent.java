package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import edu.team08.pacman.EntityStates;

public class StateComponent implements Component {
    private float time = 0.0f;
    private EntityStates state = EntityStates.IDLE_RIGHT;

    public StateComponent() {
    }

    public StateComponent(EntityStates state) {
        set(state);
    }

    public void set(EntityStates newState) {
        state = newState;
        time = 0.0f;
    }

    public EntityStates get() {
        return state;
    }

    public void addTime(float delta){ time =+ delta; }

    public float getTime() {
        return time;
    }
}