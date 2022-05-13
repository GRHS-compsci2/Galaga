package com.github.grhscompsci2.galaga.ai;

import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.LinePathParam;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.ashley.components.SteeringComponent;
import com.github.grhscompsci2.galaga.b2d.Box2dLocation;

public class SteeringPresets {

  public static FollowPath<Vector2, LinePathParam> goPath(SteeringComponent follower,
      LinePath<Vector2> linePath) {
    FollowPath<Vector2, LinePathParam> followPath = new FollowPath<Vector2, LinePathParam>(
        follower, linePath, 1f)
        .setTimeToTarget(0.01f)
        .setArrivalTolerance(1f)
        .setDecelerationRadius(0)
        .setPredictionTime(0);
    followPath.setTarget(new Box2dLocation(linePath.getStartPoint()));
    return followPath;
  }

  public static FollowPath<Vector2, LinePathParam> goPoint(SteeringComponent follower, Vector2 point) {
    Array<Vector2> waypoints = new Array<Vector2>();
    waypoints.add(follower.getPosition());
    waypoints.add(point);
    LinePath<Vector2> path = new LinePath<Vector2>(waypoints, true);
    FollowPath<Vector2, LinePathParam> followPath = new FollowPath<Vector2, LinePathParam>(follower, path, 0.1f)
        .setTimeToTarget(0.1f*8)
        .setArrivalTolerance(2f*8)
        .setDecelerationRadius(0)
        .setPredictionTime(0);
    followPath.setTarget(new Box2dLocation(path.getStartPoint()));
    return followPath;
  }

  public static FollowPath<Vector2, LinePathParam> goPoint(SteeringComponent steeringComponent, Vector2 add,
      float arrivalTolerance) {
    FollowPath<Vector2, LinePathParam> retVal = goPoint(steeringComponent, add);
    retVal.setArrivalTolerance(arrivalTolerance);
    return retVal;
  }
}
