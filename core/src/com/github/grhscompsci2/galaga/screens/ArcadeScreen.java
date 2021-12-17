package com.github.grhscompsci2.galaga.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.EnemyFormation;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.b2d.B2dContactListener;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.entities.BeeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.ButterflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.GreenBatGalagaEntity;
import com.github.grhscompsci2.galaga.entities.LevelEntity;
import com.github.grhscompsci2.galaga.entities.LivesEntity;
import com.github.grhscompsci2.galaga.entities.PlayerEntity;
import com.github.grhscompsci2.galaga.systems.AnimationSystem;
import com.github.grhscompsci2.galaga.systems.CollisionSystem;
import com.github.grhscompsci2.galaga.systems.EnemySystem;
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
	private Label readyLabel;
	private Label p1ScoreLabel;
	private Label p2ScoreLabel;
	private float resumeTime;
	private float blinkTime;
	private boolean rest;

	/**
	 * Constructor for ArcadeScreen. Sets up Box 2D world. Sets up keyboard. Sets up
	 * stage. Adds all systems to the engine.
	 * 
	 * @param myGdxGame - the parent game
	 */
	public ArcadeScreen(MyGdxGame myGdxGame) {
		parent = myGdxGame;
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new B2dContactListener(parent));
		bodyFactory = BodyFactory.getInstance(world);
		controller = new KeyboardController();
		arcadeStage = new Stage(new FitViewport(Utility.SCREEN_WIDTH, Utility.SCREEN_HEIGHT, new OrthographicCamera()));
		EnemyFormation.init();
		RenderingSystem renderingSystem = new RenderingSystem(arcadeStage.getBatch());
		cam = renderingSystem.getCamera();
		arcadeStage.getBatch().setProjectionMatrix(cam.combined);
		setUpTable();
		engine = new PooledEngine();

		// add all the relevant systems our engine should run
		engine.addSystem(renderingSystem);
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem(controller, parent));
		engine.addSystem(new EnemySystem());
	}

	@Override
	public void show() {
		// initialize variables to control the delay when we start
		resumeTime = 0;
		blinkTime = 0;
		// rest will be true when we are delaying, false when we are active
		rest = true;

		Gdx.input.setInputProcessor(controller);
		Utility.playMusicAsset(parent, Utility.scoreMusic);
		// scoreMusic.play();

		// needs to be fixed. States for the game should add all these entities
		PlayerEntity player = new PlayerEntity();
		player.setUp(engine, bodyFactory);
		engine.addEntity(player);

		// needs to be fixed. States for the game should add all these entities
		createFormation1();
		createLives();

		// needs to be fixed. States for the game should add all these entities
		LevelEntity le = new LevelEntity();
		le.init(engine, bodyFactory);
		engine.addEntity(le);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// if we are still delaying
		if (rest) {
			// blink the readyLabel
			pauseAndBlink(delta);
			// do not advance the engine
			delta = 0;
		}

		Utility.background.render(delta, rest);
		engine.update(delta);
		arcadeStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		arcadeStage.getViewport().update(width, height);
	}

	@Override
	public void dispose() {
		arcadeStage.dispose();
	}

	/**
	 * This method will blink the readyLabel every .25 seconds and end the delay
	 * when 1.5 seconds are up
	 * 
	 * @param delta the amount of elapsed time in seconds.
	 */
	private void pauseAndBlink(float delta) {
		// .25 really should be a constant in the utility class
		if (blinkTime < .25) {
			blinkTime += delta;
		} else {
			blinkTime = 0;
			// swap between "" and "Ready?"
			if (readyLabel.getText().toString().equals("")) {
				readyLabel.setText("Ready?");
			} else {
				readyLabel.setText("");
			}
		}
		// 1.5 should really be a constant in the utility class.
		if (resumeTime < 1.5) {
			// if we are not done delaying
			resumeTime += delta;
		} else {
			// stop the delay
			rest = false;
		}
	}

	/**
	 * This method will set up the table to add to the stage
	 */
	private void setUpTable() {
		Skin skin = Utility.STATUSUI_SKIN;

		Table table = new Table();
		// table.setDebug(true);
		table.setFillParent(true);
		// table
		int score = 100;

		Label p1Label = new Label("1UP:", skin, "tinyRed");
		p1Label.setAlignment(Align.center);
		p1ScoreLabel = new Label(score + "", skin, "tiny");
		p1ScoreLabel.setAlignment(Align.center);
		Label highLabel = new Label("High Score", skin, "tinyRed");
		highLabel.setAlignment(Align.center);
		Label highScoreLabel = new Label(score + "", skin, "tiny");
		highScoreLabel.setAlignment(Align.center);
		// This space is reserved for when we implement two player mode
		Label p2Label = new Label("", skin, "tinyRed");
		p2Label.setAlignment(Align.center);
		p2ScoreLabel = new Label("", skin, "tiny");
		p2ScoreLabel.setAlignment(Align.center);
		readyLabel = new Label("Ready?", skin, "tinyCyan");
		table.add(p1Label).center();
		table.add(highLabel).center();
		table.add(p2Label).center().row();
		table.add(p1ScoreLabel).center().width(Utility.SCREEN_WIDTH / 3);
		table.add(highScoreLabel).width(Utility.SCREEN_WIDTH / 3);
		table.add(p2ScoreLabel).width(Utility.SCREEN_WIDTH / 3).row();
		table.add(readyLabel).height(Utility.SCREEN_HEIGHT - p1ScoreLabel.getHeight() * 2).colspan(3).row();
		arcadeStage.addActor(table);
	}

	/**
	 * This method creates the entities for the lives. Should be controlled by the
	 * PlayerSystem
	 */
	private void createLives() {
		float y = 1.5f;
		for (float r = 4.0f; r <= 5.5f; r += 1.5) {
			LivesEntity life = new LivesEntity(r, y);
			life.init(engine, bodyFactory);
			engine.addEntity(life);
		}
	}

	/**
	 * create the basic starting formation (Without animation). Coordinates may
	 * require readjustment. Eventually we will update this to "level start", where
	 * the entities are created a the start of the level and their paths/order are
	 * set
	 */
	private void createFormation1() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < EnemyFormation.formation[i].length; j++) {
				GreenBatGalagaEntity gb = new GreenBatGalagaEntity(EnemyFormation.formation[i][j].x,
						EnemyFormation.formation[i][j].y);
				gb.init(engine, bodyFactory);
				engine.addEntity(gb);
			}
		}
		for (int i = 2; i < 4; i++) {
			for (int j = 0; j < EnemyFormation.formation[i].length; j++) {
				ButterflyGalagaEntity bf = new ButterflyGalagaEntity(EnemyFormation.formation[i][j].x,
						EnemyFormation.formation[i][j].y);
				bf.init(engine, bodyFactory);
				engine.addEntity(bf);
			}
		}
		for (int i = 4; i < 6; i++) {
			for (int j = 0; j < EnemyFormation.formation[i].length; j++) {
				BeeGalagaEntity bee = new BeeGalagaEntity(EnemyFormation.formation[i][j].x,
						EnemyFormation.formation[i][j].y);
				bee.init(engine, bodyFactory, 0);
				engine.addEntity(bee);
			}
		}

		/*
		 * }
		 * }
		 * 
		 * for (float y = 19.5f; y <= 21.25f; y += 1.75f) {
		 * for (float x = 10.0f; x < 26.0f; x += 2.0f) {
		 * ButterflyGalagaEntity bf = new ButterflyGalagaEntity(x, y);
		 * bf.init(engine, bodyFactory);
		 * engine.addEntity(bf);
		 * }
		 * }
		 * 
		 * for (float x = 14.0f; x < 22.0f; x += 2.0f) {
		 * float y = 23.0f;
		 * GreenBatGalagaEntity gb = new GreenBatGalagaEntity(x, y);
		 * gb.init(engine, bodyFactory);
		 * engine.addEntity(gb);
		 * }
		 * 
		 * for (float x = 14.0f; x < 22.0f; x += 2.0f) {
		 * float y = 23.0f;
		 * 
		 * }
		 * 
		 * for (float x = 0f; x <= 28.0f; x += 28.0f) {
		 * float y = 2.5f;
		 * BoundariesEntity be = new BoundariesEntity(x, y);
		 * be.init(engine, bodyFactory);
		 * engine.addEntity(be);
		 * }
		 */
	}
}
