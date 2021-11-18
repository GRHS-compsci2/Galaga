package com.github.grhscompsci2.galaga.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.grhscompsci2.galaga.KeyboardController;
import com.github.grhscompsci2.galaga.MyGdxGame;
import com.github.grhscompsci2.galaga.Utility;
import com.github.grhscompsci2.galaga.b2d.B2dContactListener;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.entities.BeeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.BirdGalagaEntity;
import com.github.grhscompsci2.galaga.entities.ButterflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.DragonflyGalagaEntity;
import com.github.grhscompsci2.galaga.entities.GreenBatGalagaEntity;
import com.github.grhscompsci2.galaga.entities.LevelEntity;
import com.github.grhscompsci2.galaga.entities.LivesEntity;
import com.github.grhscompsci2.galaga.entities.PhantomGalagaEntity;
import com.github.grhscompsci2.galaga.entities.PinheadGalagaEntity;
import com.github.grhscompsci2.galaga.entities.PlayerEntity;
import com.github.grhscompsci2.galaga.entities.ProbeGalagaEntity;
import com.github.grhscompsci2.galaga.entities.PurpleBatGalagaEntity;
import com.github.grhscompsci2.galaga.entities.ScorpionGalagaEntity;
import com.github.grhscompsci2.galaga.systems.AnimationSystem;
import com.github.grhscompsci2.galaga.systems.CollisionSystem;
import com.github.grhscompsci2.galaga.systems.PhysicsDebugSystem;
import com.github.grhscompsci2.galaga.systems.PhysicsSystem;
import com.github.grhscompsci2.galaga.systems.PlayerControlSystem;
import com.github.grhscompsci2.galaga.systems.RenderingSystem;

public class ArcadeScreen extends ScreenAdapter {

	
	private MyGdxGame parent;
	// private SpriteBatch batch;
	private PooledEngine engine;
	private OrthographicCamera cam;
	private BodyFactory bodyFactory;
	private World world;
	private KeyboardController controller;
	private Stage arcadeStage;
	private Stage _stage;

	private int score;
	private String yourScoreName;
	SpriteBatch batch;
	BitmapFont font;

	public ArcadeScreen(MyGdxGame myGdxGame) {

		Skin skin=Utility.STATUSUI_SKIN;

		Table table = new Table();
    	table.setFillParent(true);
		//table
		int score = 100;
		Label scoreLabel = new Label("Score: "+score, skin, "tiny");
		table.add(scoreLabel).left().width(288.0f).row();
		table.add().width(288.0f).height(228.0f).row();


		 _stage = new Stage();

		 _stage.addActor(table);



		parent = myGdxGame;
		world = new World(new Vector2(0, 0), true);
		world.setContactListener(new B2dContactListener(parent));
		bodyFactory = BodyFactory.getInstance(world);
		controller = new KeyboardController();
		arcadeStage = new Stage(new FitViewport(288, 244, new OrthographicCamera()));
		Vector2 meters = RenderingSystem.getScreenSizeInMeters();
		System.out.println(meters);

		RenderingSystem renderingSystem = new RenderingSystem(arcadeStage.getBatch());
		cam = renderingSystem.getCamera();
		arcadeStage.getBatch().setProjectionMatrix(cam.combined);
		engine = new PooledEngine();

		PlayerEntity player = new PlayerEntity();
		player.setUp(engine, bodyFactory);
		
		engine.addEntity(player);

		//score = 0;
    	//yourScoreName = "score: 0";
    	//yourBitmapFontName = new BitmapFont();

		
		createFormation1();
		createLives();
		
/*
		BirdGalagaEntity bird = new BirdGalagaEntity();
		bird.init(engine, bodyFactory);
		engine.addEntity(bird);

		ButterflyGalagaEntity bf = new ButterflyGalagaEntity();
		bf.init(engine, bodyFactory);
		engine.addEntity(bf);

		DragonflyGalagaEntity df = new DragonflyGalagaEntity();
		df.init(engine, bodyFactory);
		engine.addEntity(df);

		GreenBatGalagaEntity gb = new GreenBatGalagaEntity();
		gb.init(engine, bodyFactory);
		engine.addEntity(gb);

		PhantomGalagaEntity phan = new PhantomGalagaEntity();
		phan.init(engine, bodyFactory);
		engine.addEntity(phan);

		PinheadGalagaEntity ph = new PinheadGalagaEntity();
		ph.init(engine, bodyFactory);
		engine.addEntity(ph);

		ProbeGalagaEntity probe = new ProbeGalagaEntity();
		probe.init(engine, bodyFactory);
		engine.addEntity(probe);

		PurpleBatGalagaEntity pb = new PurpleBatGalagaEntity();
		pb.init(engine, bodyFactory);
		engine.addEntity(pb);

		ScorpionGalagaEntity sc = new ScorpionGalagaEntity();
		sc.init(engine, bodyFactory);
		engine.addEntity(sc);
		*/

		LevelEntity le = new LevelEntity();
		le.init(engine, bodyFactory);
		engine.addEntity(le);

		// add all the relevant systems our engine should run
		engine.addSystem(renderingSystem);
		engine.addSystem(new AnimationSystem());
		engine.addSystem(new PhysicsDebugSystem(world, renderingSystem.getCamera()));
		engine.addSystem(new PhysicsSystem(world));
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new PlayerControlSystem(controller));
	}

	private void createLives() {
		float y=1.5f;
		for(float r=4.0f;r<=5.5f;r+=1.5){
		LivesEntity life=new LivesEntity(r,y);
		life.init(engine, bodyFactory);
		engine.addEntity(life);
		}
	}

	//create the basic starting formation (Without animation)
	//Coordinates may require readjustment
	private void createFormation1() {
		
		for(float y=16.0f; y<=17.75f;y+=1.75f){
			for(float x=8.0f; x<28.0f;x+=2.0f){
				BeeGalagaEntity bee = new BeeGalagaEntity(x,y);
				bee.init(engine, bodyFactory);
				engine.addEntity(bee);
			}
		}
	
		for(float y=19.5f; y<=21.25f; y+=1.75f){
			for(float x=10.0f; x<26.0f;x+=2.0f){
				ButterflyGalagaEntity bf = new ButterflyGalagaEntity(x,y);
				bf.init(engine, bodyFactory);
				engine.addEntity(bf);
			}
		}
		
		for(float x=14.0f; x<22.0f; x+=2.0f){
			float y = 23.0f;
			GreenBatGalagaEntity gb = new GreenBatGalagaEntity(x,y);
			gb.init(engine, bodyFactory);
			engine.addEntity(gb);
		}

	}
	/*@Override
 	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		

	}*/

	@Override
	public void show() {
		Gdx.input.setInputProcessor(controller);
	}

	
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Utility.background.render(delta);
		engine.update(delta);
		_stage.draw();

		/*batch.begin(); 
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, "Score", 1, 1); 
		batch.end();*/
		

		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void resize(int width, int height) {
		arcadeStage.getViewport().update(width, height);
		// arcadeStage.getCamera().position.set(Gdx.graphics.getWidth() / 2,
		// Gdx.graphics.getHeight() / 2, 0);
	}

	@Override
	public void dispose() {
		arcadeStage.dispose();
	}
}
