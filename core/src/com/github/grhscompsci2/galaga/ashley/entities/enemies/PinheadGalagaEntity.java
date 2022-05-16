package com.github.grhscompsci2.galaga.ashley.entities.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class PinheadGalagaEntity extends Entity {
    public void init(Engine engine, BodyFactory bodyFactory) {
/*
        float x = (float) (Math.random() * 16 + 1);
        float y = (float) (Math.random() * 16 + 1);

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        keyFrames.add(Utility.getTextureRegionAsset("pinhead1"));
        keyFrames.add(Utility.getTextureRegionAsset("pinhead2"));

        Animation<TextureRegion> ani = new Animation<TextureRegion>(Utility.ANI_FRAME_RATE, keyFrames);

        AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
        aComponent.animations.put(StateComponent.STATE_NORMAL, ani);

        keyFrames.clear();
        keyFrames.add(Utility.getTextureRegionAsset("explosion1"));        
        keyFrames.add(Utility.getTextureRegionAsset("explosion2"));        
        keyFrames.add(Utility.getTextureRegionAsset("explosion3"));
        
        Animation<TextureRegion> explosionAni = new Animation<TextureRegion>(Utility.ANI_FRAME_RATE, keyFrames);
        aComponent.animations.put(StateComponent.STATE_EXPLOSION, explosionAni);
        super.add(aComponent);

        TextureComponent tex = engine.createComponent(TextureComponent.class);
        tex.region = Utility.getTextureRegionAsset("pinhead1");
        super.add(tex);

        StateComponent sComponent = engine.createComponent(StateComponent.class);
        sComponent.set(StateComponent.STATE_NORMAL);
        super.add(sComponent);

        TransformComponent pos = engine.createComponent(TransformComponent.class);
        pos.setPosition(x, y);
        super.add(pos);

        BodyComponent b2d = engine.createComponent(BodyComponent.class);
        b2d.body = bodyFactory.makeBoxPolyBody(x, y, 1.5f, 1.5f, BodyFactory.STONE, BodyType.DynamicBody,
                BodyFactory.CATEGORY_ENEMY, BodyFactory.MASK_ENEMY, true);
        super.add(b2d);*/
    }
}
