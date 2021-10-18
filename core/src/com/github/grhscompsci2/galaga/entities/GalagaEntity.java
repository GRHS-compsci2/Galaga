package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

public class GalagaEntity extends Entity{
    public void setTexture(Engine engine){
        TextureComponent tex=engine.createComponent(TextureComponent.class);
        tex.region=Utility.getTextureRegionAsset("greenBat1");
        add(tex); 
    }
    public void setStart(Engine engine){
        TranslationComponent pos=engine.createComponent(TranslationComponent.class);
        pos.setPosition(18.0f,18.0f);
        //pos.scale=new Vector2(10.0f,10.0f);
        add(pos);
    }
}
