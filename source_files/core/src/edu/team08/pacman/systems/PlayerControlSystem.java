package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import edu.team08.pacman.EntityStates;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.PlayerComponent;
import edu.team08.pacman.components.StateComponent;
import edu.team08.pacman.managers.InputManager;

public class PlayerControlSystem extends IteratingSystem {
    ComponentMapper<PlayerComponent> pm; // gets player component of entity
    ComponentMapper<BodyComponent> bodm; // gets body component
    ComponentMapper<StateComponent> sm; // gets state component
    private InputManager inputManager = InputManager.getInstance();

    public PlayerControlSystem() {
        super(Family.all(PlayerComponent.class).get());
        // create our component mappers
        pm = ComponentMapper.getFor(PlayerComponent.class);
        bodm = ComponentMapper.getFor(BodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //get the entity body
        BodyComponent b2body = bodm.get(entity);
        // get the entity state
        StateComponent state = sm.get(entity);

        // apply forces depending on controller input
        if (inputManager.isKeyPressed(Input.Keys.DOWN)) {
            b2body.body.setLinearVelocity(0,
                    MathUtils.lerp(b2body.body.getLinearVelocity().y, -5f, 0.2f));
            state.set(EntityStates.MOVING_DOWN);
        } else if (inputManager.isKeyPressed(Input.Keys.UP)) {
            b2body.body.setLinearVelocity(0,
                    MathUtils.lerp(b2body.body.getLinearVelocity().y, 5f, 0.2f));
            state.set(EntityStates.MOVING_UP);
        } else if (inputManager.isKeyPressed(Input.Keys.LEFT)) {
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, -5f, 0.2f),
                    0);
            state.set(EntityStates.MOVING_LEFT);
        } else if (inputManager.isKeyPressed(Input.Keys.RIGHT)) {
            b2body.body.setLinearVelocity(MathUtils.lerp(b2body.body.getLinearVelocity().x, 5f, 0.2f),
                    0);
            state.set(EntityStates.MOVING_RIGHT);
        }
    }
}
