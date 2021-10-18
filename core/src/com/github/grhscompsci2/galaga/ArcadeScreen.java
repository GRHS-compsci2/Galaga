package com.github.grhscompsci2.galaga;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.grhscompsci2.galaga.entities.GalagaEntity;
import com.github.grhscompsci2.galaga.entities.PlayerEntity;
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

		GalagaEntity galagaBoss=new GalagaEntity();
		galagaBoss.setAnimation(engine);
		galagaBoss.setStart(engine);
		engine.addEntity(galagaBoss);

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
