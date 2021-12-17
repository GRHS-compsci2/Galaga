package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;

public class EnemySystem extends IteratingSystem {
    private static String TAG = EnemySystem.class.getSimpleName();
    // Component Mappers
    private ComponentMapper<EnemyComponent> eCMapper;
    private ComponentMapper<B2dBodyComponent> bodm;
    private ComponentMapper<StateComponent> sm;

    @SuppressWarnings("unchecked")
    public EnemySystem() {
        // get all of the entites with the enemy component
        super(Family.all(EnemyComponent.class).get());
        // initialize the component mappers
        eCMapper = ComponentMapper.getFor(EnemyComponent.class);
        bodm = ComponentMapper.getFor(B2dBodyComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // grab the specific enemy and body components for the entity
        EnemyComponent enemyComponent = eCMapper.get(entity);
        StateComponent stateComponent = sm.get(entity);
        Vector2 idle = enemyComponent.updateFormation(deltaTime);

        // decide what to do based on the entity's state
        switch (stateComponent.getState()) {
            case StateComponent.STATE_ENTRY:
                entry(entity, deltaTime);
                break;
            case StateComponent.STATE_ENTRY_IDLE:
                entryIdle(entity, deltaTime, idle);
                break;

        }

    }

    private void entry(Entity entity, float deltaTime) {
        EnemyComponent enemyComponent = eCMapper.get(entity);
        B2dBodyComponent bodyComponent = bodm.get(entity);
        StateComponent stateComponent = sm.get(entity);
        // holds the new position
        final Vector2 out = new Vector2();
        // holds the new angle
        final Vector2 tmpV2 = new Vector2();
        // decide to switch paths
        // calculate the speed
        enemyComponent.getPath().derivativeAt(out, enemyComponent.getCurTime());
        float increment = (deltaTime * enemyComponent.getSpeed() / enemyComponent.getPath().spanCount) / out.len();
        // update the current position in the path using the speed
        enemyComponent.updateCurTime(increment);
        // update the out vector with the new position
        enemyComponent.getPath().valueAt(out, enemyComponent.getCurTime());
        // update tmpV2 with the new angle
        enemyComponent.getPath().derivativeAt(tmpV2, enemyComponent.getCurTime());
        // set the body to the new position.
        bodyComponent.body.setTransform(out, tmpV2.angleRad() + MathUtils.degRad * -90);
        // Gdx.app.debug(TAG, "Position:" + bodyComponent.body.getPosition());
        if (enemyComponent.getCurTime() > 1) {
            stateComponent.set(StateComponent.STATE_ENTRY_IDLE);
            enemyComponent.setInTransit(true);
        }
    }

    private void swarming(Entity entity) {

    }

    private void entryIdle(Entity entity, float deltaTime, Vector2 idle) {
        EnemyComponent enemyComponent = eCMapper.get(entity);
        B2dBodyComponent bodyComponent = bodm.get(entity);
        Vector2 next = new Vector2();
        if (enemyComponent.isInTransit()) {
            next = enemyComponent.goHome(bodyComponent.body.getPosition());
            bodyComponent.body.setTransform(bodyComponent.body.getPosition().add(next), next.angleRad());
            enemyComponent.areWeThereYet(bodyComponent.body.getPosition());
        } else {
            bodyComponent.body.setTransform(idle, 0);
        }
    }
}
