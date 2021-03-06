package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Body;
import edu.team08.pacman.components.BodyComponent;
import edu.team08.pacman.components.BonusNuggetComponent;
import edu.team08.pacman.constants.FilePathConstants;
import edu.team08.pacman.constants.PointConstants;
import edu.team08.pacman.managers.GameManager;

public class BonusNuggetSystem extends IteratingSystem
{
    private ComponentMapper<BonusNuggetComponent> bonusNuggetComponentMapper;
    private ComponentMapper<BodyComponent> bodyComponentMapper;

    public BonusNuggetSystem()
    {
        super(Family.all(BonusNuggetComponent.class, BodyComponent.class).get());
        bonusNuggetComponentMapper = ComponentMapper.getFor(BonusNuggetComponent.class);
        bodyComponentMapper = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        BonusNuggetComponent bonusNuggetComponent = bonusNuggetComponentMapper.get(entity);
        BodyComponent bodyComponent = bodyComponentMapper.get(entity);

        if (bonusNuggetComponent.isEaten())
        {
            GameManager.getInstance().addScore(PointConstants.BONUS_TO_POINTS_MAP.get(bonusNuggetComponent.getBonusNugget()));
            GameManager.getInstance().getAssetManager().get(FilePathConstants.SOUND_EAT_FRUIT_PATH, Sound.class).play();
            Body body = bodyComponent.getBody();
            body.getWorld().destroyBody(body);
            getEngine().removeEntity(entity);
        } else
        {
            bonusNuggetComponent.decreaseTimeLeft(deltaTime);

            if (bonusNuggetComponent.getTimeLeft() <= 0)
            {
                Body body = bodyComponent.getBody();
                body.getWorld().destroyBody(body);
                getEngine().removeEntity(entity);
            }
        }
    }
}
