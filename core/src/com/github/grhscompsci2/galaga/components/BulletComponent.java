package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class BulletComponent implements Component, Poolable {

  public float xVel = 0;
  public float yVel = 0;

  @Override
  public void reset() {
    xVel = 0;
    yVel = 0;
  }

  public void revive(float xVel, float yVel) {
    this.xVel = xVel;
    this.yVel = yVel;
  }

}
