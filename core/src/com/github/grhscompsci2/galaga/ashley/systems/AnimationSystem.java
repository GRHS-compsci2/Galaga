package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;

public class AnimationSystem extends IteratingSystem {
  private static final String TAG = AnimationSystem.class.getName();

  public AnimationSystem() {
    super(Family.all(TextureComponent.class,
        AnimationComponent.class,
        StateComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

    AnimationComponent ani = K2ComponentMappers.animation.get(entity);
    StateComponent state = K2ComponentMappers.state.get(entity);
    if (ani.animations.containsKey(state.get())) {
      TextureComponent tex = K2ComponentMappers.texture.get(entity);
      tex.region = ani.animations.get(state.get()).getKeyFrame(state.time, state.isLooping);
    } else if (ani.shouldClearOnBlankState) {
      TextureComponent tex = K2ComponentMappers.texture.get(entity);
      tex.setRegion(null);
    }

    if (!ani.isPaused) {
      state.time += deltaTime;
    }
  }
}
