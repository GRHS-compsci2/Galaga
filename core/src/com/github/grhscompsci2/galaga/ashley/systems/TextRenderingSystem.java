package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;
import com.github.grhscompsci2.galaga.ashley.components.TextComponent;
import com.github.grhscompsci2.galaga.ashley.components.TransformComponent;

/**
 * Created by barry on 3/1/16 @ 8:46 PM.
 */
public class TextRenderingSystem extends QueuedIteratingSystem {

  private OrthographicCamera guiCam;
  private float pixelPerMetersW;
  private float pixelPerMetersH;

  private Color tintPlaceholder = Color.WHITE.cpy();
  private SpriteBatch batch;

  public TextRenderingSystem(SpriteBatch batch, OrthographicCamera guiCam, OrthographicCamera worldCam) {
    super(Family.all(TextComponent.class, TransformComponent.class).get());

    this.guiCam = guiCam;
    this.batch = batch;

    pixelPerMetersW = guiCam.viewportWidth / worldCam.viewportWidth;
    pixelPerMetersH = guiCam.viewportHeight / worldCam.viewportHeight;
  }

  @Override
  protected void processQueue(Array<Entity> queue, float deltaTime) {
    guiCam.update();
    batch.setProjectionMatrix(guiCam.combined);
    batch.enableBlending();
    batch.begin();

    for (Entity entity : queue) {
      TextComponent text = K2ComponentMappers.text.get(entity);
      TransformComponent transform = K2ComponentMappers.transform.get(entity);

      if (text.text != null && !transform.isHidden) {

        batch.setColor(tintPlaceholder);
        tintPlaceholder.set(text.font.getColor());
        text.font.setColor(transform.tint);

        GlyphLayout textData = new GlyphLayout(text.font, text.text);
        float targetX = (transform.position.x - ((textData.width / 2f) / pixelPerMetersW)) * pixelPerMetersW;
        float targetY = (transform.position.y - ((textData.height / 2f) / pixelPerMetersH)) * pixelPerMetersH;
        text.font.draw(batch, text.text, targetX, targetY);
        text.font.setColor(tintPlaceholder);
      }
    }
    batch.end();
  }
}