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

    private final ComponentMapper<TextureComponent> textureComponentMapper;
    private final ComponentMapper<AnimationComponent> animationComponentMapper;
    private final ComponentMapper<StateComponent> stateComponentMapper;

    public AnimationSystem()
    {
        super(Family.all(AnimationComponent.class, TextureComponent.class, StateComponent.class).get());
        textureComponentMapper = ComponentMapper.getFor(TextureComponent.class);
        animationComponentMapper = ComponentMapper.getFor(AnimationComponent.class);
        stateComponentMapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        TextureComponent textureComponent = textureComponentMapper.get(entity);
        AnimationComponent animationComponent = animationComponentMapper.get(entity);
        StateComponent stateComponent = stateComponentMapper.get(entity);
        textureComponent.setRegion(animationComponent.getAnimations().get(stateComponent.getState()).getKeyFrame(stateComponent.getTime()));
    }
}
