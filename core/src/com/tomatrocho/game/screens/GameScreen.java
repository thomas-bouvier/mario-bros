package com.tomatrocho.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.gfx.Hud;
import com.tomatrocho.game.world.Level;
import com.tomatrocho.game.world.LevelInformation;
import com.tomatrocho.game.world.WorldList;

public class GameScreen implements Screen {
	
	/**
	 * 
	 */
	private MarioBros instance;
	
	/**
	 * 
	 */
	private Viewport viewport;
	
	/**
	 * 
	 */
	private OrthographicCamera camera;
	
	/**
	 * 
	 */
	private Hud hud;
	
	/**
	 * 
	 */
	private Level level;

	
	/**
	 * 
	 * @param instance
	 */
	public GameScreen(MarioBros instance) {
		this.instance = instance;
		
		this.camera = new OrthographicCamera();
		this.viewport = new FitViewport(MarioBros.V_W / MarioBros.PPM, MarioBros.V_H / MarioBros.PPM, camera);
		
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
		
		initLevel();
		
		this.hud = new Hud(instance.getSpriteBatch());
		hud.setScore(0);
		hud.setLevelTimer(level.getLevelInformation().getCountdown());
	}
	
	/**
	 * 
	 */
	private void initLevel() {
		level = new Level();
		createWorld(WorldList.getLevelByName("level-1"));
	}
	
	/**
	 * 
	 * @param wi
	 */
	private void createWorld(LevelInformation wi) {
		level.generateWorld(wi);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		if (level.gameOver()) {
			instance.setScreen(new GameOverScreen(instance));
//			dispose();
		}
		
		handleInput(delta);
		
		level.update(delta);
		
		hud.setScore(level.getScore());
		hud.setLevelTimer(level.getLevelTimer());
		
		if (!level.getWorld().getPlayer().dead()) {			
			camera.position.x = MathUtils.clamp(level.getWorld().getPlayer().getBody().getPosition().x, camera.viewportWidth / 2, 40 - camera.viewportWidth);
		}
		camera.update();
		level.getWorld().getMapRenderer().setView(camera);
	}
	
	/**
	 * 
	 * @param delta
	 */
	public void handleInput(float delta) {
		Player player = level.getWorld().getPlayer();
		
		if (!player.dead()) {			
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				player.jump();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				player.moveLeft();
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				player.moveRight();
			}
		}
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		level.render(camera, instance.getSpriteBatch());
		
		instance.getSpriteBatch().setProjectionMatrix(camera.combined);
		hud.getStage().draw();
	}

	@Override
	public void resize(int w, int h) {
		viewport.update(w, h);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		level.dispose();
	}
	
	/**
	 * 
	 * @return
	 */
	public Level getLevel() {
		return level;
	}
}
