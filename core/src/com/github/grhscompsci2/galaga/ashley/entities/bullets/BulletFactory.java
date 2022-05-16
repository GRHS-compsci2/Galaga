package com.github.grhscompsci2.galaga.ashley.entities.bullets;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class BulletFactory {
  PooledEngine engine;
  BodyFactory bodyFactory;
  private int numPlayerBullets = 0;

  public BulletFactory(PooledEngine engine, BodyFactory bodyFactory) {
    this.engine = engine;
    this.bodyFactory = bodyFactory;
  }

  public void playerFire(Vector2 position, float xVel, float yVel) {
    PlayerBulletEntity bullet = new PlayerBulletEntity();
    bullet.init(engine, bodyFactory, position, xVel, yVel);
    numPlayerBullets++;
    engine.addEntity(bullet);
  }
  
  public void enemyFire(Vector2 position, float xVel, float yVel) {
    EnemyBulletEntity bullet = new EnemyBulletEntity();
    bullet.init(engine, bodyFactory, position, xVel, yVel);
    engine.addEntity(bullet);
  }

  public int getNumPlayerBullets() {
    return numPlayerBullets;
  }

  public void subtractPlayerBullets() {
    numPlayerBullets--;
  }

  public void checkDead(Entity entity) {
    TypeComponent type=K2ComponentMappers.type.get(entity);
    if(type!=null&&type.type==TypeComponent.PLAYER_BULLET){
      subtractPlayerBullets();
    }
  }

}
