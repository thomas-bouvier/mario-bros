package com.tomatrocho.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.screens.GameScreen;
import com.tomatrocho.game.world.Level;
import com.tomatrocho.game.world.WorldInformation;
import com.tomatrocho.game.world.WorldList;

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
	private SpriteBatch spriteBatch;
	
	/**
	 * 
	 */
	private Level level;
	
	/**
	 * 
	 */
	private Player player;
	
	
	@Override
	public void create() {
		init();
		
		setScreen(new GameScreen(this));
	}
	
	/**
	 * 
	 */
	private void init() {
		spriteBatch = new SpriteBatch();
		
		initLevel();
	}
	
	/**
	 * 
	 */
	private void initLevel() {
		level = new Level();
		createWorld(WorldList.getLevelByName("level-1"));
		
		if (level.getWorld() != null) {
			player = new Player(level);
		}
	}
	
	/**
	 * 
	 * @param wi
	 */
	private void createWorld(WorldInformation wi) {
		level.generateWorld(wi);
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
	
	/**
	 * 
	 * @return
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
}
