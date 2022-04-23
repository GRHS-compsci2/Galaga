package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.CollisionComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

	private final String TAG=CollisionSystem.class.getSimpleName();
	public CollisionSystem() {
		// only need to worry about player collisions
		super(Family.all(CollisionComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get player collision component
		CollisionComponent cc = Mapper.collisionCom.get(entity);
		Entity collidedEntity = cc.collisionEntity;
		if (collidedEntity != null) {
			StateComponent sc = Mapper.stateCom.get(entity);
			TypeComponent usType = Mapper.typeCom.get(entity);
			TypeComponent themType = Mapper.typeCom.get(collidedEntity);
			if (themType != null) {
				if ((usType.type == TypeComponent.ENEMY || usType.type == TypeComponent.PLAYER)
						&& themType.type == TypeComponent.BULLET) {
					// enemy or player is hit by bullet
					sc.set(StateComponent.STATE_HIT);
					entity.remove(CollisionComponent.class);
					entity.remove(B2dBodyComponent.class);
				} else if (usType.type == TypeComponent.PLAYER && themType.type == TypeComponent.ENEMY) {
					// Player is hit by Enemy
					sc.set(StateComponent.STATE_HIT);
					entity.remove(CollisionComponent.class);
					entity.remove(B2dBodyComponent.class);
				} else if (usType.type == TypeComponent.BULLET) {
					// Bullet has hit something
					sc.set(StateComponent.STATE_DEAD);
					Gdx.app.debug(TAG,"Bullet");
					entity.remove(CollisionComponent.class);
					entity.remove(B2dBodyComponent.class);
				}
				cc.collisionEntity = null; // collision handled reset component
			}
		}
	}
}