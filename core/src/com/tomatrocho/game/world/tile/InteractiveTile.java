package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.world.Level;

public abstract class InteractiveTile extends Entity {

	/**
	 * 
	 */
	protected Rectangle bounds;
	
	
	/**
	 * 
	 * @param world
	 * @param bounds
	 */
	public InteractiveTile(Level level, Rectangle bounds) {
		super(level);
		
		this.bounds = bounds;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / MarioBros.PPM, (bounds.getY() + bounds.getHeight() / 2) / MarioBros.PPM);
		
		this.body = level.getWorld().createBody(bodyDef);
		
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox((bounds.getWidth() / 2) / MarioBros.PPM, (bounds.getHeight() / 2) / MarioBros.PPM);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygon;
		
		body.createFixture(fixtureDef);
	}
}
