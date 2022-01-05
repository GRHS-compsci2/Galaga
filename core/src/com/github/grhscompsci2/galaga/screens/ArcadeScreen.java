package com.github.grhscompsci2.galaga.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.b2d.B2dContactListener;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.entities.BeeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.BoundariesEntity;
import com.github.grhscompsci2.galaga.entities.ButterflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.GreenBatGalagaEntity;
import com.github.grhscompsci2.galaga.entities.LevelEntity;
import com.github.grhscompsci2.galaga.entities.LivesEntity;
import com.github.grhscompsci2.galaga.entities.PlayerEntity;
import com.github.grhscompsci2.galaga.systems.AnimationSystem;
import com.github.grhscompsci2.galaga.systems.CollisionSystem;
import com.github.grhscompsci2.galaga.systems.PhysicsDebugSystem;
import com.github.grhscompsci2.galaga.systems.PhysicsSystem;
import com.github.grhscompsci2.galaga.systems.PlayerControlSystem;
import com.github.grhscompsci2.galaga.systems.RenderingSystem;

public class ArcadeScreen extends ScreenAdapter {

	private MyGdxGame parent;

	private PooledEngine engine;
	private OrthographicCamera cam;
	private BodyFactory bodyFactory;
	private World world;
	private KeyboardController controller;
	private Stage arcadeStage;
	private int score;
	private String yourScoreName;
	private Music scoreMusic;

	public ArcadeScreen(MyGdxGame myGdxGame) {
		parent = myGdxGame;
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new B2dContactListener(parent));
		bodyFactory = BodyFactory.getInstance(world);
		controller = new KeyboardController();
		arcadeStage = new Stage(new FitViewport(Utility.SCREEN_WIDTH, Utility.SCREEN_HEIGHT, new OrthographicCamera()));

		RenderingSystem renderingSystem = new RenderingSystem(arcadeStage.getBatch());
		cam = renderingSystem.getCamera();
		arcadeStage.getBatch().setProjectionMatrix(cam.combined);
		engine = new PooledEngine();

		// add all the relevant systems our engine should run
		engine.addSystem(renderingSystem);
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem(controller, parent));

	}

	private void createLives() {
		float y = 1.5f;
		for (float r = 4.0f; r <= 5.5f; r += 1.5) {
			LivesEntity life = new LivesEntity(r, y);
			life.init(engine, bodyFactory);
			engine.addEntity(life);
		}
	}

	// create the basic starting formation (Without animation)
	// Coordinates may require readjustment
	private void createFormation1() {

		for (float y = 16.0f; y <= 17.75f; y += 1.75f) {
			for (float x = 8.0f; x < 28.0f; x += 2.0f) {
				BeeGalagaEntity bee = new BeeGalagaEntity(x, y);
				bee.init(engine, bodyFactory);
				engine.addEntity(bee);
			}
		}

		for (float y = 19.5f; y <= 21.25f; y += 1.75f) {
			for (float x = 10.0f; x < 26.0f; x += 2.0f) {
				ButterflyGalagaEntity bf = new ButterflyGalagaEntity(x, y);
				bf.init(engine, bodyFactory);
				engine.addEntity(bf);
			}
		}

		for (float x = 14.0f; x < 22.0f; x += 2.0f) {
			float y = 23.0f;
			GreenBatGalagaEntity gb = new GreenBatGalagaEntity(x, y);
			gb.init(engine, bodyFactory);
			engine.addEntity(gb);
		}

		for (float x = 14.0f; x < 22.0f; x += 2.0f) {
			float y = 23.0f;
			GreenBatGalagaEntity gb = new GreenBatGalagaEntity(x, y);
			gb.init(engine, bodyFactory);
			engine.addEntity(gb);
		}

		for (float x = 0f; x <= 28.0f; x += 28.0f) {
			float y = 2.5f;
			BoundariesEntity be = new BoundariesEntity(x, y);
			be.init(engine, bodyFactory);
			engine.addEntity(be);
		}
	}
float resumeTime=0;
	@Override
	public void show() {
		resumeTime=0;

		Gdx.input.setInputProcessor(controller);
		scoreMusic = Utility.getMusicAsset(Utility.scoreMusic);
		scoreMusic.play();
		Skin skin = Utility.STATUSUI_SKIN;

		Table table = new Table();
		table.setFillParent(true);
		// table
		int score = 100;
		Label scoreLabel = new Label("Score: " + score, skin, "tiny");
		table.add(scoreLabel).left().width(Utility.SCREEN_WIDTH).row();
		table.add().width(Utility.SCREEN_WIDTH).height(Utility.SCREEN_HEIGHT).row();

		PlayerEntity player = new PlayerEntity();
		player.setUp(engine, bodyFactory);

		engine.addEntity(player);
		createFormation1();
		createLives();

		LevelEntity le = new LevelEntity();
		le.init(engine, bodyFactory);
		engine.addEntity(le);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Utility.background.render(delta);
		if(resumeTime<1.5){
			resumeTime+=delta;
			}
			else{
				
				engine.update(delta);
				arcadeStage.draw();	
			}
	}

	@Override
	public void resize(int width, int height) {
		arcadeStage.getViewport().update(width, height);
	}

	@Override
	public void dispose() {
		arcadeStage.dispose();
	}
}
