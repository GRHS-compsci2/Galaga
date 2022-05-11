package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.K2MathUtil;
import com.github.grhscompsci2.galaga.ashley.components.*;

import java.util.Random;

/**
 * Created by barry on 4/26/16 @ 7:48 PM.
 */
public class ScreenWrapSystem extends IteratingSystem {

  Random r = new Random();

  private float left;
  private float bottom;
  private float right;
  private float top;
  private float ppm;

  public ScreenWrapSystem(Vector2 minBounds, Vector2 maxBounds, float pixelsPerUnit) {
    super(Family.all(TransformComponent.class, VelocityComponent.class, ScreenWrapComponent.class)
        .one(BoundsComponent.class, TextureComponent.class).get());
    this.left = minBounds.x;
    this.right = maxBounds.x;
    this.bottom = minBounds.y;
    this.top = maxBounds.y;

    this.ppm = pixelsPerUnit;
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

    BoundsComponent bc = K2ComponentMappers.bounds.get(entity);
    VelocityComponent vc = K2ComponentMappers.velocity.get(entity);
    TransformComponent tc = K2ComponentMappers.transform.get(entity);
    ScreenWrapComponent swc = K2ComponentMappers.screenWrap.get(entity);

    if (vc.speed.x == 0f && vc.speed.y == 0f) {
      return;
    }

    // TODO: UnF*%& This logic. There has to be a cleaner approach. Too much
    // duplicated code from a game-jam
    boolean isResetting = false;
    TextureComponent txc = K2ComponentMappers.texture.get(entity);
    if (bc == null) {
      if (txc.region != null) {

        if (swc.mode == ScreenWrapMode.HORIZONTAL) {
          float regionWidthInMeters = txc.region.getRegionWidth() / ppm;
          boolean isOffRight = tc.position.x - (regionWidthInMeters / 2f) > right;
          boolean isOffLeft = (regionWidthInMeters / 2f) + tc.position.x < left;

          float y = swc.shouldRandomizePerpendicularPosition ? K2MathUtil.getRandomInRange(swc.minPos, swc.maxPos)
              : tc.position.y;

          if (!swc.isReversed && isOffRight) {
            tc.position.set(left - (regionWidthInMeters / 2f) - swc.wrapOffset, y, tc.position.z);
            isResetting = true;
          } else if (swc.isReversed && isOffLeft) {
            tc.position.set(right + (regionWidthInMeters / 2f) + swc.wrapOffset, y, tc.position.z);
            isResetting = true;
          }
        } else {
          float x = swc.shouldRandomizePerpendicularPosition ? K2MathUtil.getRandomInRange(swc.minPos, swc.maxPos)
              : tc.position.x;
          float regionHeightInUnits = txc.region.getRegionHeight() / ppm;
          boolean isAboveTop = tc.position.y - (regionHeightInUnits / 2f) > top;
          boolean isBelowBottom = tc.position.y + (regionHeightInUnits / 2f) < bottom;
          if (!swc.isReversed && isAboveTop) {
            tc.position.set(x, top - (regionHeightInUnits / 2f) - swc.wrapOffset, tc.position.z);
            isResetting = true;
          } else if (swc.isReversed && isBelowBottom) {
            tc.position.set(x, top + (regionHeightInUnits / 2f) + swc.wrapOffset, tc.position.z);
            isResetting = true;
          }
        }
      }
    } else {
      if (swc.mode == ScreenWrapMode.HORIZONTAL) {

        float y = swc.shouldRandomizePerpendicularPosition ? K2MathUtil.getRandomInRange(swc.minPos, swc.maxPos)
            : tc.position.y;

        if (bc.bounds.x > right && !swc.isReversed) {
          tc.position.set(left - (bc.bounds.width / 2f) - swc.wrapOffset, y, tc.position.z);
          isResetting = true;
        } else if (bc.bounds.x + bc.bounds.width < left && swc.isReversed) {
          tc.position.set(right + (bc.bounds.width / 2f) + swc.wrapOffset, y, tc.position.z);
          isResetting = true;
        }
      } else {
        float x = swc.shouldRandomizePerpendicularPosition ? K2MathUtil.getRandomInRange(swc.minPos, swc.maxPos)
            : tc.position.x;
        if (bc.bounds.y > top && !swc.isReversed) {
          tc.position.set(x, bottom - (bc.bounds.width / 2f) - swc.wrapOffset, tc.position.z);
          isResetting = true;
        } else if (bc.bounds.y + bc.bounds.height < bottom && swc.isReversed) {
          tc.position.set(x, top + (bc.bounds.height / 2f) + swc.wrapOffset, tc.position.z);
          isResetting = true;
        }
      }
    }

    if (isResetting && swc.shouldRandomizeTexture) {
      txc.setRegion(swc.possibleRegions.get(r.nextInt(swc.possibleRegions.size)));
    }
  }
}