package com.tomatrocho.game.entity.mob;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tomatrocho.game.AssetContainer;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.world.World;

public class Goomba extends HostileMob {
	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public Goomba(World world, Vector2 pos) {
		super(world, pos);
		
		PolygonShape polygonShape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / MarioBros.PPM);
        vertice[1] = new Vector2( 5, 8).scl(1 / MarioBros.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / MarioBros.PPM);
        vertice[3] = new Vector2( 3, 3).scl(1 / MarioBros.PPM);
        polygonShape.set(vertice);

		FixtureDef fixtureDef = new FixtureDef();
		
		fixtureDef.shape = polygonShape;
		fixtureDef.restitution = 0.5f;
		fixtureDef.filter.categoryBits = MOB_HEAD_BIT;
		
		def.get(bodyDef).add(fixtureDef);
		
		currentState = previousState = State.RUNNING;
		
		sprite.setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
	}
	
	/**
	 * 
	 */
	private Animation runningAnimation;
	
	/**
	 * 
	 */
	protected void createAnimations() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		for (int i = 0; i <= 1; i++) {
			frames.add(new TextureRegion(textureAtlas.findRegion("goomba"), i * 16, 0, 16, 16));
		}
		runningAnimation = new Animation(0.4f, frames);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		// updating the sprite position
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		
		// updating the state
		stateTime += delta;
		
		if (toDestroy) {
			sprite.setRegion(new TextureRegion(textureAtlas.findRegion("goomba"), 2 * 16, 0, 16, 16));
			body.setActive(false);
			
			if (stateTime > 1) {
				destroyBody();
				destroy();
			}
		}
	}

	@Override
	protected TextureRegion getFrame(float delta) {
		return runningAnimation.getKeyFrame(stateTime, true);
	}

	@Override
	public void hit(Mob mob) {
		AssetContainer.get("audio/sounds/stomp.wav", Sound.class).play();
		
		flagToDestroy();
	}
	
	@Override
	public String toString() {
		return "I'm a Goomba!";
	}
}