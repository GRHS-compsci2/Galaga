package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.BulletManager;
import com.github.grhscompsci2.galaga.EnemyFormation;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ai.SteeringPresets;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.SteeringComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;
import com.github.grhscompsci2.galaga.components.SteeringComponent.SteeringState;

public class EnemySystem extends IteratingSystem {
  private static String TAG = EnemySystem.class.getSimpleName();
  private BulletManager bulMan;
  private MyGdxGame parent;

  @SuppressWarnings("unchecked")
  public EnemySystem(MyGdxGame parent, BulletManager bulMan) {
    // get all of the entites with the enemy component
    super(Family.all(EnemyComponent.class)
        .exclude(InactiveComponent.class)
        .get());
    this.bulMan = bulMan;
    this.parent = parent;
  }

  public void update(float deltaTime) {
    super.update(deltaTime);
    EnemyFormation.updateFormation(deltaTime);
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    // grab the specific enemy and body components for the entity
    StateComponent stateComponent = Mapper.stateCom.get(entity);

    switch (stateComponent.getState()) {
      case StateComponent.STATE_ENTRY:
        entry(entity);
        break;
      case StateComponent.STATE_ENTRY_GO_HOME:
        entryGoHome(entity);
        break;
      case StateComponent.STATE_ENTRY_IDLE:
        entryIdle(entity);
        break;
      case StateComponent.STATE_DEAD:
        die(entity);
        break;
    }
  }

  private void die(Entity entity) {
    StateComponent sc = Mapper.stateCom.get(entity);
    B2dBodyComponent b2c = Mapper.b2dCom.get(entity);
    b2c.body.setTransform(-5, -5, 0);
    sc.set(StateComponent.STATE_NORMAL);
    entity.add(new InactiveComponent());
  }

  public void revive(Entity entity) {
    StateComponent sc = Mapper.stateCom.get(entity);
    B2dBodyComponent b2c = Mapper.b2dCom.get(entity);
    b2c.body.setActive(true);
    sc.set(StateComponent.STATE_ENTRY);
  }

  private void entry(Entity entity) {
    SteeringComponent steeringComponent = Mapper.sCom.get(entity);
    EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
    StateComponent stateComponent = Mapper.stateCom.get(entity);
    if (steeringComponent != null) {
      if (steeringComponent.currentMode != SteeringState.GO) {
        steeringComponent.setSteeringBehavior(SteeringPresets.goPath(steeringComponent, enemyComponent.getPath()));
        steeringComponent.setPosition(enemyComponent.getFirstPoint());
        steeringComponent.currentMode = SteeringState.GO;
      }
      if (enemyComponent.areWeThereYet(steeringComponent.followPath.getArrivalTolerance(),
          steeringComponent.getPosition())) {
        stateComponent.set(StateComponent.STATE_ENTRY_GO_HOME);
        steeringComponent.currentMode = SteeringState.STOP;
      }
    }
  }

  private void entryGoHome(Entity entity) {
    SteeringComponent steerCom = Mapper.sCom.get(entity);
    EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
    StateComponent stateComponent = Mapper.stateCom.get(entity);
    // Go to the home position
    steerCom.setSteeringBehavior(
        SteeringPresets.goPoint(steerCom, enemyComponent.updateHome(), 0.5f));
    enemyComponent.setPath(steerCom.followPath.getPath());
    if (enemyComponent.areWeThereYet(steerCom.followPath.getArrivalTolerance(), steerCom.getPosition())) {
      steerCom.currentMode = SteeringState.STOP;
      stateComponent.set(StateComponent.STATE_ENTRY_IDLE);
    }
  }

  private void entryIdle(Entity entity) {
    SteeringComponent steeringComponent = Mapper.sCom.get(entity);
    B2dBodyComponent b2dBodyComponent = Mapper.b2dCom.get(entity);
    EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
    TranslationComponent translationComponent = Mapper.transCom.get(entity);
    if (steeringComponent.currentMode != SteeringState.GO) {
      steeringComponent.steeringBehavior = null;
      steeringComponent.currentMode = SteeringState.GO;
    }
    Vector2 idlePosition = enemyComponent.updateHome();
    b2dBodyComponent.body.setTransform(idlePosition, 0);
    translationComponent.setPosition(idlePosition);
  }

}
