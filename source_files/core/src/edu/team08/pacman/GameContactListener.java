package edu.team08.pacman;

import com.badlogic.gdx.physics.box2d.*;

import javax.swing.text.html.parser.Entity;

public class GameContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        Object object1 = a.getBody().getUserData();
        Object object2 = b.getBody().getUserData();

        if(object1 instanceof Entity && object2 instanceof Entity)
        {
            Entity entity1 = (Entity)object1;
            Entity entity2 = (Entity)object2;
        }

    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("No longer contact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
