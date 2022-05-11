package com.github.grhscompsci2.galaga.gdx.screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.gdx.helpers.IGameProcessor.ScreenType;

public class LoadingScreen extends BaseDemoScreen {

  private Stage loadingStage;

  public LoadingScreen(MyGdxGame game) {
    super(game);
    loadingStage = new Stage(game.getViewport());
  }

  @Override
  public void render(float delta) {
    super.render(delta);
    if (!Utility._assetManager.update()) {
      // stay here until it is all loaded.
    } else {
      game.switchScreens(ScreenType.Menu);
    }
  }

  @Override
  public void dispose() {
    loadingStage.dispose();
  }

  @Override
  void childInit() {
    Utility.loadAllMusicAsset();
    Utility.loadAllSoundAsset();
    Utility.loadTextureAtlasAsset();
  }
}
