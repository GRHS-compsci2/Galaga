package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.components.AnimationComponent;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;

public class StateSystem extends IteratingSystem {
    ComponentMapper<AnimationComponent> am;
    ComponentMapper<StateComponent> sm;
    ComponentMapper<B2dBodyComponent> bm;
    
    public StateSystem() {
        super(Family.all(StateComponent.class).get());
        am = ComponentMapper.getFor(AnimationComponent.class);
        sm = ComponentMapper.getFor(StateComponent.class);
        bm = ComponentMapper.getFor(B2dBodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AnimationComponent ani = am.get(entity);
        StateComponent state = sm.get(entity);

        if (state.getState() == StateComponent.STATE_HIT) {
            //is there an animation for a hit?
            if (ani.animations.containsKey(state.getState())) {
                if (ani.animations.get(state.getState()).isAnimationFinished(state.time)) {
                    state.set(StateComponent.STATE_DEAD);
                }
            }
            else{
                state.set(StateComponent.STATE_DEAD);
            }

        } else if (state.getState() == StateComponent.STATE_DEAD) {
            getEngine().removeEntity(entity);
        }
    }
}
