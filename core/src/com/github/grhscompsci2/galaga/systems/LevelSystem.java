package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.EnemyFormation;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;

public class LevelSystem extends IteratingSystem {
  private final String TAG = LevelSystem.class.getName();
  private Array<Entity> enemies;

  public LevelSystem() {
    super(Family.all(EnemyComponent.class)
        .exclude(InactiveComponent.class)
        .get());
    enemies = new Array<Entity>();
  }

  public void update(float deltaTime) {
    super.update(deltaTime);
    if (EnemyFormation.stillLaunching()) {
      EnemyFormation.launchNext(deltaTime);
      // Gdx.app.debug(TAG, "Waited "+deltaTime);
    }

    EnemyFormation.setWaveDone(true);
    for (Entity e : enemies) {
      StateComponent stateComponent = Mapper.stateCom.get(e);
      if (stateComponent.getState() != StateComponent.STATE_ENTRY_IDLE) {
        EnemyFormation.setWaveDone(false);
      }
    }
    if (enemies.isEmpty() && !EnemyFormation.stillLaunching()) {
      EnemyFormation.nextLevel();
    }
    enemies.clear();
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    // TODO Auto-generated method stub
    enemies.add(entity);
  }

}
