package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BulletComponent implements Component, Poolable {
    public static enum OWNER {
        ENEMY, PLAYER, SCENERY, NONE
    }

    public float xVel = 0;
    public float yVel = 0;
    public float speed = 200.0f;
    public boolean isDead = false;
    public OWNER owner = OWNER.NONE;

    @Override
    public void reset() {
        owner = OWNER.NONE;
        xVel = 0;
        yVel = 0;
        isDead = false;
    }

}
