package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import edu.team08.pacman.components.AnimationComponent;
import edu.team08.pacman.components.StateComponent;
import edu.team08.pacman.components.TextureComponent;

public class AnimationSystem extends IteratingSystem
{

    private final ComponentMapper<TextureComponent> textureComponentComponentMapper;
    private final ComponentMapper<AnimationComponent> animationComponentComponentMapper;
    private final ComponentMapper<StateComponent> stateComponentComponentMapper;

    public AnimationSystem()
    {
        super(Family.all(AnimationComponent.class, TextureComponent.class, StateComponent.class).get());
        textureComponentComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        animationComponentComponentMapper = ComponentMapper.getFor(AnimationComponent.class);
        stateComponentComponentMapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        TextureComponent texture = textureComponentComponentMapper.get(entity);
        AnimationComponent animation = animationComponentComponentMapper.get(entity);
        StateComponent state = stateComponentComponentMapper.get(entity);
        texture.region.setRegion(animation.getAnimations().get(state.getState()).getKeyFrame(state.getTime()));
    }
}
