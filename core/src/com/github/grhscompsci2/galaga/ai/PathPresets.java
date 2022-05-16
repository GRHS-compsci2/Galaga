package com.github.grhscompsci2.galaga.ai;

import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;

public class PathPresets {
  public static LinePath<Vector2> entryPath1;
  public static LinePath<Vector2> entryPath2;
  public static LinePath<Vector2> entryPath3;
  public static LinePath<Vector2> entryPath4;
  private static final float x1 = 173;
  private static final float y1 = 142;
  private static final float r = 45;
  private static final float d = r / 4;
  private static final float x2 = 112 + r / 2;
  private static final float y2 = y1 + 4 * r;
  private static final int MIDDLE_X = (int)Utility.pixelsToMeters(224 / 2);

  private static final int ENTRY_PARABOLA_TOP_RIGHT = 0;
  private static final int ENTRY_PARABOLA_TRANSITION = 1;
  private static final int ENTRY_CIRCLE_BOTTOM_RIGHT = 2;
  private static final int ENTRY_PARABOLA_EXIT = 3;
  private static final int ENTRY_PARABOLA_BOTTOM_RIGHT = 4;
  private static final int ENTRY_CIRCLE_TOP_LEFT = 5;

  public static void init() {
    entryPath1 = new LinePath<Vector2>(genPath1(), true);
    entryPath2 = new LinePath<Vector2>(genPath2(), true);
    entryPath3 = new LinePath<Vector2>(genPath3(), true);
    entryPath4 = new LinePath<Vector2>(genPath4(), true);
  }

  // All math is based on 224x288 screen
  public static Array<Vector2> genPath1() {
    Array<Vector2> wayPoints;
    wayPoints = new Array<>();
    wayPoints.addAll(genPoints(288f, 238f, 1, ENTRY_PARABOLA_TOP_RIGHT));
    wayPoints.addAll(genPoints(238f, 142f, 1, ENTRY_PARABOLA_TRANSITION));
    wayPoints.addAll(genPoints(218f, 173f, 1, ENTRY_CIRCLE_BOTTOM_RIGHT));
    wayPoints.addAll(genPoints(173f, 132f, 1, ENTRY_PARABOLA_EXIT));
    return wayPoints;
  }

  public static Array<Vector2> genPath2() {
    Array<Vector2> arr = genPath1();
    mirrorYAxis(arr);
    return arr;
  }

  public static Array<Vector2> genPath3() {
    Array<Vector2> arr = genPath4();
    mirrorYAxis(arr);
    return arr;
  }

  public static Array<Vector2> genPath4() {
    Array<Vector2> wayPoints;
    wayPoints = new Array<>();
    wayPoints.addAll(genPoints(224f, 128, 1, ENTRY_PARABOLA_BOTTOM_RIGHT));
    wayPoints.addAll(genPoints(128f, 218f, 1, ENTRY_CIRCLE_TOP_LEFT));
    wayPoints.addAll(genPoints(218f, 173f, 1, ENTRY_CIRCLE_BOTTOM_RIGHT));
    wayPoints.addAll(genPoints(173f, 132f, 1, ENTRY_PARABOLA_EXIT));
    return wayPoints;
  }

  public static Array<Vector2> genPoints(float start, float end, float stepSize, int which) {
    float range = Math.abs(end - start);
    int numSteps = (int) (range / stepSize);
    if (numSteps < 1) {
      return null;
    }
    Array<Vector2> arr = new Array<>();
    float y = start;
    float x = start;
    if (start > end) {
      stepSize *= -1;
    }
    for (int i = 0; i < numSteps; i++) {
      switch (which) {
        case ENTRY_PARABOLA_TOP_RIGHT:
          x = (float) (Math.pow((y - y2) / d, 2) + x2 - r);
          arr.add(new Vector2(x, y).scl(1/Utility.PPM));
          y += stepSize;
          break;
        case ENTRY_PARABOLA_TRANSITION:
          x = (float) (-1 * Math.pow((y - y1) / d, 2) + x1 + r);
          arr.add(new Vector2(x, y).scl(1/Utility.PPM));
          y += stepSize;
          break;
        case ENTRY_CIRCLE_BOTTOM_RIGHT:
          y = y1 - (float) Math.sqrt((Math.pow(r, 2)) - (Math.pow(x - x1, 2)));
          arr.add(new Vector2(x, y).scl(1/Utility.PPM));
          x += stepSize;
          break;
        case ENTRY_PARABOLA_EXIT:
          y = (float) (Math.pow((x - x1) / d, 2) + y1 - r);
          arr.add(new Vector2(x, y).scl(1/Utility.PPM));
          x += stepSize;
          break;
        case ENTRY_PARABOLA_BOTTOM_RIGHT:
          x = (float) (Math.pow((y - y1) / d, 2) + x1 - r);
          arr.add(new Vector2(x, y).scl(1/Utility.PPM));
          y += stepSize;
          break;
        case ENTRY_CIRCLE_TOP_LEFT:
          x = y1 - (float) Math.sqrt((Math.pow(r, 2)) - (Math.pow(x - x1, 2)));
          arr.add(new Vector2(x, y).scl(1/Utility.PPM));
          y += stepSize;
          break;
      }
    }
    return arr;
  }

  public static void mirrorYAxis(Array<Vector2> arr) {
    for (int i = 0; i < arr.size; i++) {
      float distance = arr.get(i).x - MIDDLE_X;
      arr.get(i).x = MIDDLE_X - distance;
    }
  }
}
