package com.github.grhscompsci2.galaga.ashley.entities.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class LivesEntity extends Entity {
  float x;
  float y;

  public LivesEntity(float r, float y) {
    x = r;
    this.y = y;
  }

  public void init(Engine engine, BodyFactory bodyFactory) {

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    keyFrames.add(Utility.getTextureRegionAsset("playerShip1"));

    Animation<TextureRegion> ani = new Animation<TextureRegion>(Utility.ANI_FRAME_RATE, keyFrames);

    AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
    aComponent.animations.put(StateComponent.STATE_NORMAL, ani);
    super.add(aComponent);

    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset("playerShip1");
    super.add(tex);

    StateComponent sComponent = engine.createComponent(StateComponent.class);
    sComponent.set(StateComponent.STATE_NORMAL);
    super.add(sComponent);

    TransformComponent pos = engine.createComponent(TransformComponent.class);
    pos.setPosition(x, y);
    super.add(pos);
  }
}
