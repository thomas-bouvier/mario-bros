package com.tomatrocho.game.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.Entity;
import com.tomatrocho.game.entity.item.Item;
import com.tomatrocho.game.entity.mob.Goomba;
import com.tomatrocho.game.entity.mob.HostileMob;
import com.tomatrocho.game.entity.mob.KoopaTroopa;
import com.tomatrocho.game.entity.mob.Player;
import com.tomatrocho.game.world.tile.BrickBlock;
import com.tomatrocho.game.world.tile.Ground;
import com.tomatrocho.game.world.tile.InteractiveTileObject;
import com.tomatrocho.game.world.tile.Pipe;
import com.tomatrocho.game.world.tile.QuestionBlock;
import com.tomatrocho.game.world.tile.TileObject;

public class World implements com.badlogic.gdx.physics.box2d.ContactListener {

	/**
	 * 
	 */
	private Level level;

	/**
	 * 
	 */
	private TiledMap map;

	/**
	 * 
	 */
	private TiledMapTileSet mapTileSet;

	/**
	 * 
	 */
	private OrthogonalTiledMapRenderer mapRenderer;

	/**
	 * 
	 */
	private com.badlogic.gdx.physics.box2d.World physics;

	/**
	 * 
	 */
	private List<Entity> entities = new ArrayList<Entity>();

	/**
	 * 
	 */
	private List<Body> bodies = new ArrayList<Body>();

	/**
	 * 
	 */
	private LinkedBlockingQueue<Body> bodiesToDestroy = new LinkedBlockingQueue<Body>();

	/**
	 * 
	 */
	private LinkedBlockingQueue<Item> itemsToSpawn = new LinkedBlockingQueue<Item>();

	/**
	 * 
	 */
	private LinkedBlockingQueue<TileObject> tilesToCreate = new LinkedBlockingQueue<TileObject>();

	/**
	 * 
	 */
	private Player player;

	/**
	 * 
	 * @param level
	 * @param map
	 */
	public World(Level level, TiledMap map) {
		this.level = level;
		this.map = map;
		this.mapTileSet = map.getTileSets().getTileSet("tileset_gutter");
		this.mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);

