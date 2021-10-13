package com.github.grhscompsci2.galaga.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.grhscompsci2.galaga.Assets;
import com.github.grhscompsci2.galaga.Background;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.entities.Player;

public class ArcadeScreen extends ScreenAdapter {

	private MyGdxGame parent;
	private SpriteBatch batch  = new SpriteBatch();
	private Texture img;
	private Sprite sprte;
	private Player player;
	Assets myAssets = new Assets();
	
	
	public ArcadeScreen(MyGdxGame myGdxGame) {
		parent=myGdxGame;
		player=new Player();
	}

	@Override
	public void show () {
		
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//ScreenUtils.clear(1, 0, 0, 1);
		Utility.background.render(delta);
		batch.begin();
		//sprite.draw(batch);
		player.draw(batch);
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
