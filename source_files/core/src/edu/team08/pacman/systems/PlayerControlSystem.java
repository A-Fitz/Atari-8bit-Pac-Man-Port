package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.StateComponent;
import edu.team08.pacman.constants.EntityStates;
import edu.team08.pacman.managers.InputManager;

public class PlayerControlSystem extends IteratingSystem
{
    private ComponentMapper<BodyComponent> bodyComponentComponentMapper;
    private ComponentMapper<StateComponent> stateComponentComponentMapper;
    private InputManager inputManager = InputManager.getInstance();

    public PlayerControlSystem()
    {
        super(Family.all(BodyComponent.class, StateComponent.class).get());

        bodyComponentComponentMapper = ComponentMapper.getFor(BodyComponent.class);
        stateComponentComponentMapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime)
    {
        BodyComponent bodyComponent = bodyComponentComponentMapper.get(entity);
        StateComponent stateComponent = stateComponentComponentMapper.get(entity);

        // move the entity on keypress and apply constant velocity without keypress based on state
        moveOnKeyPress(bodyComponent, stateComponent);
        continueMovement(bodyComponent, stateComponent);
    }

    private void moveOnKeyPress(BodyComponent bodyComponent, StateComponent stateComponent)
    {
        if ((inputManager.isKeyPressed(Input.Keys.DOWN) || inputManager.isKeyPressed(Input.Keys.S)) && stateComponent.getState() != EntityStates.IDLE_DOWN)
        {
            setYVelocity(bodyComponent, -bodyComponent.getSpeed());
            stateComponent.setState(EntityStates.MOVING_DOWN);
        } else if ((inputManager.isKeyPressed(Input.Keys.UP) || inputManager.isKeyPressed(Input.Keys.W)) && stateComponent.getState() != EntityStates.IDLE_UP)
        {
            setYVelocity(bodyComponent, bodyComponent.getSpeed());
            stateComponent.setState(EntityStates.MOVING_UP);
        } else if ((inputManager.isKeyPressed(Input.Keys.LEFT) || inputManager.isKeyPressed(Input.Keys.A)) && stateComponent.getState() != EntityStates.IDLE_LEFT)
        {
            setXVelocity(bodyComponent, -bodyComponent.getSpeed());
            stateComponent.setState(EntityStates.MOVING_LEFT);
        } else if ((inputManager.isKeyPressed(Input.Keys.RIGHT) || inputManager.isKeyPressed(Input.Keys.D)) && stateComponent.getState() != EntityStates.IDLE_RIGHT)
        {
            setXVelocity(bodyComponent, bodyComponent.getSpeed());
            stateComponent.setState(EntityStates.MOVING_RIGHT);
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

    private void continueMovement(BodyComponent bodyComponent, StateComponent stateComponent)
    {
        if (stateComponent.getState() == EntityStates.MOVING_DOWN)
        {
            if (isMovingYDirection(bodyComponent.getBody()))
            {
                setYVelocity(bodyComponent, -bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityStates.IDLE_DOWN);
            }
        } else if (stateComponent.getState() == EntityStates.MOVING_UP)
        {
            if (isMovingYDirection(bodyComponent.getBody()))
            {
                setYVelocity(bodyComponent, bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityStates.IDLE_UP);
            }
        } else if (stateComponent.getState() == EntityStates.MOVING_LEFT)
        {
            if (isMovingXDirection(bodyComponent.getBody()))
            {
                setXVelocity(bodyComponent, -bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityStates.IDLE_LEFT);
            }
        } else if (stateComponent.getState() == EntityStates.MOVING_RIGHT)
        {
            if (isMovingXDirection(bodyComponent.getBody()))
            {
                setXVelocity(bodyComponent, bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityStates.IDLE_RIGHT);
            }
        }
    }

    private boolean isMovingXDirection(Body body)
    {
        return body.getLinearVelocity().x != 0;
    }

    private boolean isMovingYDirection(Body body)
    {
        return body.getLinearVelocity().y != 0;
    }
}
