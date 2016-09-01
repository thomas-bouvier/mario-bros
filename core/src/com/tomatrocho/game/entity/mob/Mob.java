package com.tomatrocho.game.entity.mob;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.world.World;

public abstract class Mob extends Entity {
	
	/**
	 *
	 */
	public enum State {
		STANDING,
		RUNNING,
		STANDING_SHELL,
		ROLLING_SHELL,
		JUMPING,
		FALLING,
		GROWING,
		DEAD
	}
	
	/**
	 * 
	 */
	protected static TextureAtlas textureAtlas = new TextureAtlas("mobs.pack");
	
	/**
	 * 
	 */
	protected State previousState;
	
	/**
	 * 
	 */
	protected State currentState;
	
	/**
	 * 
	 */
	protected float stateTime = 0;
	
	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public Mob(World world, Vector2 pos) {
		super(world, pos);
		
		this.sprite = new Sprite();
		
		createAnimations();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(pos.x, pos.y);
		
		this.bodyDef = bodyDef;
		def.put(bodyDef, new ArrayList<FixtureDef>());
	}

	/**
	 * 
	 */
	protected abstract void createAnimations();
	
	@Override
	public void update(float delta) {
		sprite.setRegion(getFrame(delta));
	}
	
	/**
	 * 
	 * @param delta
	 * @return
	 */
	protected abstract TextureRegion getFrame(float delta);
	
	/**
	 * 
	 * @param mob
	 */
	public abstract void hit(Mob mob);
	
	/**
	 * 
	 * @return
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getStateTime() {
		return stateTime;
	}
}