		createPhysics();
	}

	/**
	 * 
	 * @param map
	 */
	private void createPhysics() {
		this.physics = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -10), true);
		physics.setContactListener(this);

		createBodies("ground");
		createBodies("pipe");
		createBodies("question_block");
		createBodies("brick_block");

		createBodies("player", true);
		createBodies("goomba", true);
		createBodies("koopa_troopa", true);
	}

	/**
	 * 
	 * @param layerName
	 */
	private void createBodies(String layerName) {
		createBodies(layerName, false);
	}

	/**
	 * 
	 * @param layerName
	 * @param mobs
	 * @return
	 */
	private List<Body> createBodies(String layerName, boolean mobs) {
		MapLayer layer = map.getLayers().get(layerName);
		if (layer == null) {
			return null;
		}

		MapObjects objects = layer.getObjects();
		Iterator<MapObject> objectIterator = objects.iterator();
		while (objectIterator.hasNext()) {
			MapObject object = objectIterator.next();

			Rectangle rectangle = null;
			if (object instanceof RectangleMapObject) {
				rectangle = ((RectangleMapObject) object).getRectangle();
			} else {
				continue;
			}

			Vector2 pos = new Vector2((rectangle.x + rectangle.width / 2) / MarioBros.PPM,
					(rectangle.y + rectangle.height / 2) / MarioBros.PPM);
			Vector2 size = new Vector2(rectangle.width / MarioBros.PPM, rectangle.height / MarioBros.PPM);

			if (layerName.equals("question_block")) {
				QuestionBlock tileObject = new QuestionBlock(this, pos);
				tileObject.enableMushroomSpawning(object.getProperties().containsKey("mushroom"));
				tileObject.init();
			}
			if (layerName.equals("brick_block")) {
				new BrickBlock(this, pos).init();
			}
			if (layerName.equals("ground")) {
				new Ground(this, pos, size).init();
			}
			if (layerName.equals("pipe")) {
				new Pipe(this, pos, size).init();
			}
			if (layerName.equals("player")) {
				Player player = new Player(this, pos);
				player.init();
				this.player = player;
			}
			if (layerName.equals("goomba")) {
				new Goomba(this, pos).init();
			}
			if (layerName.equals("koopa_troopa")) {
				new KoopaTroopa(this, pos).init();
			}
		}
		return bodies;
	}

	/**
	 * 
	 * @param item
	 */
	public void spawnItem(Item item) {
		if (!itemsToSpawn.contains(item)) {
			itemsToSpawn.add(item);
		}
	}

	/**
	 * 
	 * @param tileObject
	 */
	public void setTileObject(TileObject tileObject) {
		if (!tilesToCreate.contains(tileObject)) {			
			tilesToCreate.add(tileObject);
		}
	}

	/**
	 * 
	 * @param bodyDef
	 * @return
	 */
	public Body createBody(BodyDef bodyDef) {
		Body body = physics.createBody(bodyDef);
		bodies.add(body);
		return body;
	}

	/**
	 * 
	 * @param body
	 */
	public void addBodyToDestroy(Body body) {
		if (!bodiesToDestroy.contains(body)) {			
			bodiesToDestroy.add(body);
		}
	}

	/**
	 * 
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		if (!entities.contains(entity)) {			
			entities.add(entity);
		}
	}

	/**
	 * 
	 * @param entity
	 */
	public void destroyEntity(Entity entity) {
		if (entities.contains(entity)) {
			entities.remove(entity);
		}
	}

	/**
	 * 
	 */
	public void destroyPhysics() {
		for (Body body : bodies) {
			physics.destroyBody(body);
		}

		bodies.clear();
		bodiesToDestroy.clear();
	}

	/**
	 * 
	 * @param perSecond
	 */
	public void update(float delta) {
		if (!player.frozen()) {
			physics.step(1 / 60f, 6, 2);
		}

		while (!tilesToCreate.isEmpty()) {
			tilesToCreate.poll().init();
		}

		while (!itemsToSpawn.isEmpty()) {
			itemsToSpawn.poll().init();
		}

		if (!bodiesToDestroy.isEmpty()) {
			for (Body body : bodiesToDestroy) {
				if (bodies.contains(body)) {
					bodies.remove(body);
					physics.destroyBody(body);
				}
			}
			bodiesToDestroy.clear();
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			if (entity.destroyed()) {
				entities.remove(i--);
			} else {
				entity.update(delta);
			}
		}
	}

	/**
	 * 
	 * @param camera
	 * @param spriteBatch
	 */
	public void render(OrthographicCamera camera, SpriteBatch spriteBatch) {
		mapRenderer.render();

		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (Entity entity : entities) {
			entity.render(spriteBatch);
		}
		spriteBatch.end();
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param tile
	 */
	public void setTile(int x, int y, TiledMapTile tile) {
		getTile(x, y).setTile(tile);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public TiledMapTileLayer.Cell getTile(int x, int y) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
		return layer.getCell(x, y);
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		switch (fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits) {

		case Entity.MARIO_HEAD_BIT | Entity.BLOCK_BIT:
			// fixtureA contains the block
			if (fixtureA.getFilterData().categoryBits == Entity.MARIO_HEAD_BIT) {
				fixtureA = fixtureB;
			}
			((InteractiveTileObject) fixtureA.getUserData()).onHeadHit(player);
			break;

		case Entity.MARIO_BIT | Entity.MOB_BIT:
			// fixtureA contains the player
			if (fixtureA.getFilterData().categoryBits == Entity.MOB_BIT) {
				Fixture copy = fixtureA;
				fixtureA = fixtureB;
				fixtureB = copy;
			}
			((Player) fixtureA.getUserData()).hit((HostileMob) fixtureB.getUserData());
			break;

		case Entity.MARIO_BIT | Entity.MOB_HEAD_BIT:
			// fixtureA contains the mob
			if (fixtureA.getFilterData().categoryBits == Entity.MOB_BIT) {
				Fixture copy = fixtureA;
				fixtureA = fixtureB;
				fixtureB = copy;
			}
			((HostileMob) fixtureA.getUserData()).hit((Player) fixtureB.getUserData());
			break;

		case Entity.MOB_BIT | Entity.PIPE_BIT:
			// fixtureA contains the mob
			if (fixtureA.getFilterData().categoryBits == Entity.PIPE_BIT) {
				fixtureA = fixtureB;
			}
			((HostileMob) fixtureA.getUserData()).reverseVelocity(true, false);
			break;

		case Entity.MOB_BIT | Entity.MOB_BIT:
			((HostileMob) fixtureA.getUserData()).reverseVelocity(true, false);
			((HostileMob) fixtureB.getUserData()).reverseVelocity(true, false);
			break;

		case Entity.ITEM_BIT | Entity.PIPE_BIT:
			// fixtureA contains the item
			if (fixtureA.getFilterData().categoryBits == Entity.PIPE_BIT) {
				fixtureA = fixtureB;
			}
			((Item) fixtureA.getUserData()).reverseVelocity(true, false);
			break;

		case Entity.ITEM_BIT | Entity.MARIO_BIT:
			// fixtureA contains the item
			if (fixtureA.getFilterData().categoryBits == Entity.MARIO_BIT) {
				Fixture copy = fixtureA;
				fixtureA = fixtureB;
				fixtureB = copy;
			}
			((Item) fixtureA.getUserData()).use((Player) fixtureB.getUserData());
			break;
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 * @return
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * 
	 * @return
	 */
	public TiledMap getTiledMap() {
		return map;
	}

	/**
	 * 
	 * @return
	 */
	public TiledMapTileSet getTileSet() {
		return mapTileSet;
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
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * 
	 * @return
	 */
	public OrthogonalTiledMapRenderer getMapRenderer() {
		return mapRenderer;
	}

	/**
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
}
