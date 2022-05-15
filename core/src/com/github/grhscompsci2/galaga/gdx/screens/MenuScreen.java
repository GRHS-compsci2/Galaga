package com.github.grhscompsci2.galaga.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.gdx.helpers.IGameProcessor.ScreenType;

public class MenuScreen extends BaseDemoScreen {

  public MenuScreen(MyGdxGame game) {
    super(game);
  }

  @Override
  public void update(float delta) {
    Utility.background.render(delta, true);
    super.update(delta);
  }

  private void setupTable() {
    Skin skin = Utility.STATUSUI_SKIN;
    Table table = new Table();
    table.setFillParent(true);
    //table.setDebug(true);
    Label title = new Label("GALAGA", skin);
    TextButton loadGameButton = new TextButton("Start", skin, "small");
    TextButton prefrenceButton = new TextButton("Settings", skin, "small");
    TextButton exitButton = new TextButton("Exit", skin, "small");

    table.add(title).spaceBottom(75).row();
    table.add(loadGameButton).spaceBottom(10).row();
    table.add(prefrenceButton).spaceBottom(10).row();
    table.add(exitButton).spaceBottom(10).row();

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
