package com.github.grhscompsci2.galaga.entities;

<<<<<<< HEAD
=======
import com.badlogic.ashley.core.Entity;
import com.github.grhscompsci2.galaga.Utility;
>>>>>>> a4bf55d (Got the groundwork for the RenderingSystem)
import com.github.grhscompsci2.galaga.components.InputComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

<<<<<<< HEAD
public class PlayerEntity {
    TranslationComponent translationComponent;
    InputComponent inputComponent;
    TextureComponent textureComponent;

    /**
     * @param translationComponent
     * @param inputComponent
     * @param textureComponent
     */
    public PlayerEntity() {
        this.translationComponent = new TranslationComponent();
        this.inputComponent = new InputComponent();
        this.textureComponent = new TextureComponent();
    }

=======
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
>>>>>>> a4bf55d (Got the groundwork for the RenderingSystem)
}
