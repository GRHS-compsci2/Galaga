package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.MyGdxGame.ScreenType;

public class PreferencesScreen extends ScreenAdapter {

     private MyGdxGame parent;
    private Stage stage;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private SpriteBatch batch;

    public PreferencesScreen(MyGdxGame myGdxGame) {
        parent=myGdxGame;
        stage=new Stage(new ScreenViewport());
        batch=new SpriteBatch();
    }

    @Override
    public void show() {
       // TODO Auto-generated method stub
       Gdx.input.setInputProcessor(stage);

        stage.clear();

        Table table=new Table();
        table.setFillParent(true);
        
        // shows debug for preferences menu
        // table.setDebug(true);
            table.addActor(table);

            // temporary
            Skin skin=new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        // volume sliders
        final Slider volumeMusicSlider=new Slider(0f, 1f, 0.1f, false, skin);
            volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
            volumeMusicSlider.addListener(new EventListener() {  
                @Override
                public boolean handle(Event event) {
                    parent.getPreferences().setMusicVolume(volumeMusicSlider.getValue());
                    return false;
                }
            });

        final Slider volumeSoundSlider=new Slider(0f, 1f, 0.1f, false, skin);
            volumeSoundSlider.setValue(parent.getPreferences().getSoundVolume());
            volumeSoundSlider.addListener(new EventListener(){
                @Override
                public boolean handle(Event event) {
                    parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                    return false;
                }
            });

            // on/off
        final CheckBox musicCheckbox=new CheckBox(null, skin);
        musicCheckbox.setChecked(parent.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled=musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled(enabled);
                return false;
            }
        });

        final CheckBox soundCheckbox=new CheckBox(null, skin);
        soundCheckbox.setChecked(parent.getPreferences().isSoundEnabled());
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled=soundCheckbox.isChecked();
                parent.getPreferences().setSoundEnabled(enabled);
                return false;
            }
        });

        final TextButton backButton=new TextButton("Back", skin, "small");
        backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				parent.setScreen(parent.getScreenType(ScreenType.Menu));				
			}
		});

        titleLabel=new Label("Preferences", skin);
        volumeMusicLabel=new Label(null, skin);
        volumeSoundLabel=new Label(null, skin);
        musicOnOffLabel=new Label(null, skin);
        soundOnOffLabel=new Label(null, skin);

        table.add(titleLabel).colspan(2);
        table.row();
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider).pad(10, 0, 0, 10);
        table.row();
        table.add(musicOnOffLabel).left();
        table.add(musicCheckbox).pad(10, 0, 0, 10);
        table.row();
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider).pad(10, 0, 0, 10);
        table.row();
        table.add(soundOnOffLabel).left();
        table.add(soundCheckbox).pad(10, 0, 0, 10);

    }

    @Override
    public void render(float delta) {

        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Utility.background.render(delta);
        batch.begin();
        //_stage.act(delta);
        stage.draw(); 
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

        
    }

    @Override
    public void pause() {

        
    }

    @Override
    public void resume() {

        
    }

    @Override
    public void hide() {
     
        
    }

    @Override
    public void dispose() {
        
    }
    


}
