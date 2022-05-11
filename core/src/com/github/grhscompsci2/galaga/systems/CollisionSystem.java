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
    // get collision component
    CollisionComponent cc = Mapper.collisionCom.get(entity);
    Entity collidedEntity = cc.collisionEntity;
    if (collidedEntity != null) {
      if (Mapper.typeCom.has(entity)) {
        hitCheck(entity);
      }
      if (Mapper.typeCom.has(collidedEntity)) {
        hitCheck(collidedEntity);
      }
    }
    cc.collisionEntity = null; // collision handled reset component
  }
  
  public void hitCheck(Entity entity){
    TypeComponent tC = Mapper.typeCom.get(entity);
    StateComponent sC = Mapper.stateCom.get(entity);
    if (sC.getState() != StateComponent.STATE_HIT && sC.getState() != StateComponent.STATE_DEAD) {
      switch (tC.type) {
        case TypeComponent.BULLET:
          sC.set(StateComponent.STATE_DEAD);
          break;
        case TypeComponent.PLAYER:
        case TypeComponent.ENEMY:
          sC.set(StateComponent.STATE_HIT);
          break;
      }
    }
  }
}