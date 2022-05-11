package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.GdxAI;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.InactiveComponent;
import com.github.grhscompsci2.galaga.ashley.components.SteeringComponent;

public class SteeringSystem extends IteratingSystem {
    @SuppressWarnings("unchecked")
    public SteeringSystem() {
        super(Family.all(SteeringComponent.class)
        .exclude(InactiveComponent.class)
        .get());
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        GdxAI.getTimepiece().update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SteeringComponent steer = K2ComponentMappers.steering.get(entity);
        steer.update(deltaTime);
    }
}
