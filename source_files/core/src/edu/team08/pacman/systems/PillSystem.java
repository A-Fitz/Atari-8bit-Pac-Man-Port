package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.PillComponent;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.constants.PointConstants;
import edu.team08.pacman.managers.GameManager;

public class PillSystem extends IteratingSystem
{
    private ComponentMapper<PillComponent> pillComponentMapper;
    private ComponentMapper<BodyComponent> bodyComponentMapper;

    public PillSystem()
    {
        super(Family.all(PillComponent.class).get()); // only work for PillComponents
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
            GameManager.getInstance().getAssetManager().get(FilePathConstants.SOUND_EAT_PILL_PATH, Sound.class).play();

            if (pillComponent.isBig())
            {
                GameManager.getInstance().addScore(PointConstants.BIG_PILL_POINTS);
            } else
            {
                GameManager.getInstance().addScore(PointConstants.PILL_POINTS);
            }

            GameManager.getInstance().decreaseTotalPills();

            // removes the entity from the world
            body.getWorld().destroyBody(body);
            getEngine().removeEntity(entity);
        }
    }
}
