package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.EnemyComponent;
import com.github.grhscompsci2.galaga.ashley.components.StateComponent;
import com.github.grhscompsci2.galaga.ashley.components.TextureComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

import java.util.Comparator;

/**
 * This system will handle rendering all of our
 * entities with a Texture and Transform. Uses the
 * Z value of the TransformComponent.position to determine
 * rendering order HIGH to LOW. (ex: 100 renders behind 99)
 */
public class RenderingSystem extends IteratingSystem {

  private float PPM = Utility.PPM;
  // this gets the height and width of our camera frustrum based off the width and
  // height of the screen and our pixel per meter ratio
  final float FRUSTUM_WIDTH = Gdx.graphics.getWidth() / PPM;
  final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight() / PPM;

  private SpriteBatch batch;
  private Array<Entity> renderQueue;
  private Comparator<Entity> comparator;
  private OrthographicCamera cam;

  private Color tintPlaceholder = Color.WHITE.cpy();

  public RenderingSystem(SpriteBatch batch, OrthographicCamera cam, float pixelsPerMeter) {
    super(Family.all(TransformComponent.class, TextureComponent.class).get());// , new ZComparator());
    PPM = pixelsPerMeter;

    renderQueue = new Array<>();
    comparator = new ZComparator();
    // comparator = new Comparator<Entity>() {
    // @Override
    // public int compare(Entity entityA, Entity entityB) {
    // return (int) Math.signum(K2ComponentMappers.transform.get(entityB).position.z
    // -
    // K2ComponentMappers.transform.get(entityA).position.z);
    // }
    // };

    this.batch = batch;

    // set up the camera to match our screen size
    this.cam = cam;

  }

  @Override
  public void update(float deltaTime) {
    super.update(deltaTime);

    renderQueue.sort(comparator);

    cam.update();
    if (Utility.DEBUG_MODE) {
      for (Entity entity : renderQueue) {
        // Draw the current Path
        EnemyComponent eComponent = K2ComponentMappers.enemy.get(entity);
        StateComponent sComponent = K2ComponentMappers.state.get(entity);
        TransformComponent t = K2ComponentMappers.transform.get(entity);

        if (eComponent != null && eComponent.getPath() != null) {
          ShapeRenderer sr = new ShapeRenderer();
          Gdx.gl.glLineWidth(1);
          sr.setProjectionMatrix(cam.combined);
          sr.begin(ShapeRenderer.ShapeType.Line);
          sr.setColor(Color.WHITE);
          if (sComponent.get() == StateComponent.STATE_ENTRY)
            for (int i = 0; i < eComponent.getPath().getSegments().size; i++) {
              sr.line(eComponent.getPath().getSegments().get(i).getBegin(),
                  eComponent.getPath().getSegments().get(i).getEnd());
            }
          else {
            //sr.line(t.position, new Vector3(eComponent.getHome(), 0));
          }
          sr.end();
        }
      }
    }
    batch.setProjectionMatrix(cam.combined);
    batch.enableBlending();
    batch.begin();

    for (Entity entity : renderQueue) {
      TextureComponent tex = K2ComponentMappers.texture.get(entity);
      TransformComponent t = K2ComponentMappers.transform.get(entity);

      if (tex.region == null || t.isHidden) {
        continue;
      }

      Color c = batch.getColor();
      tintPlaceholder.set(t.tint.r, t.tint.g, t.tint.b, t.tint.a);
      batch.setColor(tintPlaceholder);
      float width = pixelsToMeters(tex.region.getRegionWidth());
      float height = pixelsToMeters(tex.region.getRegionHeight());
      float halfWidth = width / 2f;
      float halfHeight = height / 2f;
      // Allow for Offset
      float originX = halfWidth + t.originOffset.x;
      float originY = halfHeight + t.originOffset.y;

      batch.draw(tex.region,
          t.position.x - halfWidth, t.position.y - halfHeight,
          originX, originY,
          width, height,
          t.scale.x, t.scale.y,
          t.rotation);
      batch.setColor(c);
    }

    batch.end();
    renderQueue.clear();
  }

  @Override
  public void processEntity(Entity entity, float deltaTime) {
    renderQueue.add(entity);
  }

  public OrthographicCamera getCamera() {
    return cam;
  }

  private float pixelsToMeters(float pixelValue) {
    return pixelValue * (1.0f / 1);
  }

  private float MetersToPixels(float meterValue) {
    return PPM * meterValue;
  }
}
