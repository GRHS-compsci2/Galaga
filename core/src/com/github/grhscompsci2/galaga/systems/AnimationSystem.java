package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    ComponentMapper<TextureComponent> textureManager;
    ComponentMapper<AnimationComponent> animationMapper;
    ComponentMapper<StateComponent> stateMapper;

    public AnimationSystem() {
        super(Family.all(TextureComponent.class, AnimationComponent.class, StateComponent.class).get());

        textureManager = ComponentMapper.getFor(TextureComponent.class);
        animationMapper = ComponentMapper.getFor(AnimationComponent.class);
        stateMapper = ComponentMapper.getFor(StateComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent animation = animationMapper.get(entity);
        StateComponent state = stateMapper.get(entity);

        if (animation.animations.containsKey(state.get())) {
            TextureComponent texture = textureManager.get(entity);
            texture.region = (TextureRegion) animation.animations.get(state.get()).getKeyFrame(state.time,
                    state.isLooping);
        }

        state.time += deltaTime;
    }

}
