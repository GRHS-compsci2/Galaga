package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.BulletManager;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;
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
    AnimationComponent ani = Mapper.animCom.get(entity);
    StateComponent state = Mapper.stateCom.get(entity);
    B2dBodyComponent b2d = Mapper.b2dCom.get(entity);
    if (state.getState() == StateComponent.STATE_HIT) {
      //Freeze the hit thing
      b2d.body.setLinearVelocity(0, 0);
      b2d.body.setAngularVelocity(0);
      b2d.body.setType(BodyType.StaticBody);
      // is there an animation for a hit?
      if (ani.animations.containsKey(state.getState())) {
        if (ani.animations.get(state.getState()).isAnimationFinished(state.time)) {
          state.set(StateComponent.STATE_DEAD);
        }
      } else {
        state.set(StateComponent.STATE_DEAD);
      }

    } else if (state.getState() == StateComponent.STATE_DEAD) {
      TypeComponent typeComponent = Mapper.typeCom.get(entity);
      entity.add(new InactiveComponent());
      if (typeComponent != null) {
        if (typeComponent.type == TypeComponent.BULLET) {
          bulMan.deadBullet((BulletEntity) entity);
        }
      }
    }
  }
}
