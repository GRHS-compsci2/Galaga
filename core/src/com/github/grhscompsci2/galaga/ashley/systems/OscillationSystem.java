package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.OscillationComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

/**
 * Created by barry on 4/26/16 @ 7:40 PM.
 */
public class OscillationSystem extends IteratingSystem {

  public OscillationSystem() {
    super(Family.all(OscillationComponent.class, TransformComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    OscillationComponent oc = K2ComponentMappers.oscillation.get(entity);
    TransformComponent tc = K2ComponentMappers.transform.get(entity);

    boolean isAboveMax = tc.rotation >= oc.maxRotation;
    boolean isBelowMin = tc.rotation <= oc.minRotation;

    if (isAboveMax || isBelowMin) {
      if (isAboveMax) {
        tc.setRotation(oc.maxRotation);
      } else {
        tc.setRotation(oc.minRotation);
      }
      oc.isClockwise = !oc.isClockwise;
    }

    float direction = oc.isClockwise ? -1 : 1;
    float adjustment = oc.rotationSpeed * direction * deltaTime;
    tc.setRotation(tc.rotation + adjustment);
  }
}