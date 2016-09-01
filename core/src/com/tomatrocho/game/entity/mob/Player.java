package com.tomatrocho.game.entity.mob;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.tomatrocho.game.AssetContainer;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.world.Level;
import com.tomatrocho.game.world.World;

public class Player extends Mob {
	
	/**
	 * 
	 */
	private static final float MAX_VELOCITY = 1.8f;
	
	/**
	 * 
	 */
	private BodyDef shrinkedBodyDef;
	
	/**
	 * 
	 */
	private BodyDef grownBodyDef;
	
	/**
	 * 
	 */
	private boolean willGrow = false;
	
	/**
	 * 
	 */
	private boolean growing = false;
	
	/**
	 * 
	 */
	private boolean grown = false;
	
	/**
	 * 
	 */
	private boolean willShrink = false;
	
	/**
	 * 
	 */
	private boolean runningRight = true;
	
	/**
	 * 
	 */
	private boolean dead = false;

	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public Player(World world, Vector2 pos) {
		super(world, pos);
		
		shrinkedBodyDef = bodyDef;
		
		CircleShape circleShape1 = new CircleShape();
		circleShape1.setRadius(7 / MarioBros.PPM);
		
		FixtureDef fixtureDef1 = new FixtureDef();
		fixtureDef1.shape = circleShape1;
		fixtureDef1.filter.categoryBits = MARIO_BIT;
		
		def.get(shrinkedBodyDef).add(fixtureDef1);
			
		EdgeShape edgeShape = new EdgeShape();
		edgeShape.set(new Vector2(-2 / MarioBros.PPM, 7 / MarioBros.PPM), new Vector2(2 / MarioBros.PPM, 7 / MarioBros.PPM));
		
		FixtureDef fixtureDef2 = new FixtureDef();
		fixtureDef2.shape = edgeShape;
		fixtureDef2.filter.categoryBits = MARIO_HEAD_BIT;
		fixtureDef2.isSensor = true;
		
		def.get(shrinkedBodyDef).add(fixtureDef2);

		// grown mario definition
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(pos.x, pos.y + 10 / MarioBros.PPM);
		
		def.put(bodyDef, new ArrayList<FixtureDef>());
		grownBodyDef = bodyDef;
		
		CircleShape circleShape2 = new CircleShape();
		circleShape2.setRadius(7 / MarioBros.PPM);
		
		FixtureDef fixtureDef3 = new FixtureDef();
		fixtureDef3.shape = circleShape2;
		fixtureDef3.filter.categoryBits = MARIO_BIT;
		
		def.get(grownBodyDef).add(fixtureDef3);
		
		CircleShape circleShape3 = new CircleShape();
		circleShape3.setRadius(7 / MarioBros.PPM);
		circleShape3.setPosition(circleShape2.getPosition().add(new Vector2(0, -14 / MarioBros.PPM)));
		
		FixtureDef fixtureDef4 = new FixtureDef();
		fixtureDef4.shape = circleShape3;
		fixtureDef4.filter.categoryBits = MARIO_BIT;
		
		def.get(grownBodyDef).add(fixtureDef4);
		
		FixtureDef fixtureDef5 = new FixtureDef();
		fixtureDef5.shape = edgeShape;
		fixtureDef5.filter.categoryBits = MARIO_HEAD_BIT;
		fixtureDef5.isSensor = true;
		
		def.get(grownBodyDef).add(fixtureDef5);
		
		currentState = previousState = State.STANDING;
		
		sprite.setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
	}
	
	/**
	 * 
	 */
	private TextureRegion standingTextureRegion;
	
	/**
	 * 
	 */
	private Animation runningAnimation;
	
	/**
	 * 
	 */
	private TextureRegion jumpingTextureRegion;
	
	/**
	 * 
	 */
	private Animation growingAnimation;
	
	/**
	 * 
	 */
	private TextureRegion grownMarioStandingRegion;
	
	/**
	 * 
	 */
	private Animation grownMarioRunningAnimation;
	
	/**
	 * 
	 */
	private TextureRegion grownMarioJumpingRegion;
	
	/**
	 * 
	 */
	private TextureRegion marioDeadRegion;
	
	/**
	 * 
	 */
	protected void createAnimations() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		standingTextureRegion = new TextureRegion(textureAtlas.findRegion("little_mario"), 0, 0, 16, 16);
		
		for (int i = 1; i <= 3; i++) {
			frames.add(new TextureRegion(textureAtlas.findRegion("little_mario"), i * 16, 0, 16, 16));
		}
		runningAnimation = new Animation(0.075f, frames);
		frames.clear();
		
		jumpingTextureRegion = new TextureRegion(textureAtlas.findRegion("little_mario"), 5 * 16, 0, 16, 16);
		
		for (int i = 1; i <= 3; i++) {
			frames.add(new TextureRegion(textureAtlas.findRegion("little_mario"), i * 16, 0, 16, 16));
		}
		runningAnimation = new Animation(0.075f, frames);
		frames.clear();
		
		frames.add(new TextureRegion(textureAtlas.findRegion("big_mario"), 15 * 16, 0, 16, 32));
		frames.add(new TextureRegion(textureAtlas.findRegion("big_mario"), 0, 0, 16, 32));
		frames.add(new TextureRegion(textureAtlas.findRegion("big_mario"), 15 * 16, 0, 16, 32));
		frames.add(new TextureRegion(textureAtlas.findRegion("big_mario"), 0, 0, 16, 32));
		growingAnimation = new Animation(0.2f, frames);
		frames.clear();
		
		grownMarioStandingRegion = new TextureRegion(textureAtlas.findRegion("big_mario"), 0, 0, 16, 32);
		
