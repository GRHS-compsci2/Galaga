package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

public class GalagaEntity extends Entity{
    /*public void setTexture(Engine engine){
        TextureComponent tex=engine.createComponent(TextureComponent.class);
        tex.region=Utility.getTextureRegionAsset("greenBat1");
        add(tex); 
    }*/
    public void setAnimation(Engine engine){
        Array<TextureRegion> keyFrames=new Array<TextureRegion>();
        keyFrames.add(Utility.getTextureRegionAsset("greenBat1"));
        keyFrames.add(Utility.getTextureRegionAsset("greenBat2"));
        Animation <TextureRegion> ani=new Animation<TextureRegion>(AnimationComponent.FRAME_RATE, keyFrames);
        AnimationComponent aComponent=engine.createComponent(AnimationComponent.class);
        aComponent.animations.put(StateComponent.STATE_NORMAL, ani);
        add(aComponent);
        TextureComponent tex=engine.createComponent(TextureComponent.class);
        tex.region=Utility.getTextureRegionAsset("greenBat1");
        add(tex); 
        StateComponent sComponent=engine.createComponent(StateComponent.class);
        sComponent.isLooping=true;
        sComponent.set(StateComponent.STATE_NORMAL);
        add(sComponent);
    
    }
    public void setStart(Engine engine){
        TranslationComponent pos=engine.createComponent(TranslationComponent.class);
        pos.setPosition(18.0f,18.0f);
        //pos.scale=new Vector2(10.0f,10.0f);
        add(pos);
    }
}
