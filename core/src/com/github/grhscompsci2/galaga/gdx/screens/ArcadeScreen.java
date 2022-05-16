package com.github.grhscompsci2.galaga.gdx.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.github.grhscompsci2.galaga.EnemyFormation;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.ashley.components.BodyComponent;
import com.github.grhscompsci2.galaga.ashley.components.TypeComponent;
import com.github.grhscompsci2.galaga.ashley.entities.BoundariesEntity;
import com.github.grhscompsci2.galaga.ashley.entities.PlayerEntity;
import com.github.grhscompsci2.galaga.ashley.entities.bullets.BulletFactory;
import com.github.grhscompsci2.galaga.ashley.entities.enemies.LivesEntity;
import com.github.grhscompsci2.galaga.ashley.systems.Box2DPhysicsDebugSystem;
import com.github.grhscompsci2.galaga.ashley.systems.Box2DPhysicsSystem;
import com.github.grhscompsci2.galaga.ashley.systems.CollisionSystem;
import com.github.grhscompsci2.galaga.ashley.systems.EnemySystem;
import com.github.grhscompsci2.galaga.ashley.systems.LevelSystem;
import com.github.grhscompsci2.galaga.ashley.systems.PlayerControlSystem;
import com.github.grhscompsci2.galaga.ashley.systems.StateSystem;
import com.github.grhscompsci2.galaga.ashley.systems.SteeringSystem;
import com.github.grhscompsci2.galaga.b2d.B2dContactListener;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;

public class ArcadeScreen extends BaseDemoScreen {

  private BodyFactory bodyFactory;
  private World world;
  private KeyboardController controller;
  private Label readyLabel;
  private Label p1ScoreLabel;
  private Label p2ScoreLabel;
  private float resumeTime;
  private float blinkTime;
  private boolean rest;
  private BulletFactory bulletFactory;

  /**
   * Constructor for ArcadeScreen. Sets up Box 2D world. Sets up keyboard. Sets up
   * stage. Adds all systems to the engine.
   * 
   * @param myGdxGame - the parent game
   */
  public ArcadeScreen(MyGdxGame myGdxGame) {
    super(myGdxGame);
    controller = new KeyboardController();
  }

  @Override
  protected void update(float deltaChange) {
    super.update(deltaChange);
    Utility.background.render(deltaChange, rest);
  }

  @Override
  void childInit() {
    world = new World(new Vector2(0, 0), true);
    world.setContactListener(new B2dContactListener((MyGdxGame) game));
    bodyFactory = BodyFactory.getInstance(world);
    bulletFactory = new BulletFactory(engine, bodyFactory);
    EnemyFormation.init(engine, bodyFactory);
    PlayerEntity player = new PlayerEntity();
    player.setUp(engine, bodyFactory, 112f, 16.0f);
    engine.addEntity(player);
    createBoundries();
    setUpTable();
    Family bodyFamily = Family.all(BodyComponent.class).get();
    EntityListener b2dListener = new EntityListener() {
      @Override
      public void entityAdded(Entity entity) {
        // TODO Auto-generated method stub

      }

      @Override
      public void entityRemoved(Entity entity) {
        bulletFactory.checkDead(entity);
        if (entity.getComponent(BodyComponent.class) != null) {
          world.destroyBody(entity.getComponent(BodyComponent.class).getBody());
        }
      }
    };
    engine.addSystem(new Box2DPhysicsDebugSystem(world, game.getCamera()));
    engine.addSystem(new Box2DPhysicsSystem(world));
    engine.addSystem(new CollisionSystem());
    engine.addSystem(new StateSystem());
    engine.addSystem(new SteeringSystem());
    engine.addSystem(new PlayerControlSystem(controller, (MyGdxGame) game, bulletFactory));
    engine.addSystem(new EnemySystem(bulletFactory));
    engine.addSystem(new LevelSystem());
    engine.addEntityListener(b2dListener);
  }

  /**
   * This method will blink the readyLabel every .25 seconds and end the delay
   * when 1.5 seconds are up
   * 
   * @param delta the amount of elapsed time in seconds.
   */
  private void pauseAndBlink(float delta) {
    // .25 really should be a constant in the utility class
    if (blinkTime < .25) {
      blinkTime += delta;
    } else {
      blinkTime = 0;
      // swap between "" and "Ready?"
      if (readyLabel.getText().toString().equals("")) {
        readyLabel.setText("Ready?");
      } else {
        readyLabel.setText("");
      }
    }
    // 1.5 should really be a constant in the utility class.
    if (resumeTime < 1.5) {
      // if we are not done delaying
      resumeTime += delta;
    } else {
      // stop the delay
      rest = false;
    }
  }

  /**
   * This method will set up the table to add to the stage
   */
  private void setUpTable() {
    Skin skin = Utility.STATUSUI_SKIN;

    Table table = new Table();
    // table.setDebug(true);
    table.setFillParent(true);
    // table
    int score = 100;

    Label p1Label = new Label("1UP:", skin, "tinyRed");
    p1Label.setAlignment(Align.center);
    p1ScoreLabel = new Label(score + "", skin, "tiny");
    p1ScoreLabel.setAlignment(Align.center);
    Label highLabel = new Label("High Score", skin, "tinyRed");
    highLabel.setAlignment(Align.center);
    Label highScoreLabel = new Label(score + "", skin, "tiny");
    highScoreLabel.setAlignment(Align.center);
    // This space is reserved for when we implement two player mode
    Label p2Label = new Label("", skin, "tinyRed");
    p2Label.setAlignment(Align.center);
    p2ScoreLabel = new Label("", skin, "tiny");
    p2ScoreLabel.setAlignment(Align.center);
    readyLabel = new Label("Ready?", skin, "tinyCyan");
    table.add(p1Label).center();
    table.add(highLabel).center();
    table.add(p2Label).center().row();
    table.add(p1ScoreLabel).center().width(Utility.SCREEN_WIDTH / 3);
    table.add(highScoreLabel).width(Utility.SCREEN_WIDTH / 3);
    table.add(p2ScoreLabel).width(Utility.SCREEN_WIDTH / 3).row();
    table.add(readyLabel).height(Utility.SCREEN_HEIGHT - p1ScoreLabel.getHeight() * 2).colspan(3).row();
    stage.addActor(table);
  }

  /**
   * This method creates the entities for the lives. Should be controlled by the
   * PlayerSystem
   */
  private void createLives() {
    float y = 1.5f;
    for (float r = 4.0f; r <= 5.5f; r += 1.5) {
      LivesEntity life = new LivesEntity(r, y);
      life.init(engine, bodyFactory);
      engine.addEntity(life);
    }
  }

  private void createBoundries() {
    // Create Player bumpers to keep players on the field.
    for (float x = 0f; x <= 224.0f; x += 224.0f) {
      float y = 20f;
      float s1 = .25f;
      float s2 = .25f;
      BoundariesEntity be = new BoundariesEntity();
      be.init(engine, bodyFactory, x, y, s1, s2);
      engine.addEntity(be);
    }
    // Create Bullet bumper to destroy bullets that go too high
    float w = Utility.SCREEN_WIDTH - 4f;
    float h = 2f;
    float x = Utility.SCREEN_WIDTH / 2.0f;
    float y = Utility.SCREEN_HEIGHT - h;

    BoundariesEntity be2 = new BoundariesEntity();
    be2.init(engine, bodyFactory, x, y, w, h);
    engine.addEntity(be2);
  }

  public InputProcessor getKeyboardProcessor() {
    return controller;
  }
}
