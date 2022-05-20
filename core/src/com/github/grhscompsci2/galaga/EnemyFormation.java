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
  private static float formationTime = 0;
  private static boolean launching = true;
  private static boolean waveDone = false;
  private static boolean goingUp = false;

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
  public static EnemyEntity[][] enemies = {

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

  private static Vector2[][] formation = {
      { new Vector2(88, 252), new Vector2(104, 252), new Vector2(120, 252), new Vector2(136, 252) },
      { new Vector2(56, 236), new Vector2(72, 236), new Vector2(88, 236), new Vector2(104, 236), new Vector2(120, 236),
          new Vector2(136, 236), new Vector2(152, 236), new Vector2(168, 236) },
      { new Vector2(56, 220), new Vector2(72, 220), new Vector2(88, 220), new Vector2(104, 220), new Vector2(120, 220),
          new Vector2(136, 220), new Vector2(152, 220), new Vector2(168, 220) },
      { new Vector2(40, 204), new Vector2(56, 204), new Vector2(72, 204), new Vector2(88, 204), new Vector2(104, 204),
          new Vector2(120, 204), new Vector2(136, 204), new Vector2(152, 204), new Vector2(168, 204),
          new Vector2(184, 204) },
      { new Vector2(40, 188), new Vector2(56, 188), new Vector2(72, 188), new Vector2(88, 188), new Vector2(104, 188),
          new Vector2(120, 188), new Vector2(136, 188), new Vector2(152, 188), new Vector2(168, 188),
          new Vector2(184, 188) }
  };
  private static PooledEngine engine;

  private static BodyFactory bodyFactory;

  private static int i;

  private static int j;

  public static void init(PooledEngine e, BodyFactory f) {
    engine = e;
    bodyFactory = f;
    init();
  }

  public static void init() {
    PathPresets.init();
    for (int i = 0; i < enemies.length; i++) {
      for (int j = 0; j < enemies[i].length; j++) {
        if(i==0){
          enemies[i][j]=new GreenBatGalagaEntity();
        } else if (i<3){
          enemies[i][j]=new BeeGalagaEntity();
        }
        else {
          enemies[i][j]=new ButterflyGalagaEntity();
        }
        enemies[i][j].init(engine, bodyFactory, formation[i][j], i, j);
        engine.addEntity(enemies[i][j]);
      }
    }
  }

  public static void launchNext(float deltaTime) {
    if (waveTimer == 0 && position < waves[level][group].length) {
      EnemyEntity entity = enemies[waves[level][group][position].getX()][waves[level][group][position].getY()];
      StateComponent state = K2ComponentMappers.state.get(entity);
      EnemyComponent enemy = K2ComponentMappers.enemy.get(entity);
      BodyComponent body = K2ComponentMappers.body.get(entity);
      state.set(StateComponent.STATE_ENTRY);
      enemy.setPath(waves[level][group][position].getPath());
      body.getBody().setActive(true);
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
      Gdx.app.debug(TAG, "Level:" + level + " group:" + group + " position:" +
          position);
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
  public static void updateFormationTime(float deltaTime) {
    if (goingUp) {
      formationTime += 2 * deltaTime;
    } else {
      formationTime -= 2 * deltaTime;
    }

    if (formationTime >= 8) {
      formationTime = 8;
      goingUp = !goingUp;
    } else if (formationTime <= 0) {
      formationTime = 0;
      goingUp = !goingUp;
    }
  }

  public static Vector2 getSwarmVector(int i, int j) {

    float xPos = 0;
    if (i == 0) {
      xPos = calcSwarmX(j + 3);
    } else if (i < 3) {
      xPos = calcSwarmX(j + 1);
    } else {
      xPos = calcSwarmX(j);
    }

    return new Vector2(xPos, formation[i][j].y);
  }

  private static float calcSwarmX(int i) {
    // format formation time to be between 0 and 2
    float swarmTime = formationTime * 2.5f;
    float[] m = { -1.6f, -1.2444f, -0.8889f, -0.5333f, -0.1778f, 0.1778f, 0.5333f, 0.889f, 1.2444f, 1.6f };
    float[] c = { 41.6f, 57.244f, 72.899f, 88.533f, 104.18f, 119.82f, 135.47f, 151.11f, 166.76f, 182.4f };
    return m[i] * swarmTime + c[i];
  }

  public static Vector2 getBounceVector() {
    // format formationTime to be between -4 and 4
    float bounceTime = formationTime - 4;
    return new Vector2(bounceTime, 0);
  }

  public static void nextLevel() {
    init();
    level = 0;
    group = 0;
    position = 0;
    launching = true; 
  }

}
