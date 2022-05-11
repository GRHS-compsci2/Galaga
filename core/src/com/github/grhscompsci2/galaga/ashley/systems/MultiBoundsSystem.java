package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.VectorUtils;
import com.github.grhscompsci2.galaga.ashley.components.*;

/**
 * Created by barry on 4/16/16 @ 2:54 PM.
 */
public class MultiBoundsSystem extends IteratingSystem {
  public MultiBoundsSystem() {
    super(Family.all(TransformComponent.class, MultiBoundsComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    TransformComponent tfm = K2ComponentMappers.transform.get(entity);

    float xOffsetAdjust = tfm.scale.x >= 0f ? 1f : -1f;
    float yOffsetAdjust = tfm.scale.y >= 0f ? 1f : -1f;

    MultiBoundsComponent mbc = K2ComponentMappers.multiBounds.get(entity);

    for (Bound bound : mbc.bounds) {

      Vector2 rotatedOffset = bound.offset.cpy().scl(xOffsetAdjust, yOffsetAdjust);
      if (tfm.rotation != 0f) {
        rotatedOffset = VectorUtils.rotateVector(rotatedOffset, tfm.rotation);
      }
      if (bound.isCircle) {
        bound.circle.x = tfm.position.x + tfm.originOffset.x + rotatedOffset.x;
        bound.circle.y = tfm.position.y + tfm.originOffset.y + rotatedOffset.y;
      } else {
        bound.rect.x = tfm.position.x - (bound.rect.width * 0.5f) +
            tfm.originOffset.x + rotatedOffset.x;
        bound.rect.y = tfm.position.y - (bound.rect.height * 0.5f) +
            tfm.originOffset.y + rotatedOffset.y;
      }
    }
  }
}
