package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.ApplicationAdapter;
<<<<<<< Updated upstream
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
=======
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
>>>>>>> Stashed changes
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;

<<<<<<< Updated upstream
public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	ShapeRenderer shaper;
	int width;
	int height;
	int numStars;
	int starX[];
	int starY[];
	int bgFrameCounter = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shaper = new ShapeRenderer();

		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		numStars = width / 4;
		starX = new int[numStars];
		starY = new int[numStars];

		// create initial random star pattern;
		for (int i = 0; i < starX.length; i++) {
			starX[i] = (int) (Math.random() * (width - 1) + 1);
		}

		for (int i = 0; i < starY.length; i++) {
			starY[i] = (int) (Math.random() * (height - 1) + 1);
		}

	}

	@Override
	public void render() {
		ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);

		// background should be drawn first!
		drawBackground();

	}

	private void drawBackground() {
		shaper.begin(ShapeType.Filled);

		// draw each star
		for (int i = 0; i < numStars; i++) {

			// choose either bright or dim
			if (Math.round(Math.random()) == 1) {
				shaper.setColor(Color.GRAY);
			} else {
				shaper.setColor(Color.DARK_GRAY);
			}

			// draw actual star.
			shaper.rect(starX[i], starY[i], 2, 2);

			// move star down random amounts.
			starY[i] -= (int) (Math.random() * (5 - 3) + 3);
			// move star to top & random x if below screen.
			if (starY[i] < 1) {
				starY[i] = height;
				starX[i] = (int) (Math.random() * (width - 1) + 1);
			}
		}

		shaper.end();
	}

=======
public class MyGdxGame extends ScreenAdapter {

	private SpriteBatch batch  = new SpriteBatch();
	private Texture img;
	private Sprite sprite;

	Assets myAssets = new Assets();
	
	
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
	
>>>>>>> Stashed changes
	@Override
	public void dispose() {
		batch.dispose();
		shaper.dispose();
	}

	
}
