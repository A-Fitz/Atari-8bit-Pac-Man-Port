package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import edu.team08.pacman.EntityStates;

public class StateComponent implements Component
{
    private float time = 0.0f;
    private EntityStates state = EntityStates.IDLE_RIGHT;

    public StateComponent()
    {
    }

    public StateComponent(EntityStates state)
    {
        set(state);
    }

    public void set(EntityStates newState)
    {
        this.state = newState;
        this.time = 0.0f;
    }

    public EntityStates getState()
    {
        return state;
    }

    public void addTime(float delta)
    {
        /*  LET IT BE KNOWN:
            ON THE DATE 2/26/2020, NATE STELKEN ACCIDENTALLY WROTE "=+" INSTEAD OF "+=", AND THEN SPENT HOURS TRYING TO
            FIGURE OUT WHY ANIMATIONS WERE NOT RENDERING CORRECTLY.
         */
        time += delta;
    }

    public float getTime()
    {
        return time;
    }
}