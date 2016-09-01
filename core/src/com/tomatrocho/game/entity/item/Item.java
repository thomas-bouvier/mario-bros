package com.tomatrocho.game.entity.item;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.world.World;

public abstract class Item extends Entity {
	
	/**
	 * 
	 */
	protected static TextureAtlas textureAtlas = new TextureAtlas("items.pack");
	
	/**
	 * 
	 */
	protected Vector2 velocity;
	
	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public Item(World world, Vector2 pos) {
		super(world, pos);
		
		this.velocity = new Vector2(0.7f, 0);
		
		this.sprite = new Sprite();
		sprite.setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
		sprite.setRegion(new TextureRegion(textureAtlas.findRegion("mushroom"), 0, 0, 18, 18));
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(pos.x, pos.y);
	
		this.bodyDef = bodyDef;
		def.put(bodyDef, new ArrayList<FixtureDef>());
		
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(6 / MarioBros.PPM);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.filter.categoryBits = ITEM_BIT;
		fixtureDef.filter.maskBits = BLOCK_BIT | PIPE_BIT | MARIO_BIT | ITEM_BIT;
		
		def.get(bodyDef).add(fixtureDef);
	}

	/**
	 * 
	 */
	public void update(float delta) {
		// updating the sprite
		sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
	
		if (!toDestroy) {
			velocity.y = body.getLinearVelocity().y;
			body.setLinearVelocity(velocity);
		}
		else {
			destroy();
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
	public abstract void use(Player player);
}
