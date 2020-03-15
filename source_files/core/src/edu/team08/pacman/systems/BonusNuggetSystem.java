package edu.team08.pacman.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Body;
import edu.team08.pacman.components.*;
import edu.team08.pacman.constants.DisplayConstants;
import edu.team08.pacman.constants.EntityStates;
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

        if(bonusNuggetComponent.isEaten())
        {
            GameManager.getInstance().addScore(PointConstants.BONUS_TO_POINTS_MAP.get(bonusNuggetComponent.getBonusNugget()));

            Body body = bodyComponent.getBody();
            body.getWorld().destroyBody(body);
            getEngine().removeEntity(entity);
        } else
        {
            bonusNuggetComponent.decreaseTimeLeft(deltaTime);

            if(bonusNuggetComponent.getTimeLeft() <= 0)
            {
                Body body = bodyComponent.getBody();
                body.getWorld().destroyBody(body);
                getEngine().removeEntity(entity);
            }
        }
    }
}
