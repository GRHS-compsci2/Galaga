package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.CollisionComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

  private final String TAG = CollisionSystem.class.getSimpleName();

  public CollisionSystem() {
    // only need to worry about player collisions
    super(Family.all(CollisionComponent.class)
        .exclude(InactiveComponent.class)
        .get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    // get player collision component
    CollisionComponent cc = Mapper.collisionCom.get(entity);
    Entity collidedEntity = cc.collisionEntity;
    if (collidedEntity != null) {
      TypeComponent usType = Mapper.typeCom.get(entity);
      TypeComponent themType = Mapper.typeCom.get(collidedEntity);
      StateComponent sc = Mapper.stateCom.get(entity);

      if (themType != null && usType.type != themType.type && sc.getState() != StateComponent.STATE_HIT) {
        //
        if (usType.type == TypeComponent.ENEMY || usType.type == TypeComponent.PLAYER
            || themType.type == TypeComponent.BULLET) {
          if (Mapper.stateCom.has(collidedEntity)) {
            StateComponent colSc = Mapper.stateCom.get(collidedEntity);
            colSc.set(StateComponent.STATE_HIT);
          }
          // enemy or player is hit by bullet
          Gdx.app.debug(TAG, "Collision between " + entity + " and " + collidedEntity);
          sc.set(StateComponent.STATE_HIT);
        }
      }
    }
    cc.collisionEntity = null; // collision handled reset component
  }
}