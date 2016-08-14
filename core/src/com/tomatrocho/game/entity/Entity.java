package com.tomatrocho.game.entity;

import com.badlogic.gdx.physics.box2d.Body;
import com.tomatrocho.game.world.Level;

public abstract class Entity {

	/**
	 * 
	 */
	protected Level level;
	
	/**
	 * 
	 */
	protected Body body;
	
	
	/**
	 * 
	 * @param world
	 */
	public Entity(Level level) {
		this.level = level;
	}
	
	/**
	 * 
	 */
	public abstract void update(float delta);
	
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
	public Body getBody() {
		return body;
	}
}
