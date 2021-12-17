package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.MyGdxGame.ScreenType;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.PlayerComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;

public class PlayerControlSystem extends IteratingSystem {
	private String TAG = PlayerControlSystem.class.getSimpleName();
	ComponentMapper<PlayerComponent> pm;
	ComponentMapper<B2dBodyComponent> bodm;
	ComponentMapper<StateComponent> sm;
	KeyboardController controller;
	float speed = 15.0f;
	MyGdxGame parentGdxGame;

	public PlayerControlSystem(KeyboardController keyCon, MyGdxGame game) {
		super(Family.all(PlayerComponent.class).get());
		parentGdxGame = game;
		controller = keyCon;
		pm = ComponentMapper.getFor(PlayerComponent.class);
		bodm = ComponentMapper.getFor(B2dBodyComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		B2dBodyComponent b2body = bodm.get(entity);
		StateComponent state = sm.get(entity);

		if (b2body.body.getLinearVelocity().y > 0) {
			state.set(StateComponent.STATE_FALLING);
		}

		if (b2body.body.getLinearVelocity().y == 0) {
			if (state.getState() == StateComponent.STATE_FALLING) {
				state.set(StateComponent.STATE_NORMAL);
			}
			if (b2body.body.getLinearVelocity().x != 0) {
				state.set(StateComponent.STATE_MOVING);
			}
		}

		if (controller.left) {
			b2body.body.setLinearVelocity((speed * -1), 0);
		}
		if (controller.right) {
			b2body.body.setLinearVelocity(speed, 0);
		}

		if (!controller.left && !controller.right) {
			b2body.body.setLinearVelocity(0, 0);
		}
		if (controller.esc) {
			controller.esc = false;
			parentGdxGame.setScreen(ScreenType.Pause);
		}

		/*
		 * if(controller.up &&
		 * (state.get() == StateComponent.STATE_NORMAL || state.get() ==
		 * StateComponent.STATE_MOVING)){
		 * b2body.body.applyForceToCenter(0, 3000,true);
		 * b2body.body.applyLinearImpulse(0, 75f,
		 * b2body.body.getWorldCenter().x,b2body.body.getWorldCenter().y, true);
		 * state.set(StateComponent.STATE_JUMPING);
		 * }
		 */
	}
}