package com.tomatrocho.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.tomatrocho.game.AssetContainer;
import com.tomatrocho.game.MarioBros;
import com.tomatrocho.game.entity.mob.Player;

public class Level implements Disposable {
	
	/**
	 * 
	 */
	private LevelInformation levelInformation;
	
	/**
	 * 
	 */
	private ArrayList<World> worlds = new ArrayList<World>();
	
	/**
	 * 
	 */
	private static Music music;
	
	/**
	 * 
	 */
	private int score = 0;
	
	/**
	 * 
	 */
	private float time;
	
	/**
	 * 
	 */
	private int countdown;
	
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
		music = AssetContainer.get("audio/music/mario_music.ogg", Music.class);
		music.setLooping(true);
//		music.play();
	}
	
	/**
	 * 
	 */
	public void generateWorld(LevelInformation levelInformation) {
		this.levelInformation = levelInformation;
		this.countdown = levelInformation.getCountdown();
		
		worlds.add(new World(this, mapLoader.load(levelInformation.getFilePath())));
	}
	
	/**
	 * 
	 * @param delta
	 */
	public void update(float delta) {
		time += delta;
		if (time >= 1) {
			countdown--;
			time = 0;
		}
		
		worlds.get(0).update(delta);
	}
	
	/**
	 * 
	 * @param value
	 */
	public void addScore(int value) {
		this.score += value;
	}
	
	/**
	 * 
	 * @param camera
	 * @param spriteBatch
	 */
	public void render(OrthographicCamera camera, SpriteBatch spriteBatch) {
		worlds.get(0).render(camera, spriteBatch);
		
		if (MarioBros.DEBUG) {
			b2dr.render(worlds.get(0).getPhysics(), camera.combined);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean gameOver() {
		final Player player = worlds.get(0).getPlayer();
		return player.dead() && player.getStateTime() > 3;
	}
	
	/**
	 * 
	 * @return
	 */
	public LevelInformation getLevelInformation() {
		return levelInformation;
	}
	
	/**
	 * 
	 * @return
	 */
	public World getWorld() {
		return worlds.get(0);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Music getMusic() {
		return music;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLevelTimer() {
		return countdown;
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
		worlds.get(0).getMapRenderer().dispose();
		worlds.get(0).getTiledMap().dispose();
		worlds.get(0).getPhysics().dispose();
		worlds.get(0).destroyPhysics();
		
		b2dr.dispose();
	}
}
