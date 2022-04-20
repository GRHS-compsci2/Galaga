package com.github.grhscompsci2.galaga.b2d;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.components.CollisionComponent;

public class B2dContactListener implements ContactListener {

    public B2dContactListener(MyGdxGame parent) {
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        System.out.println(fa.getBody().getType() + " has hit " + fb.getBody().getType());

        if (fa.getBody().getUserData() instanceof Entity) {
            Entity ent = (Entity) fa.getBody().getUserData();
            entityCollision(ent, fb);
            return;
        } else if (fb.getBody().getUserData() instanceof Entity) {
            Entity ent = (Entity) fb.getBody().getUserData();
            entityCollision(ent, fa);
            return;
        }
    }

    private void entityCollision(Entity ent, Fixture fb) {
        if (fb.getBody().getUserData() instanceof Entity) {
            Entity colEnt = (Entity) fb.getBody().getUserData();

            CollisionComponent col = ent.getComponent(CollisionComponent.class);
            CollisionComponent colb = colEnt.getComponent(CollisionComponent.class);

            if (col != null) {
                col.collisionEntity = colEnt;
            }
            if (colb != null) {
                colb.collisionEntity = ent;
            }
        }
    }

    /*
     * private void shootUpInAir(Fixture staticFixture, Fixture otherFixture) {
     * System.out.println("Adding Force");
     * otherFixture.getBody().applyForceToCenter(new Vector2(-1000, -1000), true);
     * parent.playSound(B2dModel.BOING_SOUND);
     * }
     */
    @Override
    public void endContact(Contact contact) {
        /*
         * System.out.println("Contact");
         * Fixture fa = contact.getFixtureA();
         * Fixture fb = contact.getFixtureB();
         * if (fa.getBody().getUserData() == "IAMTHESEA") {
         * parent.isSwimming = false;
         * return;
         * } else if (fb.getBody().getUserData() == "IAMTHESEA") {
         * parent.isSwimming = false;
         * return;
         * }
         */
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
