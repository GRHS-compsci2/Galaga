package com.github.grhscompsci2.galaga.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.PlayerComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class PlayerEntity extends Entity {

  public void setUp(PooledEngine engine, BodyFactory bodyFactory, float x, float y) {

    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset("playerShip");
    super.add(tex);

    TransformComponent pos = engine.createComponent(TransformComponent.class);
    pos.setPosition(x, y);
    super.add(pos);

    BodyComponent b2d = engine.createComponent(BodyComponent.class);
    Body body = bodyFactory.makeBoxPolyBody(x, y, tex.region.getRegionWidth() * 0.75f,
        tex.region.getRegionHeight() * 0.75f, BodyFactory.STONE, BodyType.DynamicBody,
        BodyFactory.CATEGORY_PLAYER, BodyFactory.MASK_PLAYER, true);
    b2d.setBody(body);
    super.add(b2d);

    PlayerComponent player = engine.createComponent(PlayerComponent.class);
    super.add(player);

    TypeComponent type = engine.createComponent(TypeComponent.class);
    type.type = TypeComponent.PLAYER;
    super.add(type);

    StateComponent state = engine.createComponent(StateComponent.class);
    state.set(StateComponent.STATE_NORMAL);
    super.add(state);
  }

}
