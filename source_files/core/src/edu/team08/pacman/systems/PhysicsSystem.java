package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.TransformComponent;


public class PhysicsSystem extends IteratingSystem
{
    // variable for our box2d world and bodies
    private World world;
    private Array<Entity> bodiesQueue;
    // component mappers
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public PhysicsSystem(World world)
    {
        // System for all Entities that have B2dBodyComponent and TransformComponent
        super(Family.all(BodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.bodiesQueue = new Array<>();
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        //Loop through all Entities and update our components
        for (Entity entity : bodiesQueue)
        {
            world.step(1 / 60f, 8, 3);
            // get components
            TransformComponent tfm = tm.get(entity);
            BodyComponent bodyComp = bm.get(entity);
            // update our transform to match body position
            tfm.set(bodyComp.getBody().getPosition());
        }
        // empty queue
        bodiesQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        // add Items to queue
        bodiesQueue.add(entity);
    }
}