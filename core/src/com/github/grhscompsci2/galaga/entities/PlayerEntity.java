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
import com.github.grhscompsci2.galaga.components.PlayerComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class PlayerEntity extends Entity {
    public void setUp(Engine engine, BodyFactory bodyFactory) {
        TextureComponent tex = engine.createComponent(TextureComponent.class);
        tex.region = Utility.getTextureRegionAsset("playerShip1");
        super.add(tex);

        TranslationComponent pos = engine.createComponent(TranslationComponent.class);
        pos.setPosition(18.0f, 2.0f);
        super.add(pos);

        B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);
        b2d.body = bodyFactory.makeBoxPolyBody(18.0f, 3.0f, 1.5f, 1.5f, BodyFactory.STONE, BodyType.DynamicBody,
                BodyFactory.CATEGORY_PLAYER, BodyFactory.MASK_PLAYER, true);
        super.add(b2d);

        PlayerComponent player = engine.createComponent(PlayerComponent.class);
        super.add(player);

        TypeComponent type = engine.createComponent(TypeComponent.class);
        type.type = TypeComponent.PLAYER;
        super.add(type);

        StateComponent state = engine.createComponent(StateComponent.class);
        state.set(StateComponent.STATE_NORMAL);
        super.add(state);
        add(state);

        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        keyFrames.add(Utility.getTextureRegionAsset("playerShip1"));
        Animation<TextureRegion> ani = new Animation<TextureRegion>(AnimationComponent.FRAME_RATE, keyFrames);

        AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
        aComponent.animations.put(StateComponent.STATE_NORMAL, ani);
        
        keyFrames.clear();
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion1"));        
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion2"));        
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion3"));
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion4"));
        
        keyFrames = new Array<TextureRegion>();
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion1"));
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion2"));
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion3"));
        keyFrames.add(Utility.getTextureRegionAsset("shipExplosion4"));


        ani = new Animation<TextureRegion>(AnimationComponent.FRAME_RATE, keyFrames);
        aComponent.animations.put(StateComponent.STATE_EXPLOSION, ani);
    }
}
