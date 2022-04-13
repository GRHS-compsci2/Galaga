package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.grhscompsci2.galaga.components.CollisionComponent;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
	private ComponentMapper<CollisionComponent> cm;
	private ComponentMapper<StateComponent> sm;
	private ComponentMapper<TypeComponent> tm;

	public CollisionSystem() {
		// only need to worry about player collisions
		super(Family.all(CollisionComponent.class).get());

		cm = ComponentMapper.getFor(CollisionComponent.class);
		sm = ComponentMapper.getFor(StateComponent.class);
		tm = ComponentMapper.getFor(TypeComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get player collision component
		CollisionComponent cc = cm.get(entity);
		Entity collidedEntity = cc.collisionEntity;
		if (collidedEntity != null) {
			StateComponent sc = sm.get(entity);
			TypeComponent usType = tm.get(entity);
			TypeComponent themType = tm.get(collidedEntity);
			if (themType != null) {
				if ((usType.type == TypeComponent.ENEMY || usType.type == TypeComponent.PLAYER)
						&& themType.type == TypeComponent.BULLET) {
					// enemy or player is hit by bullet
					sc.set(StateComponent.STATE_HIT);
					entity.remove(CollisionComponent.class);
				} else if (usType.type == TypeComponent.PLAYER && themType.type == TypeComponent.ENEMY) {
					// Player is hit by Enemy
					sc.set(StateComponent.STATE_HIT);
					entity.remove(CollisionComponent.class);
				} else if (usType.type == TypeComponent.BULLET) {
					// Bullet has hit something
					sc.set(StateComponent.STATE_HIT);
					entity.remove(CollisionComponent.class);
				}
				cc.collisionEntity = null; // collision handled reset component
			}
		}
	}
}