package com.github.grhscompsci2.galaga.ashley.entities.bullets;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.CollisionComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class BulletEntity extends Entity {

  public void init(Engine engine, BodyFactory bodyFactory, Vector2 position, float xVel, float yVel, String fileName, int type) {
    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset(fileName);
    super.add(tex);

    BodyComponent b2d = engine.createComponent(BodyComponent.class);
    Body body = bodyFactory.makeBoxPolyBody(position.x, position.y, tex.region.getRegionWidth() * 0.75f,
        tex.region.getRegionHeight() * 0.75f, BodyFactory.STONE, BodyType.DynamicBody,
        BodyFactory.CATEGORY_BULLET, BodyFactory.MASK_BULLET, true);
    body.setBullet(true);
    bodyFactory.makeAllFixturesSensors(body);
    body.setUserData(this);
    body.setLinearVelocity(xVel, yVel);
    b2d.setBody(body);
    add(b2d);

    CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
    add(collisionComponent);

    StateComponent sComponent = engine.createComponent(StateComponent.class);
    sComponent.set(StateComponent.STATE_NORMAL);
    super.add(sComponent);

    TransformComponent pos = engine.createComponent(TransformComponent.class);
    super.add(pos);

    TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
    typeComponent.type = type;
    add(typeComponent);
  }

}
