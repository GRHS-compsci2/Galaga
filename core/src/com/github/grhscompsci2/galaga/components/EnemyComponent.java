package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class EnemyComponent implements Component, Poolable {

    private static final String TAG = EnemyComponent.class.getSimpleName();
    // used to push the formation back and forth during idle entry
    private float idleTime;
    private boolean goingLeft;
    // The home position in the formation
    private Vector2 home;
    // array to hold all possible paths
    private LinePath<Vector2> path;

    /**
     * Initialize the arrays for the paths
     * 
     * @param x the x coordinate of the home position
     * @param y the y coordinate of the home position
     */
    public void initPaths(float x, float y, LinePath<Vector2> entryPath) {
        // formation starts going right
        goingLeft = false;
        idleTime = 0;
        // set the home position
        home = new Vector2(x, y);
        // create LinePaths (curves) for each path
        path = entryPath;
    }

    /**
     * gets the current path as a LinePath
     * 
     * @return current path
     */
    public LinePath<Vector2> getPath() {
        return path;
    }

    /**
     * get the last point of the current path
     * 
     * @return last point as a Vector2 object
     */
    public Vector2 getLastPoint() {
        return path.getEndPoint();
    }

    /**
     * This method will update the position of the formation, so all enemies can
     * move in sync
     * 
     * @param deltaTime the elapsed time
     * @return the new position of the enemy
     */
    public Vector2 updateFormation(float deltaTime) {
        Vector2 position = new Vector2();
        idleTime += deltaTime;

        float x = (idleTime - 2) * 2;
        if (goingLeft) {
            x *= -1;
        }
        if (idleTime > 4) {
            idleTime = 0;
            goingLeft = !goingLeft;
        }
        position.set(x, 0);

        return position.add(home);
    }

    /**
     * Check to see if we are close enough to home
     * 
     * @param vector2
     */
    public boolean areWeThereYet(float tolerance, Vector2 position) {
        Vector2 extremity = path.getEndPoint();
        if (position.dst2(extremity) < tolerance * tolerance) {
            Gdx.app.debug(TAG, "Close Enough! " + position.dst2(extremity));
            return true;
        }
        return false;
    }

    @Override
    public void reset() {

    }

    public Vector2 getHome() {
        return home;
    }
}
