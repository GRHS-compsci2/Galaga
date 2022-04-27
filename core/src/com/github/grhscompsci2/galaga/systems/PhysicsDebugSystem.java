package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.TranslationComponent;

public class PhysicsDebugSystem extends IteratingSystem {

  private static final String TAG = PhysicsDebugSystem.class.getName();
  private Box2DDebugRenderer debugRenderer;
  private World world;
  private OrthographicCamera camera;

  public PhysicsDebugSystem(World world, OrthographicCamera camera) {
    super(Family.all(TranslationComponent.class, B2dBodyComponent.class).get());
    debugRenderer = new Box2DDebugRenderer();
    this.world = world;
    this.camera = camera;
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);
    debugRenderer.render(world, camera.combined);
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    TranslationComponent tC = Mapper.transCom.get(entity);
    B2dBodyComponent bC = Mapper.b2dCom.get(entity);
  }
}