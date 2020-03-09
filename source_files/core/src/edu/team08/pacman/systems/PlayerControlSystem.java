package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import edu.team08.pacman.EntityStates;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.PlayerComponent;
import edu.team08.pacman.components.StateComponent;
import edu.team08.pacman.managers.InputManager;

public class PlayerControlSystem extends IteratingSystem {
    private ComponentMapper<BodyComponent> bodyComponentComponentMapper;
    private ComponentMapper<StateComponent> stateComponentComponentMapper;
    private InputManager inputManager = InputManager.getInstance();

    public PlayerControlSystem() {
        super(Family.all(PlayerComponent.class).get());

        bodyComponentComponentMapper = ComponentMapper.getFor(BodyComponent.class);
        stateComponentComponentMapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        //get the entity body
        BodyComponent bodyComponent = bodyComponentComponentMapper.get(entity);
        // get the entity state
        StateComponent stateComponent = stateComponentComponentMapper.get(entity);

        // move the entity on keypress and apply constant velocity without keypress based on state
        moveOnKeyPress(bodyComponent, stateComponent);
        continueMovement(bodyComponent, stateComponent);
    }

    private void moveOnKeyPress(BodyComponent bodyComponent, StateComponent stateComponent)
    {
        // apply forces depending on controller input
        if (inputManager.isKeyPressed(Input.Keys.DOWN)|| inputManager.isKeyPressed(Input.Keys.S))
        {
            setYVelocity(bodyComponent, -bodyComponent.getSpeed());
            if(stateComponent.getState() != EntityStates.MOVING_DOWN)
            {
                stateComponent.set(EntityStates.MOVING_DOWN);
            }
        }
        else if (inputManager.isKeyPressed(Input.Keys.UP)|| inputManager.isKeyPressed(Input.Keys.W)) {
            setYVelocity(bodyComponent, bodyComponent.getSpeed());
            if(stateComponent.getState() != EntityStates.MOVING_UP)
            {
                stateComponent.set(EntityStates.MOVING_UP);
            }
        }
        else if (inputManager.isKeyPressed(Input.Keys.LEFT)|| inputManager.isKeyPressed(Input.Keys.A)) {
            setXVelocity(bodyComponent, -bodyComponent.getSpeed());
            if(stateComponent.getState() != EntityStates.MOVING_LEFT)
            {
                stateComponent.set(EntityStates.MOVING_LEFT);
            }
        }
        else if (inputManager.isKeyPressed(Input.Keys.RIGHT) || inputManager.isKeyPressed(Input.Keys.D)) {
            setXVelocity(bodyComponent, bodyComponent.getSpeed());
            if(stateComponent.getState() != EntityStates.MOVING_RIGHT)
            {
                stateComponent.set(EntityStates.MOVING_RIGHT);
            }
        }
    }

    private void continueMovement(BodyComponent bodyComponent, StateComponent stateComponent)
    {
        if(stateComponent.getState() == EntityStates.MOVING_DOWN)
        {
            setYVelocity(bodyComponent, -bodyComponent.getSpeed());
        }
        else if(stateComponent.getState() == EntityStates.MOVING_UP)
        {
            setYVelocity(bodyComponent, bodyComponent.getSpeed());
        }
        else if(stateComponent.getState() == EntityStates.MOVING_LEFT)
        {
            setXVelocity(bodyComponent, -bodyComponent.getSpeed());
        }
        else if(stateComponent.getState() == EntityStates.MOVING_RIGHT)
        {
            setXVelocity(bodyComponent, bodyComponent.getSpeed());
        }
    }

    private void setXVelocity(BodyComponent bodyComponent, float vX)
    {
        bodyComponent.getBody().setLinearVelocity(MathUtils.lerp(bodyComponent.getBody().getLinearVelocity().x, vX, 1), 0);
    }

    private void setYVelocity(BodyComponent bodyComponent, float vY)
    {
        bodyComponent.getBody().setLinearVelocity(0, MathUtils.lerp(bodyComponent.getBody().getLinearVelocity().y, vY, 1));
    }
}
