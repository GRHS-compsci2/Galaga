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
    StateComponent stateC = K2ComponentMappers.state.get(entity);
    switch (stateC.get()) {
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
    AnimationComponent aniC = K2ComponentMappers.animation.get(entity);
    BodyComponent bodyC = K2ComponentMappers.body.get(entity);
    StateComponent stateC = K2ComponentMappers.state.get(entity);
    // set the rotation to zero
    bodyC.setRotation(0);
    // Freeze the hit thing
    bodyC.getBody().setActive(false);
    // is there an animation for a hit?
    if (aniC != null && aniC.animations.containsKey(stateC.get())) {
      if (aniC.animations.get(stateC.get()).isAnimationFinished(stateC.time)) {
        stateC.set(StateComponent.STATE_DEAD);
      }
    } else {
      stateC.set(StateComponent.STATE_DEAD);
    }
  }
}
