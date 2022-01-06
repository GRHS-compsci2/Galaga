package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.PlayerComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class PlayerEntity extends Entity {
    public void setUp(Engine engine, BodyFactory bodyFactory){
        TextureComponent tex=engine.createComponent(TextureComponent.class);
        tex.region=Utility.getTextureRegionAsset("playerShip1");
        add(tex); 

        TranslationComponent pos=engine.createComponent(TranslationComponent.class);
        pos.setPosition(18.0f,2.0f);
        
        add(pos);

        B2dBodyComponent b2d=engine.createComponent(B2dBodyComponent.class);
        b2d.body = bodyFactory.makeBoxPolyBody(18.0f, 3.0f, 1.5f, 1.5f, BodyFactory.STONE, BodyType.DynamicBody, true);
        add(b2d);

        PlayerComponent player=engine.createComponent(PlayerComponent.class);
        add(player);

        TypeComponent type=engine.createComponent(TypeComponent.class);
        type.type=TypeComponent.PLAYER;
        add(type);

        StateComponent state=engine.createComponent(StateComponent.class);
        state.set(StateComponent.STATE_NORMAL);
        add(state);
    }
}
