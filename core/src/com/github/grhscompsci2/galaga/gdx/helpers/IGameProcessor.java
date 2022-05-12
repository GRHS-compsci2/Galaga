package com.github.grhscompsci2.galaga.gdx.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Provides an interface to access common globally available
 * items like {@link SpriteBatch} and {@link com.badlogic.gdx.graphics.Camera}
 * as well as a way to interact with your screen switching logic and handling
 * input processor wiring.
 */
public interface IGameProcessor {

  public static enum ScreenType {
    Arcade, Preferences, Loading, Menu, Pause
  }

  void switchScreens(ScreenType screenType);

  void addInputProcessor(InputProcessor processor);

  void removeInputProcessor(InputProcessor processor);

  void pauseBgMusic();

  void playBgMusic(String musicName);

  void playSound(String soundName);

  ScreenType getLastScreen();

  SpriteBatch getBatch();

  OrthographicCamera getCamera();

  OrthographicCamera getGUICamera();

  Viewport getViewport();

  IPreferenceManager getPreferenceManager();
}
