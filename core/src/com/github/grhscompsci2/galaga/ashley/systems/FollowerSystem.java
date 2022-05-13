package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.VectorUtils;
import com.github.grhscompsci2.galaga.ashley.components.FollowMode;
import com.github.grhscompsci2.galaga.ashley.components.FollowerComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

/**
 * Created by barry on 4/26/16 @ 7:31 PM.
 */
public class FollowerSystem extends IteratingSystem {

  private Vector2 moveToAdjustment = new Vector2();

  Array<Entity> queue;
  Family familyToWatchForRemovals;

  public FollowerSystem(Family familyToWatchForRemovals) {
    super(Family.all(FollowerComponent.class).get());
    this.queue = new Array<>();
    this.familyToWatchForRemovals = familyToWatchForRemovals;
  }

  EntityListener el;

  @Override
  public void addedToEngine(Engine engine) {
    super.addedToEngine(engine);

    final Engine eg = engine;
    if (el == null) {
      el = new EntityListener() {
        public void entityAdded(Entity entity) {
        }

        @Override
        public void entityRemoved(Entity entity) {
          for (Entity follower : eg.getEntitiesFor(Family.all(FollowerComponent.class).get())) {
            FollowerComponent fc = K2ComponentMappers.follower.get(follower);
            if (fc != null && fc.target == entity) {
              fc.target = null;
              follower.removeAll();
              eg.removeEntity(follower);
            }
          }

        }
      };
    }

    engine.addEntityListener(familyToWatchForRemovals, el);
  }

  @Override
  public void removedFromEngine(Engine engine) {
    super.removedFromEngine(engine);
    if (el != null) {
      engine.removeEntityListener(el);
    }
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

    FollowerComponent fc = K2ComponentMappers.follower.get(entity);
    if (fc.target != null) {
      TransformComponent targetPos = K2ComponentMappers.transform.get(fc.target);
      if (targetPos != null) {
        TransformComponent tc = K2ComponentMappers.transform.get(entity);
        Vector2 offset = VectorUtils.rotateVector(fc.offset, targetPos.rotation);

        switch (fc.followMode) {
          case STICKY:
            tc.position.set(targetPos.position.x + offset.x,
                targetPos.position.y + offset.y,
                tc.position.z);
            if (fc.shouldMatchOpacity) {
              tc.setOpacity(targetPos.tint.a);
            }
            if (fc.shouldMatchParentRotation) {
              tc.setRotation(fc.baseRotation + targetPos.rotation);
            }
            break;
          case MOVETO:
            processMoveToAdjustment(deltaTime, fc, targetPos, tc, offset);
            break;
          case MOVETOSTICKY:
            processMoveToAdjustment(deltaTime, fc, targetPos, tc, offset);
            if (tc.position.x == (targetPos.position.x + offset.x) &&
                tc.position.y == (targetPos.position.y + offset.y)) {
              fc.setMode(FollowMode.STICKY);
            }
            break;
          default:
            break;
        }
      }
    }
  }

  private void processMoveToAdjustment(float deltaTime, FollowerComponent fc, TransformComponent targetPos,
      TransformComponent tc, Vector2 offset) {
    moveToAdjustment.set(targetPos.position.x + offset.x, targetPos.position.y + offset.y);
    moveToAdjustment.sub(tc.position.x, tc.position.y)
        .nor()
        .scl(fc.followSpeed * deltaTime);

    float newX = tc.position.x + moveToAdjustment.x;
    float newY = tc.position.y + moveToAdjustment.y;

    if (tc.position.x < targetPos.position.x + offset.x) {
      newX = MathUtils.clamp(newX, tc.position.x, targetPos.position.x + offset.x);
    } else {
      newX = MathUtils.clamp(newX, targetPos.position.x + offset.x, tc.position.x);
    }

    if (tc.position.y < targetPos.position.y + offset.y) {
      newY = MathUtils.clamp(newY, tc.position.y, targetPos.position.y + offset.y);
    } else {
      newY = MathUtils.clamp(newY, targetPos.position.y + offset.y, tc.position.y);
    }

    tc.position.set(newX, newY, tc.position.z);

    if (fc.shouldMatchOpacity) {
      tc.setOpacity(targetPos.tint.a);
    }

    if (fc.shouldMatchParentRotation) {
      tc.setRotation(fc.baseRotation + targetPos.rotation);
    }
  }
}