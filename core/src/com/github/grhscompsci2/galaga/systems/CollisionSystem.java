package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.CollisionComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.StateComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {

	private final String TAG=CollisionSystem.class.getSimpleName();
	public CollisionSystem() {
		// only need to worry about player collisions
		super(Family.all(CollisionComponent.class)
    //.exclude(InactiveComponent.class)
    .get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		// get player collision component
		CollisionComponent cc = Mapper.collisionCom.get(entity);
		Entity collidedEntity = cc.collisionEntity;
		if (collidedEntity != null) {
			StateComponent sc = Mapper.stateCom.get(entity);
			TypeComponent usType = Mapper.typeCom.get(entity);
			B2dBodyComponent usBody = Mapper.b2dCom.get(entity);
			TypeComponent themType = Mapper.typeCom.get(collidedEntity);
			//B2dBodyComponent usBody = Mapper.b2dCom.get(entity);
			if (themType != null) {
				//
				if ((usType.type == TypeComponent.ENEMY || usType.type == TypeComponent.PLAYER)
        && themType.type == TypeComponent.BULLET) {
          // enemy or player is hit by bullet
					sc.set(StateComponent.STATE_HIT);
          usBody.body.setActive(false);
          usBody.body.setTransform(usBody.body.getPosition(), 0);
				} else if (usType.type == TypeComponent.PLAYER && themType.type == TypeComponent.ENEMY) {
          // Player is hit by Enemy
					sc.set(StateComponent.STATE_HIT);
          usBody.body.setActive(false);
				} else if (usType.type == TypeComponent.BULLET) {
          // Bullet has hit something
					sc.set(StateComponent.STATE_DEAD);
				}
				cc.collisionEntity = null; // collision handled reset component
			}
		}
	}
}