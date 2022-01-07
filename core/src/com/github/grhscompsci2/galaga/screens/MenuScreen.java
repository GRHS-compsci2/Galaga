package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.MyGdxGame.ScreenType;
import com.github.grhscompsci2.galaga.Utility;

public class MenuScreen extends ScreenAdapter {

    private Stage _stage;
    private MyGdxGame parent;

    public MenuScreen(MyGdxGame game) {
        parent = game;
        _stage = new Stage(new FitViewport(Utility.SCREEN_WIDTH, Utility.SCREEN_HEIGHT, new OrthographicCamera()));
        setupTable();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Utility.background.render(delta, true);
        _stage.draw();
    }

    @Override
    public void dispose() {
        _stage.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(_stage);
         Utility.playMusicAsset(parent, Utility.themeMusic);
        // themeMusic.play();
    }

    @Override
    public void resize(int width, int height) {
        _stage.getViewport().update(width, height);
    }

    private void setupTable() {
        Skin skin = Utility.STATUSUI_SKIN;
        Table table = new Table();
        table.setFillParent(true);

        Label title = new Label("GALAGA", skin);
        TextButton loadGameButton = new TextButton("Start", skin, "small");
        TextButton prefrenceButton = new TextButton("Settings", skin, "small");
        TextButton exitButton = new TextButton("Exit", skin, "small");

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
                parent.setScreen(ScreenType.Arcade);
            }
        });

        prefrenceButton.addListener(new ChangeListener() {

            @Override

            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(ScreenType.Preferences);
            }
        });
    }
}
