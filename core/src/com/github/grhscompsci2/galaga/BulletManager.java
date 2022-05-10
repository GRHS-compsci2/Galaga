package com.github.grhscompsci2.galaga;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
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
    revive(bullet,position, xVel, yVel);
  }

  private void revive(BulletEntity bullet, Vector2 position, float xVel, float yVel) {
    B2dBodyComponent b2dBodyComponent=Mapper.b2dCom.get(bullet);
    StateComponent stateComponent=Mapper.stateCom.get(bullet);

    b2dBodyComponent.body.setActive(true);
    float angle=180-(yVel/Math.abs(yVel)*180);
    b2dBodyComponent.body.setTransform(position, angle);
    b2dBodyComponent.body.setLinearVelocity(xVel, yVel);
    stateComponent.set(StateComponent.STATE_NORMAL);
    bullet.remove(InactiveComponent.class);
  }

  public void deadBullet(BulletEntity bulletEntity) {
    bulletPool.free(bulletEntity);
  }

}
