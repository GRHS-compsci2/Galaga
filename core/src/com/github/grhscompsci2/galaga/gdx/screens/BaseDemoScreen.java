package com.github.grhscompsci2.galaga.gdx.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.systems.AnimationSystem;
import com.github.grhscompsci2.galaga.ashley.systems.DebugSystem;
import com.github.grhscompsci2.galaga.ashley.systems.RenderingSystem;
import com.github.grhscompsci2.galaga.ashley.systems.TextRenderingSystem;
import com.github.grhscompsci2.galaga.gdx.helpers.IGameProcessor;

/**
 * Created by barry on 5/3/16 @ 7:43 PM.
 */
public abstract class BaseDemoScreen extends LazyInitScreen {
  private static final float MAX_DELTA = 0.16f;

  abstract void childInit();

  protected IGameProcessor game;
  protected PooledEngine engine;
  protected RenderingSystem renderer;
  protected Stage stage;

  public BaseDemoScreen(IGameProcessor game) {
    this.game = game;
  }

  private void baseInit() {
    engine = new PooledEngine();
    renderer = new RenderingSystem(game.getBatch(), game.getCamera(), Utility.PPM);
    stage = new Stage(game.getViewport(), game.getBatch());
  }

  @Override
  protected void init() {
    baseInit();
    childInit();
    engine.addSystem(renderer);
    engine.addSystem(new TextRenderingSystem(game.getBatch(), game.getGUICamera(), game.getCamera()));
    engine.addSystem(new DebugSystem(game.getCamera(), Color.BLUE, Color.MAGENTA, Input.Keys.TAB));
    engine.addSystem(new AnimationSystem());
  }

  @Override
  protected void update(float deltaChange) {
    float clippedDelta = Math.min(deltaChange, MAX_DELTA);
    engine.update(clippedDelta);
  }

}
