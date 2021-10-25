package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.entities.Player;

public class ArcadeScreen extends ScreenAdapter {

	private MyGdxGame parent;
	private Background background;
	// private SpriteBatch batch = new SpriteBatch();

	private Player player;
	private Stage arcadeStage;

	public ArcadeScreen(MyGdxGame myGdxGame) {
		parent = myGdxGame;
		background = new Background();
		player = new Player();
		arcadeStage = new Stage(new FitViewport(288, 244, new OrthographicCamera()));
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// ScreenUtils.clear(1, 0, 0, 1);
		background.render(delta);

		arcadeStage.getBatch().begin();
		// sprite.draw(batch);
		player.draw(arcadeStage.getBatch());
		arcadeStage.getBatch().end();
		arcadeStage.act();
		arcadeStage.draw();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void resize(int width, int height) {
		arcadeStage.getViewport().update(width, height);
		//arcadeStage.getCamera().position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
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
