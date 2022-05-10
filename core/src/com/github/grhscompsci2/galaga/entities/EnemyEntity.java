package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ai.PathPresets;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.CollisionComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.SteeringComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class EnemyEntity extends Entity {
  private InactiveComponent inactiveComponent = new InactiveComponent();

  public void init(Engine engine, BodyFactory factory, Vector2 home) {

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    keyFrames.clear();
    keyFrames.add(Utility.getTextureRegionAsset("explosion1"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion2"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion3"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion4"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion5"));

    Animation<TextureRegion> explosionAni = new Animation<TextureRegion>(AnimationComponent.FRAME_RATE / 2,
        keyFrames, PlayMode.NORMAL);
    AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
    aComponent.animations.put(StateComponent.STATE_HIT, explosionAni);
    super.add(aComponent);

    StateComponent sComponent = engine.createComponent(StateComponent.class);
    super.add(sComponent);

    TranslationComponent pos = engine.createComponent(TranslationComponent.class);
    super.add(pos);

    B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);
    b2d.body = factory.makeBoxPolyBody(-5, -5, Utility.SPRITE_WIDTH, Utility.SPRITE_WIDTH,
        BodyFactory.STONE, BodyType.DynamicBody, BodyFactory.CATEGORY_ENEMY, BodyFactory.MASK_ENEMY, true);
    b2d.body.setUserData(this);
    super.add(b2d);

    EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
    enemyComponent.initPaths(home, PathPresets.ENTRY_PATH_1);
    super.add(enemyComponent);

    CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
    add(collisionComponent);

    TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
    typeComponent.type = TypeComponent.ENEMY;
    add(typeComponent);

    SteeringComponent steeringComponent = engine.createComponent(SteeringComponent.class);
    steeringComponent.body = b2d.body;
    super.add(steeringComponent);

    super.add(inactiveComponent);
  }
}
