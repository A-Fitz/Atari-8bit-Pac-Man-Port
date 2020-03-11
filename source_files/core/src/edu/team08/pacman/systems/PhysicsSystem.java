package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.TransformComponent;
import edu.team08.pacman.constants.MovementConstants;


public class PhysicsSystem extends IteratingSystem
{
    private World world;
    private Queue<Entity> entityQueue;
    // component mappers
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

    public PhysicsSystem(World world)
    {
        super(Family.all(BodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.entityQueue = new Queue<>();
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        //Loop through all Entities and update our components
        while(entityQueue.notEmpty())
        {
            Entity entity = entityQueue.removeFirst();
            world.step(1 / 60f, 8, 3); // 60fps and recommended velocity/position iterations
            // get components
            TransformComponent tfm = tm.get(entity);
            BodyComponent bodyComp = bm.get(entity);

            // teleport entity if needed
            if(bodyComp.getBody().getPosition().x >= MovementConstants.ENTITY_TELEPORT_MAX)
                bodyComp.getBody().setTransform(MovementConstants.ENTITY_TELEPORT_MIN, bodyComp.getBody().getPosition().y, bodyComp.getBody().getAngle());
            else if(bodyComp.getBody().getPosition().x <= MovementConstants.ENTITY_TELEPORT_MIN)
                bodyComp.getBody().setTransform(MovementConstants.ENTITY_TELEPORT_MAX, bodyComp.getBody().getPosition().y, bodyComp.getBody().getAngle());

            // update our transform to match body position
            tfm.set(bodyComp.getBody().getPosition());
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        // add bodies to queue
        entityQueue.addLast(entity);
    }
}