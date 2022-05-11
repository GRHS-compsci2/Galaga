package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.CollisionComponent;
import com.github.grhscompsci2.galaga.ashley.components.InactiveComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class BulletEntity extends Entity implements Poolable {

  public void init(Engine engine, BodyFactory bodyFactory) {

    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset("playerBullet1");
    super.add(tex);

    StateComponent sComponent = engine.createComponent(StateComponent.class);
    sComponent.set(StateComponent.STATE_NORMAL);
    super.add(sComponent);

    TransformComponent pos = engine.createComponent(TransformComponent.class);
    super.add(pos);

    BodyComponent b2d = engine.createComponent(BodyComponent.class);
    b2d.body = bodyFactory.makeBoxPolyBody(0, 0, 0.24f, 0.24f, BodyFactory.STONE, BodyType.DynamicBody,
        BodyFactory.CATEGORY_BULLET, BodyFactory.MASK_BULLET, true);
    b2d.body.setBullet(true);
    bodyFactory.makeAllFixturesSensors(b2d.body);
    b2d.body.setUserData(this);
    b2d.body.setActive(false);
    add(b2d);

    CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
    add(collisionComponent);

    TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
    typeComponent.type = TypeComponent.BULLET;
    add(typeComponent);

    InactiveComponent inactiveComponent = engine.createComponent(InactiveComponent.class);
    super.add(inactiveComponent);

    if (!engine.getEntities().contains(this, true))
      engine.addEntity(this);
  }

  @Override
  public void reset() {
    StateComponent stateComponent = K2ComponentMappers.state.get(this);
    BodyComponent bodyComponent = K2ComponentMappers.body.get(this);
    stateComponent.set(StateComponent.STATE_NORMAL);
    bodyComponent.body.setActive(false);
    bodyComponent.body.setTransform(-5, -5, 0);
    add(new InactiveComponent());

  }

  public void revive(Vector2 position, float xVel, float yVel) {
    BodyComponent bodyComponent = K2ComponentMappers.body.get(this);
    bodyComponent.body.setActive(true);
    bodyComponent.body.setType(BodyType.DynamicBody);
    bodyComponent.body.setTransform(position, 0);
    bodyComponent.body.setLinearVelocity(xVel, yVel);
    remove(InactiveComponent.class);
  }
}
