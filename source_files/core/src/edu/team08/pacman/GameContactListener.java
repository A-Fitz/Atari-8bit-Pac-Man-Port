package edu.team08.pacman;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import edu.team08.pacman.components.BonusNuggetComponent;
import edu.team08.pacman.components.GhostComponent;
import edu.team08.pacman.components.PillComponent;
import edu.team08.pacman.components.PlayerComponent;
import edu.team08.pacman.constants.CategoryBitsConstants;
import edu.team08.pacman.constants.GameConstants;
import edu.team08.pacman.managers.GameManager;
import edu.team08.pacman.managers.GhostMovementManager;

public class GameContactListener implements ContactListener
{
    private final ComponentMapper<PillComponent> pillComponentMapper;
    private final ComponentMapper<BonusNuggetComponent> bonusNuggetComponentMapper;
    private final ComponentMapper<GhostComponent> ghostComponentMapper;
    private final ComponentMapper<PlayerComponent> playerComponentMapper;

    private GhostMovementManager gmm = new GhostMovementManager();

    public GameContactListener()
    {
        this.ghostComponentMapper = ComponentMapper.getFor(GhostComponent.class);
        this.pillComponentMapper = ComponentMapper.getFor(PillComponent.class);
        this.bonusNuggetComponentMapper = ComponentMapper.getFor(BonusNuggetComponent.class);
        this.playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
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
                this.eatBonusNugget(fixtureB);
            } else if (fixtureB.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS)
            {
                this.eatBonusNugget(fixtureA);
            }
        }

        // if a Pac-Man and a Pill contact, then the Pac-Man eats the Pill
        else if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.PILL_BITS || fixtureB.getFilterData().categoryBits == CategoryBitsConstants.PILL_BITS)
        {
            if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS)
            {
                this.eatPill(fixtureB);
            } else if (fixtureB.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS)
            {
                this.eatPill(fixtureA);
            }
        }

        // if a Pac-Man and a ghost contact
        if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.GHOST_BITS || fixtureB.getFilterData().categoryBits == CategoryBitsConstants.GHOST_BITS)
        {
            if (fixtureB.getBody().getType() == BodyDef.BodyType.StaticBody) {
                this.ChangeGhostDirections(fixtureA.getBody());
            } else if (fixtureA.getBody().getType() == BodyDef.BodyType.StaticBody) {
                this.ChangeGhostDirections(fixtureB.getBody());
            } else if (fixtureA.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS) {
                this.getGhostContact(fixtureB, fixtureA);
            } else if (fixtureB.getFilterData().categoryBits == CategoryBitsConstants.PLAYER_BITS) {
                this.getGhostContact(fixtureA, fixtureB);
            }
        }

    }

    private void getGhostContact(Fixture ghostFixture, Fixture playerFixture )
    {
        Body body = ghostFixture.getBody();
        Entity entity = (Entity) body.getUserData();
        GhostComponent ghostComponent = ghostComponentMapper.get(entity);

        Body playerBody = playerFixture.getBody();
        Entity playerEntity = (Entity) playerBody.getUserData();
        PlayerComponent playerComponent = playerComponentMapper.get(playerEntity);

        if(ghostComponent.isWeaken())
        {
            ghostComponent.setAlive(false);
        }
        else
        {
            //playerComponent.setAlive(false);
            GameManager.getInstance().killPacMan();
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
            GameManager.getInstance().setGhostsFlashing(true);
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

    /**
     * changes the direction of the body that is tied to the ghost.
     * @param body ghost's body
     */
    private void ChangeGhostDirections(Body body){
        int speed = 2;
        this.gmm.setDirections(body, speed);
    }
}
