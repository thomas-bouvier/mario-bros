package com.tomatrocho.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.gfx.Hud;
import com.tomatrocho.game.world.World;

public class GameScreen implements Screen {
	
	/**
	 * 
	 */
	private MarioBros instance;
	
	/**
	 * 
	 */
	private Hud hud;
	
	/**
	 * 
	 */
	private OrthographicCamera camera;
	
	/**
	 * 
	 */
	private Viewport viewport;

	
	/**
	 * 
	 * @param instance
	 */
	public GameScreen(MarioBros instance) {
		this.instance = instance;
		
		hud = new Hud(instance.getSpriteBatch());
		
		camera = new OrthographicCamera();
		viewport = new FitViewport(MarioBros.V_W / MarioBros.PPM, MarioBros.V_H / MarioBros.PPM, camera);
		
		camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
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
		handleInput(delta);
		
		final World world = instance.getLevel().getWorld();
		
		world.update();
		instance.getPlayer().update(delta);
		
		camera.position.x = instance.getPlayer().getBody().getPosition().x;
		camera.update();
		world.getMapRenderer().setView(camera);
	}
	
	/**
	 * 
	 * @param delta
	 */
	public void handleInput(float delta) {
		final Player player = instance.getPlayer();
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

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		instance.getLevel().getWorld().getMapRenderer().render();
		instance.getLevel().getBox2DRenderer().render(instance.getLevel().getWorld().getPhysics(), camera.combined);
		
		instance.getSpriteBatch().setProjectionMatrix(camera.combined);
		instance.getSpriteBatch().begin();
		instance.getPlayer().getSprite().draw(instance.getSpriteBatch());
		instance.getSpriteBatch().end();
		
//		instance.getSpriteBatch().setProjectionMatrix(camera.combined);
//		hud.getStage().draw();
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
		instance.getLevel().dispose();
	}
}
