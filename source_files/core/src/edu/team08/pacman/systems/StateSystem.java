package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import edu.team08.pacman.components.StateComponent;

public class StateSystem extends IteratingSystem
{

    private final ComponentMapper<StateComponent> stateComponentMapper;

    public StateSystem()
    {
        super(Family.all(StateComponent.class).get());
        stateComponentMapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        StateComponent stateComponent = stateComponentMapper.get(entity);
        stateComponent.addTime(deltaTime);
    }
}
