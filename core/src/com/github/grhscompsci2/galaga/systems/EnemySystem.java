package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.ai.SteeringPresets;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.SteeringComponent;
import com.github.grhscompsci2.galaga.components.SteeringComponent.SteeringState;

public class EnemySystem extends IteratingSystem {
    private static String TAG = EnemySystem.class.getSimpleName();

    @SuppressWarnings("unchecked")
    public EnemySystem() {
        // get all of the entites with the enemy component
        super(Family.all(EnemyComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        // grab the specific enemy and body components for the entity
        StateComponent stateComponent = Mapper.stateCom.get(entity);
        // decide what to do based on the entity's state
        switch (stateComponent.getState()) {
            case StateComponent.STATE_ENTRY:
                entry(entity);
                break;
            case StateComponent.STATE_ENTRY_IDLE:
                entryIdle(entity);
                break;
        }
    }

    private void entry(Entity entity) {
        SteeringComponent steeringComponent = Mapper.sCom.get(entity);
        EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
        StateComponent stateComponent = Mapper.stateCom.get(entity);
        if (steeringComponent.currentMode != SteeringState.GO) {
            steeringComponent.setSteeringBehavior(SteeringPresets.goPath(steeringComponent, enemyComponent.getPath()));
            steeringComponent.setPosition(enemyComponent.getPath().getStartPoint());
            steeringComponent.currentMode = SteeringState.GO;
        }
        if (enemyComponent.areWeThereYet(steeringComponent.followPath.getArrivalTolerance(),
                steeringComponent.getPosition())) {
            stateComponent.set(StateComponent.STATE_ENTRY_IDLE);
            steeringComponent.currentMode = SteeringState.STOP;
        }

    }

    private void entryIdle(Entity entity) {
        SteeringComponent steeringComponent = Mapper.sCom.get(entity);
        EnemyComponent enemyComponent = Mapper.enemyCom.get(entity);
        // StateComponent stateComponent = Mapper.stateCom.get(entity);
        if (steeringComponent.currentMode != SteeringState.GO) {
            steeringComponent
                    .setSteeringBehavior(SteeringPresets.goPoint(steeringComponent, enemyComponent.getHome()));
            steeringComponent.currentMode = SteeringState.GO;
        }

    }
}
