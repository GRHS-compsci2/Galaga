package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Utility {
    public static final AssetManager _assetManager=new AssetManager(); 
    private static final String TAG=Utility.class.getSimpleName();
    private static InternalFileHandleResolver _filePathResolver=new InternalFileHandleResolver();

    // need to add file paths for the textures
    private final static String STATUSUI_TEXTURE_ATLAS_PATH = null;
    private final static String STATUSUI_SKIN_PATH = null;
    private final static String ITEMS_TEXTURE_ATLAS_PATH = null;
    private final static String ITEMS_SKIN_PATH = null;

    public static TextureAtlas STATUSUI_TEXTUREATLAS=new TextureAtlas(STATUSUI_TEXTURE_ATLAS_PATH);
    public static TextureAtlas ITEMS_TEXTUREATLAS=new TextureAtlas(ITEMS_TEXTURE_ATLAS_PATH);
    public static Skin STATUSUI_SKIN=new Skin(Gdx.files.internal(STATUSUI_SKIN_PATH),STATUSUI_TEXTUREATLAS);

    public static void unloadAsset(String assetFilenamePath) {
        if(_assetManager.isLoaded(assetFilenamePath)) {
            _assetManager.unload(assetFilenamePath);
        }
        else {
            Gdx.app.debug(TAG, "Asset is not loaded; Nothing to unload: "+assetFilenamePath);
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

    public static void loadSoundAsset (String soundAssetPath) {

        if (soundAssetPath==null||soundAssetPath.isEmpty()) {
            return;
        } 

        if (_assetManager.isLoaded(soundAssetPath)) {
            return;
        }

        //loads the asset
        if(_filePathResolver.resolve(soundAssetPath).exists()) {
            _assetManager.setLoader(Sound.class, new SoundLoader(_filePathResolver));
            _assetManager.load(soundAssetPath, Sound.class);
            _assetManager.finishLoadingAsset(soundAssetPath);
            Gdx.app.debug(TAG, "Sound loaded!: "+soundAssetPath);
        } else {
            Gdx.app.debug(TAG, "Sound is not of exsistence: "+soundAssetPath);
        }
    }

    public static Sound getSoundAsset(String soundAssetPath) {
        Sound sound= null;
        if(_assetManager.isLoaded(soundAssetPath)) {
            sound=_assetManager.get(soundAssetPath,Sound.class);
        } else {
            Gdx.app.debug(TAG, "Sound is not loaded: "+soundAssetPath);
        }
        return sound;
    }

    public static void loadMusicAsset(String musicAssetPath) {
        if(musicAssetPath==null||musicAssetPath.isEmpty()) {
            return;
        }
        if(_assetManager.isLoaded(musicAssetPath)) {
            return;
        }
        //load asset
        if(_filePathResolver.resolve(musicAssetPath).exists()) {
            _assetManager.setLoader(Music.class, new MusicLoader(_filePathResolver));
            _assetManager.load(musicAssetPath, Music.class);
            _assetManager.finishLoadingAsset(musicAssetPath);
            Gdx.app.debug(TAG, "Music loaded: "+musicAssetPath);
        } else {
            Gdx.app.debug(TAG, "Music not loaded: "+musicAssetPath);
        }

    }

    public static Music getMusicAsset(String musicAssetPath) {
        Music music= null;

        if(_assetManager.isLoaded(musicAssetPath)) {
            music=_assetManager.get(musicAssetPath, Music.class);
        } else {
            Gdx.app.debug(TAG, "Music is not loaded: "+musicAssetPath);
        }
        return music;
    }
    public static void loadTextureAsset(String textureFilenamePath){
		if( textureFilenamePath == null || textureFilenamePath.isEmpty() ){
			return;
		}

		if( _assetManager.isLoaded(textureFilenamePath) ){
			return;
		}

		//load asset
		if( _filePathResolver.resolve(textureFilenamePath).exists() ){
			_assetManager.setLoader(Texture.class, new TextureLoader(_filePathResolver));
			_assetManager.load(textureFilenamePath, Texture.class);
			//Until we add loading screen, just block until we load the map
			_assetManager.finishLoadingAsset(textureFilenamePath);
		}
		else{
			Gdx.app.debug(TAG, "Texture doesn't exist!: " + textureFilenamePath );
		}
	}

    public static Texture getTextureAsset(String textureFilenamePath){
		Texture texture = null;

		// once the asset manager is done loading
		if( _assetManager.isLoaded(textureFilenamePath) ){
			texture = _assetManager.get(textureFilenamePath,Texture.class);
		} else {
			Gdx.app.debug(TAG, "Texture is not loaded: " + textureFilenamePath );
		}

		return texture;
	}
}
