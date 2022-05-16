package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.CollisionComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

  private final String TAG = CollisionSystem.class.getSimpleName();

  public CollisionSystem() {
    // only need to worry about player collisions
    super(Family.all(CollisionComponent.class)
        .get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    // get collision component
    CollisionComponent cc = K2ComponentMappers.collision.get(entity);
    Entity collidedEntity = cc.collisionEntity;
    if (collidedEntity != null) {
      if (K2ComponentMappers.type.has(entity)) {
        hitCheck(entity);
      }
      if (K2ComponentMappers.type.has(collidedEntity)) {
        hitCheck(collidedEntity);
      }
    }
    cc.collisionEntity = null; // collision handled reset component
  }

  public void hitCheck(Entity entity) {
    TypeComponent tC = K2ComponentMappers.type.get(entity);
    StateComponent sC = K2ComponentMappers.state.get(entity);
    if (sC.get() != StateComponent.STATE_DYING && sC.get() != StateComponent.STATE_DEAD) {
      switch (tC.type) {
        case TypeComponent.PLAYER_BULLET:
        case TypeComponent.PLAYER:
        case TypeComponent.ENEMY:
          sC.set(StateComponent.STATE_DYING);
          break;
      }
    }
  }
}