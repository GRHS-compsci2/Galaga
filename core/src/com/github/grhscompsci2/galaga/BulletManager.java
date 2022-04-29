package com.github.grhscompsci2.galaga;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.entities.BulletEntity;

public class BulletManager {
  private Engine engine;
  private BodyFactory bodyFactory;

  public BulletManager(Engine engine, BodyFactory bodyFactory) {
    this.engine = engine;
    this.bodyFactory = bodyFactory;
  }

  private Pool<BulletEntity> bulletPool = new Pool<BulletEntity>() {

    @Override
    protected BulletEntity newObject() {
      BulletEntity bullet = new BulletEntity();
      bullet.init(engine, bodyFactory);
      return bullet;
    }
  };

  public void fire(Vector2 position, float xVel, float yVel) {
    BulletEntity bullet = bulletPool.obtain();
    bullet.revive(position, xVel, yVel);
  }

  public void deadBullet(BulletEntity bulletEntity) {
    bulletPool.free(bulletEntity);
  }

}
