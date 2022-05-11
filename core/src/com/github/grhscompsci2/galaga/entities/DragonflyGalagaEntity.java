package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TranslationComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class DragonflyGalagaEntity extends Entity {
    public void init(Engine engine, BodyFactory bodyFactory) {
        float x = (float) (Math.random() * 16 + 1);
        float y = (float) (Math.random() * 16 + 1);

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        keyFrames.add(Utility.getTextureRegionAsset("dragonfly1"));

        Animation<TextureRegion> ani = new Animation<TextureRegion>(AnimationComponent.FRAME_RATE, keyFrames);

        AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
        aComponent.animations.put(StateComponent.STATE_NORMAL, ani);

        keyFrames.clear();
        keyFrames.add(Utility.getTextureRegionAsset("explosion1"));        
        keyFrames.add(Utility.getTextureRegionAsset("explosion2"));        
        keyFrames.add(Utility.getTextureRegionAsset("explosion3"));
        
        Animation<TextureRegion> explosionAni = new Animation<TextureRegion>(AnimationComponent.FRAME_RATE, keyFrames);
        aComponent.animations.put(StateComponent.STATE_EXPLOSION, explosionAni);
        super.add(aComponent);

        TextureComponent tex = engine.createComponent(TextureComponent.class);
        tex.region = Utility.getTextureRegionAsset("dragonfly1");
        super.add(tex);

        StateComponent sComponent = engine.createComponent(StateComponent.class);
        sComponent.set(StateComponent.STATE_NORMAL);
        super.add(sComponent);

        TranslationComponent pos = engine.createComponent(TranslationComponent.class);
        pos.setPosition(x, y);
        super.add(pos);

        B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);
        b2d.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 1.5f, BodyFactory.STONE, BodyType.DynamicBody,
                BodyFactory.CATEGORY_ENEMY, BodyFactory.MASK_ENEMY, true);
        super.add(b2d);
    }
}
