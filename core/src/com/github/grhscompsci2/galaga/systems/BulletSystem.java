package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.BulletComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;

public class BulletSystem extends IteratingSystem {
  public BulletSystem() {
    super(Family.all(BulletComponent.class)
        .exclude(InactiveComponent.class)
        .get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    // TODO Auto-generated method stub
    // get box 2d body and bullet components
    B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
    BulletComponent bullet = Mapper.bulletCom.get(entity);

    // apply bullet velocity to bullet body
    b2body.body.setLinearVelocity(bullet.xVel, bullet.yVel);
  }
}
