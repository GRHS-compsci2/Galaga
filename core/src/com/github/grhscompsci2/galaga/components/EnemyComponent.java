package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class EnemyComponent implements Component {
    public static final int PATH_1 = 0;
    public static final int PATH_2 = 1;
    public static final int PATH_3 = 2;
    public static final int PATH_4 = 3;
    private static final String TAG = EnemyComponent.class.getSimpleName();

    private int path;
    private float curTime;
    private float idleTime;
    private float speed = 10.0f;
    private boolean inTransit;
    private boolean goingLeft;
    private Vector2 home;
    private Array<CatmullRomSpline<Vector2>> paths = new Array<CatmullRomSpline<Vector2>>();

    // this path is actually path #4. Took the main points from the desmos graph
    private Array<Vector2[]> controlPoints = new Array<Vector2[]>();
    private Vector2[] path4 = {
            new Vector2(36, 4),
            new Vector2(36, 4),
            new Vector2(28, 4),
            new Vector2(20,10),
            new Vector2(16, 18),
            new Vector2(17,20.646f),
            new Vector2(20, 22),
            new Vector2(22.4f,21.2f),
            new Vector2(24, 18),
            new Vector2(22, 14.536f),
            new Vector2(20, 14),
            new Vector2(16, 14),
            new Vector2(16, 14)
    };

    public void initPaths(float x, float y) {

        inTransit=false;
        goingLeft=false;
        idleTime=0;
        controlPoints.add(path4);
        home=new Vector2(x,y);
        for (Vector2[] p : controlPoints) {
            CatmullRomSpline<Vector2> myCatmull = new CatmullRomSpline<Vector2>(p, false);
            paths.add(myCatmull);
        }
        // create a new spline using the points above
    }

    public void setPath(int path) {
        this.path = path;
        curTime = 0;
    }

    public CatmullRomSpline<Vector2> getPath() {
        return paths.get(path);
    }

    public float getSpeed() {
        return speed;
    }

    public void startPath() {
        curTime = 0;
    }

    public void updateCurTime(float deltaTime) {
        curTime += deltaTime;
        //Gdx.app.debug(TAG, "Current Time:" + curTime);
    }

    public float getCurTime() {
        return curTime;
    }

    public Vector2 getLastPoint() {
        Vector2[] tmp = controlPoints.get(path);
        return tmp[tmp.length - 1];
    }

    public boolean isInTransit(){
        return inTransit;
    }

    public void setInTransit(boolean val) {
        inTransit=val;
    }

    public Vector2 updateFormation(float deltaTime) {
        Vector2 position=new Vector2();
        idleTime+=deltaTime;
        
        float x=(idleTime-2)*2;
        if(goingLeft){
            x*=-1;
        }
        if(idleTime>4){
            idleTime=0;
            goingLeft=!goingLeft;
        }
        position.set(x, 0);

        return position.add(home);
    }

    public void areWeThereYet(Vector2 vector2) {
        if(home.dst(vector2)<1){
            inTransit=false;
        }
    }

    public Vector2 goHome(Vector2 position) {
        Vector2 retVal=new Vector2(.25f,.25f);
        float diffx=home.x-position.x;
        float diffy=home.y-position.y;
        float theta = (float)(180.0 / Math.PI * Math.atan2(diffy,diffx));
        retVal.setLength(.25f);
        retVal.setAngleDeg(theta);
        return retVal;
    }
}
