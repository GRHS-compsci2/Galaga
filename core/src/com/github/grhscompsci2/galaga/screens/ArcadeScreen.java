package com.github.grhscompsci2.galaga.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.b2d.B2dContactListener;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.entities.GalagaEntity;
import com.github.grhscompsci2.galaga.entities.PlayerEntity;
import com.github.grhscompsci2.galaga.systems.AnimationSystem;
import com.github.grhscompsci2.galaga.systems.CollisionSystem;
import com.github.grhscompsci2.galaga.systems.PhysicsDebugSystem;
import com.github.grhscompsci2.galaga.systems.PhysicsSystem;
import com.github.grhscompsci2.galaga.systems.PlayerControlSystem;
import com.github.grhscompsci2.galaga.systems.RenderingSystem;

public class ArcadeScreen extends ScreenAdapter {

	private MyGdxGame parent;
	//private SpriteBatch batch;
	private PooledEngine engine;
	private OrthographicCamera cam;
	private BodyFactory bodyFactory;
	private World world;
	private KeyboardController controller;
	private Stage arcadeStage;

	public ArcadeScreen(MyGdxGame myGdxGame) {
		parent = myGdxGame;
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new B2dContactListener(parent));
		bodyFactory = BodyFactory.getInstance(world);
		controller = new KeyboardController();
		arcadeStage = new Stage(new FitViewport(288, 244, new OrthographicCamera()));
		

		RenderingSystem renderingSystem = new RenderingSystem(arcadeStage.getBatch());
		cam = renderingSystem.getCamera();
		arcadeStage.getBatch().setProjectionMatrix(cam.combined);
		engine = new PooledEngine();

		PlayerEntity player = new PlayerEntity();
		player.setUp(engine, bodyFactory);
		engine.addEntity(player);

		BeeGalagaEntity bee = new BeeGalagaEntity();
		bee.init(engine, bodyFactory);
		engine.addEntity(bee);

		BirdGalagaEntity bird = new BirdGalagaEntity();
		bird.init(engine);
		engine.addEntity(bird);

		ButterflyGalagaEntity bf = new ButterflyGalagaEntity();
		bf.init(engine);
		engine.addEntity(bf);

		DragonflyGalagaEntity df = new DragonflyGalagaEntity();
		df.init(engine);
		engine.addEntity(df);

		GreenBatGalagaEntity gb = new GreenBatGalagaEntity();
		gb.init(engine);
		engine.addEntity(gb);

		PhantomGalagaEntity phan = new PhantomGalagaEntity();
		phan.init(engine);
		engine.addEntity(phan);

		PinheadGalagaEntity ph = new PinheadGalagaEntity();
		ph.init(engine);
		engine.addEntity(ph);

		ProbeGalagaEntity probe = new ProbeGalagaEntity();
		probe.init(engine);
		engine.addEntity(probe);

		PurpleBatGalagaEntity pb = new PurpleBatGalagaEntity();
		pb.init(engine);
		engine.addEntity(pb);

		ScorpionGalagaEntity sc = new ScorpionGalagaEntity();
		sc.init(engine);
		engine.addEntity(sc);


		// add all the relevant systems our engine should run
		engine.addSystem(renderingSystem);
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem(controller));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Utility.background.render(delta);
		engine.update(delta);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		arcadeStage.dispose();
	}

}
