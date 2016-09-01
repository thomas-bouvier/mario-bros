package com.tomatrocho.game.entity.mob;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.world.World;

public class KoopaTroopa extends HostileMob {
	
	/**
	 * 
	 */
	private Animation runningAnimation;
	
	/**
	 * 
	 */
	private TextureRegion standingShellTextureRegion;
	
	/**
	 * 
	 */
	private Animation rollingShellAnimation;
	

	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public KoopaTroopa(World world, Vector2 pos) {
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
		
		sprite.setBounds(0, 0, 16 / MarioBros.PPM, 24 / MarioBros.PPM);
	}

	@Override
	protected void createAnimations() {
		Array<TextureRegion> frames = new Array<TextureRegion>();
		
		for (int i = 0; i <= 1; i++) {
			frames.add(new TextureRegion(textureAtlas.findRegion("koopa_troopa"), i * 16, 0, 16, 24));
		}
		runningAnimation = new Animation(0.2f, frames);
		frames.clear();
		
		standingShellTextureRegion = new TextureRegion(textureAtlas.findRegion("koopa_troopa"), 4 * 16, 0, 16, 24);
		
		for (int i = 4; i <= 5; i++) {
			frames.add(new TextureRegion(textureAtlas.findRegion("koopa_troopa"), i * 16, 0, 16, 24));
		}
		rollingShellAnimation = new Animation(0.05f, frames);
	}
	
	/**
	 * 
	 */
	public void update(float delta) {
		super.update(delta);
		
		// updating the sprite position
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - 7 / MarioBros.PPM);
		
		// updating the state
		if (currentState == State.STANDING_SHELL && stateTime > 5) {
			currentState = State.RUNNING;
			velocity.x = 1;
		}
		
		if (currentState == State.ROLLING_SHELL) {
			velocity.x = 2f;
		}
		
		stateTime = (currentState == previousState) ? stateTime + delta : 0;
		previousState = currentState;
	}

	@Override
	protected TextureRegion getFrame(float delta) {
		TextureRegion textureRegion = null;
		
		switch(currentState) {
		
		case STANDING_SHELL :
			textureRegion = standingShellTextureRegion;
			break;
		
		case ROLLING_SHELL :
			textureRegion = rollingShellAnimation.getKeyFrame(stateTime, true);
			break;
			
		case RUNNING :
		default :
			textureRegion = runningAnimation.getKeyFrame(stateTime, true);
			break;
		}
		
		if(body.getLinearVelocity().x < 0 && textureRegion.isFlipX()) {
			textureRegion.flip(true, false);
        }
        else if(body.getLinearVelocity().x > 0 && !textureRegion.isFlipX()) {
        	textureRegion.flip(true, false);
        }
		
		return textureRegion;
	}

	@Override
	public void hit(Mob mob) {
		if (currentState != State.STANDING_SHELL) {
			currentState = State.STANDING_SHELL;
			
			velocity.x = 0;
		}
		else if (currentState == State.STANDING_SHELL) {
			if (mob instanceof Player) {
				kick(((Player) mob).getBody().getPosition().x <= body.getPosition().x ? -1 : 1);
			}
		}
	}
	
	/**
	 * 
	 * @param speed
	 */
	public void kick(int speed) {
		velocity.x = speed;
		currentState = State.ROLLING_SHELL;
	}
}
