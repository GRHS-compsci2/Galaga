package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;

public class StateSystem extends IteratingSystem {

  private String TAG = PlayerControlSystem.class.getSimpleName();
  public StateSystem() {
    super(Family.all(StateComponent.class, TypeComponent.class)
        .get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    StateComponent state = K2ComponentMappers.state.get(entity);
    switch (state.get()) {
      case StateComponent.STATE_DYING:
        entityDying(entity);
        break;
      case StateComponent.STATE_DEAD:
        entityDead(entity);
        break;
    }
  }

  private void entityDead(Entity entity) {
    //put score stuff here too
    Gdx.app.debug(TAG, entity+" is dead!");
    getEngine().removeEntity(entity);

  }

  private void entityDying(Entity entity) {
    AnimationComponent ani = K2ComponentMappers.animation.get(entity);
    BodyComponent b2d = K2ComponentMappers.body.get(entity);
    StateComponent state = K2ComponentMappers.state.get(entity);
    // set the rotation to zero
    b2d.getBody().setTransform(b2d.getX(), b2d.getY(), 0);
    // Freeze the hit thing
    b2d.getBody().setActive(false);
    // is there an animation for a hit?
    if (ani != null && ani.animations.containsKey(state.get())) {
      if (ani.animations.get(state.get()).isAnimationFinished(state.time)) {
        state.set(StateComponent.STATE_DEAD);
      }
    } else {
      state.set(StateComponent.STATE_DEAD);
    }
  }
}
