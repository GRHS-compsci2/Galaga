package com.github.grhscompsci2.galaga.entities;

import com.github.grhscompsci2.galaga.components.TranslationComponent;

public class GalagaEntity {
    private TranslationComponent translationComponent;
    // private AnimationComponent animationComponent;
    // private HealthComponent healthComponent;
    
    public GalagaEntity() {
        this.translationComponent = new TranslationComponent();
        // this.animationComponent = new AnimationComponent();
        // this.healthComponent = new HealthComponent();
    }
}
