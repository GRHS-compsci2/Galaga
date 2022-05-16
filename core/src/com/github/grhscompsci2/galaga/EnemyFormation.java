package com.github.grhscompsci2.galaga;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.ai.PathPresets;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.EnemyComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.entities.enemies.BeeGalagaEntity;
import com.github.grhscompsci2.galaga.ashley.entities.enemies.ButterflyGalagaEntity;
import com.github.grhscompsci2.galaga.ashley.entities.enemies.EnemyEntity;
import com.github.grhscompsci2.galaga.ashley.entities.enemies.GreenBatGalagaEntity;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class EnemyFormation {
  private static String TAG = EnemyFormation.class.getSimpleName();

  private static final float LAUNCH_DELAY = 0.25f;
  private static float waveTimer = 0;
  private static float idleTime = 0;
  private static boolean launching = true;
  private static boolean waveDone = false;
  private static boolean goingLeft = false;

  private static int level = 0;
  private static int group = 0;
  private static int position = 0;
  private static Vector2 idler = new Vector2();

  // waves[level][group][position]
  private static Wave[][][] waves = {
      {
          { new Wave(1, 3, 1), new Wave(1, 4, 1), new Wave(2, 3, 1), new Wave(2, 4, 1) },
          { new Wave(3, 4, 2), new Wave(3, 5, 2), new Wave(4, 4, 2), new Wave(4, 5, 2) }
      },
      {
          { new Wave(0, 0, 3), new Wave(1, 2, 3), new Wave(0, 1, 3), new Wave(1, 5, 3), new Wave(0, 2, 3),
              new Wave(2, 2, 3), new Wave(0, 3, 3), new Wave(2, 5, 3) }
      },
      {
          { new Wave(1, 6, 4), new Wave(1, 1, 4), new Wave(1, 7, 4), new Wave(1, 0, 4), new Wave(2, 6, 4),
              new Wave(2, 1, 4), new Wave(2, 7, 4), new Wave(2, 0, 4) }
      },
      {
          { new Wave(3, 6, 2), new Wave(3, 3, 2), new Wave(3, 7, 2), new Wave(3, 2, 2), new Wave(4, 6, 2),
              new Wave(4, 3, 2), new Wave(4, 7, 2), new Wave(4, 2, 2) }
      },
      {
          { new Wave(3, 8, 1), new Wave(3, 1, 1), new Wave(3, 9, 1), new Wave(3, 0, 1), new Wave(4, 8, 1),
              new Wave(4, 1, 1), new Wave(4, 9, 1), new Wave(4, 0, 1) }
      }
  };
  public static EnemyEntity[][] formation = {

      // bat rows
      {
          new GreenBatGalagaEntity(), new GreenBatGalagaEntity(), new GreenBatGalagaEntity(),
          new GreenBatGalagaEntity(),
      },
      // butterfly rows
      {
          new ButterflyGalagaEntity(), new ButterflyGalagaEntity(), new ButterflyGalagaEntity(),
          new ButterflyGalagaEntity(), new ButterflyGalagaEntity(), new ButterflyGalagaEntity(),
          new ButterflyGalagaEntity(), new ButterflyGalagaEntity()
      },
      {
          new ButterflyGalagaEntity(), new ButterflyGalagaEntity(), new ButterflyGalagaEntity(),
          new ButterflyGalagaEntity(), new ButterflyGalagaEntity(), new ButterflyGalagaEntity(),
          new ButterflyGalagaEntity(), new ButterflyGalagaEntity()
      },
      // bee rows
      {
          new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(),
          new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(),
          new BeeGalagaEntity(), new BeeGalagaEntity()
      },
      {
          new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(),
          new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(), new BeeGalagaEntity(),
          new BeeGalagaEntity(), new BeeGalagaEntity()
      }
  };

  private static PooledEngine engine;

  private static BodyFactory bodyFactory;

  public static void init(PooledEngine e, BodyFactory f) {
    engine = e;
    bodyFactory = f;
    init();
  }

  public static void init() {
    PathPresets.init();
    float centerX = (Utility.SCREEN_WIDTH) / 2;
    float y = Utility.SCREEN_HEIGHT*7/8;
    for (int i = 0; i < formation.length; i++) {
      float xStart = centerX - (formation[i].length) + 1;
      for (int j = 0; j < formation[i].length; j++) {
        float x = xStart + (j * 8);
        Gdx.app.debug(TAG, "X:" + x + "Y:" + y);
        formation[i][j].init(engine, bodyFactory, new Vector2(x, y));
        engine.addEntity(formation[i][j]);
      }
      y -= 16;
    }
  }

  public static void launchNext(float deltaTime) {
    if (waveTimer == 0 && position < waves[level][group].length) {
      EnemyEntity entity = formation[waves[level][group][position].getX()][waves[level][group][position].getY()];
      StateComponent sc = K2ComponentMappers.state.get(entity);
      EnemyComponent ec = K2ComponentMappers.enemy.get(entity);
      BodyComponent b2dc = K2ComponentMappers.body.get(entity);
      sc.set(StateComponent.STATE_ENTRY);
      ec.setPath(waves[level][group][position].getPath());
      b2dc.body.setActive(true);
      position++;
    }
    if (waveDone && position == waves[level][group].length) {
      position = 0;
      group++;

      if (group == waves[level].length) {
        group = 0;
        level++;
        if (level == waves.length)
          launching = false;
      }
    }
    if (deltaTime == 0) {
      deltaTime = 0.0001f;
    }
    waveTimer += deltaTime;
    if (waveTimer >= LAUNCH_DELAY) {
      // Gdx.app.debug(TAG, "Level:" + level + " group:" + group + " position:" +
      // position);
      waveTimer = 0;
    }
  }

  public static boolean stillLaunching() {
    return launching;
  }

  public static void startLaunching() {
    launching = true;
  }

  public static void setWaveDone(boolean value) {
    waveDone = value;
  }

  public static boolean getWaveDone() {
    return waveDone;
  }

  /**
   * This method will update the position of the formation, so all enemies can
   * move in sync
   * 
   * @param deltaTime the elapsed time
   */
  public static void updateFormation(float deltaTime) {
    if (goingLeft) {
      idleTime += 2 * deltaTime;
    } else {
      idleTime -= 2 * deltaTime;
    }

    if (Math.abs(idleTime) > 2.5 * Utility.SPRITE_WIDTH) {
      idleTime = Math.copySign((float) (2.5 * Utility.SPRITE_WIDTH), idleTime);
      goingLeft = !goingLeft;
    }

    idler.set(idleTime, 0);
  }

  public static Vector2 getIdle() {
    return idler;
  }

  public static void nextLevel() {
    init();
    level = 0;
    group = 0;
    position = 0;
    launching = true;
  }
}
