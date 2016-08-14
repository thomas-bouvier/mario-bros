package com.tomatrocho.game.world;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;

public class Level implements Disposable {
	
	/**
	 * 
	 */
	private World world;
	
	/**
	 * 
	 */
	private TextureAtlas textureAtlas;
	
	/**
	 * 
	 */
	private Box2DDebugRenderer b2dr = new Box2DDebugRenderer();;

	/**
	 * 
	 */
	private TmxMapLoader mapLoader = new TmxMapLoader();
	
	
	/**
	 * 
	 */
	public Level() {
		textureAtlas = new TextureAtlas("mobs.pack");
	}
	
	/**
	 * 
	 */
	public void generateWorld(WorldInformation wi) {
		this.world = new World(mapLoader.load(wi.getFilePath()));
		
		world.setPhysics(new Vector2(0, -10), true);
		world.initBodies(this);
	}
	
	/**
	 * 
	 * @return
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * 
	 * @return
	 */
	public TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}
	
	/**
	 * 
	 * @return
	 */
	public Box2DDebugRenderer getBox2DRenderer() {
		return b2dr;
	}
	
	@Override
	public void dispose() {
		world.getMapRenderer().dispose();
		world.getMap().dispose();
		world.getPhysics().dispose();
		
		b2dr.dispose();
	}
}
