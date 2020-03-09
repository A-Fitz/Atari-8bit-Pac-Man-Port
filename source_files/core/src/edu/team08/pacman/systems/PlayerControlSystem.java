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
import edu.team08.pacman.constants.MovementConstants;
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
    protected void processEntity(Entity entity, float deltaTime) {
        //get the entity body
        BodyComponent body = bodyComponentComponentMapper.get(entity);
        // get the entity state
        StateComponent state = stateComponentComponentMapper.get(entity);

        // apply forces depending on controller input
        if (inputManager.isKeyPressed(Input.Keys.DOWN)|| inputManager.isKeyPressed(Input.Keys.S))
        {
            body.getBody().setLinearVelocity(0,
                    MathUtils.lerp(body.getBody().getLinearVelocity().y, -MovementConstants.PACMAN_SPEED, 1));
            if(state.getState() != EntityStates.MOVING_DOWN)
            {
                state.set(EntityStates.MOVING_DOWN);
            }
        }
        else if (inputManager.isKeyPressed(Input.Keys.UP)|| inputManager.isKeyPressed(Input.Keys.W)) {
            body.getBody().setLinearVelocity(0,
                    MathUtils.lerp(body.getBody().getLinearVelocity().y, MovementConstants.PACMAN_SPEED, 1));
            if(state.getState() != EntityStates.MOVING_UP)
            {
                state.set(EntityStates.MOVING_UP);
            }
        }
        else if (inputManager.isKeyPressed(Input.Keys.LEFT)|| inputManager.isKeyPressed(Input.Keys.A)) {
            body.getBody().setLinearVelocity(MathUtils.lerp(body.getBody().getLinearVelocity().x, -MovementConstants.PACMAN_SPEED, 1),
                    0);
            if(state.getState() != EntityStates.MOVING_LEFT)
            {
                state.set(EntityStates.MOVING_LEFT);
            }
        }
        else if (inputManager.isKeyPressed(Input.Keys.RIGHT) || inputManager.isKeyPressed(Input.Keys.D)) {
            body.getBody().setLinearVelocity(MathUtils.lerp(body.getBody().getLinearVelocity().x, MovementConstants.PACMAN_SPEED, 1), 0);
            if(state.getState() != EntityStates.MOVING_RIGHT)
            {
                state.set(EntityStates.MOVING_RIGHT);
            }
        }
    }
}
