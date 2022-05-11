package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.BulletManager;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.MyGdxGame.ScreenType;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.InactiveComponent;
import com.github.grhscompsci2.galaga.ashley.components.PlayerComponent;

public class PlayerControlSystem extends IteratingSystem {
  private String TAG = PlayerControlSystem.class.getSimpleName();

  KeyboardController controller;
  MyGdxGame parentGdxGame;
  BulletManager bulMan;

  public PlayerControlSystem(KeyboardController keyCon, MyGdxGame game, BulletManager bulMan) {
    super(Family.all(PlayerComponent.class)
        .exclude(InactiveComponent.class)
        .get());
    parentGdxGame = game;
    controller = keyCon;
    this.bulMan = bulMan;
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
      parentGdxGame.setScreen(ScreenType.Pause);
    }

    if (controller.spacebar && (player.timeSinceLastShot >= player.shootDelay || player.numMissiles == 0)) {
      float initialX = b2body.body.getPosition().x;
      float initialY = b2body.body.getPosition().y + 1f;

      bulMan.fire(new Vector2(initialX, initialY), 0f, 25f);
      Utility.playPew(parentGdxGame);
      player.timeSinceLastShot = 0;

      player.numMissiles++;
    }

    if (player.numMissiles > 0 && player.timeSinceLastShot <= player.shootDelay) {
      player.timeSinceLastShot += deltaTime;
    }
  }
}