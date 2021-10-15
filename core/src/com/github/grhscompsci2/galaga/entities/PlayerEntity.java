package com.github.grhscompsci2.galaga.entities;

import com.github.grhscompsci2.galaga.components.InputComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

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

}
