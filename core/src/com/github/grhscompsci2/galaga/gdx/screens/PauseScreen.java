package com.github.grhscompsci2.galaga.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.gdx.helpers.IGameProcessor.ScreenType;

public class PauseScreen extends BaseDemoScreen {

  public PauseScreen(MyGdxGame game) {
    super(game);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Utility.background.render(delta, true);
  }

  private void setupTable() {
    Skin skin = Utility.STATUSUI_SKIN;
    Table table = new Table();
    table.setFillParent(true);
    Label title = new Label("Pause", skin);
    TextButton loadGameButton = new TextButton("Resume", skin, "small");
    TextButton menuButton = new TextButton("Menu", skin, "small");
    TextButton prefrenceButton = new TextButton("Settings", skin, "small");
    TextButton exitButton = new TextButton("Exit", skin, "small");

    table.add(title).spaceBottom(50).row();
    table.add(loadGameButton).spaceBottom(10).row();
    table.add(menuButton).spaceBottom(10).row();
    table.add(prefrenceButton).spaceBottom(10).row();
    table.add(exitButton).spaceBottom(10).row();

    menuButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.switchScreens(ScreenType.Menu);
      }
    });
    exitButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        Gdx.app.exit();
      }
    });

    loadGameButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.switchScreens(ScreenType.Arcade);
      }
    });

    prefrenceButton.addListener(new ChangeListener() {

      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.switchScreens(ScreenType.Preferences);
      }
    });
    stage.addActor(table);
  }

  @Override
  void childInit() {
    setupTable();

  }
}
