package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;

public class StateSystem extends IteratingSystem {

    public StateSystem() {
        super(Family.all(StateComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent ani = Mapper.animCom.get(entity);
        StateComponent state = Mapper.stateCom.get(entity);

        if (state.getState() == StateComponent.STATE_HIT) {
            // is there an animation for a hit?
            if (ani.animations.containsKey(state.getState())) {
                if (ani.animations.get(state.getState()).isAnimationFinished(state.time)) {
                    state.set(StateComponent.STATE_DEAD);
                }
            } else {
                state.set(StateComponent.STATE_DEAD);
            }

        } else if (state.getState() == StateComponent.STATE_DEAD) {
            getEngine().removeEntity(entity);
        }
    }
}
