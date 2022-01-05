package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;

public class PreferencesScreen extends ScreenAdapter {

    private MyGdxGame parent;
    private Stage stage;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public PreferencesScreen(MyGdxGame myGdxGame) {
        parent = myGdxGame;
        stage = new Stage(new FitViewport(Utility.SCREEN_WIDTH, Utility.SCREEN_HEIGHT, new OrthographicCamera()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.clear();

        Table table = new Table();
        table.setFillParent(true);

        // shows debug for preferences menu
        // table.setDebug(true);
        stage.addActor(table);

        // temporary
        Skin skin = Utility.STATUSUI_SKIN;

        // volume sliders
        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });

        // on/off
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        final CheckBox soundCheckbox = new CheckBox(null, skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEffectsEnabled());
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        final TextButton backButton = new TextButton("Back", skin,"small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.getlastScreen());
            }
        });

        titleLabel = new Label("Settings", skin,"small");
        volumeMusicLabel = new Label("Music", skin, "tiny");
        volumeSoundLabel = new Label("Sounds", skin, "tiny");
        musicOnOffLabel = new Label("Mute", skin, "tiny");
        soundOnOffLabel = new Label("Mute", skin, "tiny");

        table.add(titleLabel).colspan(2);
        table.row().height(24.2f);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider).pad(10, 0, 0, 10);
        table.row().height(24.2f);
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox).pad(10, 0, 0, 10);
        table.row().height(24.2f);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider).pad(10, 0, 0, 10);
        table.row().height(24.2f);
        table.add(soundOnOffLabel).left();
        table.add(soundCheckbox).pad(10, 0, 0, 10).row();
        table.add(backButton).colspan(2);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Utility.background.render(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}
