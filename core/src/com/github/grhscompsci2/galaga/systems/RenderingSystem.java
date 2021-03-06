package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.LinePathParam;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.Mapper;
import com.github.grhscompsci2.galaga.components.SteeringComponent;
import com.github.grhscompsci2.galaga.components.TextureComponent;
import com.github.grhscompsci2.galaga.components.TranslationComponent;
import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {

    static final float PPM = Utility.PPM; // sets the amount of pixels each metre of box2d objects contains

    // this gets the height and width of our camera frustrum based off the width and
    // height of the screen and our pixel per meter ratio
    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth() / PPM;
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight() / PPM;
    private static final String TAG = RenderingSystem.class.getSimpleName();

    public static final float PIXELS_TO_METRES = 1.0f / PPM; // get the ratio for converting pixels to metres

    // static method to get screen width in metres
    private static Vector2 meterDimensions = new Vector2();
    private static Vector2 pixelDimensions = new Vector2();

    public static Vector2 getScreenSizeInMeters() {
        meterDimensions.set(Gdx.graphics.getWidth() * PIXELS_TO_METRES, Gdx.graphics.getHeight() * PIXELS_TO_METRES);
        return meterDimensions;
    }

    // static method to get screen size in pixels
    public static Vector2 getScreenSizeInPixels() {
        pixelDimensions.set(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return pixelDimensions;
    }

    // convenience method to convert pixels to meters
    public static float PixelsToMeters(float pixelValue) {
        return pixelValue * PIXELS_TO_METRES;
    }

    private Batch batch; // a reference to our spritebatch
    private Array<Entity> renderQueue; // an array used to allow sorting of images allowing us to draw images on top of
                                       // each other
    private Comparator<Entity> comparator; // a comparator to sort images based on the z position of the
                                           // transfromComponent
    private OrthographicCamera cam; // a reference to our camera

    public RenderingSystem(Batch batch) {
        // gets all entities with a TranslationComponent and TextureComponent
        super(Family.all(TranslationComponent.class, TextureComponent.class).get(), new ZComparator());
        comparator = new ZComparator();
        Gdx.app.debug(TAG, "Screen Resolution: " + getScreenSizeInMeters());

        // create the array for sorting entities
        renderQueue = new Array<Entity>();

        this.batch = batch; // set our batch to the one supplied in constructor

        // set up the camera to match our screen size
        cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // sort the renderQueue based on z index
        renderQueue.sort(comparator);

        // update camera and sprite batch
        cam.update();

        //Draw the paths
        if (Utility.DEBUG_MODE) {
            for (Entity entity : renderQueue) {
                // Draw the current Path
                EnemyComponent eComponent = Mapper.enemyCom.get(entity);
                if (eComponent != null) {
                    ShapeRenderer sr = new ShapeRenderer();
                    Gdx.gl.glLineWidth(1);
                    sr.setProjectionMatrix(cam.combined);
                    sr.begin(ShapeRenderer.ShapeType.Line);
                    sr.setColor(Color.WHITE);
                    for (int i = 0; i < eComponent.getPath().getSegments().size; i++) {
                        sr.line(eComponent.getPath().getSegments().get(i).getBegin(),
                                eComponent.getPath().getSegments().get(i).getEnd());
                    }
                    sr.end();
                }
            }
        }
        batch.setProjectionMatrix(cam.combined);
        batch.enableBlending();
        batch.begin();

        // loop through each entity in our render queue
        for (Entity entity : renderQueue) {
            TextureComponent tex = Mapper.texCom.get(entity);
            TranslationComponent t = Mapper.transCom.get(entity);

            if (tex.region == null || t.isHidden) {
                continue;
            }

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();

            float originX = width / 2f;
            float originY = height / 2f;

            batch.draw(tex.region, t.position.x - originX, t.position.y - originY, originX, originY, width, height,
                    PixelsToMeters(t.scale.x), PixelsToMeters(t.scale.y), t.rotation);
            if (Utility.DEBUG_MODE) {
                SteeringComponent sComponent = Mapper.sCom.get(entity);
                if (sComponent != null) {
                    if (sComponent.followPath != null) {
                        FollowPath<Vector2, LinePathParam> a = sComponent.followPath;
                        if (a.getTarget() != null) {
                            width = tex.region.getRegionWidth();
                            height = tex.region.getRegionHeight();

                            originX = width / 6f;
                            originY = height / 6f;
                            float x = a.getInternalTargetPosition().x;
                            float y = a.getInternalTargetPosition().y;
                            batch.draw(tex.region, x - originX, y - originY, originX, originY, width / 3, height / 3,
                                    PixelsToMeters(t.scale.x), PixelsToMeters(t.scale.y), t.rotation);
                        }
                    }
                }
            }
        }

        batch.end();

        renderQueue.clear();
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    // convenience method to get camera
    public OrthographicCamera getCamera() {
        return cam;
    }
}