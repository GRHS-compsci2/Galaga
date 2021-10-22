package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.grhscompsci2.galaga.Background;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.MyGdxGame.ScreenType;

public class MenuScreen extends ScreenAdapter {

    private Stage _stage;
    private SpriteBatch batch;
    private MyGdxGame parent;

    
    public MenuScreen(MyGdxGame game) {
        parent=game;
        batch=new SpriteBatch();
        // Creation Table
        // Initialize Stage and Table for later use
        Skin skin=Utility.STATUSUI_SKIN;
        _stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);

        // It is my job to import a image of the button i want to use
        // I could prob utilize my own class for that because
        // I can't use desktop or else it won't work for everbody

        /**
         * We made a skin, then set it up in the utility class. The labels and text buttons,
         * as well as a checkbox and slider were created there.
         */
        Label title = new Label("GALAGA",skin);
        TextButton loadGameButton = new TextButton("Start", skin);
        TextButton prefrenceButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Figure out what .spaceBottom does pleasse
        // I want to understand why I am doing this
        /**
         * .spaceBottom is a method that tells the table how much space to put below the text.
         * For instance, here we are inserting 75 pixels under the title to give some seperation 
         * between it and the menu options.
         */
        // Button Locations
        table.add(title).spaceBottom(75).row();
        table.add(loadGameButton).spaceBottom(10).row();
        table.add(prefrenceButton).spaceBottom(10).row();
        table.add(exitButton).spaceBottom(10).row();

        _stage.addActor(table);

        // Add listeners v

    }


    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Utility.background.render(delta);
        batch.begin();
        //_stage.act(delta);
        _stage.draw();
        batch.end();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        _stage.dispose();
        batch.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_stage);

        Skin skin=Utility.STATUSUI_SKIN;
        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("GALAGA",skin);
        TextButton loadGameButton = new TextButton("Start", skin);
        TextButton prefrenceButton = new TextButton("Settings", skin);
        TextButton exitButton = new TextButton("Exit", skin);


        table.add(title).spaceBottom(75).row();
        table.add(loadGameButton).spaceBottom(10).row();
        table.add(prefrenceButton).spaceBottom(10).row();
        table.add(exitButton).spaceBottom(10).row();

        _stage.addActor(table);

        exitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();				
			}
		});

        loadGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.setScreen(parent.getScreenType(ScreenType.Arcade));				
			}
		});


        prefrenceButton.addListener(new ChangeListener() {
			private Game parent;

            @Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.setScreen(parent.getScreenType(ScreenType.Preferences));				
			}
		});
        

    }

}
