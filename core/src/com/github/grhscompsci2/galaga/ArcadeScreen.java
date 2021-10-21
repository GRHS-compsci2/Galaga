package com.github.grhscompsci2.galaga;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.github.grhscompsci2.galaga.systems.RenderingSystem;

public class ArcadeScreen extends ScreenAdapter {

	private MyGdxGame parent;
	private SpriteBatch batch;
	private PooledEngine engine;
	private OrthographicCamera cam;

	public ArcadeScreen(MyGdxGame myGdxGame) {
		parent = myGdxGame;
		
		batch = new SpriteBatch();
		RenderingSystem renderingSystem = new RenderingSystem(batch);
		cam = renderingSystem.getCamera();
		batch.setProjectionMatrix(cam.combined);
		engine = new PooledEngine();

		PlayerEntity player=new PlayerEntity();
		player.setTexture(engine);
		player.setStart(engine);
		engine.addEntity(player);

		BeeGalagaEntity bee = new BeeGalagaEntity();
		bee.init(engine);
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
	}

	@Override
	public void show() {

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
		batch.dispose();
	}

}
