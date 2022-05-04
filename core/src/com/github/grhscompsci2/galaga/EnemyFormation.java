package com.github.grhscompsci2.galaga;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.InactiveComponent;
import com.github.grhscompsci2.galaga.entities.BeeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.ButterflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.EnemyEntity;
import com.github.grhscompsci2.galaga.entities.GreenBatGalagaEntity;

public class EnemyFormation {
  private static String TAG = EnemyFormation.class.getSimpleName();

  private static final float LAUNCH_DELAY = 0.5f;
  private static float waveTimer = 0;
  private static boolean launching = true;
  private static boolean waveDone = false;

  private static int level = 0;
  private static int group = 0;
  private static int position = 0;

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

  public static void init(Engine engine, BodyFactory factory) {
    float centerX = (Utility.SCREEN_WIDTH_METERS) / 2;
    float y = Utility.SCREEN_HEIGHT_METERS - 5;
    for (int i = 0; i < formation.length; i++) {
      float xStart = centerX - (formation[i].length) + 1;
      for (int j = 0; j < formation[i].length; j++) {
        float x = xStart + (j * 2);
        Gdx.app.debug(TAG, "X:" + x + "Y:" + y);
        formation[i][j].init(engine, factory, new Vector2(x, y));
        engine.addEntity(formation[i][j]);
      }
      y -= 2;
    }
  }

  public static void launchNext(float deltaTime) {
    if (waveTimer == 0 && position < waves[level][group].length) {
      EnemyEntity entity = formation[waves[level][group][position].getX()][waves[level][group][position].getY()];
      entity.remove(InactiveComponent.class);
      entity.setPath(waves[level][group][position].getPath());
      position++;
    }
    if (waveDone&&position==waves[level][group].length) {
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
      Gdx.app.debug(TAG, "Waited " + waveTimer);
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
}
