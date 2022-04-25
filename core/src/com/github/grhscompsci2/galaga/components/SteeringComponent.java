package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.LinePathParam;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.b2d.Box2dLocation;

public class SteeringComponent implements Steerable<Vector2>, Component, Poolable {

    public static enum SteeringState {
        GO, STOP
    }

    private final String TAG = SteeringComponent.class.getSimpleName();
    public SteeringState currentMode = SteeringState.STOP;
    public Body body;
    public FollowPath<Vector2, LinePathParam> followPath;
    // Steering Data
    // Don't go too fast
    float maxLinearSpeed = 10f;
    float maxAngularSpeed = 20f;
    // accellerate and decellerate almost instantly
    float maxLinearAcceleration = 10000.0f;
    float maxAngularAcceleration = 10000.0f;
    float zeroThreshold = 0.1f;
    public SteeringBehavior<Vector2> steeringBehavior;
    private static final SteeringAcceleration<Vector2> STEERING_OUTPUT = new SteeringAcceleration<Vector2>(
            new Vector2());
    private float boundingRadius = 0.10f;
    private boolean tagged = true;
    private boolean independentFacing = false;

    /**
     * Call this to update the steering behaviour (per frame)
     * 
     * @param delta delta time between frames
     */
    public void update(float delta) {
        if (steeringBehavior != null) {
            steeringBehavior.calculateSteering(STEERING_OUTPUT);
            applySteering(STEERING_OUTPUT, delta);
            //Gdx.app.debug(TAG, "Position: " + body.getPosition() + " Steering Output:" + STEERING_OUTPUT.linear);
        }
    }

    @Override
    public void reset() {
        currentMode = SteeringState.STOP;
        body = null;
        steeringBehavior = null;
    }

    /**
     * apply steering to the Box2d body
     * 
     * @param steering  the steering vector
     * @param deltaTime teh delta time
     */
    protected void applySteering(SteeringAcceleration<Vector2> steering, float deltaTime) {
        boolean anyAccelerations = false;

        // Update position and linear velocity.
        if (!STEERING_OUTPUT.linear.isZero()) {
            // this method internally scales the force by deltaTime
            body.applyForceToCenter(STEERING_OUTPUT.linear, true);
            anyAccelerations = true;
        }

        // Update orientation and angular velocity
        if (isIndependentFacing()) {
            if (STEERING_OUTPUT.angular != 0) {
                // this method internally scales the torque by deltaTime
                body.applyTorque(STEERING_OUTPUT.angular, true);
                anyAccelerations = true;
            }
        } else {
            // If we haven't got any velocity, then we can do nothing.
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * deltaTime); // this is superfluous if
                // independentFacing is
                // always true
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        if (anyAccelerations) {
            // Cap the linear speed
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (currentSpeedSquare > (maxLinearSpeed * maxLinearSpeed)) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }
            // Cap the angular speed
            float maxAngVelocity = getMaxAngularSpeed();
            if (body.getAngularVelocity() > maxAngVelocity) {
                body.setAngularVelocity(maxAngVelocity);
            }
        }
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return Utility.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return Utility.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Box2dLocation();
    }

    public boolean isIndependentFacing() {
        return independentFacing;
    }

    public void setIndependentFacing(boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(getPosition(), orientation);
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return zeroThreshold;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        zeroThreshold = value;
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public void setSteeringBehavior(FollowPath<Vector2, LinePathParam> goPath) {
        this.followPath = goPath;
        this.steeringBehavior = goPath;
    }

    public void setPosition(Vector2 startPoint) {
        body.setTransform(startPoint, 0);
    }

    public void setSteeringBehavior(Seek<Vector2> spotSeek) {
        this.steeringBehavior = spotSeek;
    }

    public void setSteeringBehavior(Arrive<Vector2> spotArrive) {
        this.steeringBehavior = spotArrive;
    }

}
