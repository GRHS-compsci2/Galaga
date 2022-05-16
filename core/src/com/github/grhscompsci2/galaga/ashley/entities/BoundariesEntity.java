package com.github.grhscompsci2.galaga.ashley.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.CollisionComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class BoundariesEntity extends Entity {

    public void init(PooledEngine engine, BodyFactory bodyFactory, float x, float y, float width, float height) {

        StateComponent sComponent = engine.createComponent(StateComponent.class);
        sComponent.set(StateComponent.STATE_NORMAL);
        super.add(sComponent);

        BodyComponent b2d = engine.createComponent(BodyComponent.class);
        Body body = bodyFactory.makeBoxPolyBody(x, y, width, height, BodyFactory.STONE, BodyType.StaticBody,
                BodyFactory.CATEGORY_ENEMY, BodyFactory.MASK_ENEMY, true);
        body.setUserData(this);
        b2d.setBody(body);
        super.add(b2d);

        CollisionComponent collisionComponent = engine.createComponent(CollisionComponent.class);
        add(collisionComponent);

        TypeComponent typeComponent = engine.createComponent(TypeComponent.class);
        typeComponent.type = TypeComponent.OTHER;
        add(typeComponent);
    }

}