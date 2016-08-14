package com.tomatrocho.game.world;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.world.tile.Brick;
import com.tomatrocho.game.world.tile.Coin;

public class World {

	/**
	 * 
	 */
	private TiledMap map;
	
	/**
	 * 
	 */
	private com.badlogic.gdx.physics.box2d.World physics;
	
	/**
	 * 
	 */
	private OrthogonalTiledMapRenderer mapRenderer;
	
	/**
	 * 
	 * @param tiledMap
	 */
	public World(TiledMap map) {
		this.map = map;
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
	}
	
	/**
	 * 
	 * @param gravity
	 * @param doSleep
	 */
	public void setPhysics(Vector2 gravity, boolean doSleep) {
		this.physics = new com.badlogic.gdx.physics.box2d.World(gravity, doSleep);
	}
	
	/**
	 * 
	 */
	public void initBodies(Level level) {
		BodyDef bodyDef = new BodyDef();
		PolygonShape polygon = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		Body body = null;
		
		for (int i = 2; i <= 3; i++) {
			for (MapObject obj : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {
				final Rectangle rect = ((RectangleMapObject) obj).getRectangle();
				
				bodyDef.type = BodyDef.BodyType.StaticBody;
				bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / MarioBros.PPM, (rect.getY() + rect.getHeight() / 2) / MarioBros.PPM);
				
				body = physics.createBody(bodyDef);
				
				polygon.setAsBox((rect.getWidth() / 2) / MarioBros.PPM, (rect.getHeight() / 2) / MarioBros.PPM);
				fixtureDef.shape = polygon;
				
				body.createFixture(fixtureDef);
			}
		}
		
		for (MapObject obj : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			new Coin(level, ((RectangleMapObject) obj).getRectangle());
		}
		for (MapObject obj : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			new Brick(level, ((RectangleMapObject) obj).getRectangle());
		}
	}
	
	/**
	 * 
	 * @param perSecond
	 */
	public void update() {
		physics.step(1 / 60f, 6, 2);
	}
	
	/**
	 * 
	 * @param bodyDef
	 * @return
	 */
	public Body createBody(BodyDef bodyDef) {
		return physics.createBody(bodyDef);
	}
	
	/**
	 * 
	 * @return
	 */
	public TiledMap getMap() {
		return map;
	}
	
	/**
	 * 
	 * @return
	 */
	public com.badlogic.gdx.physics.box2d.World getPhysics() {
		return physics;
	}
	
	/**
	 * 
	 * @return
	 */
	public OrthogonalTiledMapRenderer getMapRenderer() {
		return mapRenderer;
	}
	
}
