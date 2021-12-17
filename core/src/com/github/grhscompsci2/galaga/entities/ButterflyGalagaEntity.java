package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

public class ButterflyGalagaEntity extends Entity {

    float x;
    float y;
    public ButterflyGalagaEntity(float x, float y) {
        this.x=x;
        this.y=y;
    }

    public void init(Engine engine, BodyFactory bodyFactory) {

       // float x = (float) (Math.random() * 16 + 1);
       // float y = (float) (Math.random() * 16 + 1);
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        keyFrames.add(Utility.getTextureRegionAsset("butterfly1"));
        keyFrames.add(Utility.getTextureRegionAsset("butterfly2"));

        Animation<TextureRegion> ani = new Animation<TextureRegion>(AnimationComponent.FRAME_RATE, keyFrames);

        AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
        aComponent.animations.put(StateComponent.STATE_NORMAL, ani);
        aComponent.animations.put(StateComponent.STATE_ENTRY_IDLE, ani);
        aComponent.animations.put(StateComponent.STATE_ENTRY, ani);
        super.add(aComponent);

        TextureComponent tex = engine.createComponent(TextureComponent.class);
        tex.region = Utility.getTextureRegionAsset("butterfly1");
        super.add(tex);

        StateComponent sComponent = engine.createComponent(StateComponent.class);
        sComponent.isLooping = true;
        sComponent.set(StateComponent.STATE_ENTRY);
        super.add(sComponent);

        TranslationComponent pos = engine.createComponent(TranslationComponent.class);
        pos.setPosition(x, y);
        super.add(pos);

        B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);
        b2d.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 1.5f, BodyFactory.STONE, BodyType.DynamicBody, true);
        add(b2d);
        EnemyComponent enemyComponent=engine.createComponent(EnemyComponent.class);
        enemyComponent.initPaths(x, y);
        enemyComponent.setPath(0);
        super.add(enemyComponent);
    }
}
