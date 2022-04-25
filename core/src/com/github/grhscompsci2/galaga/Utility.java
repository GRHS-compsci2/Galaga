package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Utility {
    public static final AssetManager _assetManager = new AssetManager();
    private static final String TAG = Utility.class.getSimpleName();
    private static InternalFileHandleResolver _filePathResolver = new InternalFileHandleResolver();

    public static final boolean DEBUG_MODE=true;
    private final static String SPRITES_TEXTURE_ATLAS_PATH = "images/galaga.atlas";
    public static TextureAtlas SPRITES_TEXTUREATLAS = new TextureAtlas(SPRITES_TEXTURE_ATLAS_PATH);

    private final static String STATUSUI_TEXTURE_ATLAS_PATH = "skins/galagaSkin.atlas";
    private final static String STATUSUI_SKIN_PATH = "skins/galagaSkin.json";
    public static int SCREEN_WIDTH = 224;
    public static int SCREEN_HEIGHT = 288;
    public static TextureAtlas STATUSUI_TEXTUREATLAS = new TextureAtlas(STATUSUI_TEXTURE_ATLAS_PATH);
    public static Skin STATUSUI_SKIN = new Skin(Gdx.files.internal(STATUSUI_SKIN_PATH), STATUSUI_TEXTUREATLAS);
    public static final String themeMusic = "music/galagaTheme.mp3";
    public static final String scoreMusic = "music/nameEntry.mp3";
    public static final String shotFired = "music/shotFired.mp3";
    public static final String galagaAttack = "music/galagaAttack.mp3";
    public static final String galagaExplosion1 = "music/galagaExplosion1.mp3";
    public static final String galagaExplosion2 = "music/galagaExplosion2.mp3";
    public static final float PPM = 8;

    public static float SCREEN_WIDTH_METERS=SCREEN_WIDTH/PPM;
    public static float SCREEN_HEIGHT_METERS=SCREEN_HEIGHT/PPM;
    public static Background background = new Background();
    private static Music music;
    private static Sound sound;
    public static boolean frameUpdate;

    public static void unloadAsset(String assetFilenamePath) {
        if (_assetManager.isLoaded(assetFilenamePath)) {
            _assetManager.unload(assetFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Asset is not loaded; Nothing to unload: " + assetFilenamePath);
        }
    }

    public static float loadCompleted() {
        return _assetManager.getProgress();
    }

    public static int numberAssetsQueued() {
        return _assetManager.getQueuedAssets();
    }

    public static boolean updateAssetLoading() {
        return _assetManager.update();
    }

    public static boolean isAssetLoaded(String fileName) {
        return _assetManager.isLoaded(fileName);
    }

    public static void loadSoundAsset(String soundAssetPath) {

        if (soundAssetPath == null || soundAssetPath.isEmpty()) {
            return;
        }

        if (_assetManager.isLoaded(soundAssetPath)) {
            return;
        }

        // loads the asset
        if (_filePathResolver.resolve(soundAssetPath).exists()) {
            _assetManager.setLoader(Sound.class, new SoundLoader(_filePathResolver));
            _assetManager.load(soundAssetPath, Sound.class);
            _assetManager.finishLoadingAsset(soundAssetPath);
            Gdx.app.debug(TAG, "Sound loaded!: " + soundAssetPath);
        } else {
            Gdx.app.debug(TAG, "Sound is not found: " + soundAssetPath);
        }
    }

    public static Sound getSoundAsset(String soundAssetPath) {
        Sound sound = null;
        if (_assetManager.isLoaded(soundAssetPath)) {
            sound = _assetManager.get(soundAssetPath, Sound.class);
        } else {
            Gdx.app.debug(TAG, "Sound is not loaded: " + soundAssetPath);
        }
        return sound;
    }

    public static void playSoundAsset(MyGdxGame parent, String soundAssetPath) {
        sound = getSoundAsset(soundAssetPath);
        float vol = 0;
        if (parent.getPreferences().isMusicEnabled()) {
            vol = parent.getPreferences().getMusicVolume();
        }
        Gdx.app.debug(TAG, "Volume:" + vol);
        sound.play(vol);
    }

    public static void loadMusicAsset(String musicAssetPath) {
        if (musicAssetPath == null || musicAssetPath.isEmpty()) {
            return;
        }
        if (_assetManager.isLoaded(musicAssetPath)) {
            return;
        }
        // load asset
        if (_filePathResolver.resolve(musicAssetPath).exists()) {
            _assetManager.setLoader(Music.class, new MusicLoader(_filePathResolver));
            _assetManager.load(musicAssetPath, Music.class);
            _assetManager.finishLoadingAsset(musicAssetPath);
            Gdx.app.debug(TAG, "Music loaded: " + musicAssetPath);
        } else {
            Gdx.app.debug(TAG, "Music not found: " + musicAssetPath);
        }

    }

    public static Music getMusicAsset(String musicAssetPath) {
        Music music = null;

        if (_assetManager.isLoaded(musicAssetPath)) {
            music = _assetManager.get(musicAssetPath, Music.class);
        } else {
            Gdx.app.debug(TAG, "Music is not loaded: " + musicAssetPath);
        }
        return music;
    }

    public static void playMusicAsset(MyGdxGame parent, String musicAssetPath) {
        music = getMusicAsset(musicAssetPath);
        float vol = 0;
        if (parent.getPreferences().isMusicEnabled()) {
            vol = parent.getPreferences().getMusicVolume();
        }
        Gdx.app.debug(TAG, "Volume:" + vol);
        music.setVolume(vol);
        music.play();
    }

    public static void updateVolume(MyGdxGame parent) {
        float vol = 0;
        if (parent.getPreferences().isMusicEnabled()) {
            vol = parent.getPreferences().getMusicVolume();
        }
        music.setVolume(vol);
    }

    public static void loadTextureAtlasAsset() {
        if (SPRITES_TEXTURE_ATLAS_PATH == null || SPRITES_TEXTURE_ATLAS_PATH.isEmpty()) {
            return;
        }

        if (_assetManager.isLoaded(SPRITES_TEXTURE_ATLAS_PATH)) {
            return;
        }

        // load asset
        if (_filePathResolver.resolve(SPRITES_TEXTURE_ATLAS_PATH).exists()) {
            Gdx.app.debug(TAG, "Loading Texture!: " + SPRITES_TEXTURE_ATLAS_PATH);
            _assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(_filePathResolver));
            _assetManager.load(SPRITES_TEXTURE_ATLAS_PATH, TextureAtlas.class);
            // Until we add loading screen, just block until we load the map
            // _assetManager.finishLoadingAsset(SPRITES_TEXTURE_ATLAS_PATH);
        } else {
            Gdx.app.debug(TAG, "Texture doesn't exist!: " + SPRITES_TEXTURE_ATLAS_PATH);
        }
    }

    public static TextureRegion getTextureRegionAsset(String textureName) {
        TextureRegion region = null;

        // once the asset manager is done loading
        if (_assetManager.isLoaded(SPRITES_TEXTURE_ATLAS_PATH)) {
            region = _assetManager.get(SPRITES_TEXTURE_ATLAS_PATH, TextureAtlas.class).findRegion(textureName);
            Gdx.app.debug(TAG, "Region: " + region);

        } else {
            // System.out.println("Texture is not loaded: " + SPRITES_TEXTURE_ATLAS_PATH);
            Gdx.app.debug(TAG, "Texture is not loaded: " + SPRITES_TEXTURE_ATLAS_PATH);
        }

        return region;
    }

    public static void loadAllMusicAsset() {
        loadMusicAsset(themeMusic);
        loadMusicAsset(scoreMusic);
    }

    public static void loadAllSoundAsset() {
        loadSoundAsset(shotFired);
        loadSoundAsset(galagaAttack);
        loadSoundAsset(galagaExplosion1);
        loadSoundAsset(galagaExplosion2);
    }

    public static float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

}
