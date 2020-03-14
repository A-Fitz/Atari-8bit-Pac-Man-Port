package edu.team08.pacman;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import edu.team08.pacman.components.PillComponent;
import edu.team08.pacman.constants.GameConstants;
import edu.team08.pacman.game.WorldBuilder;

public class GameContactListener implements ContactListener
{
    private final ComponentMapper<PillComponent> pillM = ComponentMapper.getFor(PillComponent.class);

    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();



        if (fixtureA.getFilterData().categoryBits == GameConstants.PILL_BIT || fixtureB.getFilterData().categoryBits == GameConstants.PILL_BIT) {
            if (fixtureA.getFilterData().categoryBits == GameConstants.PLAYER_BIT) {
                Body body = fixtureB.getBody();
                Entity entity = (Entity) body.getUserData();
                PillComponent pill = pillM.get(entity);
                pill.setEaten(true);
            } else if (fixtureB.getFilterData().categoryBits == GameConstants.PLAYER_BIT) {
                Body body = fixtureA.getBody();
                Entity entity = (Entity) body.getUserData();
                PillComponent pill = pillM.get(entity);
                pill.setEaten(true);
            }
        }

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
