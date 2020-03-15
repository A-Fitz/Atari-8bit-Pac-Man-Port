package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import edu.team08.pacman.states.EntityState;

public class StateComponent implements Component
{
    private float time = 0.0f;
    private EntityState state;

    public EntityState getState()
    {
        return state;
    }

    public void setState(EntityState newState)
    {
        this.state = newState;
        this.time = 0.0f;
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