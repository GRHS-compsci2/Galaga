package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.MyGdxGame.ScreenType;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.BulletComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.PlayerComponent;
import com.github.grhscompsci2.galaga.entities.BulletEntity;

public class PlayerControlSystem extends IteratingSystem {
	private String TAG = PlayerControlSystem.class.getSimpleName();

	KeyboardController controller;
	MyGdxGame parentGdxGame;
	BodyFactory bodyFactory;

	public PlayerControlSystem(KeyboardController keyCon, MyGdxGame game, BodyFactory bodyFactory) {
		super(Family.all(PlayerComponent.class).get());
		parentGdxGame = game;
		controller = keyCon;
		this.bodyFactory = bodyFactory;
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		B2dBodyComponent b2body = Mapper.b2dCom.get(entity);
		PlayerComponent player = Mapper.playerCom.get(entity);

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
			float initialY = b2body.body.getPosition().y;

			BulletEntity bu = new BulletEntity();

			bu.init(getEngine(), bodyFactory,BulletComponent.OWNER.PLAYER,initialX,initialY);

			getEngine().addEntity(bu);

			player.timeSinceLastShot = 0;

			player.numMissiles++;
		}

		if (player.numMissiles > 0 && player.timeSinceLastShot <= player.shootDelay) {
			player.timeSinceLastShot += deltaTime;
		}
	}
}