package edu.team08.pacman;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import edu.team08.pacman.components.BonusNuggetComponent;
import edu.team08.pacman.components.PillComponent;
import edu.team08.pacman.constants.CategoryBitsConstants;
import edu.team08.pacman.constants.GameConstants;
import edu.team08.pacman.managers.GameManager;

public class GameContactListener implements ContactListener
{
    private final ComponentMapper<PillComponent> pillComponentMapper;
    private final ComponentMapper<BonusNuggetComponent> bonusNuggetComponentMapper;

    public GameContactListener()
    {
        this.pillComponentMapper = ComponentMapper.getFor(PillComponent.class);
        this.bonusNuggetComponentMapper = ComponentMapper.getFor(BonusNuggetComponent.class);
    }

    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // if a Pac-Man and a Bonus Nugget contact, then the Pac-Man eats the Bonus Nugget
        if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.BONUS_NUGGET_BITS || fixtureB.getFilterData().categoryBits == CategoryBitsConstants.BONUS_NUGGET_BITS)
        {
            if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS)
            {
                eatBonusNugget(fixtureB);
            } else if (fixtureB.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS)
            {
                eatBonusNugget(fixtureA);
            }
        }

        // if a Pac-Man and a Pill contact, then the Pac-Man eats the Pill
        if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.PILL_BITS || fixtureB.getFilterData().categoryBits == CategoryBitsConstants.PILL_BITS)
        {
            if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS)
            {
                eatPill(fixtureB);
            } else if (fixtureB.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS)
            {
                eatPill(fixtureA);
            }
        }
    }

    private void eatBonusNugget(Fixture bonusNuggetFixture)
    {
        Body body = bonusNuggetFixture.getBody();
        Entity entity = (Entity) body.getUserData();
        BonusNuggetComponent bonusNuggetComponent = bonusNuggetComponentMapper.get(entity);
        bonusNuggetComponent.setEaten(true);
    }

    private void eatPill(Fixture pillFixture)
    {
        Body body = pillFixture.getBody();
        Entity entity = (Entity) body.getUserData();
        PillComponent pillComponent = pillComponentMapper.get(entity);
        pillComponent.setEaten(true);

        if(pillComponent.isBig())
            GameManager.getInstance().setGhostsFlashing();
    }

    @Override
    public void endContact(Contact contact)
    {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {

    }
}
