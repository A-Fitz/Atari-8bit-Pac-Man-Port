package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.team08.pacman.constants.EntityStates;

import java.util.HashMap;

public class AnimationComponent implements Component
{
    private HashMap<EntityStates, Animation<TextureRegion>> animations = new HashMap<>();

    public HashMap<EntityStates, Animation<TextureRegion>> getAnimations()
    {
        return animations;
    }

    public void addAnimation(EntityStates entityStates, Animation<TextureRegion> animation)
    {
        animations.put(entityStates, animation);
    }
}
