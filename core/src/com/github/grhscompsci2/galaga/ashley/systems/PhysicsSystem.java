package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

public class PhysicsSystem extends IteratingSystem {

  private static final String TAG = PhysicsSystem.class.getName();
  private static final float MAX_STEP_TIME = 1 / 45f;
  private static float accumulator = 0f;

  private World world;
  private Array<Entity> bodiesQueue;

  public PhysicsSystem(World world) {
    super(Family.all(BodyComponent.class, TransformComponent.class)
        .get());
    this.world = world;
    this.bodiesQueue = new Array<Entity>();
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);
    float frameTime = Math.min(deltaTime, 0.25f);
    accumulator += frameTime;
    if (accumulator >= MAX_STEP_TIME) {
      world.step(MAX_STEP_TIME, 6, 2);
      accumulator -= MAX_STEP_TIME;

      // Entity Queue
      for (Entity entity : bodiesQueue) {
        TransformComponent tfm = K2ComponentMappers.transform.get(entity);
        BodyComponent bodyComp = K2ComponentMappers.body.get(entity);

        tfm.position.x = bodyComp.getX();
        tfm.position.y = bodyComp.getY();
        tfm.rotation = bodyComp.getAngle() * MathUtils.radiansToDegrees;
      }
    }
    bodiesQueue.clear();
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    bodiesQueue.add(entity);
  }
}