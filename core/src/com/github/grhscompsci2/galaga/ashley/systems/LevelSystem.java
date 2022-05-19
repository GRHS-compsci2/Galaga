package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.EnemyFormation;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.EnemyComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;

public class LevelSystem extends IteratingSystem {
  private final String TAG = LevelSystem.class.getName();
  private Array<Entity> enemies;

  public LevelSystem() {
    super(Family.all(EnemyComponent.class)
        .get());
    enemies = new Array<Entity>();
  }

  public void update(float deltaTime) {
    super.update(deltaTime);
    if (EnemyFormation.stillLaunching()) {
      EnemyFormation.launchNext(deltaTime);
    }

    EnemyFormation.setWaveDone(true);
    for (Entity e : enemies) {
      StateComponent stateComponent = K2ComponentMappers.state.get(e);
      if (stateComponent.get() != StateComponent.STATE_ENTRY_IDLE) {
        EnemyFormation.setWaveDone(false);
      }
    }
    if (!EnemyFormation.stillLaunching()) {
      for (Entity e : enemies) {
        StateComponent stateComponent = K2ComponentMappers.state.get(e);
        if (stateComponent.get() != StateComponent.STATE_SWARMING) {
          stateComponent.set(StateComponent.STATE_SWARMING);
        }
      }
    }
    if (enemies.isEmpty() && !EnemyFormation.stillLaunching()) {
      EnemyFormation.nextLevel();
    }
    enemies.clear();
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    BodyComponent body = K2ComponentMappers.body.get(entity);
    if (body.getBody().isActive()) {
      enemies.add(entity);
    }
  }

}
