package com.github.grhscompsci2.galaga.ashley.entities.bullets;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class BulletFactory {
  PooledEngine engine;
  BodyFactory bodyFactory;
  private int numPlayerBullets = 0;

  public BulletFactory(PooledEngine engine, BodyFactory bodyFactory) {
    this.engine = engine;
    this.bodyFactory = bodyFactory;
  }

  public PlayerBulletEntity playerFire(Vector2 position, float xVel, float yVel) {
    PlayerBulletEntity bullet = new PlayerBulletEntity();
    bullet.init(engine, bodyFactory, xVel, yVel);
    numPlayerBullets++;
    return bullet;
  }

  public EnemyBulletEntity enemyFire(Vector2 position, float xVel, float yVel) {
    EnemyBulletEntity bullet = new EnemyBulletEntity();
    bullet.init(engine, bodyFactory, xVel, yVel);
    return bullet;
  }

  public int getNumPlayerBullets() {
    return numPlayerBullets;
  }

  public void subtractPlayerBullets() {
    numPlayerBullets--;
  }

}
