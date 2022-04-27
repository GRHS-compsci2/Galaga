package com.github.grhscompsci2.galaga;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.entities.BeeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.ButterflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.EnemyEntity;
import com.github.grhscompsci2.galaga.entities.GreenBatGalagaEntity;

public class EnemyFormation {
  private static String TAG = EnemyFormation.class.getSimpleName();
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
        if (i == 0) {
          formation[i][j] = new GreenBatGalagaEntity();
        } else if (i < 3) {
          formation[i][j] = new ButterflyGalagaEntity();
        } else {
          formation[i][j] = new BeeGalagaEntity();

        }
        formation[i][j].init(engine, factory, new Vector2(x, y));
        engine.addEntity(formation[i][j]);
      }
      y -= 2;
    }
  }
}
