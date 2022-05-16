package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.RotationComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

/**
 * Created by barry on 12/27/15 @ 10:48 PM.
 */
public class RotationSystem extends IteratingSystem {

  // Holds our queue for rotating. Like potatoes that spin.
  private Array<Entity> rotatoes;

  public RotationSystem() {
    super(Family.all(RotationComponent.class, TransformComponent.class).get());
    rotatoes = new Array<>();
  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);

    for (Entity rotato : rotatoes) {
      TransformComponent tc = K2ComponentMappers.transform.get(rotato);
      RotationComponent rc = K2ComponentMappers.rotation.get(rotato);

      float adjust = rc.rotationSpeed * deltaTime;
      if (!rc.hasTargetRotation) {
        tc.rotation += adjust;
      } else {
        float newRotation = tc.rotation + adjust;
        boolean isAtTarget = false;
        if (rc.rotationSpeed < 0f && newRotation <= rc.targetRotation) {
          tc.rotation = Math.max(newRotation, rc.targetRotation);
          isAtTarget = true;
        } else if (rc.rotationSpeed > 0f && newRotation >= rc.targetRotation) {
          tc.rotation = Math.min(newRotation, rc.targetRotation);
          isAtTarget = true;
        } else {
          tc.rotation = newRotation;
        }

        // We're done here
        if (isAtTarget) {
          rotato.remove(RotationComponent.class);
        }
      }
    }
    rotatoes.clear();
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    rotatoes.add(entity);
  }
}
