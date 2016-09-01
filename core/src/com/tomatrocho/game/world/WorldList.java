package com.tomatrocho.game.world;

import java.util.ArrayList;
import java.util.List;

public class WorldList {

	/**
	 * 
	 */
	private static List<LevelInformation> levels = new ArrayList<>();
	
	static {
        levels.add(new LevelInformation("level-1", "level-1.tmx", 300));
    }
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static LevelInformation getLevelByName(String name) {
    	for (LevelInformation levelInformation : levels) {
    		if (levelInformation.getName().equalsIgnoreCase(name)) {
    			return levelInformation;
    		}
    	}
    	return null;
    }
}
