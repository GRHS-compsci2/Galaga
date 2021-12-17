package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class EnemyFormation {
    private static String TAG = EnemyFormation.class.getSimpleName();
    public static Vector2[][] formation = {
            // bat rows
            {
                    new Vector2(), new Vector2(), new Vector2(), new Vector2(),
            },
            {
                    new Vector2(), new Vector2(), new Vector2(), new Vector2(),
            },
            // butterfly rows
            {
                    new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(),
                    new Vector2(), new Vector2()
            },
            {
                    new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(),
                    new Vector2(), new Vector2()
            },
            // bee rows
            {
                    new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(),
                    new Vector2(), new Vector2(), new Vector2(), new Vector2()
            },
            {
                    new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(), new Vector2(),
                    new Vector2(), new Vector2(), new Vector2(), new Vector2()
            }
    };

    public static void init() {
        float centerX = (Utility.SCREEN_WIDTH / Utility.PPM) / 2;
        float y = Utility.SCREEN_HEIGHT / Utility.PPM - 5;
        Gdx.app.debug(TAG, "Formation Length:");
        for (int i = 0; i < formation.length; i++) {
            float xStart = centerX - (formation[i].length) + 1;
            for (int j = 0; j < formation[i].length; j++) {
                float x = xStart + (j * 2);
                Gdx.app.debug(TAG, "X:" + x + "Y:" + y);
                formation[i][j] = new Vector2(x, y);
            }
            y -= 2;
        }
    }
}
