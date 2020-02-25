package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import edu.team08.pacman.EntityStates;

public class StateComponent implements Component {
    private EntityStates state = EntityStates.IDLE_RIGHT;
    public float time = 0.0f;

    public void set(EntityStates newState){
        state = newState;
        time = 0.0f;
    }

    public EntityStates get(){
        return state;
    }
}
