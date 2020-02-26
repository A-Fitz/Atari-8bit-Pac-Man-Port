package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.TransformComponent;


public class PhysicsSystem extends IteratingSystem {
    // create variables to stabilize speed
    private static final float MAX_STEP_TIME = 1 / 45f;
    private static float accumulator = 0f;
    // variable for our box2d world and bodies
    private World world;
    private Array<Entity> bodiesQueue;
    // component mappers
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    @SuppressWarnings("unchecked")
    public PhysicsSystem(World world) {
        // System for all Entities that have B2dBodyComponent and TransformComponent
        super(Family.all(BodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.bodiesQueue = new Array<Entity>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        if (accumulator >= MAX_STEP_TIME) {
            world.step(MAX_STEP_TIME, 6, 2);
            accumulator -= MAX_STEP_TIME;

            //Loop through all Entities and update our components
            for (Entity entity : bodiesQueue) {
                // get components
                TransformComponent tfm = tm.get(entity);
                BodyComponent bodyComp = bm.get(entity);
                // get position from body
                Vector2 position = bodyComp.body.getPosition();
                // update our transform to match body position
                tfm.position.x = position.x;
                tfm.position.y = position.y;
                tfm.rotation = bodyComp.body.getAngle() * MathUtils.radiansToDegrees;
            }
        }
        // empty queue
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // add Items to queue
        bodiesQueue.add(entity);
    }
}