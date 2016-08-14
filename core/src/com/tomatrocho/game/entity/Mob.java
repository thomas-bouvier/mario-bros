package com.tomatrocho.game.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tomatrocho.game.world.Level;

public abstract class Mob extends Entity {
	
	/**
	 * 
	 */
	protected Sprite sprite;

	/**
	 * 
	 * @param world
	 */
	public Mob(Level level) {
		super(level);
	}
	
	/**
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}
}
