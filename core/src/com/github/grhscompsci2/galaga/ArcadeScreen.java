package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ArcadeScreen extends ScreenAdapter {

	private SpriteBatch batch  = new SpriteBatch();
	private Texture img;
	private Sprite sprite;

	Assets myAssets = new Assets();
	
	
	public ArcadeScreen(MyGdxGame myGdxGame) {
	}

	@Override
	public void show () {
		img = new Texture(myAssets.getImage());
		sprite = new Sprite(img);
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void hide(){
		dispose();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}

	
}
