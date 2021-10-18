package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Entity;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.components.InputComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

public class PlayerEntity extends Entity {
    public PlayerEntity() {
        TextureComponent tex=new TextureComponent();
        //tex.region=Utility.getTextureRegionAsset("player1");
        add(tex);
        add(new TranslationComponent(50.0f,50.0f));
        add(new InputComponent());
    }
    public void setTexture(){
        TextureComponent tex=getComponent(TextureComponent.class);
        tex.region=Utility.getTextureRegionAsset("player1");
    }
}
