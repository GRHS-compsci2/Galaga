package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.CollisionComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class BulletEntity extends Entity implements Poolable {

  public void init(Engine engine, BodyFactory bodyFactory) {

    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset("playerBullet1");
    super.add(tex);

    StateComponent sComponent = engine.createComponent(StateComponent.class);
    sComponent.set(StateComponent.STATE_NORMAL);
    super.add(sComponent);

    TranslationComponent pos = engine.createComponent(TranslationComponent.class);
    super.add(pos);

    B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);
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
    StateComponent stateComponent = Mapper.stateCom.get(this);
    B2dBodyComponent b2dBodyComponent = Mapper.b2dCom.get(this);
    stateComponent.set(StateComponent.STATE_NORMAL);
    b2dBodyComponent.body.setActive(false);
    b2dBodyComponent.body.setTransform(-5,-5, 0);
    add(new InactiveComponent());

  }

  public void revive(Vector2 position, float xVel, float yVel) {
    B2dBodyComponent b2dBodyComponent = Mapper.b2dCom.get(this);
    b2dBodyComponent.body.setActive(true);
    b2dBodyComponent.body.setType(BodyType.DynamicBody);
    b2dBodyComponent.body.setTransform(position, 0);
    b2dBodyComponent.body.setLinearVelocity(xVel, yVel);
    remove(InactiveComponent.class);
  }
}