		for (int i = 1; i <= 3; i++) {
			frames.add(new TextureRegion(textureAtlas.findRegion("big_mario"), i * 16, 0, 16, 32));
		}
		grownMarioRunningAnimation = new Animation(0.075f, frames);
		frames.clear();
		
		grownMarioJumpingRegion = new TextureRegion(textureAtlas.findRegion("big_mario"), 5 * 16, 0, 16, 32);
		
		marioDeadRegion = new TextureRegion(textureAtlas.findRegion("little_mario"), 6 * 16, 0, 16, 16);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		
		// updating the sprite position
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
		
		// updating the state
		if (dead) {
			currentState = State.DEAD;
		}
		else if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
			currentState = State.JUMPING;
		}
		else if (body.getLinearVelocity().y < 0) {
			currentState = State.FALLING;
		}
		else if (body.getLinearVelocity().x != 0) {
			currentState = State.RUNNING;
		}
		else if (growing) {
			currentState = State.GROWING;
		}
		else {
			currentState = State.STANDING;
		}
		
		stateTime = (currentState == previousState) ? stateTime + delta : 0;
		previousState = currentState;
		
		// big mario
		if (grown) {
			sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2 - 6 / MarioBros.PPM);
		}
		
		if (willShrink) {
			defineShrinkedPlayer();
		}
		if (willGrow) {
			defineGrownPlayer();
		}
		
		// wake up entities
		for (Entity entity : world.getEntities()) {
			if (Math.abs(entity.getBody().getPosition().x - body.getPosition().x) < 2.5f) {				
				if (!entity.getBody().isActive()) {
					entity.getBody().setActive(true);
				}
			}
		}
	}
	
	@Override
	public void hit(Mob mob) {
		if (grown) {
			shrink();
		}
		else {
			dead = true;
			
			Filter filter = new Filter();
			filter.maskBits = 0;
			for (Fixture fixture : body.getFixtureList()) {
				fixture.setFilterData(filter);
			}
			
			body.applyLinearImpulse(new Vector2(0f, 4f), body.getWorldCenter(), true);
			
			Level.getMusic().stop();
			AssetContainer.get("audio/sounds/die.wav", Sound.class).play();
		}
	}
	
	/**
	 * 
	 */
	public void grow() {
		willGrow = true;
		growing = true;
		grown = true;
		
		sprite.setBounds(0, 0, 16 / MarioBros.PPM, 32 / MarioBros.PPM);
		freeze(body.getPosition());
		
		AssetContainer.get("audio/sounds/powerup.wav", Sound.class).play();
	}
	
	/**
	 * 
	 */
	private void defineGrownPlayer() {
		willGrow = false;
		
		destroyBody();
		
		grownBodyDef.position.set(body.getPosition().add(0, 14 / MarioBros.PPM));
		this.bodyDef = grownBodyDef;
		init();
	}
	
	/**
	 * 
	 */
	public void shrink() {
		willShrink = true;
		grown = false;
		
		sprite.setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
		
		AssetContainer.get("audio/sounds/powerdown.wav", Sound.class).play();
	}
	
	/**
	 * 
	 */
	private void defineShrinkedPlayer() {
		willShrink = false;
		
		destroyBody();
		
		shrinkedBodyDef.position.set(body.getPosition());
		this.bodyDef = shrinkedBodyDef;
		init();
	}
	
	@Override
	protected TextureRegion getFrame(float delta) {
		TextureRegion textureRegion = null;
		
		switch(currentState) {
		case RUNNING :
			textureRegion = grown ? grownMarioRunningAnimation.getKeyFrame(stateTime, true) : runningAnimation.getKeyFrame(stateTime, true);
			break;
			
		case JUMPING :
			textureRegion = grown ? grownMarioJumpingRegion : jumpingTextureRegion;
			break;
			
		case GROWING :
			textureRegion = growingAnimation.getKeyFrame(stateTime);
			if (growingAnimation.isAnimationFinished(stateTime)) {
				frozen = false;
				growing = false;
			}
			break;
			
		case DEAD :
			textureRegion = marioDeadRegion;
			break;
			
		case STANDING :
		case FALLING :
		default :
			textureRegion = grown ? grownMarioStandingRegion : standingTextureRegion;
			break;
		}
		
		if((body.getLinearVelocity().x < 0 || !runningRight) && !textureRegion.isFlipX()) {
			textureRegion.flip(true, false);
            runningRight = false;
        }
        else if((body.getLinearVelocity().x > 0 || runningRight) && textureRegion.isFlipX()) {
        	textureRegion.flip(true, false);
            runningRight = true;
        }
		
		return textureRegion;
	}
	
	/**
	 * 
	 */
	public void jump() {
		if (frozen) {
			return;
		}
		if (currentState == State.JUMPING || currentState == State.FALLING) {
			return;
		}
		if (body.getLinearVelocity().x <= Player.MAX_VELOCITY) {
			body.applyLinearImpulse(new Vector2(0, 4f), body.getWorldCenter(), true);
		}
	}
	
	/**
	 * 
	 */
	public void moveLeft() {
		if (frozen) {
			return;
		}
		if (body.getLinearVelocity().x >= -Player.MAX_VELOCITY) {			
			body.applyLinearImpulse(new Vector2(-0.1f, 0), body.getWorldCenter(), true);
		}
	}
	
	/**
	 * 
	 */
	public void moveRight() {
		if (frozen) {
			return;
		}
		if (body.getLinearVelocity().x <= Player.MAX_VELOCITY) {			
			body.applyLinearImpulse(new Vector2(0.1f, 0), body.getWorldCenter(), true);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean grown() {
		return grown;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean dead() {
		return dead;
	}
}