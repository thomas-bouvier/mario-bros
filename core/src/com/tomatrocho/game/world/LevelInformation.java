package com.tomatrocho.game.world;

import com.badlogic.gdx.Gdx;

public class LevelInformation {
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String filePath;
	
	/**
	 * 
	 */
	private int countdown;

	
	/**
	 * 
	 * @param name
	 * @param filePath
	 */
	public LevelInformation(String name, String filePath, int countdown) {
        this.name = name;
        this.filePath = filePath;
        this.countdown = countdown;
        
        Gdx.app.log("Added", "Level info " + filePath);
    }
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getCountdown() {
		return countdown;
	}
}
