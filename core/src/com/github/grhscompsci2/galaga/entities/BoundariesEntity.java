package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

public class BoundariesEntity extends Entity {

    float x;
    float y;
    float s1;
    float s2;

    public BoundariesEntity(float x, float y, float s1, float s2) {
        this.x = x;
        this.y = y;
        this.s1 = s1;
        this.s2 = s2;
    }

    public void init(PooledEngine engine, BodyFactory bodyFactory) {

        StateComponent sComponent = engine.createComponent(StateComponent.class);
        sComponent.isLooping = true;
        sComponent.set(StateComponent.STATE_NORMAL);
        super.add(sComponent);

        TranslationComponent pos = engine.createComponent(TranslationComponent.class);
        pos.setPosition(x, y);
        super.add(pos);

        B2dBodyComponent b2d = engine.createComponent(B2dBodyComponent.class);
        b2d.body = bodyFactory.makeBoxPolyBody(x, y, s1, s2, BodyFactory.STONE, BodyType.StaticBody,
                BodyFactory.CATEGORY_MONSTER, BodyFactory.MASK_MONSTER, true);
        super.add(b2d);
    }

}