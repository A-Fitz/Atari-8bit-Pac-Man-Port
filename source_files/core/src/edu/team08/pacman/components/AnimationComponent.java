package edu.team08.pacman.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.team08.pacman.states.EntityState;

import java.util.HashMap;
import java.util.Map;

public class AnimationComponent implements Component
{
    private Map<EntityState, Animation<TextureRegion>> animations;

    public AnimationComponent()
    {
        animations = new HashMap<>();
    }

    public Map<EntityState, Animation<TextureRegion>> getAnimations()
    {
        return animations;
    }

    public void addAnimation(EntityState entityState, Animation<TextureRegion> animation)
    {
        animations.put(entityState, animation);
    }
}
