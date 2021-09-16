package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

    public static final AssetManager manager = new AssetManager();

    // Textures

    public final String badLogicImage = "badlogic.jpg";

    public void loadImages() {
        manager.load(badLogicImage, Texture.class);
    }

    public String getImage() {
        return badLogicImage;
    }

    // Sounds
    public final String explosionSound = "sounds/explosion.wav";
    public final String boingSound = "sounds/boing.wav";
    public final String pingSound = "sounds/ping.wav";

    public void loadSounds() {
        manager.load(explosionSound, Sound.class);
        manager.load(boingSound, Sound.class);
        manager.load(pingSound, Sound.class);
    }

    // Fonts
    public final String visfont = "font.visitor.fnt";

    public void loadFonts() {
        manager.load(visfont, BitmapFont.class);
    }

    // Skins
    public final String skin = "uiskin.json";

    public void loadSkin() {
        SkinParameter params = new SkinParameter("uiskin.atlas");
        manager.load(skin, Skin.class, params);
    }

    public void dispose() {
        manager.dispose();
    }

}
