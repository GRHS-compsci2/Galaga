package com.github.grhscompsci2.galaga.gdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;

public class PreferencesScreen extends BaseDemoScreen {

  private Label titleLabel;
  private Label volumeMusicLabel;
  private Label volumeSoundLabel;

  public PreferencesScreen(MyGdxGame myGdxGame) {
    super(myGdxGame);
  }
  
  private void setupTable() {
    Table table = new Table();
    table.setFillParent(true);

    // shows debug for preferences menu
    // table.setDebug(true);

    Skin skin = Utility.STATUSUI_SKIN;

    // volume sliders
    final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
    volumeMusicSlider.setValue(game.getPreferenceManager().getStoredFloat("MusicVol"));
    volumeMusicSlider.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        game.getPreferenceManager().updateFloat("MusicVol", volumeMusicSlider.getValue());
        Utility.setFromPrefs(game.getPreferenceManager());
        return false;
      }
    });

    final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
    volumeSoundSlider.setValue(game.getPreferenceManager().getStoredFloat("SoundVol"));
    volumeSoundSlider.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        game.getPreferenceManager().updateFloat("SoundVol", volumeSoundSlider.getValue());
        Utility.setFromPrefs(game.getPreferenceManager());
        return false;
      }
    });

    // on/off
    final CheckBox musicCheckbox = new CheckBox(null, skin);
    musicCheckbox.setChecked(game.getPreferenceManager().getStoredBoolean("Music", true));
    musicCheckbox.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        game.getPreferenceManager().updateBoolean("Music", musicCheckbox.isChecked());
        Utility.setFromPrefs(game.getPreferenceManager());
        return false;
      }
    });

    final CheckBox soundCheckbox = new CheckBox(null, skin);
    soundCheckbox.setChecked(game.getPreferenceManager().getStoredBoolean("Sound", true));
    soundCheckbox.addListener(new EventListener() {
      @Override
      public boolean handle(Event event) {
        game.getPreferenceManager().updateBoolean("Sound", soundCheckbox.isChecked());
        Utility.setFromPrefs(game.getPreferenceManager());
        return false;
      }
    });

    final TextButton backButton = new TextButton("Back", skin, "default");
    backButton.addListener(new ChangeListener() {
      @Override
      public void changed(ChangeEvent event, Actor actor) {
        game.switchScreens(game.getLastScreen());
      }
    });

    titleLabel = new Label("Settings", skin, "small");
    volumeMusicLabel = new Label("Music", skin, "tiny");
    volumeSoundLabel = new Label("SFX", skin, "tiny");

    float width = 100;
    table.add(titleLabel).colspan(3);
    table.row();
    table.add(new Label("", skin)).colspan(3);
    table.row();
    table.add(volumeMusicLabel).center().pad(5);
    table.add(volumeMusicSlider).width(width).pad(5);
    table.add(musicCheckbox).pad(5);
    table.row();
    table.add(volumeSoundLabel).center().pad(5);
    table.add(volumeSoundSlider).width(width).pad(5);
    table.add(soundCheckbox).pad(5).row();
    table.add(new Label("", skin)).colspan(3);
    table.row();
    table.add(backButton).colspan(3);
    stage.addActor(table);
  }

  @Override
  public void update(float delta) {
    Utility.background.render(delta, true);
    super.update(delta);
  }
  
  @Override
  void childInit() {
    setupTable();
  }
}
