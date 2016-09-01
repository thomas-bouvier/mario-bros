package com.tomatrocho.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AssetContainer {
	
	/**
	 * 
	 */
	private static AssetManager assetManager = new AssetManager();
	

	/**
	 * 
	 */
	public static void loadAllRessources() {
		assetManager.load("audio/music/mario_music.ogg", Music.class);
		
		assetManager.load("audio/sounds/coin.wav", Sound.class);
		assetManager.load("audio/sounds/breakblock.wav", Sound.class);
		assetManager.load("audio/sounds/bump.wav", Sound.class);
		assetManager.load("audio/sounds/stomp.wav", Sound.class);
		assetManager.load("audio/sounds/powerup_spawn.wav", Sound.class);
		assetManager.load("audio/sounds/powerup.wav", Sound.class);
		assetManager.load("audio/sounds/powerdown.wav", Sound.class);
		assetManager.load("audio/sounds/die.wav", Sound.class);
	
		assetManager.finishLoading();
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static <T> T get(String filePath, Class<T> type) {
		return assetManager.get(filePath, type);
	}
}
