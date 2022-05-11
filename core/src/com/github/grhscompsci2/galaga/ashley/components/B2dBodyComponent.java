package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

public class B2dBodyComponent implements Component, Poolable {
  public Body body;

  public static B2dBodyComponent create(Engine engine) {
    return engine.createComponent(B2dBodyComponent.class);
  }

  public B2dBodyComponent setBody(Body body) {
    this.body = body;
    return this;
  }

  @Override
  public void reset() {
    this.body = null;
  }
}