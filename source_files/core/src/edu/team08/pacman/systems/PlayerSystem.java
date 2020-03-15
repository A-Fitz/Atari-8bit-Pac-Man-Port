package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.PlayerComponent;
import edu.team08.pacman.components.StateComponent;
import edu.team08.pacman.managers.GameManager;
import edu.team08.pacman.managers.InputManager;
import edu.team08.pacman.states.EntityState;

/**
 * Manages control and livelihood of player.
 */
public class PlayerSystem extends IteratingSystem
{
    private ComponentMapper<BodyComponent> bodyComponentMapper;
    private ComponentMapper<StateComponent> stateComponentMapper;
    private ComponentMapper<PlayerComponent> playerComponentMapper;
    private InputManager inputManager = InputManager.getInstance();

    public PlayerSystem()
    {
        super(Family.all(PlayerComponent.class).get());

        bodyComponentMapper = ComponentMapper.getFor(BodyComponent.class);
        stateComponentMapper = ComponentMapper.getFor(StateComponent.class);
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime)
    {
        BodyComponent bodyComponent = bodyComponentMapper.get(entity);
        StateComponent stateComponent = stateComponentMapper.get(entity);
        PlayerComponent playerComponent = playerComponentMapper.get(entity);

        if (!playerComponent.isAlive())
        {
            GameManager.getInstance().decreaseLivesLeft();
        } else
        {
            // move the entity on keypress and apply constant velocity without keypress based on state
            moveOnKeyPress(bodyComponent, stateComponent);
            continueMovement(bodyComponent, stateComponent);
        }
    }

    private void moveOnKeyPress(BodyComponent bodyComponent, StateComponent stateComponent)
    {
        if ((inputManager.isKeyPressed(Input.Keys.DOWN) || inputManager.isKeyPressed(Input.Keys.S)) && stateComponent.getState() != EntityState.IDLE_DOWN)
        {
            setYVelocity(bodyComponent, -bodyComponent.getSpeed());
            if (stateComponent.getState() != EntityState.MOVING_DOWN)
            {
                stateComponent.setState(EntityState.MOVING_DOWN);
            }
        } else if ((inputManager.isKeyPressed(Input.Keys.UP) || inputManager.isKeyPressed(Input.Keys.W)) && stateComponent.getState() != EntityState.IDLE_UP)
        {
            setYVelocity(bodyComponent, bodyComponent.getSpeed());
            if (stateComponent.getState() != EntityState.MOVING_UP)
            {
                stateComponent.setState(EntityState.MOVING_UP);
            }
        } else if ((inputManager.isKeyPressed(Input.Keys.LEFT) || inputManager.isKeyPressed(Input.Keys.A)) && stateComponent.getState() != EntityState.IDLE_LEFT)
        {
            setXVelocity(bodyComponent, -bodyComponent.getSpeed());
            if (stateComponent.getState() != EntityState.MOVING_LEFT)
            {
                stateComponent.setState(EntityState.MOVING_LEFT);
            }
        } else if ((inputManager.isKeyPressed(Input.Keys.RIGHT) || inputManager.isKeyPressed(Input.Keys.D)) && stateComponent.getState() != EntityState.IDLE_RIGHT)
        {
            setXVelocity(bodyComponent, bodyComponent.getSpeed());
            if (stateComponent.getState() != EntityState.MOVING_RIGHT)
            {
                stateComponent.setState(EntityState.MOVING_RIGHT);
            }
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
        if (stateComponent.getState() == EntityState.MOVING_DOWN)
        {
            if (isMovingYDirection(bodyComponent.getBody()))
            {
                setYVelocity(bodyComponent, -bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityState.IDLE_DOWN);
            }
        } else if (stateComponent.getState() == EntityState.MOVING_UP)
        {
            if (isMovingYDirection(bodyComponent.getBody()))
            {
                setYVelocity(bodyComponent, bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityState.IDLE_UP);
            }
        } else if (stateComponent.getState() == EntityState.MOVING_LEFT)
        {
            if (isMovingXDirection(bodyComponent.getBody()))
            {
                setXVelocity(bodyComponent, -bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityState.IDLE_LEFT);
            }
        } else if (stateComponent.getState() == EntityState.MOVING_RIGHT)
        {
            if (isMovingXDirection(bodyComponent.getBody()))
            {
                setXVelocity(bodyComponent, bodyComponent.getSpeed());
            } else
            {
                stateComponent.setState(EntityState.IDLE_RIGHT);
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
