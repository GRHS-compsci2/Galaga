package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {

    private String TAG = AnimationSystem.class.getSimpleName();

    public AnimationSystem() {
        super(Family.all(TextureComponent.class,
                AnimationComponent.class, StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        AnimationComponent ani = Mapper.animCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);
        if (ani.animations.containsKey(state.getState())) {
            TextureComponent tex = Mapper.texCom.get(entity);
            tex.region = (TextureRegion) ani.animations.get(state.getState()).getKeyFrame(state.time);
        }

        state.time += deltaTime;
    }
}
