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
    private ComponentMapper<PillComponent> pm;
    private ComponentMapper<BodyComponent> bm;

    public PillSystem()
    {
        super(Family.all(PillComponent.class).get());
        pm = ComponentMapper.getFor(PillComponent.class);
        bm = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        PillComponent pill = pm.get(entity);
        BodyComponent bodyComponent = bm.get(entity);
        Body body = bodyComponent.getBody();
        if (pill.isEaten())
        {
            if (pill.isBig())
            {
                // TODO  play sound
                GameManager.instance.AddScore(500);
            } else
            {
                // TODO  play sound
                GameManager.instance.AddScore(100);
            }

            // removes the entity from the world
            getEngine().removeEntity(entity);
            body.getWorld().destroyBody(body);
        }


        GameManager.instance.totalPills--;
    }
}
