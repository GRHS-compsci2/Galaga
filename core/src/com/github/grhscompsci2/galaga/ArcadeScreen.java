package com.github.grhscompsci2.galaga;

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
import com.github.grhscompsci2.galaga.entities.BeeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.BirdGalagaEntity;
import com.github.grhscompsci2.galaga.entities.ButterflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.DragonflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.GreenBatGalagaEntity;
import com.github.grhscompsci2.galaga.entities.PhantomGalagaEntity;
import com.github.grhscompsci2.galaga.entities.PinheadGalagaEntity;
import com.github.grhscompsci2.galaga.entities.PlayerEntity;
import com.github.grhscompsci2.galaga.entities.ProbeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.PurpleBatGalagaEntity;
import com.github.grhscompsci2.galaga.entities.ScorpionGalagaEntity;
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
		bird.init(engine, bodyFactory);
		engine.addEntity(bird);

		ButterflyGalagaEntity bf = new ButterflyGalagaEntity();
		bf.init(engine, bodyFactory);
		engine.addEntity(bf);

		DragonflyGalagaEntity df = new DragonflyGalagaEntity();
		df.init(engine, bodyFactory);
		engine.addEntity(df);

		GreenBatGalagaEntity gb = new GreenBatGalagaEntity();
		gb.init(engine, bodyFactory);
		engine.addEntity(gb);

		PhantomGalagaEntity phan = new PhantomGalagaEntity();
		phan.init(engine, bodyFactory);
		engine.addEntity(phan);

		PinheadGalagaEntity ph = new PinheadGalagaEntity();
		ph.init(engine, bodyFactory);
		engine.addEntity(ph);

		ProbeGalagaEntity probe = new ProbeGalagaEntity();
		probe.init(engine, bodyFactory);
		engine.addEntity(probe);

		PurpleBatGalagaEntity pb = new PurpleBatGalagaEntity();
		pb.init(engine, bodyFactory);
		engine.addEntity(pb);

		ScorpionGalagaEntity sc = new ScorpionGalagaEntity();
		sc.init(engine, bodyFactory);
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
	@Override
	public void resize(int width, int height) {
		arcadeStage.getViewport().update(width, height);
		//arcadeStage.getCamera().position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
	}
}
