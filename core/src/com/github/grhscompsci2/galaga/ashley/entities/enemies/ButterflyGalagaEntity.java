package com.github.grhscompsci2.galaga.ashley.entities.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class ButterflyGalagaEntity extends EnemyEntity {
  public void init(PooledEngine engine, BodyFactory bodyFactory, Vector2 home) {
    super.init(engine, bodyFactory, home,"butterfly1");

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    keyFrames.add(Utility.getTextureRegionAsset("butterfly1"));
    keyFrames.add(Utility.getTextureRegionAsset("butterfly2"));

    Animation<TextureRegion> ani = new Animation<TextureRegion>(Utility.ANI_FRAME_RATE, keyFrames,
        PlayMode.LOOP);

    AnimationComponent aComponent =getComponent(AnimationComponent.class);
    aComponent.animations.put(StateComponent.STATE_NORMAL, ani);
    aComponent.animations.put(StateComponent.STATE_ENTRY_IDLE, ani);
    aComponent.animations.put(StateComponent.STATE_ENTRY, ani);
  }
}