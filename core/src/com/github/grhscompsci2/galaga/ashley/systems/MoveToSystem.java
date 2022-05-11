package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.MoveToComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

/**
 * Created by barry on 4/12/16 @ 7:32 PM.
 */
public class MoveToSystem extends IteratingSystem {

  public MoveToSystem() {
    super(Family.all(TransformComponent.class, MoveToComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    TransformComponent tc = K2ComponentMappers.transform.get(entity);
    MoveToComponent mc = K2ComponentMappers.moveTo.get(entity);

    if (!mc.hasArrived) {

      boolean isLeftOfTarget = tc.position.x < mc.target.x;
      boolean isBelowTarget = tc.position.y < mc.target.y;
      boolean isBehindTarget = tc.position.z < mc.target.z;

      float maxAdjust = mc.speed * deltaTime;

      float x, y, z;
      x = Math.min(maxAdjust, Math.abs(tc.position.x - mc.target.x));
      y = Math.min(maxAdjust, Math.abs(tc.position.y - mc.target.y));
      z = Math.min(maxAdjust, Math.abs(tc.position.z - mc.target.z));

      if (!isLeftOfTarget) {
        x *= -1f;
      }
      if (!isBelowTarget) {
        y *= -1f;
      }
      if (!isBehindTarget) {
        z *= -1f;
      }

      tc.position.set(tc.position.x + x, tc.position.y + y, tc.position.z);

      if (tc.position.equals(mc.target)) {
        mc.setArrived(true);
      }
    } else {
      entity.remove(mc.getClass());
    }
  }
}