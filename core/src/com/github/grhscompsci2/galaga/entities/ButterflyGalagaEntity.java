package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ai.PathPresets;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.CollisionComponent;
import com.github.grhscompsci2.galaga.ashley.components.EnemyComponent;
import com.github.grhscompsci2.galaga.ashley.components.InactiveComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.SteeringComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TranslationComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class ButterflyGalagaEntity extends EnemyEntity {
  public void init(Engine engine, BodyFactory bodyFactory, Vector2 home) {
    super.init(engine, bodyFactory, home);

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    keyFrames.add(Utility.getTextureRegionAsset("butterfly1"));
    keyFrames.add(Utility.getTextureRegionAsset("butterfly2"));

    Animation<TextureRegion> ani = new Animation<TextureRegion>(AnimationComponent.FRAME_RATE, keyFrames,
        PlayMode.LOOP);

    AnimationComponent aComponent =getComponent(AnimationComponent.class);
    aComponent.animations.put(StateComponent.STATE_NORMAL, ani);
    aComponent.animations.put(StateComponent.STATE_ENTRY_IDLE, ani);
    aComponent.animations.put(StateComponent.STATE_ENTRY, ani);

    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset("butterfly1");
    super.add(tex);
  }
}
