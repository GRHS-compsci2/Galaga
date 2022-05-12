package com.github.grhscompsci2.galaga.ashley.entities.enemies;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.AnimationComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class PurpleBatGalagaEntity extends EnemyEntity {
    public void init(Engine engine, BodyFactory bodyFactory) {
        super.init(engine, bodyFactory, new Vector2(0,0));
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        keyFrames.add(Utility.getTextureRegionAsset("purpleBat1"));
        keyFrames.add(Utility.getTextureRegionAsset("purpleBat2"));

        Animation<TextureRegion> ani = new Animation<TextureRegion>(Utility.ANI_FRAME_RATE, keyFrames);

        AnimationComponent aComponent = engine.createComponent(AnimationComponent.class);
        aComponent.animations.put(StateComponent.STATE_NORMAL, ani);
    }
}
