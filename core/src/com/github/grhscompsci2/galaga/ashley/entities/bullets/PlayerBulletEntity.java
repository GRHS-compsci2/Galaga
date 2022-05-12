package com.github.grhscompsci2.galaga.ashley.entities.bullets;

import com.badlogic.ashley.core.Engine;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class PlayerBulletEntity extends BulletEntity {
  public void init(Engine engine, BodyFactory bodyFactory, float xVel, float yVel) {
    super.init(engine, bodyFactory, xVel,yVel);
    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset("playerBullet1");
    super.add(tex);
  }
}
