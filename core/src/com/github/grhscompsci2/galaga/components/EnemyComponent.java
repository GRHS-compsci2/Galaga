package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class EnemyComponent implements Component, Poolable {
    public static final int PATH_1 = 0;
    public static final int PATH_2 = 1;
    public static final int PATH_3 = 2;
    public static final int PATH_4 = 3;
    public static final Vector2[] PATHA = {
            new Vector2(10, 10),
            new Vector2(10, 25),
            new Vector2(25, 25),
            new Vector2(25, 10),
    };
    private static final String TAG = EnemyComponent.class.getSimpleName();
    // the path the enemy is on
    private int path;
    // percentage of the path traveled between 0 and 1
    private float curTime;
    // used to push the formation back and forth during idle entry
    private float idleTime;
    // how fast the enemy travels
    private float speed = 100.0f;
    // used to control if the enemy is moving from the end of the path to home
    private boolean inTransit;
    // Is the swarm travelling left?
    private boolean goingLeft;
    // The home position in the formation
    private Vector2 home;
    // array to hold all possible paths
    private Array<LinePath<Vector2>> paths = new Array<LinePath<Vector2>>();
    // array to hold all control points. Needs to be seperate so we can dynamically
    // path to home position
    private Array<Vector2[]> wayPointsArr = new Array<Vector2[]>();
    // Path #4 as defined in "Kat's GDD". Took the main points from the desmos graph
    private Vector2[] path4 = {
            new Vector2(36, 4),
            new Vector2(28, 4),
            new Vector2(20, 10),
            new Vector2(16, 18),
            new Vector2(17, 20.646f),
            new Vector2(20, 22),
            new Vector2(22.4f, 21.2f),
            new Vector2(24, 18),
            new Vector2(22, 14.536f),
            new Vector2(20, 14),
            new Vector2(16, 14)
    };

    /**
     * Initialize the arrays for the paths
     * 
     * @param x the x coordinate of the home position
     * @param y the y coordinate of the home position
     */
    public void initPaths(float x, float y) {
        Vector2[] entryIdle = { new Vector2(x - 5f, y), new Vector2(x + 5f, y) };
        // we will start on the entry path, so not in transit
        inTransit = false;
        // formation starts going right
        goingLeft = false;
        idleTime = 0;
        // add the paths to the control points array
        wayPointsArr.add(path4);
        wayPointsArr.add(entryIdle);
        // set the home position
        home = new Vector2(x, y);
        // create LinePaths (curves) for each path
        for (Vector2[] p : wayPointsArr) {
            LinePath<Vector2> linePath = new LinePath<Vector2>(new Array<Vector2>(p), true);
            linePath.createPath(new Array<Vector2>(p));
            paths.add(linePath);
        }
        path = 0;
    }

    /**
     * Update the current path and reset the path position
     * 
     * @param path the path number
     */
    public void setPath(int path) {
        this.path = path;
        curTime = 0;
    }

    /**
     * gets the current path as a LinePath
     * 
     * @return current path
     */
    public LinePath<Vector2> getPath() {
        return paths.get(path);
    }

    /**
     * returns the speed of the enemy
     * 
     * @return the speed of the enemy
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * resets the current position in the path
     */
    public void startPath() {
        curTime = 0;
    }

    /**
     * increase the current position by the provided amount
     * 
     * @param deltaTime
     */
    public void updateCurTime(float deltaTime) {
        curTime += deltaTime;
        // Gdx.app.debug(TAG, "Current Time:" + curTime);
    }

    public float getCurTime() {
        return curTime;
    }

    /**
     * get the last point of the current path
     * 
     * @return last point as a Vector2 object
     */
    public Vector2 getLastPoint() {
        Vector2[] tmp = wayPointsArr.get(path);
        return tmp[tmp.length - 1];
    }

    /**
     * check to see if we are marked as moving home
     * 
     * @return inTransit boolean
     */
    public boolean isInTransit() {
        return inTransit;
    }

    /**
     * update inTransit to val
     * 
     * @param val the new value of inTransit
     */
    public void setInTransit(boolean val) {
        inTransit = val;
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
        Vector2 extremity = getPath().getEndPoint();
        if (position.dst2(extremity) < tolerance * tolerance) {
            return true;
        }
        return false;
    }

    /**
     * From a given point, plot the direction home
     * 
     * @param position where the enemy is
     * @return the next position
     */
    public void goHome() {
        setPath(1);
    }

    @Override
    public void reset() {

    }
}
