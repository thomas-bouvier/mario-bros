package com.tomatrocho.game.entity.mob;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.Mob;
import com.tomatrocho.game.world.Level;

public class Player extends Mob {
	
	/**
	 * 
	 */
	private static final float MAX_VELOCITY = 1.8f;

	
	/**
	 * 
	 * @param world
	 */
	public Player(Level level) {
		super(level);
		
		// physics
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
		
		this.body = level.getWorld().createBody(bodyDef);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(7 / MarioBros.PPM);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		
		body.createFixture(fixtureDef);
		
		// sprite
		AtlasRegion atlasRegion = level.getTextureAtlas().findRegion("little_mario");
		TextureRegion textureRegion = new TextureRegion(atlasRegion, 0, 0, 16, 16);
		
		this.sprite = new Sprite();
		sprite.setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
		sprite.setRegion(textureRegion);
	}
	
	/**
	 * 
	 */
	public void update(float delta) {
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
	}
	
	/**
	 * 
	 */
	public void jump() {
		if (body.getLinearVelocity().x <= Player.MAX_VELOCITY) {
			body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
		}
	}
	
	/**
	 * 
	 */
	public void moveLeft() {
		if (body.getLinearVelocity().x <= Player.MAX_VELOCITY) {			
			body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);
		}
	}
	
	/**
	 * 
	 */
	public void moveRight() {
		if (body.getLinearVelocity().x <= Player.MAX_VELOCITY) {			
			body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
		}
	}
}