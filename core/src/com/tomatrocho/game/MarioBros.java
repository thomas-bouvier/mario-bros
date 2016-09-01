package com.tomatrocho.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tomatrocho.game.screens.GameScreen;

public class MarioBros extends Game {
	
	/**
	 * 
	 */
	public static final int V_W = 400;
	
	/**
	 * 
	 */
	public static final int V_H = 13 * 16;
	
	/**
	 * 
	 */
	public static final float PPM = 100;
	
	/**
	 * 
	 */
	public static final boolean DEBUG = false;
	
	/**
	 * 
	 */
	private SpriteBatch spriteBatch;
	
	
	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		
		Gdx.app.log("test", "loading all resources");
		AssetContainer.loadAllRessources();
		
		setScreen(new GameScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	/**
	 * 
	 * @return
	 */
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		spriteBatch.dispose();
	}
}
