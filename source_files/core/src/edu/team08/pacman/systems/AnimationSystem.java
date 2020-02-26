package edu.team08.pacman.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import edu.team08.pacman.components.AnimationComponent;
import edu.team08.pacman.components.StateComponent;
import edu.team08.pacman.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    private final ComponentMapper<TextureComponent> tm;
    private final ComponentMapper<AnimationComponent> am;
    private final ComponentMapper<StateComponent> sm;

    public AnimationSystem() {
        super(Family.all(AnimationComponent.class, TextureComponent.class, StateComponent.class).get());
        tm = ComponentMapper.getFor(TextureComponent.class);
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
}

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TextureComponent texture = tm.get(entity);
        AnimationComponent animation = am.get(entity);
        StateComponent state = sm.get(entity);
        // texture.region.setRegion(animation.animations.get(state.get()).getKeyFrame(state.time));
    }
}
