package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.github.grhscompsci2.galaga.Utility;

/**
 *
 */
public class Box2DPhysicsDebugSystem extends IteratingSystem {

  private Box2DDebugRenderer debugRenderer;
  private World world;
  private OrthographicCamera camera;

  public Box2DPhysicsDebugSystem(World world, OrthographicCamera camera) {
    super(Family.all().get());
    debugRenderer = new Box2DDebugRenderer();
    this.world = world;
    
    this.camera = camera;
    //this.camera.position.set(0f,0f,0f);
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);
    debugRenderer.render(world, camera.combined);
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

  }
}