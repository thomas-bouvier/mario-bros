package com.tomatrocho.game.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tomatrocho.game.world.World;

public abstract class Entity {
	
	/**
	 * 
	 */
	public static final short BLOCK_BIT = 1;
	public static final short PIPE_BIT = 2;
	public static final short MARIO_BIT = 4;
	public static final short MARIO_HEAD_BIT = 8;
	public static final short MOB_BIT = 16;
	public static final short MOB_HEAD_BIT = 32;
	public static final short ITEM_BIT = 64;
	
	/**
	 * 
	 */
	protected World world;
	
	/**
	 * 
	 */
	protected Body body;
	
	/**
	 * 
	 */
	protected BodyDef bodyDef;
	
	/**
	 * 
	 */
	protected Map<BodyDef, ArrayList<FixtureDef>> def = new HashMap<BodyDef, ArrayList<FixtureDef>>();
	
	/**
	 * 
	 */
	protected Sprite sprite;
	
	/**
	 * 
	 */
	protected boolean frozen = false;
	
	/**
	 * 
	 */
	protected boolean destroyed = false;
	
	/**
	 * 
	 */
	protected boolean toDestroy = false;

	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public Entity(World world, Vector2 pos) {
		this.world = world;
		
		world.addEntity(this);
	}
	
	/**
	 * 
	 */
	public void init() {
		for (Entry<BodyDef, ArrayList<FixtureDef>> entry : def.entrySet()) {
		    if (entry.getKey() == bodyDef) {
		    	this.body = world.createBody(entry.getKey());
		    	for (FixtureDef fixtureDef : entry.getValue()) {
		    		body.createFixture(fixtureDef).setUserData(this);
		    	}
		    }
		}
	}
	
	/**
	 * 
	 */
	public abstract void update(float delta);
	
	/**
	 * 
	 * @param pos
	 */
	public void freeze(Vector2 pos) {
		frozen = true;
	}
	
	/**
	 * 
	 * @param spriteBatch
	 */
	public void render(SpriteBatch spriteBatch) {
		sprite.draw(spriteBatch);
	}
	
	/**
	 * 
	 */
	protected void destroyBody() {
		world.addBodyToDestroy(body);
	}
	
	/**
	 * 
	 */
	protected void destroy() {
		destroyed = true;
	}
	
	/**
	 * 
	 */
	protected void flagToDestroy() {
		toDestroy = true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Body getBody() {
		return body;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean frozen() {
		return frozen;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean destroyed() {
		return destroyed;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean flaggedToDestroy() {
		return toDestroy;
	}
}
