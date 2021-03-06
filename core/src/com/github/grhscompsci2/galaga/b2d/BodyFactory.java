package com.github.grhscompsci2.galaga.b2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BodyFactory {
    private static BodyFactory thisInstance;
    private static final float DEGTORAD = 0.0174533f;
    private World world;

    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;

    // categories for our bodies
    public final static short CATEGORY_PLAYER = 0x0001; // 0000000000000001 in binary
    public final static short CATEGORY_ENEMY = 0x0002; // 0000000000000010 in binary
    public final static short CATEGORY_BULLET = 0x0004; // 0000000000000100 in binary
    public final static short CATEGORY_OBSTACLE = 0x0008; // 0000000000001000 in binary

    // masks for our bodies
    public final static short MASK_PLAYER = CATEGORY_ENEMY | CATEGORY_BULLET | CATEGORY_OBSTACLE; // or
                                                                                                  // ~CATEGORY_PLAYER
    public final static short MASK_ENEMY = CATEGORY_PLAYER | CATEGORY_BULLET; // or ~CATEGORY_ENEMY
    public final static short MASK_BULLET = -1;
    public final static short MASK_OBSTACLE = -1;

    private BodyFactory(World world) {
        this.world = world;
    }

    public static BodyFactory getInstance(World world) {
        if (thisInstance == null) {
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    static public FixtureDef makeFixture(int material, Shape shape, short category, short mask) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.filter.categoryBits = category;

        switch (material) {
            case STEEL:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case WOOD:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case RUBBER:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case STONE:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }
        return fixtureDef;
    }

    /*
     * posx - the x position the body will be in our world posy - the y positon
     * radius - radius of circle material - material we defined above bodyType -
     * dynamic, static, or kinematic fixedrotation - true if we want it to not
     * rotate
     */
    /*
     * public Body makeCirclePolyBody(float posx, float posy, float radius, int
     * material, BodyDef.BodyType bodyType,
     * boolean fixedRotation) {
     * // create a definition
     * BodyDef boxBodyDef = new BodyDef();
     * boxBodyDef.type = bodyType;
     * boxBodyDef.position.x = posx;
     * boxBodyDef.position.y = posy;
     * boxBodyDef.fixedRotation = fixedRotation;
     * 
     * // create the body to attach said definition
     * Body boxBody = world.createBody(boxBodyDef);
     * CircleShape circleShape = new CircleShape();
     * circleShape.setRadius(radius / 2);
     * boxBody.createFixture(makeFixture(material, circleShape));
     * circleShape.dispose();
     * return boxBody;
     * }
     * 
     * public Body makeCirclePolyBody(float posx, float posy, float radius, int
     * material) {
     * return makeCirclePolyBody(posx, posy, radius, material,
     * BodyDef.BodyType.DynamicBody, false);
     * }
     */
    public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType,
            short category, short mask, boolean fixedRotation) {
        // create a definition
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        // create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width / 2, height / 2);
        boxBody.createFixture(makeFixture(material, poly, category, mask));
        poly.dispose();

        return boxBody;
    }

    public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType,
            short category, short mask) {
        return makeBoxPolyBody(posx, posy, width, height, material, bodyType, category, mask, false);
    }

    /*
     * public Body makePolygonShapeBody(Vector2[] vertices, float posx, float posy,
     * int material, BodyType bodyType) {
     * BodyDef boxBodyDef = new BodyDef();
     * boxBodyDef.type = bodyType;
     * boxBodyDef.position.x = posx;
     * boxBodyDef.position.y = posy;
     * Body boxBody = world.createBody(boxBodyDef);
     * 
     * PolygonShape polygon = new PolygonShape();
     * polygon.set(vertices);
     * boxBody.createFixture(makeFixture(material, polygon));
     * polygon.dispose();
     * 
     * return boxBody;
     * }
     */
    // used as field of view
    public void makeConeSensor(Body body, float size) {

        FixtureDef fixtureDef = new FixtureDef();
        // fixtureDef.isSensor = true; // will add in future

        PolygonShape polygon = new PolygonShape();

        float radius = size;
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0, 0);
        for (int i = 2; i < 6; i++) {
            float angle = (float) (i / 6.0 * 145 * DEGTORAD); // convert degrees to radians
            vertices[i - 1] = new Vector2(radius * ((float) Math.cos(angle)), radius * ((float) Math.sin(angle)));
        }
        polygon.set(vertices);
        fixtureDef.shape = polygon;
        body.createFixture(fixtureDef);
        polygon.dispose();
    }

    public void makeAllFixturesSensors(Body bod) {
        for (Fixture fix : bod.getFixtureList()) {
            fix.setSensor(true);
        }
    }

    public Body makeBoxPolyBody(Vector3 position, float posy, float width, int stone2, BodyType dynamicbody,
            boolean b) {
        return null;
    }
}
