package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.ai.PathPresets;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.CollisionComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;

public abstract class EnemyEntity extends Entity {
  InactiveComponent inactiveComponent = new InactiveComponent();

  public abstract void init(Engine engine, BodyFactory factory, Vector2 home);

  public void setPath(int path) {
    EnemyComponent enemyComponent = this.getComponent(EnemyComponent.class);
    switch (path) {
      case 4:
        enemyComponent.setPath(PathPresets.ENTRY_PATH_4);
        break;
    }
  }

  public void revive() {
    StateComponent stateComponent = Mapper.stateCom.get(this);
    B2dBodyComponent b2dBodyComponent = Mapper.b2dCom.get(this);
    CollisionComponent collisionComponent = Mapper.collisionCom.get(this);
    stateComponent.set(StateComponent.STATE_ENTRY);
    b2dBodyComponent.body.setActive(true);
    b2dBodyComponent.body.setType(BodyType.DynamicBody);
    collisionComponent.collisionEntity = null;
    remove(InactiveComponent.class);
  }
}
