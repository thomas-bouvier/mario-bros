package com.tomatrocho.game.entity.mob;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.world.World;

public abstract class HostileMob extends Mob {
	
	/**
	 * 
	 */
	protected Vector2 velocity;
	
	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public HostileMob(World world, Vector2 pos) {
		super(world, pos);
		
		this.velocity = new Vector2(-1, 0);
		
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(6 / MarioBros.PPM);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.filter.categoryBits = MOB_BIT;
		fixtureDef.filter.maskBits = BLOCK_BIT | PIPE_BIT | MARIO_BIT | MOB_BIT;
		
		def.get(bodyDef).add(fixtureDef);
	}
	
	/**
	 * 
	 */
	public void init() {
		super.init();
		
		body.setActive(false);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		if (!toDestroy) {			
			velocity.y = body.getLinearVelocity().y;
			body.setLinearVelocity(velocity);
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void reverseVelocity(boolean x, boolean y) {
		if (x) {
			velocity.x = -velocity.x;
		}
		if (y) {
			velocity.y = -velocity.y;
		}
	}
}
