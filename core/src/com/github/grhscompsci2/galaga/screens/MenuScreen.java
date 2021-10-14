package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class MenuScreen extends ScreenAdapter{

    private Stage _stage;
    public MenuScreen(Game game) {


    // Creation Table
    //Initialize Stage and Table for later use
    _stage = new Stage();
    Table table = new Table();
    table.setFillParent(true);


    // It is my job to import a image of the button i want to use
    // I could prob utilize my own class for that because
    // I can't use desktop or else it won't work for everbody
    Image title = new Image(Utility.STATUSUI_TEXTURES.findRegion("galaga_title"));
    TextButton loadGameButton = new TextButton("Start", skin);
    TextButton prefrenceButton = new TextButton("Settings", skin);
    TextButton exitButton = new TextButton("Exit", skin);


        // Figure out what .spaceBottom does pleasse
        // I want to understand why I am doing this
    //Button Locations
    table.add(title).spaceBottom(75).row();
    table.add(loadGameButton).spaceBottom(10).row();
    table.add(prefrenceButton).spaceBottom(10).row();
    table.add(exitButton).spaceBottom(10).row();

    _stage.addActor(table);


    // Add listeners v



    {
        
    }


}





// involves input processors, but I can't make those happen
// untill I HAVE OUR TEXTURES :(
    @Override
    public void show() {
        
        // TODO Auto-generated method stub
       
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        _stage.act(delta);
        _stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        _stage.getViewport().setScreenSize(width, height);
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        _stage.dispose();
    }
    
    
}
