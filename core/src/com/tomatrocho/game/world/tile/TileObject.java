package com.tomatrocho.game.world.tile;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.world.World;

public class TileObject {
	
	/**
	 * 
	 */
	public static final float TILE_OBJECT_DEFAULT_SIZE = 16 / MarioBros.PPM;

	/**
	 * 
	 */
	protected World world;
	
	/**
	 * 
	 */
	protected BodyDef bodyDef;
	
	/**
	 * 
	 */
	protected Body body;
	
	/**
	 * 
	 */
	protected FixtureDef fixtureDef;
	
	/**
	 * 
	 */
	protected Shape shape;
	
	/**
	 * 
	 */
	protected Rectangle rectangle;
	
	
	/**
	 * 
	 * @param world
	 * @param pos
	 */
	public TileObject(World world, Vector2 pos) {
		this(world, pos, new Vector2(TILE_OBJECT_DEFAULT_SIZE, TILE_OBJECT_DEFAULT_SIZE));
	}
	
	/**
	 * 
	 * @param world
	 * @param pos
	 * @param size
	 */
	public TileObject(World world, Vector2 pos, Vector2 size) {
		this.world = world;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(pos.x, pos.y);
	
		this.bodyDef = bodyDef;
		
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(size.x / 2, size.y / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		fixtureDef.filter.categoryBits = Entity.BLOCK_BIT;
		
		this.fixtureDef = fixtureDef;
	}
	
	/**
	 * 
	 */
	public void init() {
		this.body = world.createBody(bodyDef);
		body.createFixture(fixtureDef).setUserData(this);
	}
	
	/**
	 * 
	 */
	public BodyDef getBodyDef() {
		return bodyDef;
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
	public TiledMapTileLayer.Cell getCell() {
		return world.getTile((int) (body.getPosition().x / 16 * MarioBros.PPM), (int) (body.getPosition().y / 16 * MarioBros.PPM));
	}
}
