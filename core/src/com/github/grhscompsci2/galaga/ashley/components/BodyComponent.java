package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;
import com.github.grhscompsci2.galaga.Utility;

/**
 * Component holds a Box2D Body object
 */
public class BodyComponent implements Component, Pool.Poolable {

  private Body body;

  public static BodyComponent create(Engine engine) {
    return engine.createComponent(BodyComponent.class);
  }

  public BodyComponent setBody(Body body) {
    this.body = body;
    return this;
  }

  public Body getBody() {
    return body;
  }

  @Override
  public void reset() {
    this.body = null;
  }

  public float getX() {
    return Utility.metersToPixels(body.getPosition().x);
  }

  public float getY() {
    return Utility.metersToPixels(body.getPosition().y);
  }

  public float getAngle() {
    return body.getAngle();
  }

  public void setLinearVelocity(float vX, float vY) {
    body.setLinearVelocity(new Vector2(vX, vY).scl(Utility.PPM));
  }

  public void setTransform(Vector2 position, int angle) {
    body.setTransform(position.cpy().scl(Utility.PPM), angle);
  }
}