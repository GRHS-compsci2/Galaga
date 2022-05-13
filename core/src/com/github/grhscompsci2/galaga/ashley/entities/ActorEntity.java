package com.github.grhscompsci2.galaga.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class ActorEntity extends Entity {
  public void init(PooledEngine engine, BodyFactory bodyFactory, String textureName, float x, float y) {
    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset(textureName);
    super.add(tex);

    TransformComponent pos = engine.createComponent(TransformComponent.class);
    pos.setPosition(x, y);
    super.add(pos);

    BodyComponent b2d = engine.createComponent(BodyComponent.class);
    b2d.body = bodyFactory.makeBoxPolyBody(x, y, tex.region.getRegionWidth() * 0.75f,
        tex.region.getRegionHeight() * 0.75f, BodyFactory.STONE, BodyType.DynamicBody,
        BodyFactory.CATEGORY_PLAYER, BodyFactory.MASK_PLAYER, true);
    super.add(b2d);
  }

}
