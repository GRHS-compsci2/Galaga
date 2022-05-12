package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.grhscompsci2.galaga.gdx.helpers.IGameProcessor;
import com.github.grhscompsci2.galaga.gdx.helpers.IPreferenceManager;
import com.github.grhscompsci2.galaga.gdx.helpers.K2PreferenceManager;
import com.github.grhscompsci2.galaga.gdx.screens.ArcadeScreen;
import com.github.grhscompsci2.galaga.gdx.screens.LoadingScreen;
import com.github.grhscompsci2.galaga.gdx.screens.MenuScreen;
import com.github.grhscompsci2.galaga.gdx.screens.PauseScreen;
import com.github.grhscompsci2.galaga.gdx.screens.PreferencesScreen;

public class MyGdxGame extends Game implements IGameProcessor {

  private static ArcadeScreen arcadeScreen;
  private static PreferencesScreen preferencesScreen;
  private static LoadingScreen loadingScreen;
  private static MenuScreen menuScreen;
  private static PauseScreen pauseScreen;
  private static final String TAG = MyGdxGame.class.getSimpleName();

  private InputMultiplexer multiplexer = new InputMultiplexer();
  private SpriteBatch batch;
  private OrthographicCamera cam;
  private OrthographicCamera guiCam;
  private Viewport viewport;

  private IPreferenceManager prefManager = new K2PreferenceManager("galaga");

  private ScreenType lastScreen;
  private ScreenType currentScreen;

  public Screen getScreenType(ScreenType screenType) {
    switch (screenType) {
      case Arcade:
        return arcadeScreen;
      case Preferences:
        return preferencesScreen;
      case Loading:
        return loadingScreen;
      case Pause:
        return pauseScreen;
      default:
        return menuScreen;
    }
  }

  @Override
  public void create() {
    Gdx.app.setLogLevel(Application.LOG_DEBUG);
    batch = new SpriteBatch();
    cam = new OrthographicCamera(Utility.SCREEN_WIDTH, Utility.SCREEN_HEIGHT);
    viewport = new FitViewport(Utility.SCREEN_WIDTH, Utility.SCREEN_HEIGHT, cam);
    viewport.apply();
    viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);

    guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    guiCam.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0f);

    arcadeScreen = new ArcadeScreen(this);
    preferencesScreen = new PreferencesScreen(this);
    loadingScreen = new LoadingScreen(this);
    menuScreen = new MenuScreen(this);
    pauseScreen = new PauseScreen(this);

    Utility.setFromPrefs(prefManager);
    Gdx.input.setInputProcessor(multiplexer);
    switchScreens(ScreenType.Loading);
  }

  @Override
  public void dispose() {
    arcadeScreen.dispose();
    preferencesScreen.dispose();
    loadingScreen.dispose();
    pauseScreen.dispose();
    menuScreen.dispose();
    Utility.dispose();
  }

  @Override
  public void switchScreens(ScreenType screenType) {
    lastScreen = currentScreen;
    currentScreen = screenType;
    if (lastScreen == null)
      lastScreen = currentScreen;
    Gdx.app.debug(TAG, lastScreen + " " + currentScreen);
    setScreen(getScreenType(screenType));
  }

  @Override
  public ScreenType getLastScreen() {
    return lastScreen;
  }

  @Override
  public void render() {
    float r = 29 / 255f;
    float g = 29 / 255f;
    float b = 27 / 255f;
    Gdx.gl.glClearColor(r, g, b, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    super.render();
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);
    if (viewport != null) {
      this.viewport.update(width, height);
    }
  }

  @Override
  public void addInputProcessor(InputProcessor processor) {
    multiplexer.addProcessor(processor);
  }

  @Override
  public void removeInputProcessor(InputProcessor processor) {
    multiplexer.removeProcessor(processor);
  }

  @Override
  public void pauseBgMusic() {
    // NO-OP
  }

  @Override
  public void playBgMusic(String musicName) {
    // NO-OP
  }

  @Override
  public SpriteBatch getBatch() {
    return batch;
  }

  @Override
  public OrthographicCamera getCamera() {
    return cam;
  }

  @Override
  public OrthographicCamera getGUICamera() {
    return guiCam;
  }

  @Override
  public Viewport getViewport() {
    return viewport;
  }

  @Override
  public IPreferenceManager getPreferenceManager() {
    return prefManager;
  }

  @Override
  public void playSound(String soundName) {
    // TODO Auto-generated method stub

  }

}
