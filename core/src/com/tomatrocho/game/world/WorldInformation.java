package com.tomatrocho.game.world;

public class WorldInformation {
	
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
	 * @param name
	 * @param filePath
	 */
	public WorldInformation(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
        System.out.println("Level info added: " + filePath);
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
}
