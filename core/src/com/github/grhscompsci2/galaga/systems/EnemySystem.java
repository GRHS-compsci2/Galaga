package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ai.SteeringPresets;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.SteeringComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;
import com.github.grhscompsci2.galaga.components.SteeringComponent.SteeringState;

public class EnemySystem extends IteratingSystem {
  private static String TAG = EnemySystem.class.getSimpleName();
  private static Vector2 idler = new Vector2();
  private static boolean goingLeft = false;
  private static float idleTime = 0;

  @SuppressWarnings("unchecked")
  public EnemySystem() {
    // get all of the entites with the enemy component
    super(Family.all(EnemyComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    // grab the specific enemy and body components for the entity
    StateComponent stateComponent = Mapper.stateCom.get(entity);

    updateFormation(deltaTime);
    switch (stateComponent.getState()) {
      case StateComponent.STATE_ENTRY:
        entry(entity);
        break;
      case StateComponent.STATE_ENTRY_GO_HOME:
        entryGoHome(entity);
        break;
      case StateComponent.STATE_ENTRY_IDLE:
        entryIdle(entity);
    }
  }

  private void entry(Entity entity) {
    SteeringComponent steeringComponent = Mapper.sCom.get(entity);
    EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
    StateComponent stateComponent = Mapper.stateCom.get(entity);
    if (steeringComponent != null) {
      if (steeringComponent.currentMode != SteeringState.GO) {
        steeringComponent.setSteeringBehavior(SteeringPresets.goPath(steeringComponent, enemyComponent.getPath()));
        steeringComponent.setPosition(enemyComponent.getPath().getStartPoint());
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
    SteeringComponent steeringComponent = Mapper.sCom.get(entity);
    EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
    StateComponent stateComponent = Mapper.stateCom.get(entity);
    // Go to the home position
    steeringComponent
        .setSteeringBehavior(
            SteeringPresets.goPoint(steeringComponent, enemyComponent.updateHome(idler), 0.5f));
    enemyComponent.setPath(steeringComponent.followPath.getPath());
    if (enemyComponent.areWeThereYet(steeringComponent.followPath.getArrivalTolerance(),
        steeringComponent.getPosition())) {
      steeringComponent.currentMode = SteeringState.STOP;
      stateComponent.set(StateComponent.STATE_ENTRY_IDLE);
    }
  }

  private void entryIdle(Entity entity) {
    SteeringComponent steeringComponent = Mapper.sCom.get(entity);
    B2dBodyComponent b2dBodyComponent=Mapper.b2dCom.get(entity);
    TranslationComponent translationComponent=Mapper.transCom.get(entity);
    EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
    if (steeringComponent.currentMode != SteeringState.GO) {
      steeringComponent.steeringBehavior = null;
      steeringComponent.currentMode = SteeringState.GO;
    }
    Vector2 idlePosition=enemyComponent.updateHome(idler);
    b2dBodyComponent.body.setTransform(idlePosition,0);
    translationComponent.setPosition(idlePosition);
  }

  /**
   * This method will update the position of the formation, so all enemies can
   * move in sync
   * 
   * @param deltaTime the elapsed time
   */
  public void updateFormation(float deltaTime) {
    if (Utility.frameUpdate) {
      /*if (deltaTime > 1)
        deltaTime = 1;
      */

      //float x = idleTime;

      if (goingLeft) {
        idleTime += 2*deltaTime;
      }
      else{
        idleTime -= 2*deltaTime;
      }

      if (Math.abs(idleTime) > 2.5*Utility.SPRITE_WIDTH) {
        goingLeft = !goingLeft;
      }

      idler.set(idleTime, 0);
      //Gdx.app.debug(TAG, "Idler: " + idler);
      Utility.frameUpdate = false;
    }
  }

}
