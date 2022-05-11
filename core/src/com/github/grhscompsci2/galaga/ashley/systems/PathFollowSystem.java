package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.PathFollowComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

/**
 * Created by barry on 2/29/16 @ 6:24 PM.
 */
public class PathFollowSystem extends IteratingSystem {

  public PathFollowSystem() {
    super(Family.all(TransformComponent.class, PathFollowComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    PathFollowComponent pc = K2ComponentMappers.pathFollow.get(entity);
    if (!pc.isPaused && pc.path != null) {

      float adjust = pc.speed * deltaTime;
      float newPosition = Math.min(pc.pathPosition + adjust, 1f);
      if (newPosition == 1f) {
        pc.setPaused(true);
      }
      pc.setPathPosition(newPosition);

      if (pc.shouldRemoveWhenComplete && pc.pathPosition >= 1f) {
        getEngine().removeEntity(entity);
        return;
      }

      pc.path.valueAt(pc.point, pc.pathPosition);

      TransformComponent tc = K2ComponentMappers.transform.get(entity);
      tc.setPosition(pc.point.x, pc.point.y);

      if (pc.isFacingPath) {
        pc.path.derivativeAt(pc.point, pc.pathPosition);
        // We subtract 90 degrees to our angle, because the angle
        // starts against the x axis instead of the Y
        // 0° → instead of ↑
        tc.rotation = pc.baseRotation + (pc.point.angle() - 90f);
      }
    }
  }
}
