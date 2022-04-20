package com.github.grhscompsci2.galaga.ai;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.LinePathParam;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.b2d.Box2dLocation;
import com.github.grhscompsci2.galaga.components.SteeringComponent;

public class SteeringPresets {
    public static Seek<Vector2> getSeek(SteeringComponent seeker, SteeringComponent target) {
        Seek<Vector2> seek = new Seek<Vector2>(seeker, target);
        return seek;
    }

    public static Arrive<Vector2> getArrive(SteeringComponent runner, SteeringComponent target) {
        Arrive<Vector2> arrive = new Arrive<Vector2>(runner, target)
                .setTimeToTarget(0.1f) // default 0.1f
                .setArrivalTolerance(7f) //
                .setDecelerationRadius(10f);

        return arrive;
    }

    public static FollowPath<Vector2, LinePathParam> goPath(SteeringComponent follower,
            LinePath<Vector2> linePath) {
        FollowPath<Vector2, LinePathParam> followPath = new FollowPath<Vector2, LinePathParam>(
                follower, linePath,1f)
                .setTimeToTarget(0.1f)
                .setArrivalTolerance(.5f)
                .setDecelerationRadius(0.1f)
                .setPredictionTime(0);
                followPath.setTarget(new Box2dLocation(linePath.getStartPoint()));
        return followPath;
    }
}
