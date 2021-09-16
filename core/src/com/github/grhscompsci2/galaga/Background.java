package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Background implements Screen {
    ShapeRenderer shaper;
    int width;
    int height;
    int numStars;
    int starX[];
    int starY[];
    int bgFrameCounter = 0;

    public Background() {
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
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
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

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
