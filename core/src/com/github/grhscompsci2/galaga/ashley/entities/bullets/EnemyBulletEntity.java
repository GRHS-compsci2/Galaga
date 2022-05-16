package com.github.grhscompsci2.galaga.ashley.entities.bullets;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class EnemyBulletEntity extends BulletEntity {
  public void init(Engine engine, BodyFactory bodyFactory, Vector2 position,float xVel, float yVel) {
    super.init(engine, bodyFactory, position, xVel, yVel, "enemyBullet",TypeComponent.ENEMY_BULLET);
  }
}
