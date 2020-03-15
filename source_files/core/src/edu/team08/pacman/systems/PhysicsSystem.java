package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Queue;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.BonusNuggetComponent;
import edu.team08.pacman.components.PillComponent;
import edu.team08.pacman.components.TransformComponent;
import edu.team08.pacman.constants.MovementConstants;


public class PhysicsSystem extends IteratingSystem
{
    private World world;
    private Queue<Entity> entityQueue;
    // component mappers
    private ComponentMapper<BodyComponent> bodyComponentMapper = ComponentMapper.getFor(BodyComponent.class);
    private ComponentMapper<TransformComponent> transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);

    public PhysicsSystem(World world)
    {
        super(Family.exclude(PillComponent.class, BonusNuggetComponent.class).all(BodyComponent.class, TransformComponent.class).get());
        this.world = world;
        this.entityQueue = new Queue<>();
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        // loop through entities and update positions
        while (entityQueue.notEmpty())
        {
            Entity entity = entityQueue.removeFirst();
            world.step(1 / 60f, 8, 3); // 60fps and recommended velocity/position iterations
            // get components
            TransformComponent transformComponent = transformComponentMapper.get(entity);
            BodyComponent bodyComponent = bodyComponentMapper.get(entity);

            // teleport entity if needed
            if (bodyComponent.getBody().getPosition().x >= MovementConstants.ENTITY_TELEPORT_MAX)
            {
                bodyComponent.getBody().setTransform(MovementConstants.ENTITY_TELEPORT_MIN, bodyComponent.getBody().getPosition().y, bodyComponent.getBody().getAngle());
            } else if (bodyComponent.getBody().getPosition().x <= MovementConstants.ENTITY_TELEPORT_MIN)
            {
                bodyComponent.getBody().setTransform(MovementConstants.ENTITY_TELEPORT_MAX, bodyComponent.getBody().getPosition().y, bodyComponent.getBody().getAngle());
            }

            // update our transform to match body position
            transformComponent.setPosition(bodyComponent.getBody().getPosition());
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        // add bodies to queue
        entityQueue.addLast(entity);
    }
}