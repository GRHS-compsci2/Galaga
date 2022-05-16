package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.PlayerComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.entities.bullets.BulletFactory;
import com.github.grhscompsci2.galaga.gdx.helpers.IGameProcessor.ScreenType;

public class PlayerControlSystem extends IteratingSystem {
  private String TAG = PlayerControlSystem.class.getSimpleName();

  KeyboardController controller;
  MyGdxGame parentGdxGame;
  BulletFactory bulletFactory;

  public PlayerControlSystem(KeyboardController keyCon, MyGdxGame game, BulletFactory bulletFactory) {
    super(Family.all(PlayerComponent.class).get());
    this.parentGdxGame = game;
    this.controller = keyCon;
    this.bulletFactory = bulletFactory;
  }

  @Override
  protected void processEntity(Entity entity, float deltaTime) {
    BodyComponent b2body = K2ComponentMappers.body.get(entity);
    PlayerComponent player = K2ComponentMappers.player.get(entity);

    if (controller.left) {
      b2body.body.setLinearVelocity((player.speed * -1), 0);
    }
    if (controller.right) {
      b2body.body.setLinearVelocity(player.speed, 0);
    }

    if (!controller.left && !controller.right) {
      b2body.body.setLinearVelocity(0, 0);
    }
    if (controller.esc) {
      controller.esc = false;
      parentGdxGame.switchScreens(ScreenType.Pause);
    }

    if (controller.spacebar && bulletFactory.getNumPlayerBullets() < 3
        && (player.timeSinceLastShot >= player.shootDelay || bulletFactory.getNumPlayerBullets() == 0)) {
      TextureComponent tex = K2ComponentMappers.texture.get(entity);
      float initialX = b2body.body.getPosition().x;
      float initialY = b2body.body.getPosition().y + tex.region.getRegionHeight();

      bulletFactory.playerFire(new Vector2(initialX, initialY), 0f, 800f);

      player.timeSinceLastShot = 0;
      player.numMissiles++;
    }

    if (bulletFactory.getNumPlayerBullets() > 0 && player.timeSinceLastShot <= player.shootDelay) {
      player.timeSinceLastShot += deltaTime;
    }
  }

}