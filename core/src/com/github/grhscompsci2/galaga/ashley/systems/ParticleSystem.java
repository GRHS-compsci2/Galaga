package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.K2MathUtil;
import com.github.grhscompsci2.galaga.ashley.VectorUtils;
import com.github.grhscompsci2.galaga.ashley.components.*;

import java.util.Random;

/**
 * System to spawn and clean-up particles
 */
public class ParticleSystem extends IteratingSystem {

  private static Random r = new Random();

  public ParticleSystem() {
    super(Family.all(TransformComponent.class)
        .one(ParticleEmitterComponent.class, ParticleComponent.class).get());
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {

    if (K2ComponentMappers.particle.has(entity)) {
      // Release particles
      ParticleComponent pc = K2ComponentMappers.particle.get(entity);
      pc.timeAlive += deltaTime;
      if (pc.timeAlive >= pc.lifespan) {
        getEngine().removeEntity(entity);
      }

    } else if (K2ComponentMappers.particleEmitter.has(entity)) {
      ParticleEmitterComponent pc = K2ComponentMappers.particleEmitter.get(entity);
      if (pc.isPaused) {
        return;
      }

      TransformComponent tc = K2ComponentMappers.transform.get(entity);

      for (int i = 0; i < pc.spawnRate; i++) {
        spawnParticle(pc, tc);
      }

      float secsBetweenSpawns = 1f / pc.spawnRate;
      pc.elapsedTime += deltaTime;

      // float timeThisSpawnBlock = pc.elapsedTime - pc.lastSpawnTime;
      // if (timeThisSpawnBlock >= secsBetweenSpawns) {
      // int numberToSpawnThisInterval = (int) Math.ceil(pc.spawnRate *
      // timeThisSpawnBlock);
      //
      // for (int i = 0; i < numberToSpawnThisInterval; i++) {
      // spawnParticle(pc, tc);
      // }
      //
      // pc.lastSpawnTime = pc.elapsedTime;
      // }

      // Once it is done, remove
      if (!pc.isLooping && pc.elapsedTime >= pc.duration) {
        entity.remove(pc.getClass());
      }
    }
  }

  Vector2 upNorm = new Vector2(0f, 1f).nor();

  private void spawnParticle(ParticleEmitterComponent pc, TransformComponent tc) {

    PooledEngine engine = (PooledEngine) getEngine();
    Entity particle = engine.createEntity();

    float x = tc.position.x;
    float y = tc.position.y;
    float scale = K2MathUtil.getRandomInRange(pc.particleMinMaxScale.x, pc.particleMinMaxScale.y);
    if (pc.spawnType == ParticleSpawnType.RANDOM_IN_BOUNDS) {
      x += K2MathUtil.getRandomInRange(-pc.particleSpawnRange.x, pc.particleSpawnRange.x);
      y += K2MathUtil.getRandomInRange(-pc.particleSpawnRange.y, pc.particleSpawnRange.y);
    }

    particle.add(TransformComponent.create(engine)
        .setPosition(x, y, pc.zIndex)
        .setScale(scale, scale));

    float lifeSpan = K2MathUtil.getRandomInRange(pc.particleMinMaxLifespans.x, pc.particleMinMaxLifespans.y); // (r.nextFloat()
                                                                                                              // *
                                                                                                              // (pc.particleMinMaxLifespans.y
                                                                                                              // -
                                                                                                              // pc.particleMinMaxLifespans.x))
                                                                                                              // +
                                                                                                              // pc.particleMinMaxLifespans.x;
    particle.add(ParticleComponent.create(engine)
        .setLifespan(lifeSpan));

    float angle = tc.rotation + (pc.angleRange.getBetween(r.nextFloat()));
    float particleSpeed = K2MathUtil.getRandomInRange(pc.particleMinMaxSpeeds.x, pc.particleMinMaxSpeeds.y);
    Vector2 speed = VectorUtils.rotateVector(upNorm.cpy(), angle).scl(particleSpeed);
    particle.add(VelocityComponent.create(engine)
        .setSpeed(speed.x, speed.y));

    particle.add(TextureComponent.create(engine)
        .setRegion(pc.particleImages.get(r.nextInt(pc.particleImages.size))));

    if (pc.shouldFade) {
      particle.add(FadingComponent.create(engine)
          .setPercentPerSecond(100f / lifeSpan));
    }

    getEngine().addEntity(particle);
  }
}