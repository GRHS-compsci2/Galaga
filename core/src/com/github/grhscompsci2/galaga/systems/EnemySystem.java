package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;

public class EnemySystem extends IteratingSystem {
    private static String TAG = EnemySystem.class.getSimpleName();
    // Component Mappers
    private ComponentMapper<EnemyComponent> eCMapper;
    private ComponentMapper<B2dBodyComponent> bodm;

    // Array to store all of the paths: swoop, attack, challenge stages
    private Array<CatmullRomSpline<Vector2>> paths = new Array<CatmullRomSpline<Vector2>>();

    // holds the new position
    final Vector2 out = new Vector2();
    // holds the new angle
    final Vector2 tmpV2 = new Vector2();

    @SuppressWarnings("unchecked")
    public EnemySystem() {
        // get all of the entites with the enemy component
        super(Family.all(EnemyComponent.class).get());
        // initialize the component mappers
        eCMapper = ComponentMapper.getFor(EnemyComponent.class);
        bodm = ComponentMapper.getFor(B2dBodyComponent.class);

        // this path is actually path #4. Took the main points from the desmos graph
        Vector2[] points = {
                new Vector2(36, 4),
                new Vector2(28, 4),
                new Vector2(16, 18),
                new Vector2(20, 22),
                new Vector2(24, 18),
                new Vector2(20, 14),
                new Vector2(10, 20)
        };
        // create a new spline using the points above
        CatmullRomSpline<Vector2> myCatmull = new CatmullRomSpline<Vector2>(points, false);
        // add spline to the path array
        paths.add(myCatmull);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // grab the specific enemy and body components for the entity
        EnemyComponent enemyComponent = eCMapper.get(entity);
        B2dBodyComponent bodyComponent = bodm.get(entity);
        // calculate the speed
        paths.get(enemyComponent.getPath()).derivativeAt(out, enemyComponent.getTime());
        float increment = (deltaTime * enemyComponent.getSpeed() / paths.get(enemyComponent.getPath()).spanCount)
                / out.len();
        // update the current position in the path using the speed
        enemyComponent.updateCurTime(increment);
        // update the out vector with the new position
        paths.get(enemyComponent.getPath()).valueAt(out, enemyComponent.getTime());
        // update tmpV2 with the new angle
        paths.get(enemyComponent.getPath()).derivativeAt(tmpV2, enemyComponent.getTime());
        // set the body to the new position.
        bodyComponent.body.setTransform(out, tmpV2.angleRad() + MathUtils.degRad * -90);
    }
}
