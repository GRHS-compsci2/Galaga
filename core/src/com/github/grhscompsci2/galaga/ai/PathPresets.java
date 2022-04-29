package com.github.grhscompsci2.galaga.ai;

import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PathPresets {
  public static final Vector2[] ENTRY_WP_0 = {
      // side parabola up to the circle
      new Vector2(36, 4), new Vector2(28, 4), new Vector2(26.5625f, 5),
      new Vector2(25, 6), new Vector2(23.5625f, 7), new Vector2(22.25f, 8), new Vector2(21.0625f, 9),
      new Vector2(20, 10), new Vector2(19.0625f, 11), new Vector2(18.25f, 12), new Vector2(17.5625f, 13f),
      new Vector2(17, 14), new Vector2(16.5625f, 15), new Vector2(16.25f, 16), new Vector2(16.0625f, 17),
      new Vector2(16f, 18f),
      // circle to top parabola
      new Vector2(16.536f, 20), new Vector2(17.354f, 21), new Vector2(18.064f, 21.5f),
      new Vector2(20, 22f), new Vector2(21.963f, 21.5f), new Vector2(22.646f, 21), new Vector2(23.646f, 20f),
      new Vector2(23.837f, 19f), new Vector2(24f, 18f), new Vector2(23.873f, 17), new Vector2(23.646f, 16f),
      new Vector2(23.122f, 15.5f), new Vector2(22.646f, 15f), new Vector2(21.936f, 14.5f), new Vector2(20f, 14f),
      // Exit parabola
      new Vector2(19f, 14.063f), new Vector2(18f, 14.25f), new Vector2(17f, 14.5625f), new Vector2(16f, 15f),
      new Vector2(15, 15.5625f), new Vector2(14, 16.25f), new Vector2(13, 17.0625f), new Vector2(12, 18)
  };
  public static final Vector2[] ENTRY_WP_1 = {
      // entry parabola
      new Vector2(11f, 38.5f), new Vector2(12f, 34.5f), new Vector2(13f, 32.843f), new Vector2(14f, 31.572f),
      new Vector2(15f, 30.5f), new Vector2(16f, 29.556f),
      // Entry Parabola 2
      new Vector2(17f,28.583f), new Vector2(18f, 27.798f), new Vector2(19f, 26.944f), new Vector2(20f, 26f), 
      new Vector2(21f, 24.928f), new Vector2(22f, 23.657f), new Vector2(22.5f, 22.899f), new Vector2(23f, 22f), 
      new Vector2(23.5f, 20.828f),
      // circle to exit parabola
      new Vector2(24f, 18f), new Vector2(23.873f, 17), new Vector2(23.646f, 16f), new Vector2(23.122f, 15.5f),
      new Vector2(22.646f, 15f), new Vector2(21.936f, 14.5f), new Vector2(20f, 14f),
      // Exit parabola
      new Vector2(19f, 14.063f), new Vector2(18f, 14.25f), new Vector2(17f, 14.5625f), new Vector2(16f, 15f),
      new Vector2(15, 15.5625f), new Vector2(14, 16.25f), new Vector2(13, 17.0625f), new Vector2(12, 18)
  };

  public static final LinePath<Vector2> ENTRY_PATH_0 = new LinePath<Vector2>(new Array<Vector2>(ENTRY_WP_0), true);
  public static final LinePath<Vector2> ENTRY_PATH_1 = new LinePath<Vector2>(new Array<Vector2>(ENTRY_WP_1), true);
}
