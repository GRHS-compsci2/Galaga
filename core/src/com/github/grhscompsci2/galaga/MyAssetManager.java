package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Game;

public class MyAssetManager extends Game{

    Assets myAssets = new Assets();

    @Override
    public void create() {
        myAssets.loadImages();
        myAssets.manager.finishLoading();
        //while(!myAssets.manager.update())
        //  System.out.println(myAssets.manager.getProgress() * 100 + "%");
        
        setScreen(new MyGdxGame());
        
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }
    
    @Override
    public void render(){
        super.render();
    }

    @Override
    public void pause(){
        super.pause();
    }

    @Override
    public void resume(){
        super.resume();
    }

    @Override
    public void dispose(){
        super.dispose();
       // myAssets.dispose();
    }
}
