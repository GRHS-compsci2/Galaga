package com.github.grhscompsci2.galaga.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    Texture sprite; // new Texture("Galaga"); requires spritemap
    int x;
    int y;

    public Player() {
        sprite = new Texture("images/Raw Sprites/Starfighter090.png");
        x = 100;
        y = 100;
    }

    public void draw(Batch batch) {
        batch.draw(sprite, (float)x,(float)y);
    }
}
