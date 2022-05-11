package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool.Poolable;

public class FPSComponent implements Component, Poolable {
  @Override
  public void reset() {

  }

  public static FPSComponent create(Engine e) {
    if (e instanceof PooledEngine) {
      return ((PooledEngine) e).createComponent(FPSComponent.class);
    } else {
      return new FPSComponent();
    }
  }
}