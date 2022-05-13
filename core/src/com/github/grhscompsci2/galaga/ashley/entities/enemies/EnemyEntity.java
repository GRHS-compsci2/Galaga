package com.github.grhscompsci2.galaga.ashley.entities.enemies;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ai.PathPresets;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.CollisionComponent;
import com.github.grhscompsci2.galaga.ashley.components.EnemyComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.SteeringComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class EnemyEntity extends Entity {

  public void init(PooledEngine engine, BodyFactory factory, Vector2 home, String textureName) {

    TextureComponent tex = engine.createComponent(TextureComponent.class);
    tex.region = Utility.getTextureRegionAsset(textureName);
    super.add(tex);

    Array<TextureRegion> keyFrames = new Array<TextureRegion>();
    keyFrames.clear();
    keyFrames.add(Utility.getTextureRegionAsset("explosion1"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion2"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion3"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion4"));
    keyFrames.add(Utility.getTextureRegionAsset("explosion5"));

    Animation<TextureRegion> explosionAni = new Animation<TextureRegion>(Utility.ANI_FRAME_RATE / 2,
        keyFrames, PlayMode.NORMAL);
    AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
    aComponent.animations.put(StateComponent.STATE_DYING, explosionAni);
    super.add(aComponent);

    BodyComponent b2d = engine.createComponent(BodyComponent.class);
    b2d.body = factory.makeBoxPolyBody(-5, -5, tex.region.getRegionWidth() * 0.75f,
        tex.region.getRegionHeight() * 0.75f, BodyFactory.STONE, BodyType.DynamicBody, BodyFactory.CATEGORY_ENEMY,
        BodyFactory.MASK_ENEMY, true);
    b2d.body.setUserData(this);
    b2d.body.setActive(false);
    super.add(b2d);

    CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
    add(collisionComponent);

    EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
    enemyComponent.initPaths(home, PathPresets.entryPath1);
    super.add(enemyComponent);

    StateComponent sComponent = engine.createComponent(StateComponent.class);
    sComponent.set(StateComponent.STATE_NORMAL);
    super.add(sComponent);

    SteeringComponent steeringComponent = engine.createComponent(SteeringComponent.class);
    steeringComponent.body = b2d.body;
    super.add(steeringComponent);

    TransformComponent pos = engine.createComponent(TransformComponent.class);
    super.add(pos);

    TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
    typeComponent.type = TypeComponent.ENEMY;
    add(typeComponent);
  }

  public void init(PooledEngine engine, BodyFactory bodyFactory, Vector2 vector2) {
  }
}
