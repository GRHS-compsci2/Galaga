package com.github.grhscompsci2.galaga.ashley.entities.bullets;

import com.badlogic.ashley.core.Engine;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class EnemyBulletEntity extends BulletEntity {
  public void init(Engine engine, BodyFactory bodyFactory, float xVel, float yVel) {
    super.init(engine, bodyFactory, xVel, yVel, "enemyBullet1");
  }
}
