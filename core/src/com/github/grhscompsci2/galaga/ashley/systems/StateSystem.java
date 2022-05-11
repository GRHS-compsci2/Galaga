package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.BulletManager;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.InactiveComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.entities.BulletEntity;

public class StateSystem extends IteratingSystem {

  private BulletManager bulMan;

  public StateSystem(BulletManager bulMan) {
    super(Family.all(StateComponent.class)
        .exclude(InactiveComponent.class)
        .get());
    this.bulMan = bulMan;
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    StateComponent state = K2ComponentMappers.state.get(entity);
    BodyComponent b2d = K2ComponentMappers.body.get(entity);
    if (state.get() == StateComponent.STATE_HIT) {
      // set the rotation to zero
      b2d.body.setTransform(b2d.body.getPosition().x, b2d.body.getPosition().y, 0);
      // Freeze the hit thing
      b2d.body.setActive(false);
      // is there an animation for a hit?
      AnimationComponent ani = K2ComponentMappers.animation.get(entity);
      if (ani != null && ani.animations.containsKey(state.get())) {
        if (ani.animations.get(state.get()).isAnimationFinished(state.time)) {
          state.set(StateComponent.STATE_DEAD);
        }
      } else {
        state.set(StateComponent.STATE_DEAD);
      }

    } else if (state.get() == StateComponent.STATE_DEAD) {
      TypeComponent typeComponent = K2ComponentMappers.type.get(entity);
      entity.add(new InactiveComponent());
      if (typeComponent != null) {
        if (typeComponent.type == TypeComponent.BULLET) {
          bulMan.deadBullet((BulletEntity) entity);
        }
      }
    }
  }
}
