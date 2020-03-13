package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Body;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.PillComponent;
import edu.team08.pacman.managers.GameManager;

public class PillSystem extends IteratingSystem
{
    private ComponentMapper<PillComponent> pillComponentMapper;
    private ComponentMapper<BodyComponent> bodyComponentMapper;

    public PillSystem()
    {
        super(Family.all(PillComponent.class).get());
        pillComponentMapper = ComponentMapper.getFor(PillComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        PillComponent pillComponent = pillComponentMapper.get(entity);
        BodyComponent bodyComponent = bodyComponentMapper.get(entity);
        Body body = bodyComponent.getBody();
        if (pillComponent.isEaten())
        {
            if (pillComponent.isBig())
            {
                // TODO  play sound
                GameManager.getInstance().addScore(500);
            } else
            {
                // TODO  play sound
                GameManager.getInstance().addScore(100);
            }

            // removes the entity from the world
            getEngine().removeEntity(entity);
            body.getWorld().destroyBody(body);
        }


        GameManager.getInstance().decreaseTotalPills();
    }
}
