package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class VelocityComponent implements Component, Poolable {
  public Vector2 speed = new Vector2();

  public static VelocityComponent create(Engine engine){
      if(engine instanceof PooledEngine){
          return ((PooledEngine)engine).createComponent(VelocityComponent.class);
      }else {
          return new VelocityComponent();
      }
  }

  public VelocityComponent setSpeed(float x, float y){
      this.speed.set(x, y);
      return this;
  }

  @Override
  public void reset() {
      this.speed.set(0f, 0f);
  }
}